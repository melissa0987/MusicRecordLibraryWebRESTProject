//Melissa Louise Bangloy
package musicRecordLibrary;

public class Record {
	private String recordID;
    private String title;
    private String artist;
    private String genre;
    private int availableCopies;
    private int totalCopies;
    
  //default constructor
    public Record( ) {
		super();
		this.recordID = "";
		this.title = "";
		this.artist = "";
		this.genre = "";
		this.availableCopies = 0;
		this.totalCopies = 0;
	} 
  //param constructor
	public Record(String recordID, String title, String artist, String genre, int availableCopies, int totalCopies) {
		super();
		this.recordID = recordID;
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.availableCopies = availableCopies;
		this.totalCopies = totalCopies;
	}
	
	//getters
	public String getRecordID() { return recordID; }
	public String getTitle() { return title; }
	public String getArtist() { return artist; }
	public String getGenre() { return genre; }
	public int getAvailableCopies() { return availableCopies; }
	public int getTotalCopies() { return totalCopies; }
	
	//setters
	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}
	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}
	
	//string
	@Override
	public String toString() {
		return "Record [recordID=" + recordID 
				+ ", title=" + title 
				+ ", artist=" + artist 
				+ ", genre=" + genre
				+ ", availableCopies="  + availableCopies 
				+ ", totalCopies=" + totalCopies + "]";
	} 
	
   
     
    
	 
	
	
	
	
	
    
    
    
}
