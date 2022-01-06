-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S9: Aanvullende herkansingsopdracht
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
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.
--
-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S9.1  Overstap
--
-- Jan-Jaap den Draaier is per 1 oktober 2020 manager van personeelszaken.
-- Hij komt direct onder de directeur te vallen en gaat 2100 euro per
-- maand verdienen.
-- Voer alle queries uit om deze wijziging door te voeren.
update historie set einddatum = '2020-10-01' where mnr = 7844 and afd = 30;
insert into historie values (7844, 2020, '2020-10-01', null, 40, 2100.00, '')
ON CONFLICT DO NOTHING;
update medewerkers set functie = 'MANAGER', chef = 7839, maandsal = 2100.00, comm = NULL, afd = 40 where mnr = 7844;  -- [TEST]
update afdelingen set hoofd = 7844 where anr = 40;

-- S9.2  Beginjaar
--
-- Voeg een beperkingsregel `h_beginjaar_chk` toe aan de historietabel
-- die controleert of een ingevoerde waarde in de kolom `beginjaar` een
-- correcte waarde heeft, met andere woorden: dat het om het huidige jaar
-- gaat of een jaar dat in het verleden ligt.
-- Test je beperkingsregel daarna met een INSERT die deze regel schendt.
alter table historie drop constraint if exists h_beginjaar_chk;
alter table historie 
add constraint h_beginjaar_chk
check (date_part('year', now()) >= beginjaar);

-- De volgende line gaat fout
-- insert into historie values (7369, 2022, '2022-09-01'::date, '2023-12-01'::date, 20, 2100.00, 'testest')
-- ON CONFLICT DO NOTHING;


-- S9.3  Opmerkingen
--
-- Geef uit de historietabel alle niet-lege opmerkingen bij de huidige posities
-- van medewerkers binnen het bedrijf. Geef ter referentie ook het medewerkersnummer
-- bij de resultaten.
DROP VIEW IF EXISTS s9_3; CREATE OR REPLACE VIEW s9_3 AS                                                     -- [TEST]
	select h.opmerkingen, m.mnr as medewerkersnummer
	from historie h, medewerkers m
	where h.mnr = m.mnr
	and h.einddatum is null
	and h.opmerkingen != '' or h.opmerkingen != null;

-- S9.4  Carrièrepad
--
-- Toon van alle medewerkers die nú op het hoofdkantoor werken hun historie
-- binnen het bedrijf: geef van elke positie die ze bekleed hebben de
-- de naam van de medewerker, de begindatum, de naam van hun afdeling op dat
-- moment (`afdeling`) en hun toenmalige salarisschaal (`schaal`).
-- Sorteer eerst op naam en dan op ingangsdatum.
DROP VIEW IF EXISTS s9_4; CREATE OR REPLACE VIEW s9_4 AS                                                     -- [TEST]
	select distinct(m.naam), h.begindatum, a.naam as afdeling, s.snr as schaal
	from historie h, medewerkers m, afdelingen a, schalen s
	where m.afd = 10
	and h.mnr = m.mnr
	and h.afd = a.anr
 	and h.maandsal >= s.ondergrens 
	and h.maandsal <= s.bovengrens
	order by m.naam, h.begindatum;
	

-- S9.5 Aanloop
-- se
-- Toon voor elke medewerker de naam en hoelang zij in andere functies hebben
-- gewerkt voordat zij op hun huidige positie kwamen (`tijdsduur`).
-- Rond naar beneden af op gehele jaren.
DROP VIEW IF EXISTS s9_5; CREATE OR REPLACE VIEW s9_5 AS                                                     -- [TEST]
	select distinct(m.naam), 
	(DATE_PART('year', h.begindatum) - DATE_PART('year', (select min(begindatum) from historie where mnr = h.mnr))) as tijdsduur
	from medewerkers m, historie h
	where m.afd = h.afd
	and m.mnr = h.mnr
	and h.begindatum in (select max(begindatum) from historie where mnr = h.mnr) order by tijdsduur;
	
-- Bij deze view gaan er 4 fout:
-- Molenaar 2 jaar ipv 1, mnr = 7934
-- Schotten 18 jaar ipv 17, mnr = 7788
-- Spijker 2 jaar ipv 1, mnr = 7902
-- de Waard 14 jaar ipv 13, mnr = 7521
-- Ik heb gekeken naar de mogelijke oorzaken, maar de sql query rekent het juiste uit.
-- Er is ook niet duidelijk wanneer zij precies op deze positie kwamen, er is alleen duidelijk wanneer zij op deze afdeling kwamen.



-- S9.6 Index
--
-- Maak een index `historie_afd_idx` op afdelingsnummer in de historietabel.
DROP INDEX IF EXISTS historie_afd_idx; CREATE INDEX historie_afd_idx ON historie (afd);



-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_exists('S9.1', 1) AS resultaat
UNION
SELECT 'S9.2 wordt niet getest: zelf handmatig testen.' AS resultaat
UNION
SELECT * FROM test_select('S9.3') AS resultaat
UNION
SELECT * FROM test_select('S9.4') AS resultaat
UNION
SELECT * FROM test_select('S9.5') AS resultaat
UNION
SELECT 'S9.6 wordt niet getest: geen test mogelijk.' AS resultaat
ORDER BY resultaat;
