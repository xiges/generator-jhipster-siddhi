# generator-jhipster-Siddhi



Siddhi is a java library that listens to events from data streams, detects complex conditions described via a Streaming SQL language, and triggers actions. It performs both Stream Processing and Complex Event Processing.


# Introduction

This is a [JHipster](http://jhipster.github.io/) module, that is meant to be used in a JHipster application.

# Prerequisites

This module requires Jhipster version greater than 3.0.0 in order to work.

As this is a [JHipster](http://jhipster.github.io/) module, we expect you have JHipster and its related tools already installed:

- [Installing JHipster](https://jhipster.github.io/installation.html)

# Module generation
Here is how to create a module named generator-jhipster-xigesTest
```sh
$ yarn global add generator-jhipster-module
$ mkdir generator-jhipster-xigesTest
$ cd generator-jhipster-xigesTest
$ yo jhipster-module
```
Anwser the questions to generate the module, so we can implement the changes.
# Installation

### Run the module

When the module is ready, here is how to link it locally:

```sh
$ cd generator-jhipster-xigesTest
$ yarn install
$ yarn link
```
### Usage
Here is how to run the module in an empty folder named xiges-microservice

```sh
$ mkdir xiges-microservice
$ yarn link 'generator-jhipster-xigesTest'
// '--force' overwrites existing files without prompting
$ yo jhipster-xigesTest xiges-microservice com.xigesTest --force
```
###### This will generate a brand new JHipster microservice called xiges-microservice using the package com.company.



### Development

Want to Register a module to the JHipster marketplace? Great!

To have your module available in the JHipster marketplace, you need to add it to the modules.json file by doing a Pull Request to the jhipster/jhipster.github.io project.



