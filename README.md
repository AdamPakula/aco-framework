ACO Framework
=========

Licensed under [Apache License, Version 2.0][].

Description
-----------

Ant Colony Optimization (ACO) studies artificial systems that take inspiration from the behavior of real ant colonies and which are used to solve discrete optimization problems. In 1999, the Ant Colony Optimization metaheuristic was defined by Dorigo, Di Caro and Gambardella.

Algorithms
-----------
This framework has implemented the follows algorithms:

- Ant System
- Ant Colony System

Solved Problems
-----------

- Travelling Salesman Problem
- Next Release Problem
- Quadratic Assignment Problem

How To Use
----

This is a example for Travelling Salesman Problem

```sh
int numberOfAnts = 10;
int numberOfInterations = 100;

Problem p = new TravellingSalesmanProblem("in/oliver30.tsp", true);
ACO aco = new AntSystem(p, ants, interations);

ExecutionStats es = ExecutionStats.execute(aco, p);
es.printStats();
```

or for the Next Release Problem

```sh
int numberOfAnts = 10;
int numberOfInterations = 100;

Problem p = new NextReleaseProblem("in/delsagrado20.nrp");
ACO aco = new AntSystem(p, ants, interations);

ExecutionStats es = ExecutionStats.execute(aco, p);
es.printStats();
```

Extras
----
- Export the matrix of pheromone to Dot File ([http://www.graphviz.org])
- Support the TSPLIB instance. 

Version
----

1.0


Acknowledgement
----

#####GOES - Opitimization in Software Engineering Group

[http://www.goes.uece.br]

[Apache License, Version 2.0]:  http://www.apache.org/licenses/LICENSE-2.0
[http://www.goes.uece.br]:  http://www.goes.uece.br
[http://www.graphviz.org]: http://www.graphviz.org
