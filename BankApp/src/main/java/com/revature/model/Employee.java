package com.revature.model;

public class Employee {
	private int employeeId;
	private String eUsername;
	private String ePassword;
	private String eFirstName;
	private String eLastName;
	
	public Employee() {
	}
	
	public Employee(int eId, String eUsername, String ePassword, String eFirstName, String eLastName) {
		super();
		this.employeeId = eId;
		this.eUsername = eUsername;
		this.ePassword = ePassword;
		this.eFirstName = eFirstName;
		this.eLastName = eLastName;
	}
	public int geteId() {
		return employeeId;
	}
	public void seteId(int eId) {
		this.employeeId = eId;
	}
	public String geteUsername() {
		return eUsername;
	}
	public void seteUsername(String eUsername) {
		this.eUsername = eUsername;
	}
	public String getePassword() {
		return ePassword;
	}
	public void setePassword(String ePassword) {
		this.ePassword = ePassword;
	}
	public String geteFirstName() {
		return eFirstName;
	}
	public void seteFirstName(String eFirstName) {
		this.eFirstName = eFirstName;
	}
	public String geteLastName() {
		return eLastName;
	}
	public void seteLastName(String eLastName) {
		this.eLastName = eLastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eFirstName == null) ? 0 : eFirstName.hashCode());
		result = prime * result + ((eLastName == null) ? 0 : eLastName.hashCode());
		result = prime * result + ((ePassword == null) ? 0 : ePassword.hashCode());
		result = prime * result + ((eUsername == null) ? 0 : eUsername.hashCode());
		result = prime * result + employeeId;
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
		Employee other = (Employee) obj;
		if (eFirstName == null) {
			if (other.eFirstName != null)
				return false;
		} else if (!eFirstName.equals(other.eFirstName))
			return false;
		if (eLastName == null) {
			if (other.eLastName != null)
				return false;
		} else if (!eLastName.equals(other.eLastName))
			return false;
		if (ePassword == null) {
			if (other.ePassword != null)
				return false;
		} else if (!ePassword.equals(other.ePassword))
			return false;
		if (eUsername == null) {
			if (other.eUsername != null)
				return false;
		} else if (!eUsername.equals(other.eUsername))
			return false;
		if (employeeId != other.employeeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", eUsername=" + eUsername + ", ePassword=" + ePassword
				+ ", eFirstName=" + eFirstName + ", eLastName=" + eLastName + "]";
	}
	
	
}
