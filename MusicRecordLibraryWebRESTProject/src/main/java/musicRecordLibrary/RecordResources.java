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

@Path("/Records")
public class RecordResources {
	@Context
	ServletContext context;
	List<Record> recordList = new ArrayList<>();
	
	//loads records data from input file
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

	
	//standandard table html
    private String generateRecordTableHTML() throws UnsupportedEncodingException {
        String html = "<table>"
                + "<thead><tr>"
                + "<th>Record ID</th>"
                + "<th>Title</th>"
                + "<th>Artist</th>"
                + "<th>Genre</th>"
                + "<th>Available Copies</th>"
                + "<th>Total Copies</th>"
                + "<th>Actions</th>"
            + "</tr></thead><tbody>";

        for (Record record : recordList) {
            html += "<tr>"
                    + "<td>" + record.getRecordID() + "</td>"
                    + "<td>" + record.getTitle() + "</td>"
                    + "<td>" + record.getArtist() + "</td>"
                    + "<td>" + record.getGenre() + "</td>"
                    + "<td>" + record.getAvailableCopies() + "</td>"
                    + "<td>" + record.getTotalCopies() + "</td>"
            		+ "<td>"
	            		+ "<button class='btn btn-sm btn-warning me-1' onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/UpdateRecordsForm.html?"
		            		+ "recordID=" + URLEncoder.encode(record.getRecordID(), "UTF-8")
		            		+ "&title=" + URLEncoder.encode(record.getTitle(), "UTF-8")
		            		+ "&artist=" + URLEncoder.encode(record.getArtist(), "UTF-8")
		            		+ "&genre=" + URLEncoder.encode(record.getGenre(), "UTF-8")
		            		+ "&availableCopies=" + URLEncoder.encode(String.valueOf(record.getAvailableCopies()), "UTF-8")
		            		+ "&totalCopies=" + URLEncoder.encode(String.valueOf(record.getTotalCopies()), "UTF-8")
		            		+ "'\">"
		            		+ "<i class='bi bi-pen'></i>"
	            		+ "</button>"
	            		+ "<button class='btn btn-sm btn-danger' onclick='deleteRecord(\"" + record.getRecordID() + "\")'>"
		       	         	+ "<i class='bi bi-trash'></i>"
		       	         + "</button>"
		                + "</td>"
                    + "</tr>";
        } 

        html += "</tbody></table>";
     // Add New Record button after table
        html += "<div class='add-wrapper'>"
        	     + "<button class='btn btn-success add-btn' "
        	     + "onclick=\"window.location.href='/MusicRecordLibraryWebRESTProject/AddRecordForm.html'\">"
        	     + "<i class='bi bi-plus-circle'></i> Add New Record</button></div>";
        
        return html;
    }

    private String wrapHTML(String title, String h2, String body) {
    	return "<html><head><title> Music Record Library: " + title + "</title>"
    	         + "<link rel='stylesheet' href='/MusicRecordLibraryWebRESTProject/css/style.css' />"
    	         + "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css\">"
    	         + "</head>"
    	         + "<body>"
	    	         + "<header><h1>Music Record Library</h1></header>"
	    	         + "<nav>"
	    	         	+ "<ul> "
		    	         	+ "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Clients'>Clients</a> </li>" 
		    	         	+ "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Records'>Records</a> </li>" 
		    	         	+ "<li> <a href='/MusicRecordLibraryWebRESTProject/rest/Rentals'>Rentals</a> </li>" 
		    	         + "</ul>"  
		    	         + "</nav>" 
	    	         + "<h2>" + h2 + "</h2>"
		    	         + "<form action='/MusicRecordLibraryWebRESTProject/rest/Records/searchRecord' >"
				         + "  <label for='clientId'>Search Record ID: </label>"
				         + "  <input type='text' name='recordID' id='recordID' required />"
				         + "  <button type='submit'>Search</button>"
				         + "</form>"
	    	         + body 
	    	         + "<script>"
	                 + "function deleteRecord(recordID) {\r\n"
	                 + "  if (confirm('Are you sure you want to delete recordID ' + recordID + '?')) {\r\n"
	                 + "    fetch('/MusicRecordLibraryWebRESTProject/rest/Records/deleteRecordInfo?recordID=' + recordID, {\r\n"
	                 + "      method: 'DELETE'\r\n"
	                 + "    })\r\n"
	                 + "    .then(response => response.text())\r\n"
	                 + "    .then(html => {\r\n"
	                 + "      document.body.innerHTML = html;\r\n"
	                 + "    })\r\n"
	                 + "    .catch(error => alert('Error deleting recordID: ' + error));\r\n"
	                 + "  }\r\n"
	                 + "}"
	                 + "</script>"
	                 + "</body></html>";
    }
	
	
	
