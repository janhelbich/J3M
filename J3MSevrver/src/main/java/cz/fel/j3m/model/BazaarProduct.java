package cz.fel.j3m.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name = "BazaarProduct.findAll", query = "SELECT e FROM BazaarProduct e")
})
@Table(name = "bazar_product")
public class BazaarProduct implements Serializable {

	@Id
	@Column(name = "order_id")
	private Long productId;

	@Column(name = "name")
	private String name;

	@Column(name = "product_size")
	private String productSize;

	@Column(name = "photo_url")
	private String photoUrl;

	@Embedded
	private Price price;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	
}
