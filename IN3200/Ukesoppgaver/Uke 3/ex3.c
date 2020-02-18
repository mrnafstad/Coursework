#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <math.h>

double pow100(double x) {
	double x4, x32, xp;
	xp = x*x;
	xp *= xp;
	x4 = xp;
	xp *= xp;
	xp *= xp;
	xp *= xp;
	x32 = xp;
	xp *= xp;

	return (x4*x32*xp);
}

int main(int argc, char const *argv[]){
	int n = 1000000;
	double random_value, *x = malloc(n * sizeof *x);
	double *temp = malloc(n * sizeof temp);

	for (size_t i = 0; i < n; i++) {
		random_value = (double)rand()/RAND_MAX*2.0-1.0;
		x[i] = random_value;
	}

	printf("pow(1.5, 100) = %e\n", pow(1.5, 100));
	printf("pow100(1.5, 100) = %e\n", pow100(1.5));

	clock_t start_pow = clock();
	for (size_t i = 0; i < n; i++) {
		temp[i] = pow(x[i], 100);
	}
	clock_t end_pow = clock();

	double tot_pow = (double)(end_pow - start_pow)/CLOCKS_PER_SEC;

	clock_t start = clock();
	for (size_t i = 0; i < n; i++) {
		temp[i] = pow100(x[i]);
	}
	clock_t end = clock();
	double tot = (double)(end - start)/CLOCKS_PER_SEC;

	printf("Time regular pow: %lfms\n", tot_pow*1000);
    printf("Time pow100:      %lfms\n", tot*1000);
    printf("Speedup: %lf\n", tot_pow/tot);

    free(x);
    free(temp);

    return 0;
}