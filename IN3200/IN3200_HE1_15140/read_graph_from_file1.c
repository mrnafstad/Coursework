#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void read_graph_from_file1 (char *filename, int *N, char ***table2D) {


	
	FILE *fp;
	char **table;
	fp = fopen(filename, "r");

	if (fp == NULL) {
		printf("Error! Could not open file: %s \n", filename);
	}
	char line[100];
	//Handle useless information
	fgets(line, 100, fp);
	fgets(line, 100, fp);
	//Get number of nodes and edges
	fgets(line, 100, fp);
	char *ptr2 = strtok(line, " ");
	int N1, N2;
	for (int i = 0; i < 4; i++) {
		ptr2 = strtok(NULL, " ");
		//printf("%d\n", i);
		if (i == 1) {
			N1 = atoi(ptr2);
		} else if (i == 3) {
			N2 = atoi(ptr2);
		}
	}
	*N = N1;
	//printf("%d\n", *N);
	int edges = N2;
	//printf("%d \n \n", edges);

	table = (char **)malloc(*N * sizeof(char *));
	for (int i = 0; i < *N; i ++) {
		table[i] = (char *)malloc( *N * sizeof(char));
	}

	
	//Get to the edges
	fgets(line, 100, fp);
	int node1, node2, count = 0;

	for (int i = 0; i < *N; i ++) {
		for (int j = 0; j < *N; j++)
			table[i][j] = '0';
	}

	// while (fgets(line, 100, fp) != NULL) {
	// 	node2 = atoi(strtok(line, " "));
	// 	node1 = atoi(strtok(NULL, " "));
	// 	if (!(node1 == node2)) table[node1][node2] = '1';
	// 	count ++;
	// }	

	int FromNodeId, ToNodeId;
	while (fscanf(fp, "%d %d", &FromNodeId, &ToNodeId) == 2){
	    //Disregard self-links
	    if (!(FromNodeId == ToNodeId)){
	      table[ToNodeId][FromNodeId] = '1';
	    }
	  }
	

	*table2D = table;


}

