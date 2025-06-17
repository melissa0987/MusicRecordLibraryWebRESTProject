//Melissa Louise Bangloy

package musicRecordLibrary;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType; 

@Path("/Rentals")
public class RentalResources {
	@Context
	ServletContext context;
	List<Record> recordList = new ArrayList<>();
	List<Client> clientList = new ArrayList<>(); 
	List<Rental> rentalList = new ArrayList<>();
	
	//helper method to load client data
	private void loadClientList() throws FileNotFoundException{
		clientList.clear(); // Clear before loading to avoid duplication
		InputStream clientFile = context.getResourceAsStream("/WEB-INF/data/clients.txt");
		 Scanner inFile = new Scanner (clientFile);
		  
		 while (inFile.hasNextLine()){
			String s_line = inFile.nextLine();
	        String[] array_sData = s_line.split("\t");
	      //instantiating Client class 
	        
	        int clientId = Integer.parseInt(array_sData[0]);
	        String firstName = array_sData[1];
	        String lastName = array_sData[2];
	        String phoneNo = array_sData[3];
	        String email = array_sData[4];
	        boolean status = Boolean.parseBoolean(array_sData[5]);
	        Client client = new Client(clientId, firstName, lastName, phoneNo, email, status);
	     	
	     	//adding client to clientList
	     	clientList.add(client);
		 }
		 inFile.close();    
	}
	
	//helper method to load record data
	private void loadRecordList() throws FileNotFoundException {
	    recordList.clear(); // Clear before loading to avoid duplication

	    InputStream recordFile = context.getResourceAsStream("/WEB-INF/data/records.txt");
	    Scanner inFile = new Scanner(recordFile);

	    while (inFile.hasNextLine()) {
	        String s_line = inFile.nextLine();
	        String[] array_sData = s_line.split("\t");

	        // Parse fields from file
	        String recordID = array_sData[0];
	        String title = array_sData[1];
	        String artist = array_sData[2];
	        String genre = array_sData[3];
	        int availableCopies = Integer.parseInt(array_sData[4]);
	        int totalCopies = Integer.parseInt(array_sData[5]);

	        // Create Record object and add to list
	        Record rec = new Record(recordID, title, artist, genre, availableCopies, totalCopies);
	        recordList.add(rec);
	    }

	    inFile.close();
	}
	
	//helper method to load data rentals from txt file
	private void loadRentalList() throws FileNotFoundException{
		// Load clients and build map
		loadClientList();
	    loadRecordList();


	    // Create maps for lookup to instantiate Rental obj
	    //clientMap
	    rentalList.clear(); // Clear before loading to avoid duplication
	    Map<Integer, Client> clientMap = new HashMap<>();
	    for (Client client : clientList) {
	        clientMap.put(client.getClientId(), client);
	    }
	    //recordMap
	    Map<String, Record> recordMap = new HashMap<>();
	    for (Record record : recordList) {
	        recordMap.put(record.getRecordID(), record);
	    }
		
		//loading data from rentals.txt
		 InputStream rentalFile = context.getResourceAsStream("/WEB-INF/data/rentals.txt");
		 Scanner inFile = new Scanner (rentalFile); 
		 
		 while (inFile.hasNextLine()){
			String s_line = inFile.nextLine();
	        String[] array_sData = s_line.split("\t");
	
	        String rentalId = array_sData[0];
	        int clientId = Integer.parseInt(array_sData[1]);
	        String recordId = array_sData[2];
	        LocalDate rentalDate = LocalDate.parse(array_sData[3]);
	        LocalDate dueDate = LocalDate.parse(array_sData[4]);
	        LocalDate returnDate = array_sData[5].equals("null") ? null : LocalDate.parse(array_sData[5]);
	
	        Client client = clientMap.get(clientId); //looking at clientMap
	        Record record = recordMap.get(recordId);
	
	        if (client != null && record != null) {
	            Rental rental = new Rental(rentalId, client, record, rentalDate, dueDate, returnDate);
	            rentalList.add(rental);
	        }
		 }
		 inFile.close();   
	}
	
