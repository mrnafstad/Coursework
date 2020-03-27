#include <stdio.h>

#include "read_graph_from_file1.c"

int main(void) {
	int N;
	char **table2D;
	table2D = realloc(table2D, sizeof(table2D));
	read_graph_from_file1("webgraph1.txt", &N, &table2D);

	for (int i = 0; i < N; i ++) {
		for (int j = 0; j < N; j ++) {
			printf("%d ", table2D[i][j]);
		}
		printf("\n");
	}

	return 0;
}