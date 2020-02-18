/* Oppgave 4 */

create table orders (
	id int primary key,
	customer_id int not null refers to customers(id),
	shipping_addr varchar not null,
	time_entered timestamp not null,
	status varchar not null
);

/* Oppgave 5 */
create view delayed_orders(id, customer_id, time_entered) as
	select id, customer_id, time_entered
	from orders
	where status = 'delayed';

/* Oppgave 6 */
select c.name, c.address, o.id, o.status
	from customers as c
		left join orders as o
		using (c.id = o.customer_id)
	where c.name like 'Ola%'
	order by o.time_registered;

/* Oppgave 7 */
select c.id, c.name, count(*), o.status
	from customers as c
		left join orders as o
		using (c.id = o. customer_id)
	group by c.id, c.name, o.status;

/* Oppgave 8 */
select c.id, c.name
	from customers c
		inner join orders o on c.id = o.customer_id
	group by c.id, c.name
	having count(distinct shipping_addr) > 1;

/* Oppgave 9 */
with num_sold as (
	select product_id, count(order_id) as num
	from order_items
	group by product_id
)

select o.id
	from orders o
		inner join order_items oi on oi.order_id o.id
		natural join num_sold ns
	where ns.num = (select max(ns.num) from num_sold);

/* Oppgave 10 */
select oi.product_id, p.name, sum(quantity) as antall, sum(quantity*unit_price) as kostnad
	from order_items oi
		inner join orders o on oi.order_id = o.id
		inner join customers c on o.customer_id = c.id
		inner join products p on oi.product_id = p.id
	where c.country = 'USA'
	group by oi.product_id, name
	having sum(quantity) >= 10;