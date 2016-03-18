copyrightr updates the copyright year throughout the files

This will also log a warning if no copyright line is found in one of the included files.

## How it works

Each file within the group of files is searched upon for a copyright notice with a date.  The last year is then either replaced with the current year (where there is a data range - e.g. 2010-2014), or a date range is appended to it.

The parser looks fow lines with the following format

  - The word `Copyright` or `copyright`, followed by
  - `(c)` *or* `&copy;` *or* `&#169;` *or* `©`, followed by
  - a recognised date format (see below), followed by
  - (optionally) a copyright holder's name

### Recognised date formats

The input date format and replacements are shown below:

  - `2010, 2011-2012` -> `2010, 2012-${CURRENT_YEAR}`
  - `2010,2011-2012` -> `2010,2012-${CURRENT_YEAR}`
  - `2010,2011,2014` -> `2010,2011,2104-${CURRENT_YEAR}`
  - `2010` -> `2010-${CURRENT_YEAR}`

where `${CURRENT_YEAR}` is generated through the system clock for the current year.


The last match will form the replacement.  If the matched year is equal to the `${CURRENT_YEAR}` then no replacement would be made

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

}
```

## Warning

This will over-write the files without notice (unless `dryRun = true` is set in the configuration).
