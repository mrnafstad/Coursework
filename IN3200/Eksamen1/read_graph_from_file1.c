#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void read_graph_from_file1 (char *filename, int *N, char ***table2D) {


	
	FILE *fp;
	fp = fopen(filename, "r");

	if (fp == NULL) {
		printf("Error! Could not open file\n");
	}
	char line[100];
	//Handle useless information
	fgets(line, 100, fp);
	fgets(line, 100, fp);
	//Get number of nodes and edges
	fgets(line, 100, fp);
	char *ptr = strtok(line, " ");
	for (int i = 0; i < 2; i++) {
		ptr = strtok(NULL, " ");
	}
	*N = atoi(ptr);

	// Skjer noe feil i oppsett av table2D
	*table2D = (char **)malloc(*N * sizeof (char *) + *N * *N *sizeof(char));
	char *ptr1 = (char *)(*table2D + *N);
	for (int i = 0; i < *N; i ++) {
		table2D[i] = (ptr1 + *N + i);
	}
	// for (int i = 0; i < *N; i ++) {
	// 	*table2D[i] = (char *)malloc(*N * sizeof (char));
	// }
	
	//Get to the edges
	fgets(line, 100, fp);
	int node1, node2;

	// Må kanskje skrive om initialiseringen her så jeg tar arrayet element for element
	printf("%d \n", *N);
	for (int i = 0; i < *N; i ++) {
		printf("%d \n", i);
		for (int j = 0; j < *N; j ++) {
			table2D[i][j] = "0";
			printf("%d \n", j);
		}
	}
	while (fgets(line, 100, fp) != NULL) {
		node1 = atoi(strtok(line, " "));
		node2 = atoi(strtok(NULL, " "));
		printf("35 %d %d \n", node1, node2);
		table2D[node1][node2] = "1";
		printf("%s\n", table2D[node1][node2]);
	}




	fclose(fp);
}

int main(void) {
	int N;
	char ***table2D;
	read_graph_from_file1("webgraph1.txt", &N, table2D);
	printf("54\n");

	printf("%d", N);

	for (int i = 0; i < N; i ++) {
		for (int j = 0; j < N; j ++) {
			printf("%s ", table2D[i][j]);
		}
		printf("\n");
		free(table2D[i]);
	}
	free(table2D);

	return 0;
}