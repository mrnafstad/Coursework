#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <mpi.h>

#include "MPI_count_friends_of_ten.c"

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

int main (int argc, char **argv) {
	int M=0, N=0, i, rank, num_triple_friends, global_friends;
	int **v=NULL;
	MPI_Init (&argc, &argv);
	MPI_Comm_rank (MPI_COMM_WORLD, &rank);
	if (rank==0) {
	// decide the values for M and N
	// allocate 2D array v and assign it with suitable values
		if (argc == 3) {
			M = atoi(argv[1]), N = atoi(argv[2]);
			randomArray(&v, M, N);
			showArray(v, M, N);
		} else if (argc == 1) {
			M = 10, N = 10;
			randomArray(&v, M, N);
			showArray(v, M, N);
			printf("You can also enter <filename> M N at runtime for a random MxN array\n");
		} else {
			printf("Too many arguments, try  <filename> M N\n");
		}
	} else if (rank != 0) {
		MPI_Recv(&M, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		printf("M = %d\n", M);
		MPI_Recv(&N, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		printf("%d: %d %d\n", rank, M, N);

		int *data = (int *)malloc(M*N*sizeof(int));
		v = (int **)malloc(M * sizeof(int *));
		for (i = 0; i < M; i++) {
			v[i] = &(data[i*N]);
		}

		MPI_Recv(v, M*N, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
		printf("%d\n", v[0][0]);
		showArray(v, M, N);
	}
	num_triple_friends = MPI_count_friends_of_ten (M, N, v);
	printf("MPI rank <%d>: number of triple friends=%d\n",
	rank, num_triple_friends);
	if (rank==0){
		free(v[0]);
	}
	MPI_Reduce(&num_triple_friends, &global_friends, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
	MPI_Finalize ();
	printf("Global friends %d\n", global_friends);
	return 0;
}
