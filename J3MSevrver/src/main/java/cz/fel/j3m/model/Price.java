package cz.fel.j3m.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Price implements Serializable {

	public Price(){
	}

	public Price(BigDecimal amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "currency")
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
