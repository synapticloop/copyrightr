package synapticloop.copyrightr.plugin;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

/*
 * Copyright (c) 2016 - 2018 Synapticloop.
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

import synapticloop.copyrightr.exception.CopyrightrException;

public class CopyrightrHelpTask extends DefaultTask {

	private static final String COPYRIGHTR_SNIPPET_GRADLE = "/copyrightr.snippet.gradle";

	/**
	 * Instantiate the task, setting the group and description
	 */
	public CopyrightrHelpTask() {
		super.setGroup("Documentation");
		super.setDescription("Help for the copyrightr plugin.");
	}

	@TaskAction
	public void generate() throws CopyrightrException {
		try {
			getProject().getLogger().lifecycle(IOUtils.resourceToString(COPYRIGHTR_SNIPPET_GRADLE, Charset.defaultCharset()));
		} catch (IOException ex) {
			throw new CopyrightrException("Could not load the file '" + COPYRIGHTR_SNIPPET_GRADLE + "'.");
		}
	}
}
