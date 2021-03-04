package com.revature.model;

public class Customer {
	private int customerId;
	private String cUsername;
	private String cPassword;
	private String cFirstName;
	private String cLastname;
	
	public Customer() {
		super();
	}
	
	public Customer(int customerId, String cUsername, String cPassword, String cFirstName, String cLastname) {
		super();
		this.customerId = customerId;
		this.cUsername = cUsername;
		this.cPassword = cPassword;
		this.cFirstName = cFirstName;
		this.cLastname = cLastname;
	}

	public Customer(String cUsername, String cPassword, String cFirstName, String cLastname) {
		super();
		this.cUsername = cUsername;
		this.cPassword = cPassword;
		this.cFirstName = cFirstName;
		this.cLastname = cLastname;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getcUsername() {
		return cUsername;
	}

	public void setcUsername(String cUsername) {
		this.cUsername = cUsername;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

	public String getcFirstName() {
		return cFirstName;
	}

	public void setcFirstName(String cFirstName) {
		this.cFirstName = cFirstName;
	}

	public String getcLastname() {
		return cLastname;
	}

	public void setcLastname(String cLastname) {
		this.cLastname = cLastname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cFirstName == null) ? 0 : cFirstName.hashCode());
		result = prime * result + ((cLastname == null) ? 0 : cLastname.hashCode());
		result = prime * result + ((cPassword == null) ? 0 : cPassword.hashCode());
		result = prime * result + ((cUsername == null) ? 0 : cUsername.hashCode());
		result = prime * result + customerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (cFirstName == null) {
			if (other.cFirstName != null)
				return false;
		} else if (!cFirstName.equals(other.cFirstName))
			return false;
		if (cLastname == null) {
			if (other.cLastname != null)
				return false;
		} else if (!cLastname.equals(other.cLastname))
			return false;
		if (cPassword == null) {
			if (other.cPassword != null)
				return false;
		} else if (!cPassword.equals(other.cPassword))
			return false;
		if (cUsername == null) {
			if (other.cUsername != null)
				return false;
		} else if (!cUsername.equals(other.cUsername))
			return false;
		if (customerId != other.customerId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [Username=" + cUsername + ", Password=" + cPassword
				+ ", First Name=" + cFirstName + ", Last Name=" + cLastname + "]";
	}

}
