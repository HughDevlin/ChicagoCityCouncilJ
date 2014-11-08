Chicago City Council
====================

A legislation data downloader for the [Chicago City Council](http://chicago.legistar.com/Calendar.aspx) website.

Basic meeting, legislation, and vote data are persisted as a [Neo4j](http://www.neo4j.org/) graph database
via [Tinkerpop](http://www.tinkerpop.com/) [Blueprints](http://github.com/tinkerpop/blueprints/wiki).

Parsing technology is the [Selenium WebDriver](http://docs.seleniumhq.org/projects/webdriver/) for automating interactive, javascript-heavy pages

To build database run Graph main with arguments:

1. -new
2. -meetings
3. -legislation
4. -legislators
5. -votes