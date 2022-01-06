-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S5: Subqueries
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- NB: Gebruik in elke vraag van deze opdracht een subquery.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.

-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S5.1.
-- Welke medewerkers hebben zowel de Java als de XML cursus
-- gevolgd? Geef hun personeelsnummers.
DROP VIEW IF EXISTS s5_1; CREATE OR REPLACE VIEW s5_1 AS                                                     -- [TEST]
	select m.mnr from medewerkers m, uitvoeringen u, inschrijvingen i
	where i.cursist = m.mnr
	and u.cursus = i.cursus
	and i.cursus = 'JAV'
	and i.cursist in (select i.cursist from inschrijvingen i where i.cursus = 'XML');

-- S5.2.
-- Geef de nummers van alle medewerkers die niet aan de afdeling 'OPLEIDINGEN'
-- zijn verbonden.
DROP VIEW IF EXISTS s5_2; CREATE OR REPLACE VIEW s5_2 AS                                                     -- [TEST]
	select distinct(m.mnr)
	from medewerkers m, afdelingen a
	where m.afd = a.anr 
	and a.naam in (select naam from afdelingen where naam != 'OPLEIDINGEN');

-- S5.3.
-- Geef de nummers van alle medewerkers die de Java-cursus niet hebben
-- gevolgd.
DROP VIEW IF EXISTS s5_3; CREATE OR REPLACE VIEW s5_3 AS                                                     -- [TEST]
	select mnr from medewerkers
	where mnr not in (select cursist from inschrijvingen where cursus = 'JAV');

-- S5.4.
-- a. Welke medewerkers hebben ondergeschikten? Geef hun naam.
DROP VIEW IF EXISTS s5_4a; CREATE OR REPLACE VIEW s5_4a AS                                                   -- [TEST]
	select naam from medewerkers 
	where mnr in (select chef from medewerkers);
	
-- b. En welke medewerkers hebben geen ondergeschikten? Geef wederom de naam.
DROP VIEW IF EXISTS s5_4b; CREATE OR REPLACE VIEW s5_4b AS                                                   -- [TEST]
	select naam from medewerkers 
	where mnr not in (select chef from medewerkers where chef is not null);

-- S5.5.
-- Geef cursuscode en begindatum van alle uitvoeringen van programmeercursussen
-- ('BLD') in 2020.
DROP VIEW IF EXISTS s5_5; CREATE OR REPLACE VIEW s5_5 AS                                                     -- [TEST]
	select u.cursus, u.begindatum
	from uitvoeringen u, cursussen c
	where u.begindatum between '2020-01-01'::date and '2020-12-31'::date
	and u.cursus in (select c.code from cursussen c where c.type = 'BLD');

-- S5.6.
-- Geef van alle cursusuitvoeringen: de cursuscode, de begindatum en het
-- aantal inschrijvingen (`aantal_inschrijvingen`). Sorteer op begindatum.
DROP VIEW IF EXISTS s5_6; CREATE OR REPLACE VIEW s5_6 AS                                                     -- [TEST]
	select u.cursus, u.begindatum, count(i.cursist) as aantal_inschrijvingen
	from cursussen c, uitvoeringen u, inschrijvingen i
	where u.cursus = c.code
	group by u.cursus, u.begindatum, u.locatie
	order by u.begindatum;

-- S5.7.
-- Geef voorletter(s) en achternaam van alle trainers die ooit tijdens een
-- algemene ('ALG') cursus hun eigen chef als cursist hebben gehad.
DROP VIEW IF EXISTS s5_7; CREATE OR REPLACE VIEW s5_7 AS                                                     -- [TEST]
	select m.voorl as voorletter, m.naam
	from medewerkers m, cursussen c, uitvoeringen u, inschrijvingen i
	where m.mnr = u.docent
	and m.chef = i.cursist
	and c.code = u.cursus
	and u.begindatum = i.begindatum
	and c.type = 'ALG'
	group by m.naam, m.voorl;

-- S5.8.
-- Geef de naam van de medewerkers die nog nooit een cursus hebben gegeven.
DROP VIEW IF EXISTS s5_8; CREATE OR REPLACE VIEW s5_8 AS                                                     -- [TEST]
	select m.naam
	from medewerkers m, uitvoeringen u
	where m.mnr != u.docent
	and not exists (select u.docent from uitvoeringen u where u.docent = m.mnr)
	group by m.naam;


-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_select('S5.1') AS resultaat
UNION
SELECT * FROM test_select('S5.2') AS resultaat
UNION
SELECT * FROM test_select('S5.3') AS resultaat
UNION
SELECT * FROM test_select('S5.4a') AS resultaat
UNION
SELECT * FROM test_select('S5.4b') AS resultaat
UNION
SELECT * FROM test_select('S5.5') AS resultaat
UNION
SELECT * FROM test_select('S5.6') AS resultaat
UNION
SELECT * FROM test_select('S5.7') AS resultaat
UNION
SELECT * FROM test_select('S5.8') AS resultaat
ORDER BY resultaat;
