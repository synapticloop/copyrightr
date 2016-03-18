copyrightr updates the copyright year

this will log a warning if no copyright line is found in one of the file

## Configuration

The plugin can be configured with the following information

```
copyrightr {
	// if set to true, this will log what would have been changed, rather 
	// than over-writing the file - the default for this is 'true' so no
	// changes will be made
	dryRun = false
	
	// this will be part of the 
	copyrightHolder = "Synapticloop"

	includes = [ "src/test/resources/test-files/*.txt" ]
	dryRun = true
	onlyReplaceFirst = false
	copyrightHolder = 'company regex escape($\\[]{}*.?+\\E)'
	yearSeparator = " - "

}

```

## Warning

This will over-write the files without notice (unless `dryRun = true` is set in the configuration).
