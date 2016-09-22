from matplotlib.pylab import *
from numpy import *
import sys

H0 = 70e3 				#km / s / Mpc
n = 1e6
G = 6.674e-11			#m^3 / kg / s^-2 7
rho0 = 1
lng = 200
h = lng/float(n-1)		#steplength
m = float(sys.argv[1])
c = float(sys.argv[2])


def adotovera(massfrac, constfrac, a_i):
	#print a_i
	return H0*sqrt(massfrac/float(a_i**3) + constfrac)


t = linspace(0, lng, n)
logdelta = zeros(n)
loga = zeros(n)
deltaderi = zeros(n)

loga[0] = -3
logdelta[0] = -3

ai = 10**(-3)
deltai = 10**(-3)
deltaderi = ai

deltaplus = 0
j = 0

alpha = 4*pi*G*rho0

for i in range(int(n-1)):
	b = adotovera(m, c, ai)

	deltaplus = h**2*(deltai*alpha - b*deltaderi) - h*deltaderi + deltai
	
	logdelta[i+1] = log10(abs(deltaplus))
	
	deltaderi = (10**(logdelta[i+1]) - 10**(logdelta[i]))/float(h)
	
	ai += h*ai

	loga[i+1] = log10(ai)
	j += 1
	if j == 10000:
		print i, ai
		j = 0
	
plot(loga, logdelta)
xlabel(r"$log$ a")
ylabel(r"$log $ $\delta$")
show()
