junit-webdriver
===============

Library of resources for writing tests using Selenium WebDriver in JUnit.

Features
---------------

junit-webdriver currently has the following features.

* Provides a WebDriver via a JUnit ClassRule
* Driver is built before the class and destroyed afterwards automatically
* Driver is configured using TypeSafe config library
* Configuration can be overridden at the class level by providing a configuration file under src/test/resources/{package}/{class}.conf
* Configuration can be overridden using namespacing under a profile name, which is triggered using the webtest.profile system property.
* WebDrivers provided by the class rule are thread local and thread safe, permitting parameterized and parallelized testing.
* Browsers provided can be local or remote, through sauce labs.

Roadmap
---------------

The following features are currently planned for addition to junit-webdriver.

* Optional detection and recovery from crashed browsers.
* Optional recovery from scrolling problems in partially occluded web elements.