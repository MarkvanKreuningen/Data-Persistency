-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
-- EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
-- CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht

-- 4. Verklaar de verschillen. Schrijf deze hieronder op.
-- Met select:
-- 1.	Gather (cost=1000..6152.27 rows=1010 width=96)	1010		
-- 2.	Seq Scan on order_lines as order_lines (cost=0..5051.27 rows=421 width=96)
-- Filter: (stock_item_id = 9)
-- Met index:
-- Bitmap Heap Scan on order_lines as order_lines (cost=12.12..2305.84 rows=1010 width=96)
-- Recheck Cond: (stock_item_id = 9)
-- 1010		2.	Bitmap Index Scan using ord_lines_si_id_idx (cost=0..11.87 rows=1010 width=0)
-- Index Cond: (stock_item_id = 9)
-- De index slaat alleen op de key, hierdoor kan je heel veel sneller er door heen gaan.
-- Nadeel is wel dat elke keer dat je iets update de index ook geupdate moet worden en dus moet je het altijd overwegen wat beter is.


-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
-- 	explain (format json) select * from orders where order_id = 73590;
-- 	  B. Toon uit de order tabel de order met customer_id = 1028
-- 	explain (format json) select * from orders where customer_id = 1028;

-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht
-- Seq Scan on orders as orders (cost=0..1819.94 rows=107 width=155)
-- Filter: (customer_id = 1028)
-- Index Scan using pk_sales_orders on orders as orders (cost=0.29..8.31 rows=1 width=155)
-- Index Cond: (order_id = 73590)

-- 3. Verklaar de verschillen en schrijf deze op
-- Het zoeken van de order dmv order_id kan met de primary key worden gedaan en deze gaat nog redelijk snel maar bij het zoeken van de order via customer duurt het wel langer.
-- 4. Voeg een index toe, waarmee query B versneld kan worden
-- CREATE INDEX ord_customer_si_idx ON orders (customer_id);

-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
-- explain (format json) select * from orders where customer_id = 1028;

-- 6. Verklaar de verschillen en schrijf hieronder op
-- 1.	Bitmap Heap Scan on orders as orders (cost=5.12..308.96 rows=107 width=155)
-- Recheck Cond: (customer_id = 1028)
-- 107		2.	Bitmap Index Scan using ord_customer_si_idx (cost=0..5.1 rows=107 width=0)
-- Index Cond: (customer_id = 1028)
-- Je ziet dat de width naar 0 is gegaan waardoor het gemakkelijker vinden is.


-- S7.3.A
--
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.
-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),  
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd 
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)

select o.order_id, o.order_date, o.salesperson_person_id as verkoper, 
o.expected_delivery_date - o.order_date as levertijd, ol.quantity
from orders o, order_lines ol
where ol.order_id = o.order_id
and ol.quantity > 250
and o.order_id in (select order_id from orders where (expected_delivery_date - order_date) > 1.45)
group by o.order_id, ol.quantity
order by levertijd desc, verkoper;


-- S7.3.B
--

--  explain (format json) select o.order_id, o.order_date, o.salesperson_person_id as verkoper, 
-- o.expected_delivery_date - o.order_date as levertijd, ol.quantity
--  from orders o, order_lines ol
--  where ol.order_id = o.order_id
--  and ol.quantity > 250
--  and o.order_id in (select order_id from orders where (expected_delivery_date - order_date) > 1.45)
--  group by o.order_id, ol.quantity
-- order by levertijd desc, verkoper;

-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
-- 1.	Sort (cost=7636.45..7637.21 rows=306 width=20)	306		
-- 2.	Group (cost=7591.26..7623.81 rows=306 width=20)	306		
-- 3.	Gather Merge (cost=7591.26..7621.77 rows=256 width=20)	256		
-- 4.	Group (cost=6591.24..6592.2 rows=128 width=20)	128		
-- 5.	Sort (cost=6591.24..6591.56 rows=128 width=20)	128		
-- 6.	Nested Loop Inner Join (cost=0.58..6586.76 rows=128 width=20)
-- Join Filter: (ol.order_id = o.order_id)
-- 128		7.	Nested Loop Inner Join (cost=0.29..6522.46 rows=128 width=12)	128		
-- 8.	Seq Scan on order_lines as ol (cost=0..5051.27 rows=382 width=8)
-- Filter: (quantity > 250)
-- 382		9.	Index Scan using pk_sales_orders on orders as orders (cost=0.29..3.85 rows=1 width=4)
-- Filter: (((expected_delivery_date - order_date))::numeric > 1.45)
-- Index Cond: (order_id = ol.order_id)
-- 1		10.	Index Scan using pk_sales_orders on orders as o (cost=0.29..0.49 rows=1 width=16)
-- Index Cond: (order_id = orders.order_id)

-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
drop INDEX if exists ord_quantity_test_si_idx;
CREATE INDEX ord_quantity_test_si_idx ON order_lines (quantity);

-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht) 
-- 1.	Sort (cost=4737.4..4738.16 rows=306 width=20)	306		
-- 2.	Group (cost=4721.7..4724.76 rows=306 width=20)	306		
-- 3.	Sort (cost=4721.7..4722.47 rows=306 width=20)	306		
-- 4.	Nested Loop Inner Join (cost=2180.68..4709.07 rows=306 width=20) Join Filter: (ol.order_id = o.order_id) 306		
-- 5.	Hash Inner Join (cost=2180.39..4555.35 rows=306 width=12) Hash Cond: (orders.order_id = ol.order_id) 306		
-- 6.	Seq Scan on orders as orders (cost=0..2187.91 rows=24532 width=4) Filter: (((expected_delivery_date - order_date))::numeric > 1.45) 24532		
-- 7.	Hash (cost=2168.91..2168.91 rows=918 width=8)	918		
-- 8.	Bitmap Heap Scan on order_lines as ol (cost=11.41..2168.91 rows=918 width=8) Recheck Cond: (quantity > 250) 918		
-- 9.	Bitmap Index Scan using ord_quantity_test_si_idx (cost=0..11.18 rows=918 width=0) Index Cond: (quantity > 250) 918		
-- 10.	Index Scan using pk_sales_orders on orders as o (cost=0.29..0.49 rows=1 width=16) Index Cond: (order_id = orders.order_id)

-- 4. Wat voor verschillen zie je? Verklaar hieronder.
-- Ondanks dat het de costen wel wat omlaag heeft gehaald merk ik dat er nog wel wat bijgeschaafd kan worden.
-- Volgens mij is het best om de width naar 1 te krijgen, maar je wilt ook niet overal een index achter plakken dus het is beetje trial and error.



-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?
-- In principe zou je loops kunnen weghalen maar dan houdt je niet hetzelfde eindresultaat.
-- Maar het grouperen en sorten kost het meeste tijd dus daar is het meeste tijd te winnen.


