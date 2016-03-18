package synapticloop.copyrightr.plugin;

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

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import synapticloop.copyrightr.Parser;
import synapticloop.copyrightr.exception.CopyrightrException;

public class CopyrightrTask extends DefaultTask {
	private static final List<String> DEFAULT_INCLUDES_LIST = new ArrayList<String>();

	static {
		DEFAULT_INCLUDES_LIST.add("src/**/*.java");
		DEFAULT_INCLUDES_LIST.add("src/**/*.groovy");
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

		Parser parser = new Parser(getProject(), extension);
		parser.parse();
	}
}
