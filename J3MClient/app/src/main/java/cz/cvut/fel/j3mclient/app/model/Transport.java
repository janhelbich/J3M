package cz.cvut.fel.j3mclient.app.model;

import java.io.Serializable;

public class Transport implements Serializable {

	private Long transportId;

	private String name;

	private Price price;

	public Long getTransportId() {
		return transportId;
	}

	public void setTransportId(Long transportId) {
		this.transportId = transportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

}
