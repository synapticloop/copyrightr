package synapticloop.copyrightr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class Parser {
	private Project project;

	private List<String> patterns; 
	private List<String> includes;
	private List<String> excludes;
	private boolean dryRun;

	private FileTree asFileTree;

	private List<Pattern> compiledPatterns = new ArrayList<Pattern>();


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
		try {
			List<String> readLines = FileUtils.readLines(file);
			for (String line : readLines) {
				for (Pattern pattern : compiledPatterns) {
					Matcher matcher = pattern.matcher(line);
					if(matcher.matches()) {
						int groupCount = matcher.groupCount();
						String group = matcher.group(groupCount);
						int regionStart = matcher.regionStart();
						int regionEnd = matcher.regionEnd();
						System.out.println(String.format("%s from %d->%d", group, regionStart, regionEnd));
						
						System.out.println("Pattern match " +  matcher.matches());
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
