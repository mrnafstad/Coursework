#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include "count_friends_of_ten.c"

void randomArray(int ***arrIn, int M, int N) {

	int i, j;
	int **arr = (int **)malloc(M * sizeof(int *));
	for (i = 0; i < M; i++) {
		arr[i] = (int *)malloc(N * sizeof(int *));
	}

	srand(time(0));
	for (i = 0; i < M; i++) {
		for (j = 0; j < N; j++) {
			arr[i][j] = rand() % 10;
		}
	}

	*arrIn = arr;
}

void showArray(int **arr, int M, int N) {
	int i, j;
	for (i = 0; i < M; i++) {
		printf("\n");
		for (j = 0; j < N; j++) {
			printf(" %d", arr[i][j]);
		}
	}
	printf("\n \n");
}


int main(int argc, char *argv[]) {
	if (argc == 3) {
		int M = atoi(argv[1]), N = atoi(argv[2]);
		int **arr;
		randomArray(&arr, M, N);
		showArray(arr, M, N);

		int num = count_friends_of_ten(M, N, arr);
		printf("Total friends: %d \n", num);
	} else if (argc == 1) {
		int M = 10, N = 10, i, j;
		int **arr;
		randomArray(&arr, M, N);
		showArray(arr, M, N);

		int num = count_friends_of_ten(M, N, arr);
		printf("Total friends: %d \n", num);
		printf("You can also enter <filename> M N at runtime for a random MxN array\n");
	} else {
		printf("Too many arguments, try  <filename> M N\n");
	}
}