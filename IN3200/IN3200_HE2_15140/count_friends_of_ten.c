

int checkSum(int n1, int n2, int n3) {
	int num = 0;
	if ((n1 + n2 + n3) == 10) {
		num ++;
		printf("%d %d %d \n", n1, n2, n3);
	}
	return num;
}

int count_friends_of_ten( int M, int N, int** V) {

	int num_friends = 0, i, j;

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

	return num_friends;
}