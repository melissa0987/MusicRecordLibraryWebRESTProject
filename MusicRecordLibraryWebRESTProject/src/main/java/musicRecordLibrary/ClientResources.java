//Melissa Louise Bangloy

package musicRecordLibrary;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;  

@Path("/Clients")
public class ClientResources {
    @Context
    ServletContext context;

    List<Client> clientList = new ArrayList<>();

    //helper method to load client data
    private void loadClientList() throws FileNotFoundException {
        clientList.clear(); // Clear before loading to avoid duplication
        InputStream clientFile = context.getResourceAsStream("/WEB-INF/data/clients.txt");
        Scanner inFile = new Scanner(clientFile);

        while (inFile.hasNextLine()) {
            String[] data = inFile.nextLine().split("\t");
            Client client = new Client(
                Integer.parseInt(data[0]),
                data[1],
                data[2],
                data[3],
                data[4],
                Boolean.parseBoolean(data[5])
            );
            clientList.add(client);
        }

        inFile.close();
    }

    //standard html table for client table
    private String generateClientTableHTML() throws UnsupportedEncodingException {
        String html = "<table><thead><tr>"
	                    + "<th>Client ID</th>"
	                    + "<th>First Name</th>"
	                    + "<th>Last Name</th>"
	                    + "<th>Phone No</th>"
	                    + "<th>Email</th>"
	                    + "<th>Is Active</th>"
	                    + "<th>Actions</th>"
                    + "</tr></thead><tbody>";

        for (Client client : clientList) {
            html += "<tr>"
                 + "<td>" + client.getClientId() + "</td>"
                 + "<td>" + client.getFirstName() + "</td>"
                 + "<td>" + client.getLastName() + "</td>"
                 + "<td>" + client.getPhoneNo() + "</td>"
                 + "<td>" + client.getEmail() + "</td>"
                 + "<td>" + client.getIsActive() + "</td>"
                 + "<td>"
	                 + "<button class='btn btn-sm btn-warning me-1' onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/UpdateClientInfoForm.html?"
		                 + "clientId=" + URLEncoder.encode(String.valueOf(client.getClientId()), "UTF-8")
		                 + "&firstName=" + URLEncoder.encode(client.getFirstName(), "UTF-8")
		                 + "&lastName=" + URLEncoder.encode(client.getLastName(), "UTF-8")
		                 + "&phoneNo=" + URLEncoder.encode(client.getPhoneNo(), "UTF-8")
		                 + "&email=" + URLEncoder.encode(client.getEmail(), "UTF-8")
		                 + "&isActive=" + client.getIsActive()
		                 + "'\">"
		                 + "<i class='bi bi-pen'></i>"
	                 + "</button>"
        	         + "<button class='btn btn-sm btn-danger' onclick='deleteClient(" + client.getClientId() + ")'>"
        	         	+ "<i class='bi bi-trash'></i>"
        	         + "</button>"
                 + "</td>"
                 + "</tr>";
        }

        html += "</tbody></table>";
     // Add New Client button after table
        html += "<div class='add-wrapper'>"
        	     + "<button class='btn btn-success add-btn' "
        	     + "onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/AddClientForm.html'\">"
        	     + "<i class='bi bi-plus-circle'></i> Add New Client</button></div>";
        
        return html;
    }

    //standard html wrapper: head, body and some script
    private String wrapHTML(String title, String h2, String body) {
        return "<html><head><title> Music Record Library: " + title + "</title>"
             + "<link rel='stylesheet' href='/MusicRecordLibraryWebRESTProject/css/style.css' />"
             + "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css\">"
             + "</head>"
             + "<body>"
	             + "<header><h1>Music Record Library</h1></header>"
	             + "<nav>"
		             + "<ul>"
			             + "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Clients'>Clients</a> </li>"
			             + "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Records'>Records</a> </li>"
			             + "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Rentals'>Rentals</a> </li>"
		             + "</ul>"
	             + "</nav>"
	             + "<h2>" + h2 + "</h2>"
	      //search form
         + "<form action='/MusicRecordLibraryWebRESTProject/rest/Clients/searchClientPOST' >"
         + "  <label for='clientId'>Search Client ID: </label>"
         + "  <input type='text' name='clientId' id='clientId' required />"
         + "  <button type='submit'>Search</button>"
         + "</form>"

             + body
             //// JS for deleting clients
             + "<script>"
             + "function deleteClient(clientId) {\r\n"
             + "  if (confirm('Are you sure you want to delete clientId ' + clientId + '?')) {\r\n"
             + "    fetch('/MusicRecordLibraryWebRESTProject/rest/Clients/deleteClientInfo?clientId=' + clientId, {\r\n"
             + "      method: 'DELETE'\r\n"
             + "    })\r\n"
             + "    .then(response => response.text())\r\n"
             + "    .then(html => {\r\n"
             + "      document.body.innerHTML = html;\r\n"
             + "    })\r\n"
             + "    .catch(error => alert('Error deleting client: ' + error));\r\n"
             + "  }\r\n" 
             + "}" 
             + "</script>"
             + "</body></html>";
    }


    //displayHTMLClientInfo
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLClientsInfo() throws FileNotFoundException, UnsupportedEncodingException {
        loadClientList();
        return wrapHTML("Client List View", "Client List", generateClientTableHTML());
    }

