package cz.cvut.fel.j3mclient.app.model;


import java.io.Serializable;
import java.math.BigDecimal;

public class OrderProduct implements Serializable {

	private Long orderProductId;

	private Price price;

	private BigDecimal discount;

	private BazaarOrder order;

	private BazaarProduct product;

	public Long getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Long orderProductId) {
		this.orderProductId = orderProductId;
	}

	public BazaarProduct getProduct() {
		return product;
	}

	public void setProduct(BazaarProduct product) {
		this.product = product;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BazaarOrder getOrder() {
		return order;
	}

	public void setOrder(BazaarOrder order) {
		this.order = order;
	}

}
