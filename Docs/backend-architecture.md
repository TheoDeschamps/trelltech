## 📦 Project Structure (Backend)

Voici l’arborescence du backend avec une brève description de chaque dossier/fichier important :
<pre> ```text
/backend-trelltech
│
├── /app
│   ├── /src
│   │   ├── /main
│   │   │   ├── /kotlin
│   │   │   │   ├── /com/trelltech
│   │   │   │   │
│   │   │   │   │   ├── /config
│   │   │   │   │   │   ├── DatabaseFactory.kt        # Connexion à PostgreSQL avec Exposed
│   │   │   │   │   │   ├── SecurityConfig.kt         # OAuth, gestion des tokens d’accès
│   │   │   │   │   │   ├── TrelloClient.kt           # Client HTTP vers l’API Trello
│   │   │   │   │
│   │   │   │   │   ├── /controllers
│   │   │   │   │   │   ├── AuthController.kt         # Route /auth/login
│   │   │   │   │   │   ├── BoardController.kt        # CRUD des boards (GET, etc.)
│   │   │   │   │   │   ├── CardController.kt         # CRUD des cartes + assignation utilisateur
│   │   │   │   │   │   ├── ListController.kt         # GET des listes d’un board
│   │   │   │   │   │   ├── TokenController.kt        # Sauvegarde/récupération du token
│   │   │   │   │   │   ├── WorkspaceController.kt    # Récupération des workspaces
│   │   │   │   │
│   │   │   │   │   ├── /models
│   │   │   │   │   │   ├── Token.kt                  # Modèle Exposed de token
│   │   │   │   │   │   ├── TrelloBoard.kt            # Modèle serializable d’un board
│   │   │   │   │   │   ├── TrelloCard.kt             # Modèle serializable d’une carte
│   │   │   │   │   │   ├── TrelloList.kt             # Modèle serializable d’une liste
│   │   │   │   │   │   ├── TrelloWorkspace.kt        # Modèle serializable d’un workspace
│   │   │   │   │
│   │   │   │   │   ├── /services
│   │   │   │   │   │   ├── TrelloService.kt          # Logique métier Trello (appelle TrelloClient)
│   │   │   │   │   │   ├── TokenService.kt           # Accès/écriture du token en DB
│   │   │   │   │
│   │   │   │   │   ├── Application.kt                # Configuration Ktor (routing, features…)
│   │   ├── /resources
│   │   │   ├── application.conf                      # Configuration Ktor, ports, DB, etc.
│   │   │   ├── logback.xml                           # Logging (facultatif)
│   │   ├── /test                                     # Tests unitaires et séquences
│
|   ├── /build                                          # Dossier de build
│   ├── build.gradle.kts                               # Dépendances et plugins
│   ├── .env                                           # Variables d’environnement (DB, OAuth, etc.)
├── Dockerfile                                         # Dockerisation du backend
├── README.md                                          # Documentation de build, endpoints, structure
├── settings.gradle.kts                                # Déclaration des modules
├── gradlew / gradlew.bat                              # Wrapper Gradle (Windows/Linux/macOS)
``` </pre>