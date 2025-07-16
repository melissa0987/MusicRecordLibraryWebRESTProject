# 🎵 Music Record Library Web REST Project

## Project Overview

This project implements a comprehensive Music Record Library management system using **RESTful Web Services**. The application allows users to manage **clients**, **records**, and **rentals** through REST APIs, offering full CRUD functionality, JSON responses, and HTML forms.

---

## 🚀 Business Utility

* **Digital Catalog Management**: Easily manage clients, records, and rental transactions.
* **API-First Architecture**: REST endpoints for seamless integration.
* **Simple & Scalable**: JSON support, modular REST design.
* **Persistent Data**: Data stored and retrieved from text files.

---

## ⚙️ Technology Stack

* **Backend**: Java EE with JAX-RS (Jersey)
* **Server**: GlassFish / Tomcat
* **Frontend**: HTML, CSS, JavaScript (Fetch API)
* **Data Format**: JSON, HTML
* **Tools**: Postman for API testing

---

## 🔍 REST Endpoints Summary

### 👤 ClientResources.java

```
@GET      /Clients/allClientsJSON
@GET      /Clients/searchClient/{clientId}
@POST     /Clients/addNewClient
@PUT      /Clients/updateClientInfo
@DELETE   /Clients/deleteClientInfo
```

### 🎿 RecordResources.java

```
@GET      /Records/allRecordsJSON
@GET      /Records/searchRecord/{recordID}
@POST     /Records/addNewRecord
@PUT      /Records/updateRecordInfo
@DELETE   /Records/deleteRecordInfo
```

### 📲 RentalResources.java

```
@GET      /Rentals/searchRental/{rentalId}
@GET      /Rentals/searchRentalId
@POST     /Rentals/addNewRental
@PUT      /Rentals/updateRentalInfo
@DELETE   /Rentals/deleteRentalID
```

Example client search:

```
http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Clients/searchClientPOST?clientId=2&submit=Submit
```

---

## 📊 Data Structure

### Client.java

```java
public class Client {
    private int clientId;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private boolean isActive;
    // Getters, Setters, Constructors, toString()
}
```

### Record.java

```java
public class Record {
    private String recordID;
    private String title;
    private String artist;
    private String genre;
    private int availableCopies;
    private int totalCopies;
    // Getters, Setters, Constructors, toString()
}
```

### Rental.java

```java
public class Rental {
    private String rentalId;
    private Client client;
    private Record record;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    // Getters, Setters, Constructors, toString()
}
```

---

## 🏠 HTML & Forms

* Add Client / Record / Rental
* Update Client / Record / Rental
* Search Forms (HTML POST)

---

## 📈 Screenshots & Examples

### 👤 Clients
<img src="https://github.com/user-attachments/assets/26153683-09b6-4889-9d09-f80cb4ae3302" width="700" /> <img src="https://github.com/user-attachments/assets/8b3db037-0ca6-4572-b293-2bbf2212271d" width="400" /> <img src="https://github.com/user-attachments/assets/ba5ad788-01af-4247-8426-4730d4d5d918" width="800" /> <img src="https://github.com/user-attachments/assets/2f079e74-9b77-4552-82dd-4bef20f3f1b9" width="300" /> <img src="https://github.com/user-attachments/assets/6db6b81a-49e6-4684-a5fb-d997b82a58c4" width="400" />


### 🎵 Records
<img src="https://github.com/user-attachments/assets/0c7bbb64-204d-4bdb-b99d-829657871eca" width="800" /> <img src="https://github.com/user-attachments/assets/ff7ddbb3-a00c-428e-b543-c08a3675a358" width="350" /> <img src="https://github.com/user-attachments/assets/c77fe5bb-0439-40ca-933b-8b9ce3fe07df" width="700" /> <img src="https://github.com/user-attachments/assets/80f0144e-6f4d-422f-8a66-8d18afc48e51" width="600" /> <img src="https://github.com/user-attachments/assets/89f691f3-7a7c-4b16-afb6-f601188671f8" width="500" />


### 💿 Rentals
<img src="https://github.com/user-attachments/assets/ac31c275-9006-40f8-bed2-f38ada199aed" width="850" /> <img src="https://github.com/user-attachments/assets/4d28f10f-57a9-40f2-9aa0-b163ff69c7c0" width="800" /> <img src="https://github.com/user-attachments/assets/1e5aedd4-46f8-4b13-9861-deaae64a8cea" width="700" />

---

## ⚖️ Installation and Setup

### Prerequisites

- Java 8 or higher
- Apache Tomcat 9+
- Maven 3.6+
- IDE (Eclipse, IntelliJ IDEA, or VS Code)

### Installation Steps

1. Clone the repository

```bash
    git clone https://github.com/melissa0987/MusicRecordLibraryWebRESTProject.git
    cd MusicRecordLibraryWebRESTProject
```

2. Build the project

```bash
   mvn clean compile
```

3. Deploy WAR to GlassFish or Tomcat

4. Access the application

- REST Services: ` http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Clients `
- HTML Forms: `http://localhost:8080/MusicRecordLibraryWebRESTProject/AddClientForm.html`
  
---

## Project Structure
```bash
MusicRecordLibraryWebRESTProject/
│   pom.xml
├───.settings
├───build
│   └───classes
├───src
│   └───main
│       ├───java
│       │   └───musicRecordLibrary
│       │           Client.java
│       │           ClientResources.java
│       │           Record.java
│       │           RecordResources.java
│       │           Rental.java
│       │           RentalResources.java
│       └───webapp
│           │   AddClientForm.html
│           │   AddRecordForm.html
│           │   AddRentalForm.html
│           │   SearchClientIDForm.html
│           │   SearchRecordIDForm.html
│           │   UpdateClientInfoForm.html
│           │   UpdateRecordsForm.html
│           │   UpdateRentalForm.html
│           ├───css
│           │       style.css
│           ├───META-INF
│           │       MANIFEST.MF
│           └───WEB-INF
│               │   web.xml
│               ├───data
│               │       clients.txt
│               │       records.txt
│               │       rentals.txt
│               └───lib
└───target
    ├───classes
    │   └───musicRecordLibrary
    ├───m2e-wtp
    │   └───web-resources
    │       └───META-INF
    │           │   MANIFEST.MF
    │           └───maven
    │               └───musicRecordLibraryWebRESTProject
    │                   └───MusicRecordLibraryWebRESTProject
    └───test-classes
```

## 🤝 Contributors

Developed as part of a Web Services course project. Maintained by Melissa Louise.

---

## ℹ License

Educational use only.
