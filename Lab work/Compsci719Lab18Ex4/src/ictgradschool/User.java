package ictgradschool;

public class User {

	
	private String fName;
	private String lName;
	private String nName;
	private String email;

	User(String fName, String lName, String nName, String email){
		      this.fName = fName;
		      this.lName = lName;
		      this.nName = nName;
		      this.email = email;
		   }

	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}
	
	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}
	
	
	
	
}
