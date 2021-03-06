#include <mpi.h>


int checkSum(int n1, int n2, int n3) {
	int num = 0;
	if ((n1 + n2 + n3) == 10) {
		num ++;
		printf("%d %d %d \n", n1, n2, n3);
	}
	return num;
}

int MPI_count_friends_of_ten(int M, int N, int** V) {
	int size, loc, rank;

	int num_friends = 0, i, j, k;
	MPI_Comm_rank (MPI_COMM_WORLD, &rank);
	
	if(rank == 0) {
		MPI_Comm_size(MPI_COMM_WORLD, &size);
		loc = M/(size+1);

		for(k = 0; k < size; k++) {
			if (k < size - 1) {

				int **locArr = (int **)malloc((loc+2) * sizeof(int *));

				for (i = 0; i < loc + 2; i++) {
					locArr[i] = V[i + loc*k];
				}
				// Send array, M
				MPI_Send(locArr, (loc+2)*N, MPI_INT, i, 0, MPI_COMM_WORLD);
				MPI_Send(loc+2, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
				MPI_Send(N, 1, MPI_INT, i, 2, MPI_COMM_WORLD);
			} else {

				int **locArr = (int **)malloc(size * sizeof(int *));

				for (i = 0; i < loc; i++) {
					locArr[i] = V[i + loc*k];
				}

				// Send array, M
				MPI_Send(locArr, loc*N, MPI_INT, i, 0, MPI_COMM_WORLD);
				MPI_Send(loc, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
				MPI_Send(N, 1, MPI_INT, i, 2, MPI_COMM_WORLD);
			}
		}
		M = loc + 2;
		V = locArr;
	}


	for (i = 0; i < M; i++) {
		for (j = 0; j < N; j++) {
			if (i == 0) {
				// check row, collumn and diagonal downards
				num_friends += checkSum(V[i][j], V[i][j + 1], V[i][j + 2]); 
				num_friends += checkSum(V[i][j], V[i + 1][j], V[i + 2][j]);
				if (j < N - 2) {
					num_friends += checkSum(V[i][j], V[i + 1][j + 1], V[i + 2][j + 2]);
				}
			} else if (i == M - 1) {
				// handle lowst row
				num_friends += checkSum(V[i][j], V[i][j + 1], V[i][j + 2]);
				num_friends += checkSum(V[i][j], V[i-1][j + 1], V[i-2][j + 2]);
			} else if (j == N - 1 && i < M - 2) {
				// handle rightmost collumn
				num_friends += checkSum(V[i][j], V[i + 1][j], V[i + 2][j]);
			} else {
				if (i < M - 2) {
					// 
					num_friends += checkSum(V[i][j], V[i + 1][j], V[i + 2][j]);
				}
				if (j < N - 2) {
					num_friends += checkSum(V[i][j], V[i][j + 1], V[i][j + 2]);
					if ( i < M - 2) {
						num_friends += checkSum(V[i][j], V[i + 1][j + 1], V[i + 2][j + 2]);
					} else if( i > 1) {
						num_friends += checkSum(V[i][j], V[i-1][j + 1], V[i-2][j + 2]);
					}
				}
			}
		}
	}
}