import numpy as np
import matplotlib.pylab as mpl


#Defining various initial values

n1 = 200							#grid points during recombination
n2 = 300							#grid points after recombination
nt = n1 + n2						#total grid points

z_start_rec = 1630.4				#redshift at the start of recombination
z_end_rec = 614.2					#redshift at the end of recombination
z0 = 0.0							#redshift today

x_start_rec = -np.log(1.0 + z_start_rec)		#x at start of recombination
x_end_rec = -np.log(1.0 + z_end_rec)			#x at end of recombination
x_0 = 0.0										#x today

n_eta = 1000						#number of eta gridpoints (spline)
a_init = 1e-10						#initial value of a for eta evaluation
x_eta1 = np.log(a_init)				#start value of x for eta eval
x_eta2 = 0.0						#end value of x for eta eval
