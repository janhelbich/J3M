package cz.cvut.fel.j3mclient.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Currency implements Serializable {

	private String currencyCode;

	private BigDecimal bid;

	private Date bidUpdated;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public Date getBidUpdated() {
		return bidUpdated;
	}

	public void setBidUpdated(Date bidUpdated) {
		this.bidUpdated = bidUpdated;
	}

	@Override
	public String toString() {
		return "Currency [currencyCode=" + currencyCode + ", bid=" + bid
				+ ", bidUpdated=" + bidUpdated + "]";
	}

}
