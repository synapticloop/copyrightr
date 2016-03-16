package synapticloop.copyrightr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.FileTree;

public class Parser {
	private Project project;

	private List<String> patterns; 
	private List<String> includes;
	private List<String> excludes;

	private FileTree asFileTree;

	public Parser(Project project, List<String> patterns, List<String> includes, List<String> excludes) {
		this.project = project;
		this.patterns = patterns;
		this.includes = includes;
		this.excludes = excludes;
	
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
				if(line.contains("* Copyright")) {
					System.out.println(line);
				}
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
