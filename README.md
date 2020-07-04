# podcase
Backend REST server for podcasts. 

Written in Java (version 13 is used) and the Spring boot framework. 

It should support the general CRUD operations for Podcasts via a REST api. 

Podcasts episodes should be automatically updated via the Spring scheduling api which should run a lookup for the latest episodes of all listed podcasts and download them automatically.
