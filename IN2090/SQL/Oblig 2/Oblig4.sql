
/* Oppgave 1 */
CREATE TABLE Tog (
	togNr int primary key,
	startStasjon text not null,
	endeStasjon text not null,
	ankomstTid time with time zone not null)
);
CREATE TABLE TogTabell (
	togNr int references Tog(togNr),
	avgangsTid time with time zone not null,
	stasjon varchar(50) not null,
	primary key (togNr, avgangsTid)
);
CREATE TABLE Plass (
	dato date not null,
	togNr int references Tog(togNr),
	vognNr int not null,
	plassNr int not null,
	vindu boolean not null,
	ledig boolean default true,
	primary key (dato, togNr, vognNr, plassNr)
);

/* Oppgave 2


R(A,B,C,D,E,F,G)

Q:
I)   CDE -> B    
II)  AF  -> B    
III) B   -> A    
IV)  BCF -> DE
V)   D   -> G

a)

C og F forekommer ikke i noen høyresider, dermed inneholder alle kandidatnøkler CF. Vi ser at C alene ikke gir noen
attributter, mens D gir G. Siden den eneste FDen med CF i venstresiden er BCF -> DE ser vi på tillukningen 
(CFB)+ = CFB DE A G. CFB er en kandidatnøkkel, siden den gir alle attributtene i relasjonen. Siden alle utvidelser
av BCF inneholder BCF er de ikke minimale, og dermed ikke kandidatnøkler. Videre kan vi legge på F i FD I for å få
CDEF, og C i FD II for å få ACF, som begge er kandidatnøkler sidentillukningen deres også dekker hele R.

b)
Vi ser at D -> G gjør at R bryter 3NF siden G ikke er en nøkkelattributt mens D er en nøkkelattributt. 
Da er 1NF den høyeste normalformen R tilfredsstiller.


c)

Starter med FD I og får dekomponeringen R'(A,B,C,D,E,G) og R1(C,D,E,F). Så dekomponeres R' på FD III til
R2(A,B) og R''(B,C,D,E,G), og FD V til R3(D,G) og R4(B,C,D,E) 

						---	
						|	R1(C,D,E,F)
						|	R2(A,B)
R(A,B,C,D,E,F,G)	-->								
						|	R3(D,G)			
						|	R4(B,C,D,E)
						---

*/ 