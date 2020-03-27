#include <stdio.h>
#include <stdlib.h>

int main(void) {

	int Nx, Ny, Nz;

	int ***A;

	Nx = Ny = Nz = 3;

	A = (int ***)malloc(Nz * sizeof(int **));
	A[0]  = (int **)malloc(Nz * Ny * sizeof(int *));
	A[0][0] = (int *)malloc(Nz * Ny * Nx * sizeof(int));

	for (int i = 1; i < Nz; i++) {
		A[i] = &A[0][Ny * 1];
	}

	for (int j = 1; j < Nz * Ny; j++) {
		A[0][j] = &A[0][0][j * Nx];
	}

	for (int i = 0; i < Nz; i++) {
		for (int j = 0; j < Ny; j++) {
			for (int k = 0; k < Nx; k++) {
				A[i][j][k] = Nx * Ny * i + Ny * j + k;
				printf("(%d %d %d): %d", i, j, k, A[i][j][k]);
			}
			printf("\n");
		}
		printf("\n");
	}

	free(A[0][0]);
	free(A[0]);
	free(A);
}