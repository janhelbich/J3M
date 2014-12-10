package cz.fel.j3m.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
		@NamedQuery(name = "BazaarOrder.findAll", query = "SELECT e FROM BazaarOrder e"),
		@NamedQuery(name = "BazaarOrder.findByState", query = "SELECT e FROM BazaarOrder e where e.state.orderStateId = :state") })
@Table(name = "bazaar_order")
public class BazaarOrder implements Serializable {

	@Id
	@Column(name = "order_id")
	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "state")
	private OrderState state;

	@ManyToOne
	@JoinColumn(name = "payment")
	private Payment payment;

	@ManyToOne
	@JoinColumn(name = "transport")
	private Transport transport;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderProduct> products;

	@NotNull
	@Embedded
	private Price price;

	@NotNull
	@Column(name = "email")
	private String email;

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "surname")
	private String surname;

	@NotNull
	@Column(name = "street")
	private String street;

	@NotNull
	@Column(name = "city")
	private String city;

	@NotNull
	@Column(name = "house_num")
	private String houseNumber;

	@NotNull
	@Column(name = "zip")
	private String zip;

	@Column(name = "customer_note", length = 5000)
	private String customerNote;

	@NotNull
	@Column(name = "order_url")
	private String orderUrl;

	@NotNull
	@Column(name = "order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public List<OrderProduct> getProducts() {
		if (products == null) {
			products = new ArrayList<>();
		}
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCustomerNote() {
		return customerNote;
	}

	public void setCustomerNote(String customerNote) {
		this.customerNote = customerNote;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
