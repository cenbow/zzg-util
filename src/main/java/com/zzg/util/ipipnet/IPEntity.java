package com.zzg.util.ipipnet;

public class IPEntity {
	private String county;
	private String provinces;
	private String city;

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getProvinces() {
		return provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "IPEntity [county=" + county + ", provinces=" + provinces + ", city=" + city + "]";
	}

}