	//standard html table for rental table
	private String generateRentalTableHTML( ) throws UnsupportedEncodingException { 
		 String html = "<table>"
                + "<thead><tr>"
	                + "<th>Rental ID</th>"
		                + "<th>Client ID</th>"
		                + "<th>Client Name</th>"
		                + "<th>Client Phone No</th>"
		                + "<th>Client E-mail</th>" 
		                + "<th>Client Status: Active</th>"
	                + "<th>Record ID</th>"
	                + "<th>Record Title</th>"
	                + "<th>Record Artist</th>"
	                + "<th>Rental Date</th>"
	                + "<th>Due Date</th>"
	                + "<th>Return Date</th>"
	                + "<th>Actions</th>"
                + "</tr></thead><tbody>";

		 for (Rental rental : rentalList) {
			    html += "<tr>"
			          + "<td>" + rental.getRentalId() + "</td>"
			          + "<td>" + rental.getClient().getClientId() + "</td>" 
			          + "<td>" + rental.getClient().getFirstName() + " " + rental.getClient().getLastName() + "</td>" 
			          + "<td>" + rental.getClient().getPhoneNo() + "</td>" 
			          + "<td>" + rental.getClient().getEmail() + "</td>" 
			          + "<td>" + rental.getClient().getIsActive() + "</td>" 
			          + "<td>" + rental.getRecord().getRecordID()  + "</td>" 
			          + "<td>" + rental.getRecord().getTitle()  + "</td>" 
			          + "<td>" + rental.getRecord().getArtist()  + "</td>" 
			          + "<td>" + rental.getRentalDate() + "</td>"
			          + "<td>" + rental.getDueDate() + "</td>"
			          + "<td>" + (rental.getReturnDate() != null ? rental.getReturnDate() : "Not returned") + "</td>"

			          + "<td>"
			          // Edit button
			          + "<button class='btn btn-sm btn-warning me-1' onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/UpdateRentalForm.html?"
			          + "rentalId=" + URLEncoder.encode(rental.getRentalId(), "UTF-8")
			          + "&clientId=" + URLEncoder.encode(String.valueOf(rental.getClient().getClientId()), "UTF-8")
			          + "&recordID=" + URLEncoder.encode(rental.getRecord().getRecordID(), "UTF-8")
			          + "&rentalDate=" + URLEncoder.encode(String.valueOf(rental.getRentalDate()), "UTF-8")
			          + "&dueDate=" + URLEncoder.encode(String.valueOf(rental.getDueDate()), "UTF-8")
			          + "&returnDate=" + (rental.getReturnDate() != null 
			                              ? URLEncoder.encode(String.valueOf(rental.getReturnDate()), "UTF-8") 
			                              : "")
			          + "'\">"
			          + "<i class='bi bi-pen'></i></button>"

			          // Delete button
			          + "<button class='btn btn-sm btn-danger' onclick='deleteRental(\"" + rental.getRentalId() + "\")'>"
			          + "<i class='bi bi-trash'></i></button>"
			          + "</td>"
			          + "</tr>"; 
			}


        html += "</tbody></table>"; 

     // Add New Rental button
        html += "<div class='add-wrapper'>"
              + "<button class='btn btn-success add-btn' "
              + "onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/AddRentalForm.html'\">"
              + "<i class='bi bi-plus-circle'></i> Add New Rental</button></div>";
        
        return html;
	}
	
	//standard html wrapper: head, body and some script
	public String wrapHTML(String title, String h2, String body) {
	    return "<html><head><title> Music Record Library: " + title + "</title>"
	         + "<link rel='stylesheet' href='/MusicRecordLibraryWebRESTProject/css/style.css' />"
	         + "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css\">"
	         + "</head><body>"
	         + "<header><h1>Music Record Library</h1></header>"
		         + "<nav><ul>"
			         + "<li><a href='/MusicRecordLibraryWebRESTProject/rest/Clients'>Clients</a></li>"
			         + "<li><a href='/MusicRecordLibraryWebRESTProject/rest/Records'>Records</a></li>"
			         + "<li><a href='/MusicRecordLibraryWebRESTProject/rest/Rentals'>Rentals</a></li>"
		         + "</ul></nav>"

	         + "<h2>" + h2 + "</h2>"

	         // Search Rental by searchRentalId
	         + "<form action='/MusicRecordLibraryWebRESTProject/rest/Rentals/searchRentalId'>"
	         + "<label for='clientId'>Search Rental ID: </label>"
	         + "<input type='text' name='rentalId' id='rentalId' required />"
	         + "<button type='submit'>Search</button>"
	         + "</form>"

	         + body

	         // JS for deleting rentals
	         + "<script>"
	         + "function deleteRental(rentalId) {"
	         + "  if (confirm('Are you sure you want to delete rentalId ' + rentalId + '?')) {"
	         + "    fetch('/MusicRecordLibraryWebRESTProject/rest/Rentals/deleteRentalID?rentalId=' + rentalId, { method: 'DELETE' })"
	         + "    .then(response => response.text())"
	         + "    .then(html => { document.body.innerHTML = html; })"
	         + "    .catch(error => alert('Error deleting rental: ' + error));"
	         + "  }"
	         + "}"
	         + "</script>"

	         + "</body></html>";
	}

	
	

