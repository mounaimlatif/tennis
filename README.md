# 🎾 Tennis Score Kata

## 📋 Présentation
Ce projet est un exercice d'implémentation de la gestion du score d'un match de tennis.  
Il a été étendu pour :
- Utiliser une **API REST**.
- Mettre en évidence de **bonnes pratiques de développement**.
- Appliquer **l'architecture hexagonale** (ports, adapters, domain).

---

## 🏛️ Architecture

- **Domain** :
    - Modèles métier (`Player`, `GameInput`, etc.)
    - Services métier (`StandardTennisStrategy`,`ScoreContext`, stratégie de scoring)
- **Application** :
    - Ports `input` et `output` définissant les points d'entrée et sortie
- **Infrastructure** :
    - Adapters REST et services techniques (ex: affichage du score)

---

## 🛠️ Technologies utilisées
- Java 21
- Spring Boot 3
- Lombok
- JUnit 5
- Cucumber 7 (les steps utilisées sont simplifiés , on peut faire des steps  qui vérifient plus de détails etc ... )
- Maven

---

## 🧩 Patterns et choix techniques

- **Pattern Strategy** :
    - Utilisé pour l'implémentation flexible de la logique de scoring.

- **Architecture Hexagonale** :
    - Ports et Adapters pour découpler Domain, Application et Infrastructure.
    - Dans les projets réels, je préfère généralement utiliser des modules Maven distincts pour séparer clairement le domain, l'infrastructure, et les autres couches. Ici, j'ai choisi de ne pas utiliser de modules afin de simplifier le projet.
- **Utilisation de Spring Context + Lombok dans Domain** *(toléré volontairement)* :
   
---

## 🧪 tests

- **Tests Unitaires** :
    - Ciblent principalement les **services de la couche domain** (logique métier).

- **Tests Fonctionnels** (Cucumber) :
    - Testent **l'intégralité du flow** de scoring via des scénarios utilisateurs complets.
    - Permettent de vérifier la conformité fonctionnelle globale.

---

## 🚀 Lancer le projet

### Prérequis :
- JDK 21
- Maven 3.9+

### Commandes Maven utiles :

```bash
# Lancer les tests unitaires + fonctionnels
mvn clean verify

# Démarrer l'application Spring Boot
mvn spring-boot:run
