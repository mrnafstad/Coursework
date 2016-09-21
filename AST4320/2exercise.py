from matplotlib.pylab import *
from numpy import *

H0 = 70e3 				#km / s / Mpc
n = 1e5

def adotovera(massfrac, constfrac, a_i):
	return H0*sqrt(massfrac/float(a**3) + constfrac)

t = linspace(0, 1e6, n)
logdelta = zeros(n)
loga = zeros(n)
