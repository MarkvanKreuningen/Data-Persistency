# Data-Persistency

## Sequel opdrachten
Deze zijn te vinden onder sequel opdrachten, deze kunt u achter elkaar draaien op de database.
### Let op, S7 moet op de database sales worden gedraaid. De rest kan op de database bedrijf.

## P1-P5
Deze opdrachten zijn te vinden onder src/.<br />
Ze hebben allemaal een eigen package met een eigen main.<br />
Daarnaast hebben ze allemaal een afbeelding van een test run.

* data/entity: <br />
Zoals het al zegt zijn dit de entiteiten die gebruikt worden.
* data/repository: <br />
hierin worden alle sql queries afgehandeld met een verbinding naar de dataabse.
* data/PostgresBaseDao.java: <br />
Dit is de verbinding naar de database. Mocht u een test draai willen doen, pas de DB_URL aan met uw eigen naam en wachtwoord.

## P6-P7
P6 en 7 zijn te vinden in OV-chipkaart_Hibernate.<br />
Er is gebruik gemaakt van pom.xml in combinatie met maven voor het toevoegen van postgresql, hibernate en slf4j (voor logging).<br />
In src/main/java/resources heb je de file hibernate, hierin is de informatie over hoe de klasses worden aangemaakt. Deze pakt automatisch de /java op.<br />
Dan onder /data heb je HibernateBaseDao voor het aanmaken van sessies waarin transactie worden uitgevoerd naar de database.<br />
En in de Main staat er een lange test waarin CRUD wordt getest.<br />
Ook hiervan is een afbeelding te vinden onder resources.
