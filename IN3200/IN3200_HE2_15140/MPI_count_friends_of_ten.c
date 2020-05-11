#include <mpi.h>


int checkSum(int n1, int n2, int n3) {
	int num = 0;
	if ((n1 + n2 + n3) == 10) {
		num ++;
		//printf("%d %d %d \n", n1, n2, n3);
	}
	return num;
}

void showArray2(int **arr, int M, int N, int rank) {
	int i, j;
	printf("Rank: %d \n", rank);
	for (i = 0; i < M; i++) {
		printf("\n");
		for (j = 0; j < N; j++) {
			printf(" %d", arr[i][j]);
		}
	}
	printf("\n \n");
}

int MPI_count_friends_of_ten(int M, int N, int** V) {
	int size, loc, rank;

	int num_friends = 0, i, j, k, M2;
	MPI_Comm_rank (MPI_COMM_WORLD, &rank);
	int **V2;
	
	if(rank == 0) {
		//printf("%d\n", V[0][0] );
		MPI_Comm_size(MPI_COMM_WORLD, &size);
		loc = M/(size);
		printf("Stuf: %d %d %d\n", loc, size, M);
		if (size == 1) {
			M2 = M;
			V2 = V;
		} else {
			for(k = 0; k < size; k++) {
				if (k < size - 1) {
					M2 = loc + 2;
					int *data = (int *)malloc((loc + 2)*N*sizeof(int));
					int **locArr = (int **)malloc((loc+2) * sizeof(int *));
					for (i = 0; i < loc+2; i++) {
						locArr[i] = &(data[N*i]);
					}


					for (i = 0; i < loc + 2; i++) {
						for (j = 0; j < N; j ++) {
							//printf("d %d %d %d\n", i, i + loc*k, j);
							locArr[i][j] = V[i + loc*k][j];
						}
					}
					//showArray2(locArr, loc + 2, N, k);
					if (k == 0) {
						M2 = loc +2;
						V2 = locArr;
					} else {
						showArray2(locArr, loc+2, N, k);
						MPI_Send(&(locArr[0][0]), (loc+2)*N, MPI_INT, k, 0, MPI_COMM_WORLD);
						MPI_Send(&M2, 1, MPI_INT, k, 1, MPI_COMM_WORLD);
						MPI_Send(&N, 1, MPI_INT, k, 2, MPI_COMM_WORLD);
					}	
					
				} else {
					int *data = (int *)malloc((loc)*N*sizeof(int));
					int **locArr = (int **)malloc((loc) * sizeof(int *));
					for (i = 0; i < loc; i++) {
						locArr[i] = &(data[N*i]);
					}

					for (i = 0; i < loc; i++) {
						for (j = 0; j < N; j ++) {
							//printf("h %d %d %d\n", i, i + loc*k, j);
							locArr[i][j] = V[i + loc*k][j];
						}
					}

					showArray2(locArr, loc, N, k);
					// Send array, M
					MPI_Send(locArr, loc*N, MPI_INT, k, 0, MPI_COMM_WORLD);
					MPI_Send(&loc, 1, MPI_INT, k, 1, MPI_COMM_WORLD);
					MPI_Send(&N, 1, MPI_INT, k, 2, MPI_COMM_WORLD);
				}
			}
		}
	} else if (rank != 0) M2 = M;

	printf("dims: %d %d %d\n", M2, N, rank);
	//showArray2(V2, M2, N, rank);

	for (i = 0; i < M2; i++) {		
		for (j = 0; j < N; j++) {
			//printf("%d %d %d: ", rank, i, j);
			if (i == 0) {
				// check row, collumn and diagonal downards
				num_friends += checkSum(V2[i][j], V2[i][j + 1], V2[i][j + 2]); 
				num_friends += checkSum(V2[i][j], V2[i + 1][j], V2[i + 2][j]);
				if (j < N - 2) {
					num_friends += checkSum(V2[i][j], V2[i + 1][j + 1], V2[i + 2][j + 2]);
				}
			} else if (i == M2 - 1) {
				// handle lowst row
				num_friends += checkSum(V2[i][j], V2[i][j + 1], V2[i][j + 2]);
				num_friends += checkSum(V2[i][j], V2[i-1][j + 1], V2[i-2][j + 2]);
			} else if (j == N - 1 && i < M2 - 2) {
				// handle rightmost collumn
				num_friends += checkSum(V2[i][j], V2[i + 1][j], V2[i + 2][j]);
			} else {
				if (i < M2 - 2) {
					// 
					num_friends += checkSum(V2[i][j], V2[i + 1][j], V2[i + 2][j]);
				}
				if (j < N - 2) {
					num_friends += checkSum(V2[i][j], V2[i][j + 1], V2[i][j + 2]);
					if ( i < M2 - 2) {
						num_friends += checkSum(V2[i][j], V2[i + 1][j + 1], V2[i + 2][j + 2]);
					} else if( i > 1) {
						num_friends += checkSum(V2[i][j], V2[i-1][j + 1], V2[i-2][j + 2]);
					}
				}
			}
		}
		//printf("f\n");

	}
	return num_friends;
}