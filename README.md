# Hackhub

**HackHub** è una piattaforma web backend per la gestione completa di hackathon, sviluppata in **Java** con il framework **Spring Boot**.
Gli hackathon sono competizioni di sviluppo software alle quali partecipano dei **team**. Ogni evento all'interno della piattaforma segue un ciclo di vita rigoroso composto da quattro stati: `IN_ISCRIZIONE`, `IN_CORSO`, `IN_VALUTAZIONE` e `CONCLUSO`. La piattaforma supporta l'organizzazione degli eventi, la creazione dei team, la gestione delle sottomissioni dei progetti, le valutazioni dei giudici e un sistema di segnalazioni per monitorare le violazioni.

## Architettura e Organizzazione del Codice

Il progetto segue i principi della separazione delle responsabilità e del Clean Code. Il codice sorgente principale si trova sotto il package `unicam.ids.hackhub` ed è suddiviso nei seguenti layer logici:

### `core` (Domain Layer)
Rappresenta il cuore del sistema e contiene esclusivamente la logica di dominio e le entità principali, suddivise per aree tematiche:
*   **Hackathon**: gestisce l'entità evento. Per garantire il corretto flusso del ciclo di vita dell'hackathon e prevenire transizioni illegali, è stato implementato in modo rigoroso lo **State Pattern** (attraverso l'interfaccia `StatoHackathon` e gli stati concreti come `StatoInIscrizione`, `StatoInCorso`, ecc.).
*   **Utenti e Team**: gestisce i partecipanti, i ruoli e la formazione delle squadre. Per la registrazione degli utenti è stato impiegato il **Builder Pattern** (`UtenteBuilder` e `ConcreteUtenteBuilder`), che garantisce una costruzione sicura, flessibile e validata degli account.
*   **Sottomissioni, Valutazioni e Segnalazioni**: entità che modellano l'invio dei progetti, i voti dei giudici e il sistema di reportistica per i mentori.

### `service` (Control Layer)
Questo livello contiene i **Gestori** (es. `GestoreHackathon`, `GestoreSegnalazione`, `GestoreTeam`). Queste classi agiscono da "orchestratori": applicano le regole di business del dominio, verificano i permessi operativi specifici per ogni ruolo e interagiscono con l'infrastruttura per il salvataggio dei dati.

### `controller` (Boundary / Presentation Layer)
Costituisce l'interfaccia esposta verso l'esterno. Contiene i vari REST Controller (`HackathonBoundary`, `SegnalazioneBoundary`, `TeamBoundary`, ecc.). Il compito di questi componenti è ricevere le richieste HTTP in ingresso, validare i payload tramite i DTO e delegare l'esecuzione ai Gestori del livello service, restituendo infine le risposte HTTP appropriate.

### `dto` (Data Transfer Object)
Contiene oggetti leggeri utilizzati esclusivamente per incapsulare e trasferire dati tra il client (es. Postman) e il server, isolando le vere entità di dominio dalle richieste API.

### `infrastructure`
Si occupa dell'accesso ai dati. Contiene le interfacce Repository (che estendono `JpaRepository` di Spring Data) necessarie per interrogare e manipolare il database relazionale (H2 in-memory).

### `security`
Modulo isolato dedicato alla sicurezza dell'applicativo. Implementa l'autenticazione e l'autorizzazione basata su ruoli tramite **Spring Security** e **JWT (JSON Web Tokens)**. Contiene i filtri di intercettazione (`JwtAuthenticationFilter`) che verificano la validità dei token per proteggere gli endpoint sensibili.

## Pattern e Tecnologie Utilizzate
*   **Linguaggio & Framework:** Java 21+, Spring Boot 3
*   **Database:** H2 Database (In-Memory) con Spring Data JPA e Hibernate.
*   **Sicurezza:** Spring Security, JWT (JSON Web Token).
*   **Design Pattern:**
    *   **State Pattern** (per la gestione delle transizioni di stato degli Hackathon).
    *   **Builder Pattern** (per la creazione strutturata degli Utenti).

## Guida al Test delle API (Postman)
L'applicativo utilizza un sistema di sicurezza rigoroso basato sui ruoli (`ORGANIZZATORE`, `MENTORE`, `GIUDICE`, `UTENTE`).
Per testare gli endpoint REST:
1. Effettuare una richiesta di Login (o Registrazione) con le credenziali del ruolo desiderato.
2. Copiare il token **JWT** restituito nella risposta.
3. Inserire il token nelle richieste successive andando nella tab **Authorization** di Postman, selezionando **Bearer Token** e incollando la stringa nell'apposito campo.