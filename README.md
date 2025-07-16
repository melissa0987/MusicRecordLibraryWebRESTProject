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

### Clients: 
<img width="1475" height="648" alt="image" src="https://github.com/user-attachments/assets/26153683-09b6-4889-9d09-f80cb4ae3302" />
<img width="704" height="733" alt="image" src="https://github.com/user-attachments/assets/8b3db037-0ca6-4572-b293-2bbf2212271d" />
<img width="1788" height="861" alt="image" src="https://github.com/user-attachments/assets/ba5ad788-01af-4247-8426-4730d4d5d918" />
<img width="537" height="216" alt="image" src="https://github.com/user-attachments/assets/2f079e74-9b77-4552-82dd-4bef20f3f1b9" />
<img width="544" height="571" alt="image" src="https://github.com/user-attachments/assets/6db6b81a-49e6-4684-a5fb-d997b82a58c4" />

### Records: 
<img width="1826" height="866" alt="image" src="https://github.com/user-attachments/assets/0c7bbb64-204d-4bdb-b99d-829657871eca" />
<img width="536" height="457" alt="image" src="https://github.com/user-attachments/assets/ff7ddbb3-a00c-428e-b543-c08a3675a358" />
<img width="1410" height="636" alt="image" src="https://github.com/user-attachments/assets/c77fe5bb-0439-40ca-933b-8b9ce3fe07df" />
<img width="1334" height="273" alt="image" src="https://github.com/user-attachments/assets/80f0144e-6f4d-422f-8a66-8d18afc48e51" />
<img width="847" height="520" alt="image" src="https://github.com/user-attachments/assets/89f691f3-7a7c-4b16-afb6-f601188671f8" />

### Rentals: 
<img width="1978" height="818" alt="image" src="https://github.com/user-attachments/assets/ac31c275-9006-40f8-bed2-f38ada199aed" />
<img width="1866" height="498" alt="image" src="https://github.com/user-attachments/assets/4d28f10f-57a9-40f2-9aa0-b163ff69c7c0" />
<img width="1402" height="383" alt="image" src="https://github.com/user-attachments/assets/1e5aedd4-46f8-4b13-9861-deaae64a8cea" />


---

## ⚖️ Testing & Usage


### Deployment

1. Build with Maven

```bash
mvn clean install
```

2. Deploy WAR to GlassFish or Tomcat


---

## 🤝 Contributors

Developed as part of a Web Services course project. Maintained by Melissa Louise.

---

## ℹ License

Educational use only. Not intended for production environments.
