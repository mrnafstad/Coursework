/* Oppgave 2 */

/* a */
select * from Timelistelinje where timelistenr = 3;

/* b */ 
select count(*) as antall_timelister from Timeliste;

/* c */
select count(utbetalt) as ikke_betalt from Timeliste where utbetalt is null;

/* d */
select (select count(*) as linjer_med_pauseverdi 
	from Timelistelinje where pause is not null), count(*) as timelistelinjer from Timelistelinje;

/* e */
select * from Timelistelinje where pause is null; 


/* Oppgave 3 */

/* a */
select sum(V.varighet/60) as antall_timer_ubetalt 
	from Varighet as V, Timeliste as T 
	where T.utbetalt is null and V.timelistenr = T.timelistenr;

/* b */
select * from Timeliste as T 
	where T.beskrivelse like '%test%' or T.beskrivelse like '%Test%';

/* c */
select sum(V.varighet/60)*200 as Totalt_utbetalt 
	from Varighet as V, Timeliste as T 
	where T.utbetalt is not null and V.timelistenr = T.timelistenr;
/* alternativt */
select sum(V.varighet/60)*200 as Totalt_utbetalt 
	from Varighet as V natural join timeliste as T 
	where T.utbetalt is not null;

/* Oppgave 4

a)
	I den øverste spørringen med NATURAL JOIN vil Timeliste og Timelistelinje joines på både timelistenr og beskrivelse,
	mens i nederste spørring med INNER JOIN vil Timeliste og Timelistelinje joines kun på timelistenr. Dette gir forskjellig
	count i de to spørringene.

b) 
	I disse to spørringene får vi, i motsetning til de i a), lik count siden Timeliste og Varighet kun har én felles
	kollonne i timelistenr. I dette tilfellet er NATURAL JOIN og INNER JOIN på timelistenr i praksis samme spørring.
*/