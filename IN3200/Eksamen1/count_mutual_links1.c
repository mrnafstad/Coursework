#include <omp.h>

int count_mutual_links1(int N, char **table2D, int *num_involvements) {

	printf("\n");
	int elements, pairs = 0;

	
	// parallelises if -fopenmp include when compiling
	#pragma omp parallel for private(elements) reduction(+:pairs) reduction(+:num_involvements[:N])
	
	for (int i = 0; i < N; i++) {
		elements = 0;
		//printf("%d \n", omp_get_thread_num());
		for ( int j = 0; j < N; j ++) {
			if (table2D[i][j] == '1' && j != i) {
				elements ++;
			}
		}

		// Count pairs
		pairs += elements*(elements - 1)/2;


		if (elements > 0) { 
			for (int j = 0; j < N; j++) {
				if (table2D[i][j] == '1') {
					num_involvements[j] += (elements - 1);
				}
			}
		}
	}	

	

	printf("\n");
	return pairs;
}