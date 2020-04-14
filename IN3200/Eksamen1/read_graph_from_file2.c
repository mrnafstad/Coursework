#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void read_graph_from_file2(char *filename, int *N, int *N_links, int **row_ptr, int **col_idx) {

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
		if (i == 1) {
			N1 = atoi(ptr2);
		} else if (i == 3) {
			N2 = atoi(ptr2);
		}
	}
	*N = N1;
	
	
	int *rows = (int *)malloc( (N1 + 1) * sizeof(int));


	fgets(line, 100, fp);
	int *node1, *node2, count = 0, extra = 0;

	node1 = (int *)malloc(N2 * sizeof(int));
	node2 = (int *)malloc(N2 * sizeof(int));

	// 
	// while (fgets(line, 100, fp) != NULL) {
	// 	node2[count] = atoi(strtok(line, " "));
	// 	node1[count] = atoi(strtok(NULL, " "));
	// 	printf("%d \n", count);
	// 	if (node1[count] != node2[count]) count ++;
	// }	

	int FromNodeId, ToNodeId;

	while (fscanf(fp, "%d %d", &FromNodeId, &ToNodeId) == 2){
		if ((FromNodeId <= N1) && (ToNodeId <= N1) && !(FromNodeId == ToNodeId)){
			node2[count] = FromNodeId;
			node1[count] = ToNodeId;
	      	count ++;
	    } else {
	    	extra ++;
	    }
	}
	*N_links = N2 - extra;
	int *cols = (int *)malloc((N2 - extra) * sizeof(int));
	// Sort on rows
	count = 0;
	for (int i = 0; i < N1; i ++) {
		//printf("%d \n", count);
		for (int j = 0; j < N2; j ++) {
			if (node1[j] == i) {
				cols[count] = node2[j];
				count += 1;
			}
		}
		rows[i + 1] = count;
	}



	*col_idx = cols;
	*row_ptr = rows;
}