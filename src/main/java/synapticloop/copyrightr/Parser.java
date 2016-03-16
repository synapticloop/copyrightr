package synapticloop.copyrightr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileTree;

public class Parser {
	private Project project;

	private List<String> patterns; 
	private List<String> includes;
	private List<String> excludes;
	private boolean dryRun;

	private FileTree asFileTree;

	private List<Pattern> compiledPatterns = new ArrayList<Pattern>();

	private static final String THIS_YEAR = Calendar.getInstance().getDisplayName(Calendar.YEAR, Calendar.LONG, Locale.ENGLISH);


	public Parser(Project project, List<String> patterns, List<String> includes, List<String> excludes, boolean dryRun) {
		this.project = project;
		this.patterns = patterns;
		this.includes = includes;
		this.excludes = excludes;
		this.dryRun = dryRun;

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
					Matcher matcher = pattern.matcher(line);

					if(matcher.matches()) {
						fileMatch = true;
						int groupCount = matcher.groupCount();
						String group = matcher.group(groupCount);
						int regionStart = matcher.start(groupCount);
						int regionEnd = matcher.end(groupCount);

						if(THIS_YEAR.equals(group)) {

							project.getLogger().info(String.format("Not replacing line - the year is current: %s", line));
							String conversionLine = String.format("%s%s-%s%s", line.substring(0, regionStart), group, THIS_YEAR, line.substring(regionEnd));
							if(dryRun) {
								project.getLogger().warn(String.format("Dry run, not replacing %s with %s", line, conversionLine));
							}
							readLines.set(i, conversionLine);

							project.getLogger().info(String.format("Converting line from '%s' to '%s'", line, conversionLine));
						}

					}
				}
				i++;
			}

			if(fileMatch) {
				for (String line : readLines) {
					System.out.println(line);
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
