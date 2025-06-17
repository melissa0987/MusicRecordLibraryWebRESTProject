//Melissa Louise Bangloy
package musicRecordLibrary;

public class Client {
	private int clientId;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private boolean isActive;
    
    //default constructor 
	public Client( ) { 
		this.clientId = 0;
		this.firstName = "";
		this.lastName = "";
		this.phoneNo = "";
		this.email = "";
		this.isActive = false;
	}

	//constructor with param
	public Client(int clientId, String firstName, String lastName, String phoneNo, String email, boolean isActive) {
		super();
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.isActive = isActive;
	}
	//constructor with param with autoisActive: true
		public Client(int clientId, String firstName, String lastName, String phoneNo, String email) {
			super();
			this.clientId = clientId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.phoneNo = phoneNo;
			this.email = email;
			this.isActive = true; //automatically activates membership
		}

	//getters
	public int getClientId() { return clientId; } 
	public String getFirstName() { return firstName; } 
	public String getLastName() { return lastName; } 
	public String getPhoneNo() { return phoneNo; } 
	public String getEmail() { return email; } 
	public boolean getIsActive() { return isActive; }

	//setters
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setisActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId 
				+ ", firstName=" + firstName 
				+ ", lastName=" + lastName 
				+ ", phoneNo=" + phoneNo 
				+ ", email=" + email 
				+ ", isActive=" + isActive + "]";
	} 
	
    
	
	
	
	
	
    
    
}
