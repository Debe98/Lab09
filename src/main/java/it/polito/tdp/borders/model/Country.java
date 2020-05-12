package it.polito.tdp.borders.model;

public class Country {
	
	private int cCode;
	private String stateAbb;
	private String stateName;
	
	public Country(int cCode, String stateAbb, String stateName) {
		super();
		this.cCode = cCode;
		this.stateAbb = stateAbb;
		this.stateName = stateName;
	}

	public int getcCode() {
		return cCode;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public String getStateName() {
		return stateName;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cCode;
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
		Country other = (Country) obj;
		if (cCode != other.cCode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return stateName;
	}
	
	
}