    //displayHTMLRecordInfo
    @GET 
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLRecordInfo() throws FileNotFoundException, UnsupportedEncodingException { 
    	loadRecordList();
        String tableHTML = generateRecordTableHTML();
        return wrapHTML("Record List", "Record List", tableHTML);
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Records
    
    //displayTextRecordInfo
    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String displayTextRecordInfo() throws FileNotFoundException {
    	 loadRecordList();
    	 int recordCount = 0;
    	 String output = "";
    	 for (Record record : recordList) {
             output += "Record ID: " + record.getRecordID()
                    + ", Title: " + record.getTitle()
                    + ", Artist " + record.getArtist()
                    + ", Genre: " + record.getGenre()
                    + ", Available Copies: " + record.getAvailableCopies()
                    + ", Total Copies: " + record.getTotalCopies() + "\n";
             recordCount++;
         }
         output += "Total Records: " + recordCount; 

         
        return output;
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Records/text

    //searchJSONRecordInfo
    @GET
    @Path("/searchRecord/{recordID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Record searchJSONRecordInfo(@PathParam("recordID") String recordID) throws FileNotFoundException {
    	 loadRecordList();

        for (Record record : recordList) {
            if (record.getRecordID().equalsIgnoreCase(recordID)) {
                return record;
            }
        }

        return new Record(); //returns a new obj if no match found
    }
    //http://localhost:8080/MusicRecordLibraryWebRESTProject/rest/Records/searchRecord/R001
    
//  REST 
    // GET – Search Record by ID
    @GET
    @Path("/searchRecord")
    @Produces(MediaType.TEXT_HTML)
    public String displayHTMLRecordInfo(@QueryParam("recordID") String recordID) throws FileNotFoundException {
        loadRecordList();

        String message = "";
        Record matchedRecord = new Record();
        boolean found = false;

        //looking for match
        for (Record record : recordList) {
            if (record.getRecordID().equalsIgnoreCase(recordID)) {
                matchedRecord = record;
                found = true;
                message = "Record Found!";
                break;
            }
        }

        String html = "<table><thead><tr>"
	                + "<th>Record ID</th>"
	                + "<th>Title</th>"
	                + "<th>Artist</th>"
	                + "<th>Genre</th>"
	                + "<th>Available Copies</th>"
	                + "<th>Total Copies</th>"
                + "</tr></thead><tbody>" ;
        
      //if no match
        if (!found) {
            message = "No record found with ID: " + recordID;
            html = "<p style=\"color:red\">" + message + "</p>";  
        }
        
        html += "<tr>"
        		+ "<td>" + matchedRecord.getRecordID() + "</td>"
                + "<td>" + matchedRecord.getTitle() + "</td>"
                + "<td>" + matchedRecord.getArtist() + "</td>"
                + "<td>" + matchedRecord.getGenre() + "</td>"
                + "<td>" + matchedRecord.getAvailableCopies() + "</td>" 
                + "<td>" + matchedRecord.getTotalCopies() + "</td>"
                + "</tr> <br/> <p >" + message + "</p>";
        
        

        return wrapHTML("Record Search", "Record ID Search: ", html);
    }
    
    //POST – Add New Record
    @POST
    @Path("/addNewRecord")
    @Produces(MediaType.TEXT_HTML)
    public String addNewRecordInfo(
        @FormParam("recordID") String recordID,
        @FormParam("title") String title,
        @FormParam("artist") String artist,
        @FormParam("genre") String genre,
        @FormParam("availableCopies") int availableCopies,
        @FormParam("totalCopies") int totalCopies) throws FileNotFoundException, UnsupportedEncodingException {

        loadRecordList();

        for (Record rec : recordList) {
            if (rec.getRecordID().equalsIgnoreCase(recordID)) {
                return wrapHTML("Add New Record", "Error", "<p style='color:red;'>Record ID already exists.</p>");
            }
        }

        recordList.add(new Record(recordID, title, artist, genre, availableCopies, totalCopies));
        return wrapHTML("Add New Record", "Success", generateRecordTableHTML());
    }
    
    // PUT – Update Record Info
    @PUT
    @Path("/updateRecordInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String updateRecordInfo(
        @QueryParam("recordID") String recordID,
        @QueryParam("title") String title,
        @QueryParam("artist") String artist,
        @QueryParam("genre") String genre,
        @QueryParam("availableCopies") int availableCopies,
        @QueryParam("totalCopies") int totalCopies) throws FileNotFoundException, UnsupportedEncodingException {

        loadRecordList();
        String message = "<p style='color:red;'>No record found with ID: " + recordID + "</p>";

        for (Record record : recordList) {
            if (record.getRecordID().equalsIgnoreCase(recordID)) {
                record.setTitle(title);
                record.setArtist(artist);
                record.setGenre(genre);
                record.setAvailableCopies(availableCopies);
                record.setTotalCopies(totalCopies);
                message = "<p style='color:green;'>Record " + recordID + " has been updated successfully.</p>";
                break;
            }
        }

        return wrapHTML("Update Record", "Updated", message + generateRecordTableHTML());
    }
    
    //DELETE – Delete Record
    @DELETE
    @Path("/deleteRecordInfo")
    @Produces(MediaType.TEXT_HTML)
    public String deleteRecord(@QueryParam("recordID") String recordID) throws FileNotFoundException, UnsupportedEncodingException {
        loadRecordList();
        String message = "<p style='color:red;'>No record found with ID: " + recordID + "</p>";

        for (int i = 0; i < recordList.size(); i++) {
            if (recordList.get(i).getRecordID().trim().equalsIgnoreCase(recordID.trim())) {
                recordList.remove(i);
                message = "<p style='color:blue;'>Record " + recordID + " has been deleted successfully.</p>";
                break;
            }
        }

        return wrapHTML("Delete Record", "Deleted", message + generateRecordTableHTML());
    }





    
}
