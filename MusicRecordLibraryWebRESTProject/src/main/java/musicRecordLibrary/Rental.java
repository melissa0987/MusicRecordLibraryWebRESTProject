package musicRecordLibrary;

import java.time.LocalDate;

public class Rental {
	private String rentalId;
    private Client client;
    private Record record;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    //default constructor
    public Rental( ) { 
		this.rentalId = "";
		this.client = new Client();
		this.record = new Record();
		this.rentalDate = LocalDate.now(); //todays date
		this.dueDate = LocalDate.now().plusDays(5);
		this.returnDate = null;
	}
    //constructor default today rental date
  	public Rental(String rentalId, Client client, Record record, LocalDate returnDate) {
  		super();
  		this.rentalId = rentalId;
  		this.client = client;
  		this.record = record;
  		this.rentalDate = LocalDate.now(); //dafualt todays date
  		this.dueDate = LocalDate.now().plusDays(5);
  		this.returnDate = returnDate;
  	}
    //constructor, all param
	public Rental(String rentalId, Client client, Record record, LocalDate rentalDate,LocalDate dueDate, LocalDate returnDate) {
		super();
		this.rentalId = rentalId;
		this.client = client;
		this.record = record;
		this.rentalDate = rentalDate;
		this.dueDate = rentalDate.plusDays(5);
		this.returnDate = returnDate;
	}
    //getters
	public String getRentalId() { return rentalId; }
	public Client getClient() { return client; }
	public Record getRecord() { return record; }
	public LocalDate getRentalDate() { return rentalDate; }
	public LocalDate getDueDate() { return dueDate; }
	public LocalDate getReturnDate() { return returnDate; }
	
	//setters
	public void setRentalId(String rentalId) {
		this.rentalId = rentalId;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public void setRecord(Record record) {
		this.record = record;
	}
	public void setRentalDate(LocalDate rentalDate) {
		this.rentalDate = rentalDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	@Override
	public String toString() {
		return "Rental [rentalId=" + rentalId 
				+ ", clientId=" + client.getClientId() 
				+ ", recordID=" + record.getRecordID()
				+ ", rentalDate=" + rentalDate 
				+ ", returnDate=" + returnDate + "]";
	}
	
	
    
}
