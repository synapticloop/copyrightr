
This plugin will either update the copyright years in to included filesets, or will log information to the console as a preview to the changes.

## Warning

This will over-write the files without notice (unless `dryRun = true` is set in the configuration).

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