    //displayHTMLRentalInfo
    @GET 
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLRentalInfo() throws FileNotFoundException, UnsupportedEncodingException {
        loadRentalList();
        String tableHTML = generateRentalTableHTML();
        return wrapHTML("Music Record Library: Record List View", "Record List", tableHTML);
         
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/MusicLibrarySystem/Rentals
    
    
    //plain text
    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String displayTextRentalInfo() throws FileNotFoundException {
        loadRentalList();
        
        String output = "";
        int rentalCount = 0;
        for (Rental rental : rentalList) {
            output += "Rental ID: " + rental.getRentalId()
                   + ", Client: " + rental.getClient().getFirstName() + " " + rental.getClient().getLastName()
                   + ", Phone No: " + rental.getClient().getPhoneNo()
                   + ", E-mail: " + rental.getClient().getEmail()
                   + ", Client Status: Active: " + rental.getClient().getIsActive()
                   + ", Record Title: " + rental.getRecord().getTitle()
                   + ", Artist: " + rental.getRecord().getArtist()
                   + ", Rental Date: " + rental.getRentalDate()
                   + ", Due Date: " + rental.getDueDate()
                   + ", Return Date: " + rental.getReturnDate() != null ? rental.getReturnDate() : "Not returned"
                   + "\n";
            rentalCount++;
                   
        	}
        output += "Total Rentals: " + rentalCount; 
        return output;
    }
    
    //searching rentals 
    @GET
    @Path("/searchRental/{rentalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Rental searchJSONRentalInfo(@PathParam("rentalId") String rentalId) throws FileNotFoundException {
         loadRentalList();

        for (Rental rental : rentalList) {
            if (rental.getRentalId().equalsIgnoreCase(rentalId)) {
                return rental;
            }
        }

        return new Rental(); // new Rental obj if nothing found
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/MusicLibrarySystem/Rentals/searchRental/REN005



    
    
//  REST 
    
    //GET Rental by ID searchRentalId
    @GET
    @Path("/searchRentalId")
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLRentalInfo(@QueryParam("rentalId") String rentalId) throws FileNotFoundException {
        loadRentalList();

        String message = "";
        Rental rentalMatch = null;

        String html = "<table><thead><tr>"
                + "<th>Rental ID</th>"
                + "<th>Client Name</th>"
                + "<th>Record Title</th>"
                + "<th>Rental Date</th>"
                + "<th>Due Date</th>"
                + "<th>Return Date</th>"
                + "</tr></thead><tbody>";

        for (Rental rental : rentalList) {
            if (rental.getRentalId().equalsIgnoreCase(rentalId)) {
                rentalMatch = rental;
                break;
            }
        }

        if (rentalMatch == null) {
            message = "No rental found with ID: " + rentalId;
            html = "<p style=\"color:red\">" + message + "</p>";
            return wrapHTML("Rental Search", "Rental ID Search: ", html);
        }

        message = "Rental Found!";
        html += "<tr>"
                + "<td>" + rentalMatch.getRentalId() + "</td>"
                + "<td>" + rentalMatch.getClient().getFirstName() + " " + rentalMatch.getClient().getLastName() + "</td>"
                + "<td>" + rentalMatch.getRecord().getTitle() + "</td>"
                + "<td>" + rentalMatch.getRentalDate() + "</td>"
                + "<td>" + rentalMatch.getDueDate() + "</td>"
                + "<td>" + (rentalMatch.getReturnDate() != null ? rentalMatch.getReturnDate() : "Not returned") + "</td>"
                + "</tr></tbody></table>"
                + "<p style=\"color:green\">" + message + "</p>";

        return wrapHTML("Rental Search", "Rental ID Search: ", html);
    }

    //POST Add New Rental
    @POST
    @Path("/addNewRental")
    @Produces(MediaType.TEXT_HTML)
    public String addNewRentalInfo(
            @FormParam("rentalId") String rentalId,
            @FormParam("clientId") int clientId,
            @FormParam("recordID") String recordID,
            @FormParam("rentalDate") String rentalDate,
            @FormParam("dueDate") String dueDate) throws FileNotFoundException, UnsupportedEncodingException {

        loadRentalList(); 

        for (Rental rental : rentalList) {
            if (rental.getRentalId().equalsIgnoreCase(rentalId)) {
                return wrapHTML("Add New Rental", "Error", "<p style='color:red;'>Rental ID already exists.</p>");
            }
        }
        
        Client client = null;
        Record record = null;
        
        //checks for client
        for(Client c : clientList) {
        	if(c.getClientId() == clientId) {
        		client = c;
        	}
        }
        
        //checks for record
        for(Record c : recordList) {
        	if (c.getRecordID().equalsIgnoreCase(recordID)) {
        	    record = c;
        	}
        } 

        if (client == null || record == null) {
            return wrapHTML("Add New Rental", "Error", "<p style='color:red;'>Client or Record not found.</p>");
        }

        Rental newRental = new Rental(rentalId, client, record,
                LocalDate.parse(rentalDate), LocalDate.parse(dueDate), null);

        rentalList.add(newRental);

        return wrapHTML("Add New Rental", "Rental Added", generateRentalTableHTML());
    }
    
    
    
    //PUT Update Rental
    @PUT
    @Path("/updateRentalInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String updateRentalInfo(Rental updatedRental) throws FileNotFoundException, UnsupportedEncodingException {
        loadRentalList();
        loadClientList();
        loadRecordList();

        // 1. Validate client and record
        Client client = clientList.stream()
            .filter(c -> c.getClientId() == updatedRental.getClient().getClientId())
            .findFirst()
            .orElse(null);

        Record record =  recordList.stream()
                .filter(r -> r.getRecordID().equalsIgnoreCase(updatedRental.getRecord().getRecordID()))
                .findFirst()
                .orElse(null); 

        if (client == null) {
            return wrapHTML("Update Rental", "Error",
                "<p style='color:red;'>Client ID " + updatedRental.getClient().getClientId() + " not found.</p>");
        }

        if (record == null) {
            return wrapHTML("Update Rental", "Error",
                "<p style='color:red;'>Record ID " + updatedRental.getRecord().getRecordID() + " not found.</p>");
        }

        // 2. Update matching rental
        String message = "<p style='color:red;'>No rental found with ID: " + updatedRental.getRentalId() + "</p>";

        for (Rental rental : rentalList) {
            if (rental.getRentalId().equalsIgnoreCase(updatedRental.getRentalId())) {
                rental.setClient(client);
                rental.setRecord(record);
                rental.setRentalDate(updatedRental.getRentalDate());
                rental.setDueDate(updatedRental.getDueDate());
                rental.setReturnDate(updatedRental.getReturnDate());

                message = "<p style='color:green;'>Rental ID " + rental.getRentalId() + " has been updated successfully.</p>";
                break;
            }
        }

        return wrapHTML("Update Rental", "Rental Updated", message + generateRentalTableHTML());
    }

 
    //DELETE Rental by ID
    @DELETE
    @Path("/deleteRentalID")
    @Produces(MediaType.TEXT_HTML)
    public String deleteRentalInfo(@QueryParam("rentalId") String rentalId) throws FileNotFoundException, UnsupportedEncodingException {
        loadRentalList();

        String message = "<p style='color:red;'>No rental found with ID: " + rentalId + "</p>";

        for (int i = 0; i < rentalList.size(); i++) {
            if (rentalList.get(i).getRentalId().equalsIgnoreCase(rentalId)) {
                rentalList.remove(i);
                message = "<p style='color:blue;'>Rental ID " + rentalId + " has been deleted successfully.</p>";
                break;
            }
        }

        return wrapHTML("Delete Rental", "Rental Deleted", message + generateRentalTableHTML());
    }





}
