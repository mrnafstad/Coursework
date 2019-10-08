select * from Timelistelinje where timelistenr = 3;

select count(timelistenr) as antall_timelister from Timeliste;
/* fra over eller under tho */ 
select count(timelistenr) as antall_timelister from Timelistelinje;

/*Neste linje returnerer 0, men jeg tror det skal være 2..*/
select count(utbetalt) as ikke_betalt from Timeliste where utbetalt is null;
/*Tror ikke jeg får til null counten*/
select count(*) as timelistelinjer, count(pause) as linjer_med_pauseverdi from Timelistelinje where pause is null;