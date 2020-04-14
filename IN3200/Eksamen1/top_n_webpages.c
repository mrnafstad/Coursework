

void top_n_webpages(int num_webpages, int *num_involvements, int n) {

	if (n > num_webpages) {
		printf("We can't rank more webpgages (%d) than we have (%d) \n", n, num_webpages);
		return;
	}

	// Allocate memory for an array containing the indices of the top n webpages, ordered
	int *n_top_index = (int *)malloc(n * sizeof(int));
	int prev, exists;

	for (int i = 0; i < n; i ++) {
		prev = 0;
		

		for (int j = 0; j < num_webpages; j ++) {			
			if (num_involvements[j] > prev) {
				exists = -1;
				// Check if the element is already in the array of top webpages
				for (int k = 0; k < i; k ++) {
					if (j == n_top_index[k]) {
						exists = 1;
					}
				}
				if (exists < 1) {
					n_top_index[i] = j;
					prev = num_involvements[j];
				}
			}
		}

		exists = prev;
	}



	printf("\n");
	for (int i = 0; i < n; i ++) {
		printf("#%d: %d: %d \n", i+1, n_top_index[i] + 1, num_involvements[n_top_index[i]]);
	}

	free(n_top_index);
}