/*psql -h dbpg-ifi-kurs -U halvona -d fdb*/

/* Oppgave 1 */
select filmcharacter, count(*) 
	from filmcharacter 
	group by filmcharacter 
	having count(*) > 2000
	order by count(*) desc;

/* Oppgave 2 
a), inner join*/
select title, prodyear
	from film inner join (select fptc.filmid
							from filmparticipation as fptc inner join person
								on (person.personid = fptc.personid)
							where lastname = 'Kubrick' and firstname = 'Stanley' and fptc.parttype = 'director') as films
	on (film.filmid = films.filmid);

/* b), natural join*/
select title, prodyear
	from film 
	natural join filmparticipation as fptc
	natural join person
	where person.lastname = 'Kubrick' and person.firstname = 'Stanley' and fptc.parttype = 'director';

/*c) implisitt join*/
select title, prodyear
	from film
	where film.filmid in (select filmparticipation.filmid 
						from filmparticipation 
						where parttype = 'director' and 
						filmparticipation.personid = (select person.personid
															from person
															where lastname = 'Kubrick' and firstname = 'Stanley'));

/* Oppgave 3*/
select p.personid, p.firstname || ' ' || p.lastname as full_name, title, country
	from filmcountry, film, person as p 
	inner join (select filmpart.personid as personid, filmpart.filmid as filmid
					from filmparticipation as filmpart
					inner join filmcharacter as filmchar
						on (filmpart.partid = filmchar.partid)
					where filmchar.filmcharacter = 'Ingrid' 
						and filmpart.parttype = 'cast') as new1
		on (p.personid = new1.personid)
	where p.firstname = 'Ingrid'
		and film.filmid = new1.filmid
		and film.filmid = filmcountry.filmid;


/* Oppgave 4 */
select f.filmid, f.title, count(g.genre)
	from film as f left outer join filmgenre as g
		on (f.filmid = g.filmid)
	where f.title like '%Antoine %'
	group by f.filmid, f.title;

/* Oppgave 5 */
select title, parttype, count(parttype)
	from film inner join filmitem
		on (film.filmid = filmitem.filmid)
	right outer join filmparticipation
		on (film.filmid = filmparticipation.filmid)
	where title like '%Lord of the Rings%'
		and filmitem.filmtype = 'C'
	group by title, parttype;

/* Oppgave 6 */

with
	prodyear_count as (
		select count(prodyear) as cnt, prodyear
		from film
		where prodyear is not null
		group by prodyear)

select film.title, film.prodyear
	from film natural join prodyear_count
	group by film.title, film.prodyear
	having count(film.prodyear) = min(prodyear_count.cnt);

/* Oppgave 7 */
with 
	genres as (
		select distinct filmid, genre 
		from filmgenre
		order by filmid)

select f.title, f.prodyear
	from film as f inner join genres as g
		on (f.filmid = g.filmid)
	where f.filmid in (select filmid
							from genres
							where genre = 'Comedy')
		and f.filmid in (select filmid
							from genres
							where genre = 'Film-Noir')
	group by f.prodyear, f.title;

/* Oppgave 8 */
with
	prodyear_count as (
		select count(prodyear) as cnt, prodyear
		from film
		where prodyear is not null
		group by prodyear),


	genres as (
		select distinct filmid, genre 
		from filmgenre
		order by filmid),


	sel_genres as (
		select f.title, f.prodyear, f.filmid
			from film as f inner join genres as g
				on (f.filmid = g.filmid)
			where f.filmid in (select filmid
									from genres
									where genre = 'Comedy')
				and f.filmid in (select filmid
									from genres
									where genre = 'Film-Noir')
			group by f.prodyear, f.title, f.filmid),

	lowest_prodyears as (
		select film.title, film.prodyear, film.filmid
			from film natural join prodyear_count
			group by film.title, film.prodyear, film.filmid
			having count(film.prodyear) = min(prodyear_count.cnt))

(select prodyear, title 
	from lowest_prodyears)
union
(select prodyear, title 
	from sel_genres)
	order by prodyear;

/* Oppgave 9 */
with 
	movies as (
		select filmid, parttype
		from filmparticipation
		inner join person
		using (personid)
		where person.firstname = 'Stanley' and person.lastname = 'Kubrick')

select f.prodyear, f.title
	from film as f
	where f.filmid in (select m.filmid
							from movies as m
							where m.parttype = 'cast')
		and f.filmid in (select m.filmid
							from movies as m
							where m.parttype = 'director');

/* Oppgave 10 */
with
	films_abovek as (
		select filmid, rank
			from filmrating 
			where votes > 1000
			group by filmid, rank)

select s.maintitle, f.rank
	from series as s, films_abovek as f 
	where f.filmid = s.seriesid 
	group by s.maintitle, f.rank
	having f.rank = (select max(rank) from films_abovek);

/* Oppgave 11 */
select country
	from filmcountry
	group by country
	having count(country) = 1;

/* Oppgave 12 */
with
	films_count as (
		select f.filmcharacter as fchar, count(*) as appearances
			from filmcharacter as f 
			group by f.filmcharacter
			having count(*) = 1),
	actor_appearances as (
		select p.firstname || ' ' || p.lastname as full_name, count(*) appearances
			from person as p inner join filmparticipation 
				using (personid)
			where filmparticipation.parttype = 'cast'
			and filmparticipation.partid in (select partid
												from  filmcharacter
												where filmcharacter in (select fchar from films_count))
			group by p.firstname, p.lastname
			having count(*) > 199
			order by p.firstname asc)
select *
	from actor_appearances;


/* Oppgave 13 */
(select p.firstname || ' ' || p.lastname as full_name, count(*)
	from person as p 
	inner join filmparticipation as f using (personid)
	inner join filmrating using (filmid)
	where f.parttype = 'director'
		and votes > 60000
	group by p.firstname, p.lastname)
intersect
((select p.firstname || ' ' || p.lastname as full_name, count(*)
	from person as p 
	inner join filmparticipation as f using (personid)
	inner join filmrating using (filmid)
	where f.parttype = 'director'
		and votes > 60000
		and rank >= 8
	group by p.firstname, p.lastname))
	order by full_name;