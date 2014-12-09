package cz.cvut.fel.j3mclient.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Price implements Serializable {

	private BigDecimal amount;

	private Currency currency;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