    //text
    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String displayTextClientInfo() throws FileNotFoundException {
        loadClientList();
        String output = "";
        for (Client c : clientList) {
            output += "Client ID: " + c.getClientId()
                   + ", First Name: " + c.getFirstName()
                   + ", Last Name: " + c.getLastName()
                   + ", Phone No: " + c.getPhoneNo()
                   + ", E-mail: " + c.getEmail()
                   + ", Is Active: " + c.getIsActive() + "\n";
        }
        output += "Total Client Number: " + clientList.size();
        return output;
    }

    //@GET searchClient
    @GET
    @Path("/searchClient/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client searchJSOClientInfo(@PathParam("clientId") int clientId) throws FileNotFoundException {
        loadClientList();
        for (Client client : clientList) {
            if (client.getClientId() == clientId) {
                return client;
            }
        }
        return new Client(); // empty/default if not found
    }
    
//    REST 
    //@GET searchClientPOST
    @Path("/searchClientPOST")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLClientInfo(
    		@QueryParam("clientId") int clientId) throws FileNotFoundException {
        loadClientList();
        
        String message = ""; 
        Client clientMatch = new Client();
        
        String html = "<table><thead><tr>"
                + "<th>Client ID</th>"
                + "<th>First Name</th>"
                + "<th>Last Name</th>"
                + "<th>Phone No</th>"
                + "<th>Email</th>"
                + "<th>Is Active</th>"
            + "</tr></thead><tbody>";
        
        //looping
        for (Client client : clientList) {
            if (client.getClientId() == clientId) {
            	clientMatch = client; 
            	message = "Client Found!";
            }
        }
        
        //no match 
        if (clientMatch.getClientId() == 0) {
        	message = "No client found with ID: " + clientId; 
        	html = "<p style=\"color:red\">" + message + "</p>";
        	return wrapHTML("Client Search", "Client ID Search: ", html);
    	}
        
        //match found
        if (clientMatch.getClientId() != 0) {
        	message = "Client Found!";
        
	        html += "<tr>"
	                + "<td>" + clientMatch.getClientId() + "</td>"
	                + "<td>" + clientMatch.getFirstName() + "</td>"
	                + "<td>" + clientMatch.getLastName() + "</td>"
	                + "<td>" + clientMatch.getPhoneNo() + "</td>"
	                + "<td>" + clientMatch.getEmail() + "</td>"
	                + "<td>" + clientMatch.getIsActive() + "</td>"
	                + "</tr> <br/> "
	                + "<p style=\"color:green\">" + message + "</p>";
	        
	        return wrapHTML("Client Search", "Client ID Search: ", html);
        }
        
        return wrapHTML("Client Search", "Client ID Search: ", html);
        
    
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Clients/searchClientPOST?clientId=2&submit=Submit
    

    //@@POST addNewClient
    @POST
    @Path("/addNewClient")
    @Produces(MediaType.TEXT_HTML)
    public String addNewClientInfo(
		@FormParam("clientId") int clientId,
	    @FormParam("firstName") String firstName,
	    @FormParam("lastName") String lastName,
	    @FormParam("phoneNo") String phoneNo,
	    @FormParam("email") String email) throws FileNotFoundException, UnsupportedEncodingException {

        loadClientList();

        for (Client cl : clientList) {
            if (cl.getClientId() == clientId) {
                return wrapHTML("Music Record Library: Add New Client", "Error", "<p style='color:red;'>Client ID already exists.</p>");
            }
        }

        clientList.add(new Client(clientId, firstName, lastName, phoneNo, email));
        return wrapHTML(" Add New Client","Client Added", generateClientTableHTML());
    }
  //http://localhost:8080/MusicRecordLibraryWebRESTProject/AddClientForm.html
    

    // @PUT updateClientInfo
    @PUT
    @Path("/updateClientInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String updateClientInfo(Client client) throws FileNotFoundException, UnsupportedEncodingException {
        loadClientList();
        String message = "<p style='color:red;'>No client found with ID: " + client.getClientId() + "</p>";

        for (Client c : clientList) {
            if (c.getClientId() == client.getClientId()) {
                c.setFirstName(client.getFirstName());
                c.setLastName(client.getLastName());
                c.setPhoneNo(client.getPhoneNo());
                c.setEmail(client.getEmail());
                c.setisActive(client.getIsActive());
                message = "<p style='color:green;'>Client ID " + client.getClientId() + " has been updated successfully.</p>";
                break;
            }
        }
        return wrapHTML("Update Client Info", "Client Updated", message + generateClientTableHTML());
    }

  //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Clients/updateClientInfo?clientId=1&firstName=test&lastName=test&phoneNo=514&email=new@gmail.com&isActive=false

    
    //@DELETE deleteClientInfo
    @DELETE
    @Path("/deleteClientInfo")
    @Produces(MediaType.TEXT_HTML)
    public String deleteClientForm(@QueryParam("clientId") int clientId) throws FileNotFoundException, UnsupportedEncodingException {
        loadClientList();
        String message = "<p style='color:red;'>No client found with ID: " + clientId + "</p>";

        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getClientId() == clientId) {
                clientList.remove(i);
                message = "<p style='color:blue;'>Client ID " + clientId + " has been deleted successfully.</p>";
                break;
            }
        }

        return wrapHTML("Delete Client", "Client Deleted", message + generateClientTableHTML());
    }
}
