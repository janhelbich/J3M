package cz.fel.j3m.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name = "OrderProduct.findAll", query = "SELECT e FROM OrderProduct e")
})
@Table(name = "order_product")
public class OrderProduct implements Serializable {

	@Id
	@Column(name = "order_product_id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long orderProductId;

	@Embedded
	private Price price;

	@Column(name = "discount", nullable = true)
	private BigDecimal discount;

	@ManyToOne
	@JoinColumn(name = "bazaar_order")
	private BazaarOrder order;

	@ManyToOne
	@JoinColumn(name = "product")
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
