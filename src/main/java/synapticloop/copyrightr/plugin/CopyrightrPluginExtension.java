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

public class CopyrightrPluginExtension {
	private List<String> includes = new ArrayList<String>();
	private List<String> excludes = new ArrayList<String>();
	private boolean dryRun = true;
	private boolean onlyReplaceFirst = true;
	private String copyrightHolder = "";
	private String yearSeparator = "-";

	/**
	 * Return the list of exclusion patterns
	 * 
	 * @return the list of the exclusion patterns
	 */
	public List<String> getExcludes() {
		return excludes;
	}

	/**
	 * Set the list of exclusion patterns
	 * 
	 * @param excludes the list of exclusion patterns
	 */
	public void setExcludes(List<String> excludes) {
		this.excludes = excludes;
	}

	/**
	 * Get the list of inclusion patterns
	 * 
	 * @return the list of inclusion patterns
	 */
	public List<String> getIncludes() {
		return includes;
	}

	/**
	 * Set the list of inclusion patterns
	 * 
	 * @param includes the list of inclusion patterns
	 */
	public void setIncludes(List<String> includes) {
		this.includes = includes;
	}

	/**
	 * Return whether this operation is a dry run - i.e. does not change the 
	 * files, just logs what would have been changed (default false)
	 * 
	 * @return whether this operation is a dry run operation (default false)
	 */
	public boolean getDryRun() {
		return dryRun;
	}

	/**
	 * Set whether this operation should be a dry run - i.e. does not change the
	 * files, just logs what would have changed.
	 * 
	 * @param dryRun whether this operation is a dry run operation
	 */
	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	/**
	 * Get whether we should only replace the first found instance in a file, 
	 * (default true)
	 * 
	 * @return whether we should only replace the first found instance in a file 
	 *     (default true)
	 */
	public boolean getOnlyReplaceFirst() {
		return onlyReplaceFirst;
	}

	/**
	 * Set whether we should only replace the first found instance in a file
	 * 
	 * @param onlyReplaceFirst whether we should only replace the first found 
	 *     instance
	 */
	public void setOnlyReplaceFirst(boolean onlyReplaceFirst) {
		this.onlyReplaceFirst = onlyReplaceFirst;
	}

	/**
	 * Get the copyright holder (defaults to an empty string "")
	 * 
	 * @return the copyright holder (defaults to an empty string "")
	 */
	public String getCopyrightHolder() {
		return copyrightHolder;
	}

	/**
	 * Set the copyright holder - which will be appended to the regex string to
	 * give greater control over the regex to only look for the copyright line
	 * that ends with the name of the copyright holder.
	 * 
	 * @param copyrightHolder the copyright holder to set
	 */
	public void setCopyrightHolder(String copyrightHolder) {
		this.copyrightHolder = copyrightHolder;
	}

	/**
	 * Get the separator for year ranges (default ' - '), e.g. 2010 -2012
	 * 
	 * @return the separator for year ranges
	 */
	public String getYearSeparator() {
		return yearSeparator;
	}

	/**
	 * Set the separator for year ranges (default ' - '), e.g. 2010 -2012
	 * 
	 * @param yearSeparator the separator to place between years
	 */
	public void setYearSeperator(String yearSeparator) {
		this.yearSeparator = yearSeparator;
	}
}
