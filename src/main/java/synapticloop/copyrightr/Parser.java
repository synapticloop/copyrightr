package synapticloop.copyrightr;

/*
 * Copyright (c) 2016 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

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

	private static final String KEY_DIR = "dir";
	private static final String KEY_INCLUDES = "includes";
	private static final String KEY_EXCLUDES = "excludes";

	private static final String THIS_YEAR = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

	private static final List<String> PATTERNS = new ArrayList<String>();
	static {
		PATTERNS.add("^.*[cC]opyright \\(c\\) .*(\\d{4})\\s*-\\s*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright \\(c\\) .*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright \u00A9 .*(\\d{4})\\s*-\\s*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright \u00A9 .*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright &copy; .*(\\d{4})\\s*-\\s*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright &copy; .*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright &#169; .*(\\d{4})\\s*-\\s*(\\d{4})");
		PATTERNS.add("^.*[cC]opyright &#169; .*(\\d{4})");
	}

	private Logger logger;

	private List<String> includes;
	private List<String> excludes;
	private String copyrightHolder;
	private String yearSeparator;
	private boolean dryRun;
	private boolean onlyReplaceFirst;

	private FileTree asFileTree;

	private List<Pattern> compiledPatterns = new ArrayList<Pattern>();

	public Parser(Project project, CopyrightrPluginExtension extension) {
		this.logger = project.getLogger();
		this.copyrightHolder = extension.getCopyrightHolder();

		this.includes = extension.getIncludes();
		this.excludes = extension.getExcludes();
		this.dryRun = extension.getDryRun();
		this.onlyReplaceFirst = extension.getOnlyReplaceFirst();
		this.yearSeparator = extension.getYearSeparator();

		// compile the patterns to ensure that they work
		for (String pattern : PATTERNS) {
			String patternFormat = String.format("%s%s%s%s", pattern, ".*", Pattern.quote(copyrightHolder), ".*$");
			compiledPatterns.add(Pattern.compile(patternFormat));
			logger.debug("Compiled pattern:"  + Pattern.compile(patternFormat).pattern());
		}

		String absoluteProjectPath = project.getProjectDir().getAbsolutePath();

		Map<String, Object> map  = new HashMap<String, Object>();
		map.put(KEY_DIR, absoluteProjectPath);
		map.put(KEY_INCLUDES, includes);
		map.put(KEY_EXCLUDES, excludes);

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
		logger.info(String.format("Searching for copyright notice in file '%s'", file.getPath()));
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
						logger.debug(String.format("Found a match with pattern '%s' for line '%s'", pattern.pattern(), line));

						fileMatch = true;
						int groupCount = matcher.groupCount();

						String group = matcher.group(groupCount);
						int regionStart = matcher.start(groupCount);
						int regionEnd = matcher.end(groupCount);

						boolean overwrite = false;
						switch (groupCount) {
						case 2:
							// we have a date with a '-' in it, we will replace the last group
							overwrite = true;
							break;
						case 1:
							// we have a date with no '-' in it - we are going to add one in...
						default:
							break;
						}

						readLines.set(i, getReplacementLine(line, group, regionStart, regionEnd, overwrite));
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

	private String getReplacementLine(String line, String group, int regionStart, int regionEnd, boolean overwrite) {
		if(THIS_YEAR.equals(group)) {
			logger.info(String.format("Not replacing line - the year is current: %s", line));
			return(line);
		} else {
			String conversionLine = line;
			if(overwrite) {
				// we are going to take the last date and replace it with THIS_YEAR
				conversionLine = String.format("%s%s%s", line.substring(0, regionStart), THIS_YEAR, line.substring(regionEnd));
			} else {
				conversionLine = String.format("%s%s%s%s%s", line.substring(0, regionStart), group, yearSeparator, THIS_YEAR, line.substring(regionEnd));
			}

			if(dryRun) {
				logger.warn("DRY RUN enabled, no replacements made");
				logger.warn(String.format("    before: %s", line));
				logger.warn(String.format("     after: %s", conversionLine));
				return(line);
			} else {
				logger.info(String.format("Converting line from '%s' to '%s'", line, conversionLine));
				return(conversionLine);
			}
		}
	}
}
