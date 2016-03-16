package synapticloop.copyrightr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;

import synapticloop.copyrightr.plugin.CopyrightrPluginExtension;

public class Parser {
	private static final String THIS_YEAR = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

	private Project project;
	private Logger logger;

	private List<String> patterns; 
	private List<String> includes;
	private List<String> excludes;
	private boolean dryRun;
	private boolean onlyReplaceFirst;

	private FileTree asFileTree;

	private List<Pattern> compiledPatterns = new ArrayList<Pattern>();

	public Parser(Project project, CopyrightrPluginExtension extension) {
		this.project = project;
		this.logger = project.getLogger();

		this.patterns = extension.getPatterns();
		this.includes = extension.getIncludes();
		this.excludes = extension.getExcludes();
		this.dryRun = extension.getDryRun();
		this.onlyReplaceFirst = extension.getOnlyReplaceFirst();

		// compile the patterns to ensure that they work
		for (String pattern : patterns) {
			compiledPatterns.add(Pattern.compile(pattern));
		}

		String absoluteProjectPath = project.getProjectDir().getAbsolutePath();

		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("dir", absoluteProjectPath);
		map.put("includes", includes);
		map.put("excludes", excludes);

		ConfigurableFileTree fileTree = project.fileTree(map);
		asFileTree = fileTree.getAsFileTree();
	}

	public void parse() {
		Set<File> files = asFileTree.getFiles();
		for (File file : files) {
			parseFile(file);
		}
	}

	private void parseFile(File file) {
		boolean fileMatch = false;
		try {
			List<String> readLines = FileUtils.readLines(file);
			int i = 0;
			for (String line : readLines) {
				for (Pattern pattern : compiledPatterns) {
					// if we have found a line in the file and we already only want to 
					// replace the first one, then break here
					if(onlyReplaceFirst && fileMatch) {
						break;
					}

					Matcher matcher = pattern.matcher(line);

					if(matcher.matches()) {
						logger.info(String.format("Found a match with pattern '%s' for line '%s'", pattern.pattern(), line));
						fileMatch = true;
						int groupCount = matcher.groupCount();
						String group = matcher.group(groupCount);
						int regionStart = matcher.start(groupCount);
						int regionEnd = matcher.end(groupCount);

						if(THIS_YEAR.equals(group)) {
							logger.info(String.format("Not replacing line - the year is current: %s", line));
						} else {
							String conversionLine = String.format("%s%s-%s%s", line.substring(0, regionStart), group, THIS_YEAR, line.substring(regionEnd));
							if(dryRun) {
								logger.warn(String.format("Dry run enabled - __NOT__ replacing '%s' with '%s'", line, conversionLine));
							} else {
								readLines.set(i, conversionLine);

								logger.info(String.format("Converting line from '%s' to '%s'", line, conversionLine));
							}
						}

						break;
					}
				}
				i++;
			}

			if(fileMatch) {
				if(!dryRun) {
					FileUtils.writeLines(file, readLines, false);
				}
			} else {
				logger.warn(String.format("Could not find copyright in file '%s'.", file.getName()));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
