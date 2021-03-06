#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define CLOCKS_TO_MILLISEC(t) (t*1000)/CLOCKS_PER_SEC

void initialize(double *arr, int len) {
	for (int i = 0; i < len; i++) arr[i] = i;
}

int main(int narg, char **argv) {

	clock_t start, ascii_read_timer, ascii_write_timer,  bin_write_timer,  bin_read_timer;

	int n = 1e5;
	double *data, *fromfile;

	FILE *binfile, *asciifile;

	data = (double *)malloc(n * sizeof(double));
	initialize(data, n);

	asciifile = fopen("data.txt", "w");

	start = clock();
	for (int i = 0; i < n; i++) {
		fprintf(asciifile, "%lf\n", data[i]);
	}
	ascii_write_timer = clock() - start;   

	fclose(asciifile);

	asciifile = fopen("data.txt", "r");
	fromfile = (double *)malloc(n * sizeof(double));

  	start = clock();
    for (int i = 0; i < n; i ++) fscanf(asciifile,"%lf", &fromfile[i]);
    ascii_read_timer = clock() - start;

	fclose(asciifile);
	free(fromfile);

	binfile = fopen("data.bin", "wb");

	start = clock();
	fwrite(data, sizeof(double), n, binfile);
	bin_write_timer = clock() - start;

	fclose(binfile);

	fromfile = (double *)malloc(n * sizeof(double));
	binfile = fopen("data.bin", "rb");

	start = clock();
	fread(fromfile, sizeof(double), n, binfile);
	bin_read_timer = clock() - start;

	fclose(binfile);
	free(fromfile);

	printf("Time elapsed writing to ASCII file: %lu ms.\n", CLOCKS_TO_MILLISEC(ascii_write_timer));
    printf("Time elapsed writing to binary file: %lu ms.\n", CLOCKS_TO_MILLISEC(bin_write_timer));
    printf("Time elapsed reading from ASCII file: %lu ms.\n", CLOCKS_TO_MILLISEC(ascii_read_timer));
    printf("Time elapsed reading from binary file: %lu ms.\n", CLOCKS_TO_MILLISEC(bin_read_timer));

    // And that's it
    free(data);
}