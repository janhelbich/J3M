package cz.cvut.fel.j3mclient.app.model;


import java.io.Serializable;

public class Payment implements Serializable {

	private Long paymentId;

	private String name;

	private Price price;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
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
