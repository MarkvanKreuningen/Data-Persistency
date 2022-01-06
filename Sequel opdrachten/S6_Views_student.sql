-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
create or replace view deelnemers as
	select i.cursist, i.cursus, i.begindatum, u.docent, u.locatie 
	from inschrijvingen i, uitvoeringen u 
	where i.cursus = u.cursus
	and i.begindatum = u.begindatum;

-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
CREATE OR REPLACE VIEW personeel AS
	SELECT mnr, voorl, naam as medewerker, afd, functie
	FROM medewerkers;
	
CREATE OR REPLACE VIEW deelnemers_personeel as 	
	select p.mnr, p.voorl, p.medewerker, p.afd, p.functie, d.cursus, d.begindatum, d.docent, d.locatie 
	from personeel p, deelnemers d 
	where d.cursist = p.mnr;
	
-- select * from deelnemers_personeel; 				-- [Test]

-- 3. Is de view "deelnemers" updatable ? Waarom ?
-- Nee, het is een materialized view. Dit komt doordat het uit meerdere tabellen is opgesteld. 
-- Een materialized view wordt op dat moment opgehaald en de data verandert daarna niet meer.
-- Omdat het uit meerdere tabellen bestaat is het niet mogelijk om de data te updaten.

-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt. 
CREATE OR REPLACE VIEW dagcursussen as
	select c.code, c.omschrijving, c.type from cursussen c where c.lengte = 1;

update dagcursussen set omschrijving = 'Introductie PL/SQL voor beginners' where code = 'PLS';
-- select * from dagcursussen;					-- [Test]				

-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
CREATE OR REPLACE VIEW daguitvoeringen as
	select u.cursus, d.omschrijving, u.begindatum, u.docent, u.locatie, d.type 
	from uitvoeringen u, dagcursussen d
	where u.cursus = d.code;
-- select * from daguitvoeringen;					-- [Test]				


-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

-- drop view dagcursussen restrict;
-- De line hierboven werkt niet, restrict is default en betekent dat het niet dropt als een ander object er nog een connectie naar heeft.
drop view if exists dagcursussen cascade;
-- Deze line werkt wel, je krijgt een notice dat de view daguitvoeringen er nog aan hangt, hij dropt dan ook daguitvoeringen. Dus alles wat er aan hangt wordt ook gedropped.
drop view if exists personeel cascade;
drop view if exists deelnemers restrict;


