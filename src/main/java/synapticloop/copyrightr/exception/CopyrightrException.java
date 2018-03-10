package synapticloop.copyrightr.exception;

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

public class CopyrightrException extends Exception {
	private static final long serialVersionUID = 6189454215248101148L;

	public CopyrightrException(String message) {
		super(message);
	}

	public CopyrightrException(Throwable cause) {
		super(cause);
	}

	public CopyrightrException(String message, Throwable cause) {
		super(message, cause);
	}

	public CopyrightrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
