

## Configuration

The plugin can be configured with the following information

```
copyrightr {
	// If set to true (default), this will only log what would have been 
	// changed, if set to false, this will over-write the files __without__
	// warning
	dryRun = false

	// This will be part of the regular expression that is searched for.
	// This helps to narrow down the lines that will be updated, useful
	// where there may be other companies that have copyright information
	copyrightHolder = "Synapticloop"

	// Which files are to be included, by default the included files are
	// - src/**/*.java, and
	// - src/**/*.groovy
	includes = [ 
	  "src/test/resources/test-files/*.txt",
	  "LICENSE.txt"
	]

	// which files are to be excluded, by default there are no exclusions
	excludes = [ "**/*.class" ]

	// whether to only replace the first found instance, by default this
	// is set to true
	onlyReplaceFirst = true

	// the year separator to use
	yearSeparator = " - "

	// Whether to fail the build on any missing copyright notifications, by
	// default, this is set to false
	failOnMissing = false

}

```

## Why set a `copyrightHolder` in the configuration?

Take an example of the following copyright notice, where multiple contributors are acknowledged:

```
Copyright (c) 2012-2013 Jane Doe
Copyright (c) 2001,2005-2013 Peter Smith
Copyright (c) 2010 John Citizen
```

With no copyright holder, the updated copyright notice would be replaced with

```
Copyright (c) 2012-2016 Jane Doe
Copyright (c) 2001,2005-2016 Peter Smith
Copyright (c) 2010 - 2016 John Citizen
```

erasing the original copyright dates for the previous contributors.

