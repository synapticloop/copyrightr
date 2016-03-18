[![Build Status](https://travis-ci.org/synapticloop/copyrightr.svg?branch=master)](https://travis-ci.org/synapticloop/copyrightr) [![Download](https://api.bintray.com/packages/synapticloop/maven/copyrightr/images/download.svg)](https://bintray.com/synapticloop/maven/copyrightr/_latestVersion) [![GitHub Release](https://img.shields.io/github/release/synapticloop/copyrightr.svg)](https://github.com/synapticloop/copyrightr/releases) [![Gradle Plugin Release](https://img.shields.io/badge/gradle%20plugin-1.0.1-blue.svg)](https://plugins.gradle.org/plugin/synapticloop.copyrightr) 

> **This project requires JVM version of at least 1.7**




# copyrightr



> A simple plugin to update the copyright years in a selection of files



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

erasing the original copyright dates for the previouse contributors.

# Building the Package

## *NIX/Mac OS X

From the root of the project, simply run

`./gradlew build`


## Windows

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)

# Artefact Publishing - Github

This project publishes artefacts to [GitHib](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloop/copyrightr/releases](https://github.com/synapticloop/copyrightr/releases)

As such, this is not a repository, but a location to download files from.

# Artefact Publishing - Bintray

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/copyrightr/view](https://bintray.com/synapticloop/maven/copyrightr/view)

## maven setup

this comes from the jcenter bintray, to set up your repository:

```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```

## gradle setup

Repository

```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```

or just

```
repositories {
	jcenter()
}
```

# Artefact Publishing - gradle plugin portal

This project publishes artefacts to [the gradle plugin portal](https://plugins.gradle.org/)

> Note that the latest version can be found [https://plugins.gradle.org/plugin/synapticloop.copyrightr](https://plugins.gradle.org/plugin/synapticloop.copyrightr)

## Dependencies - Gradle

```
dependencies {
	runtime(group: 'synapticloop', name: 'copyrightr', version: '1.0.1', ext: 'jar')

	compile(group: 'synapticloop', name: 'copyrightr', version: '1.0.1', ext: 'jar')
}
```

or, more simply for versions of gradle greater than 2.1

```
dependencies {
	runtime 'synapticloop:copyrightr:1.0.1'

	compile 'synapticloop:copyrightr:1.0.1'
}
```

## Dependencies - Maven

```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>copyrightr</artifactId>
	<version>1.0.1</version>
	<type>jar</type>
</dependency>
```

## Dependencies - Downloads


You will also need to download the following dependencies:



### compile dependencies

  - commons-io:commons-io:2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))


### runtime dependencies

  - commons-io:commons-io:2.4: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.4/view#files/commons-io/commons-io/2.4) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.4|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)

# License

```
The MIT License (MIT)

Copyright (c) 2016 synapticloop

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```


--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--

