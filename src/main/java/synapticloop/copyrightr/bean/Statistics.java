package synapticloop.copyrightr.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * Copyright (c) 2016 - 2019 Synapticloop.
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

/**
 * This is a simple Bean that holds the statistics for all of the information
 * about the files that were searched and parsed.
 *
 */
public class Statistics {
	private int numFiles = 0; // the number of files searched
	private int numFound = 0; // the number of copyright notices found
	private int numMissing = 0; // the number of copyright notices missing
	private int numUpdated = 0; // the number of copyright notices updated
	private int numNotUpdated = 0; // the number of copyright notices not updated

	private Set<String> filesParsed = new HashSet<String>();

	private List<String> missingFiles = new ArrayList<String>();
	private List<String> updatedFiles = new ArrayList<String>();
	private List<String> notUpdatedFiles = new ArrayList<String>();

	/**
	 * increment the number of files
	 */
	public void incrementNumFiles() { numFiles++; }

	/**
	 * increment the number of files with copyright notices found
	 */
	public void incrementNumFound() { numFound++; }

	/**
	 * increment the number of files with copyright notices missing
	 * 
	 * @param filePath the path of the file 
	 */
	public void incrementNumMissing(String filePath) {
		if(!filesParsed.contains(filePath)) {
			missingFiles.add(filePath);
			filesParsed.add(filePath);
		}
		numMissing++;
	}

	/**
	 * increment the number of files that the copyright was updated
	 * 
	 * @param filePath the path of the file 
	 */
	public void incrementNumUpdated(String filePath) {
		if(!filesParsed.contains(filePath)) {
			updatedFiles.add(filePath);
			filesParsed.add(filePath);
		}
		numUpdated++;
	}

	/**
	 * increment the number of files that the copyright was not updated
	 * 
	 * @param filePath the path of the file 
	 */
	public void incrementNumNotUpdated(String filePath) {
		if(!filesParsed.contains(filePath)) {
			notUpdatedFiles.add(filePath);
			filesParsed.add(filePath);
		}
		numNotUpdated++; 
	}

	/**
	 * Get the total number of files scanned
	 * 
	 * @return the number of files scanned
	 */
	public int getNumFiles() { return this.numFiles; }

	/**
	 * Get the number of copyright notices found
	 * 
	 * @return the number of copyright notices found
	 */
	public int getNumFound() { return this.numFound; }

	/**
	 * Get the number of missing copyright notices
	 * 
	 * @return the number of missing copyright notices
	 */
	public int getNumMissing() { return this.numMissing; }

	/**
	 * Get the number of files that were updated
	 * 
	 * @return the number of files that were updated
	 */
	public int getNumUpdated() { return this.numUpdated; }

	/**
	 * Get the number of files that were not updated
	 * 
	 * @return the number of files that were not updated
	 */
	public int getNumNotUpdated() { return this.numNotUpdated; }

	/**
	 * Get the list of files that are missing the copyright notice
	 * 
	 * @return the list of files that are missing the copyright notice
	 */
	public List<String> getMissingFiles() { return missingFiles; }

	/**
	 * Get the list of files that were updated
	 * 
	 * @return the list of files that were updated
	 */
	public List<String> getUpdatedFiles() { return updatedFiles; }

	/**
	 * Get the list of files that were not updated
	 * 
	 * @return the list of files that were not updated
	 */
	public List<String> getNotUpdatedFiles() { return notUpdatedFiles; }
}
