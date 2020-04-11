#include <omp.h>

int count_mutual_links2(int N, int N_links, int *row_ptr, int *col_idx, int *num_involvements) {

	int pairs = 0, elements, pairsOnRow;

	// parallelises if -fopenmp included when compiling
	#pragma omp parallel for private(elements) reduction(+:pairs) reduction(+:num_involvements[:N])

	for (int i = 0; i < N; i++) {
		//printf("%d \n", omp_get_thread_num());
		elements = row_ptr[i + 1] - row_ptr[i];	
		pairsOnRow = elements*(elements - 1)/2;	
		pairs += pairsOnRow;
		for (int j = row_ptr[i]; j < row_ptr[i + 1]; j++) {
			num_involvements[col_idx[j]] += elements - 1;
		}
	}
	return pairs;
}