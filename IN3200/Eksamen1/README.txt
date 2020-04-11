Compilation and run (normal example):

$ gcc -o test testprogram.c
$ ./test wegraph1.txt

With OpenMP:

$ gcc -fopenmp -o testOMP testprogram.c
$ ./testOMP wegraph1.txt

The specific implementation of OpenMP allows the user to choose at compilation wether to parallelise or not.