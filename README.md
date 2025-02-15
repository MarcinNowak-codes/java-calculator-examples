# Not So Simple Java Calculator

Forked from [mikailsheikh/cogitolearning-examples](https://github.com/mikailsheikh/cogitolearning-examples)

This project is example calculator implementation with:

* Lexer - to split string expression into tokens
* Parser - to build AST from tokens sequence
* Abstract Syntax Tree - to represent string expression as tree 
* Reverse Polish Notation - to calculate expression value

## Design patterns

* Iterator
* Visitor
* Composite
* Singleton
* Records in Java 16
* Sealed Classes in Java 17
* Pattern Matching in Java 17 (preview)
* Guarded Pattern in Java 17 (preview)

## Change added

* Maven
* JUnit5
* Lombok
* Decouple AST from calculation algorithm
* Reverse Polish Notation as calculation algorithm
* Fix code check style with Sonar, CodeMR, CheckStyle-IDEA and FindBugs-IDEA
* Migration to Java 8
* Migrate to Java 17
