from matplotlib.pylab import *
from numpy import *

H0 = 70e3 				#km / s / Mpc
n = 1e5
G = 6.674e-11			#m^3 / kg / s^-2 7
rho0 = 1
lng = 1e2
h = lng/float(n-1)		#steplength



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

alpha = 4*pi*G*rho0

for i in range(int(n-1)):
	b = adotovera(0.3, 0.8, ai)

	deltaplus = h**2*(deltai*alpha - b*deltaderi) - h*deltaderi + deltai
	
	logdelta[i+1] = log10(abs(deltaplus))
	
	delderi = (deltaplus + deltai)/float(h)
	
	loga[i] = log10(ai)
	
	ai += h*ai
	
plot(loga, logdelta)
xlabel(r"$log$ a")
ylabel(r"$log $ $\delta$")
show()
