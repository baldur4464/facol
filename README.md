# Funktionsautomatisierte Kommunikationsprüfung für OCPP-fähige Ladestationen


## Beschreibung
Diese Tool ermöglicht es Chargepoints automatisch zu testen.

## Installation

### Requirements

Java 11  
Maven 3.8.3 oder neuer

### Vorbereitung

In Application.yml können die jeweiligen Ports eingestellt werden:      

testport: Dieser Port dient zur Schnittstelle für Chargepoint   
port:  Dieser Port wird geöffnet, wenn H2-Console enabled ist

In application.properties wird die Applikation konfiguriert:    

spring.datasource.url:  Speicherort / Adresse der Datenbank

spring.h2.console.enabled: true/false - schaltet bei einer H2 Datenbank die Console hinzu. Port kann in der application.yml eingestellt werden

spring.jpa.show-sql: true/false - Gibt bei true die SQL Sequenzen aus für weiteren Debug

spring.datasource.username: Nutzername für die Datenbank

spring.datasource.password: Passwort für die Datenbank

spring.datasoruce.driver-class-name: Datenbank-Treiber für die genutzte Applikation

spring.jpa.hibernate.ddl-auto: create-drop, update, create

### Starten

Das Programm lässt sich entweder IDE kompilieren oder über mvn spring-boot:run starten
