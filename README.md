# [![UGRM Usergroups RheinMain](https://raw.github.com/tacker/ugrm-data/master/logo.png)](http://ugrm.coderbyheart.de/)

In diesem Repository finden sich mittels XML strukturierte Information zu Technologie-Usergroups im RheinMain-Gebiet. 
Eine Technologie-Usergroup hat im weitesten Sinne etwas mit dem Internet, Software oder Hardware zu tun.
Als Richtwert für die Definition des Rhein-Main-Gebietes kann man die Entfernung des Frankfurter Hauptbahnhofes 
nehmen, dieser sollte vom Treffpunkt einer Usergroup mit dem ÖPNV in weniger als einer Stunde zu erreichen sein.

## Aufbau der Daten

Die Informationen zu den Usergroups liegen im XML-Format vor. 
Das Format ist in [diesem Schema](https://github.com/tacker/ugrm-data/blob/master/xsd/usergroup.xsd) definiert.  
Zusätzlich werden Bilder der Gruppe und deren Logo als Bilder hinterlegt.

Jeder Usergroup legt ihre Daten als ``<handle>.xml`` im Ordner ``usergroup`` ab.
Das Logo der Usergroup wird als ``<handle>.logo.<format>``, ein Gruppenbild als ``<handle>.group.<format>`` abgelegt.

``<handle>`` ist ein frei wählbarer Teil des Dateinamens. Er sollte im besten Fall dem Kürzel der Usergroup
entsprechen. Unterstützte Bildformate für ``<format>`` sind PNG, JPEG und GIF.

### Beispiel

Ein ausführlicher Datensatz, der als Beispiel verwendet werden kann, ist der [Eintrag der PyUGRM](https://github.com/tacker/ugrm-data/blob/master/usergroup/pyugrm.xml).
Das Logo findet sich unter [pyugrm.logo.png](https://github.com/tacker/ugrm-data/blob/master/usergroup/pyugrm.logo.png).
Das Gruppenfoto findet sich unter [pyugrm.group.jpg](https://github.com/tacker/ugrm-data/blob/master/usergroup/pyugrm.group.jpgl).

## Usergroup eintragen oder Eintrag aktualisieren

Die Daten werden durch PullRequests (PR) gepflegt. Eine Anleitung, wie das schnell und unkomplizierte 
funktioniert findet sich hier: [Using Pull Requests](https://help.github.com/articles/using-pull-requests). 
Es sollte dabei immer ein eigener Branch für die Änderungen angelegt werden, 
dies erleichtert die Zusammenarbeit zu den Änderungen **erheblich**. 

Also:
 * Repository forken
 * Branch anlegen
 * Änderungen vornehmen
 * Pull Request erstellen

## Tests

[![Build Status](https://travis-ci.org/tacker/ugrm-data.png?branch=master)](https://travis-ci.org/tacker/ugrm-data)

Die XML-Daten werden  mithilfe eines ant build-Scripts getestet. 
Hierzu einfach ``$ ant test`` ausführen. 
Pull Requests, bei denen Tests fehl schlagen werden nicht gemerged.

## Darstellung

Die Darstellung der Daten erfolgt *when it's done* unter http://ugrm.coderbyheart.de/.
