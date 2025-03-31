# 🛹 Trelltech – Backend V1 (Skateboard)

 V1 du backend de **Trelltech**, une application de gestion de projet connectée à l’API Trello.

## 🚀 Objectif de la V1

- Implémentation des routes principales en lecture/écriture sur les **boards**, **lists**, **cards** et **workspaces** Trello.
- Connexion OAuth et persistance des tokens utilisateurs.
- Version sans Docker (hors base de données).
- Backend en **Kotlin / Ktor**, avec **Exposed** pour la gestion de la base de données.

---

## ⚙️ Prérequis

- Java 17+
- Gradle (fourni avec `./gradlew`)
- Docker (uniquement pour PostgreSQL)
- Un compte Trello + Clé et Token API
- Un fichier `.env` exemple disponible :  `backend-trelltech/app/.env.example`

---

## 🐘 Lancer la base de données (PostgreSQL avec Docker)

```bash
docker compose up -d
La base est accessible sur localhost:5432 avec les identifiants suivants :
Database : trelltech
User : trelltech_user
Password : trelltech_pass
```
**N'utilisez pas ces identifiants en production !**

## ▶️ Lancer le backend
```bash
cd backend-trelltech
./gradlew :app:run
```
Le serveur sera disponible sur http://localhost:8080


## ▶️ Lancer le frontend

**Utiliser Android Studio ou IntelliJ IDEA pour lancer l'application.**

---

## 📌 Endpoints disponibles (V1)
### 🔐 Auth
POST /auth/token
Enregistre le token OAuth pour un utilisateur (userId + token)

### 🧠 Token
GET /token/{userId}
Récupère le token Trello d’un utilisateur

### 🧩 Boards
GET /boards/{userId}
Récupère les boards de l’utilisateur

### 🧾 Lists
GET /boards/{boardId}/lists?userId={userId}
Récupère les listes d’un board

### 🃏 Cards
GET /lists/{listId}/cards?userId={userId}
Récupère les cartes d’une liste

POST /cards
Crée une carte
Body :

```json
{
"listId": "...",
"name": "...",
"desc": "...",
"userId": "..."
}
```
DELETE /cards/{cardId}?userId={userId}
Supprime une carte

POST /cards/{cardId}/assign
Assigne un membre à une carte
Body :

```json

{
"memberId": "...",
"userId": "..."
}
```
PATCH /cards/{cardId}
Met à jour une carte
Body :

```json
{
"name": "...",
"desc": "...",
"userId": "..."
}
```

### 🏢 Workspaces
GET /workspaces?userId={userId}
Récupère les workspaces de l’utilisateur

---

### 🧪 Tests 

```bash
./gradlew :app:test
```

```text
Test des services :

TokenServiceTest.kt
TrelloServiceBoardTest.kt
TrelloServiceCardTest.kt

test sequence :

TrelloServiceSequenceTest.kt
```

## Documentation
L'architecture du backend est détaillée dans le fichier `backend-trelltech/README.md`.

## 🏁 Prochaines étapes (V2)
Implémentation complète des CRUD sur tous les objets actuel (boards, lists, workspaces).

Actualisation des test unitaires et séquences en conséquence.

## 📃 License
Projet étudiant – [Epitech]

Julie Charlemagne

Emilien Dewilde

Théo Deschamps

Trello™ is a trademark of Atlassian.