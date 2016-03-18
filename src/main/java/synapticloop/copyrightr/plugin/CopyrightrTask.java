package synapticloop.copyrightr.plugin;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2016 synapticloop.
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

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import synapticloop.copyrightr.Parser;
import synapticloop.copyrightr.exception.CopyrightrException;

public class CopyrightrTask extends DefaultTask {
	private static final List<String> DEFAULT_INCLUDES_LIST = new ArrayList<String>();
	private static final List<String> DEFAULT_PATTERNS = new ArrayList<String>();

	static {
		DEFAULT_INCLUDES_LIST.add("src/**/*.java");
		DEFAULT_INCLUDES_LIST.add("src/**/*.groovy");

//		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) ((\\d{4})(.+?)).*");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) .*(\\d{4})\\s*-\\s*(\\d{4}).*");
//		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) .*(\\d{4}) - (\\d{4}).*");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) .*(\\d{4}).*");

		// the most basic of patterns * Copyright (c) 2010... -> * Copyright (c) 2010-{THIS_YEAR}...
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) (\\d{4})$");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) (\\d{4}) .*");

		// to and from patterns * Copyright (c) 2010-2011...-> * Copyright (c) 2010-{THIS_YEAR}...
		//                      * Copyright (c) 2010 - 2011...-> * Copyright (c) 2010 - {THIS_YEAR}...
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) (\\d{4})-(\\d{4}) .*");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) (\\d{4}) - (\\d{4}) .*");

		// comma separated patterns * Copyright (c) 2010, 2011, 2014...-> * Copyright (c) 2010, 2011, 2014-{THIS_YEAR}...
		//                          * Copyright (c) 2010,2011,2014...-> * Copyright (c) 2010, 2011, 2014-{THIS_YEAR}...
		//                          * Copyright (c) 2010, 2011, 2014-2015...-> * Copyright (c) 2010, 2011, 2014-{THIS_YEAR}...
		//                          * Copyright (c) 2010,2011,2014-2015...-> * Copyright (c) 2010, 2011, 2014-{THIS_YEAR}...
		//                          * Copyright (c) 2010, 2011, 2014 - 2015...-> * Copyright (c) 2010, 2011, 2014 - {THIS_YEAR}...
		//                          * Copyright (c) 2010,2011,2014 - 2015...-> * Copyright (c) 2010, 2011, 2014 - {THIS_YEAR}...
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) [(\\d{4},\\s*)].*");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) ([\\d{4},\\s*])-(\\d{4}) .*");
		DEFAULT_PATTERNS.add("\\s*\\* Copyright \\(c\\) ([\\d{4},\\s*]) - (\\d{4}) .*");
	}

	@TaskAction
	public void generate() throws CopyrightrException {
		CopyrightrPluginExtension extension = getProject().getExtensions().findByType(CopyrightrPluginExtension.class);

		if (extension == null) {
			extension = new CopyrightrPluginExtension();
		}

		if(extension.getIncludes().isEmpty()) {
			extension.setIncludes(DEFAULT_INCLUDES_LIST);
		}

		if(extension.getPatterns().isEmpty()) {
			extension.setPatterns(DEFAULT_PATTERNS);
		}

		Parser parser = new Parser(getProject(), extension);
		parser.parse();
	}
}
