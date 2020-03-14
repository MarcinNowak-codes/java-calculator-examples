# Not So Simple Java Calculator

Forked from [mikailsheikh/cogitolearning-examples](https://github.com/mikailsheikh/cogitolearning-examples)

This project is example calculator implementation with:

* Lexer - to split string expresion into tokens
* Parser - to build AST from tokens sequence
* Abstract Syntax Tree - to represent string expresion as tree 
* Reverse Polish Notation - to calculate expresion value

## Design patterns

* Iterator
* Visitor
* Composite

## Change added

* Maven
* JUnit5
* Lombok
* Decouple AST from calculation algorithm
* Reverse Polish Notation as calculation algorithm
* Fix code check style with Sonar, CodeMR, CheckStyle-IDEA and FindBugs-IDEA
* Migration to Java8
