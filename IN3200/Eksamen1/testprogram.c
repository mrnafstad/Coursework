#include <stdio.h>
#include <time.h>

#include "read_graph_from_file1.c"
#include "count_mutual_links1.c"

#include "read_graph_from_file2.c"
#include "count_mutual_links2.c"

#include "top_n_webpages.c"

#define CLOCKS_TO_MILLISEC(t) (t*1000)/CLOCKS_PER_SEC

void showMatrix(int N, char **table) {
	printf("     0  1  2  3  4  5  6  7\n \n \n");
	for (int i = 0; i < N; i ++) {
		printf("%d   ", i);
		for (int j = 0; j < N; j ++) {
			//printf("%d %d \n", i, j);
			printf(" %c ", table[i][j]);
		}
		printf("\n");
	}
}

void showCSR(int N, int N_links, int *rows, int *cols) {

		printf("\n row       column \n");

		for (int i = 0; i < N; i ++) {
			if (rows[i] < 10) {
				printf("%d:         ", i);
			} else {
				printf("%d:        ", i);
			}
			for (int j = rows[i]; j < rows [i+1]; j ++) {
				printf("%d ", cols[j]);
			}
			printf("\n");
		}

		printf("\n");

}

int main(int argc, char *argv[]) {

	
	if (argc == 2) {
		// char filename[20];
		// filename = argv[2];	
		clock_t start, start_prog, end, end_prog, total;
		start_prog = clock();
		int N;
		// char **table2D;
		// start = clock();
		// read_graph_from_file1(argv[1], &N, &table2D);
		// end = clock();
		// total = (double) (end - start) / CLOCKS_PER_SEC;
		// printf("Read to 2D array %lu ms\n", CLOCKS_TO_MILLISEC(total));
		// //showMatrix(N, table2D);
		// int *num_involvements = (int *)malloc(N * sizeof(int));
		// start = clock();
		// int pairs = count_mutual_links1(N, table2D, num_involvements);	
		// end = clock();
		// total = (double) (end - start) / CLOCKS_PER_SEC;
		// printf("Count pairs %lu ms\n", CLOCKS_TO_MILLISEC(total));	
		// // printf("%d \n", pairs);
		// // for (int i = 0; i < N; i ++) {
		// // 	printf("%d: %d \n", i+1, num_involvements[i]);
		// // }
		// start = clock();
		// top_n_webpages(N, num_involvements, 3);
		// end = clock();
		// total = (double) (end - start) / CLOCKS_PER_SEC;
		// printf("Find top webpages: %lu ms \n", CLOCKS_TO_MILLISEC(total));







		printf("\n");
		int *rows, *cols;

		int *num_involvements2 = (int *)malloc(N * sizeof(int));
		int N_links;
		start = clock();
		read_graph_from_file2(argv[1], &N, &N_links, &rows, &cols);
		end = clock();
		total = (double) (end - start) / CLOCKS_PER_SEC;
		printf("Read to CRS %ld ms\n", CLOCKS_TO_MILLISEC(total));
		//showCSR(N, N_links, rows, cols);
		start = clock();
		int pairs = count_mutual_links2(N, N_links, rows, cols, num_involvements2);
		end = clock();
		total = (double) (end - start) / CLOCKS_PER_SEC;
		printf("Count pairs (CRS) %lu ms\n", CLOCKS_TO_MILLISEC(total));
		// printf("%d \n", pairs);
		// for (int i = 0; i < N; i ++) {
		// 	printf("%d: %d \n", i+1, num_involvements2[i]);
		// }
		start = clock();
		top_n_webpages(N, num_involvements2, 4);
		end = clock();
		total = (double) (end - start) / CLOCKS_PER_SEC;
		printf("Find top CRS %ld ms\n", CLOCKS_TO_MILLISEC(total));

		//free(num_involvements);
		free(num_involvements2);
		//free(table2D);
		//free(rows);
		//free(cols);

		end_prog = clock();
		total = (double)(end_prog - start_prog)/CLOCKS_PER_SEC;
		printf("Total time %ld ms \n", CLOCKS_TO_MILLISEC(total));

		return 0;
	} else if (argc > 2) {
		printf("Can only handle one file \n");
	} else {
		printf("Need a filename \n");
	}

	return -1;
}