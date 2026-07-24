-- ==========================================
-- 1. CREAZIONE DEI TEAM
-- ==========================================
INSERT INTO team (nome_team) VALUES ('Byte Force');
INSERT INTO team (nome_team) VALUES ('Algoritmo Ribelle');
INSERT INTO team (nome_team) VALUES ('Null Pointers'); -- Team vuoto per testare gli inviti

-- ==========================================
-- 2. UTENTI CON TEAM
-- (Password per tutti: password123)
-- ==========================================
-- Team 1
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('luca_gaspa', 'luca@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 1);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('ivo_zoppi', 'ivo@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 1);

-- Team 2
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('anna_verdi', 'anna@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 2);

-- ==========================================
-- 3. UTENTI SENZA TEAM
-- ==========================================
INSERT INTO utente (username, email, password, ruolo)
VALUES ('giulia_gialli', 'giulia@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('paolo_blu', 'paolo@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE');

-- ==========================================
-- 4. STAFF HACKATHON (Organizzatori, Giudici, Mentori)
-- ==========================================
INSERT INTO utente (username, email, password, ruolo)
VALUES ('organizzatore', 'admin@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'ORGANIZZATORE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('giudice', 'giudice1@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'GIUDICE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('mentore', 'mentore@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'MENTORE');