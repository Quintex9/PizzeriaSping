#  Pizzeria – Spring Boot Web Application

Webová aplikácia pre správu pizzerie postavená na **Spring Boot**, **Spring Security**, **Thymeleaf** a **JPA/Hibernate**.  
Projekt slúži ako plnohodnotný backend + frontend systém pre objednávanie pizze s administračným rozhraním.

##  Funkcionalita

###  Používatelia
- registrácia a prihlásenie
- profil používateľa
- role-based prístup (ADMIN, KUCHAR, KURIER, USER)

###  Role & bezpečnosť
- Spring Security (form login)
- prístup podľa rolí
- admin správa rolí
- role sa **nemažú**, iba **deaktivujú** (soft delete)

###  Pizze
- CRUD správa pizze (admin)
- soft delete (`active = false`)
- slug pre SEO URL
- tagy (M:N)
- obrázky
- aktivácia / deaktivácia

###  Veľkosti pizze (PizzaSize)
- samostatná entita
- historicky bezpečné (použité v objednávkach)
- **nemažú sa**, iba sa deaktivujú
- oddelená správa od pizze

###  Objednávky
- prehľad objednávok
- napojenie na veľkosti pizze
- zachovanie historických dát

###  Ingrediencie
- CRUD správa ingrediencií
- extra cena
- aktivácia / deaktivácia

###  Tagy
- kategorizácia pizze
- CRUD správa

---

##  Technológie

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)
- Thymeleaf
- MySQL
- Maven


##  Dôležité architektonické rozhodnutia

### Soft delete namiesto DELETE
- pizze, role a veľkosti sa **neodstraňujú z databázy**
- používa sa `active = false`
- zachovanie historických objednávok

### Oddelenie zodpovedností
- edit pizze ≠ edit veľkostí
- každá entita má vlastnú správu
- žiadne nebezpečné `orphanRemoval` operácie

### Bezpečnosť dát
- zákaz mazania systémových rolí
- zákaz mazania entít použitých v objednávkach

- ---

## Ako spustiť projekt (lokálne)

### Požiadavky
- Java **17+**
- Maven **3.8+**
- MySQL **8+**

Overenie:
```bash
java -version
mvn -version

git clone https://github.com/Quintex9/PizzeriaSping.git
cd PizzeriaSping

mvn spring-boot:run

http://localhost:8080
