
/*  Oppgave 1  */
select distinct mail 
	from sp.spiller 
	inner join sp.match 
	on (sp.spiller.brukernavn = sp.match.spiller2) 
	where spiller1 = 'dr1337';

/*  Oppgave 2  
a
*/
select sid, count(*)
	from sp.spill 
		inner join sp.match
		using (sid)
	group by sid;
/*
b
*/
select s.sid, count(m.mid)
	from sp.spill as s
		left outer join sp.match as m
		using (sid)
	group by s.sid;


/*  Oppgave 3  */
(select sid, spiller1 as spiller
	from sp.match
	where spiller1_poeng > spiller2_poeng
)
union all
(select sid, spiller2 as spiller
	from sp.match
	where spiller2_poeng > spiller1_poeng
);

/*  Oppgave 4 */
with 
	vinnere as (
		(select sid, spiller1 as spiller
			from sp.match
			where spiller1_poeng > spiller2_poeng
		)
		union all
		(select sid, spiller2 as spiller
			from sp.match
			where spiller2_poeng > spiller1_poeng
		)
	)

select v.spiller, sum(s.vinner_poeng)
	from vinnere as v 
		inner join sp.spill as s
		using (sid)
	group by v.spiller;

/*  Oppgave 5  */
with 
	vinnere as (
		(select sid, spiller1 as spiller
			from sp.match
			where spiller1_poeng > spiller2_poeng
		)
		union all
		(select sid, spiller2 as spiller
			from sp.match
			where spiller2_poeng > spiller1_poeng
		)
	)

select spiller, s.navn, count(*) as antall
	from vinnere
		inner join sp.spill as s
		using (sid)
	group by spiller, s.sid, s.navn;

/*  Oppgave 6  */
update sp.spill
	set vinner_poeng = vinner_poeng - 1
	where vinner_poeng >= 5;
