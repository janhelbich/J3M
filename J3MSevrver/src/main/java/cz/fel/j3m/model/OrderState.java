package cz.fel.j3m.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
		@NamedQuery(name = "OrderState.findAll", query = "SELECT e FROM OrderState e"),
		@NamedQuery(name = "OrderState.findByState", query = "SELECT e FROM OrderState e where e.orderStateId = :state") })
@Table(name = "order_state")
public class OrderState implements Serializable {

	public static final Long NEW_STATE = 1L;
	public static final Long SENT_STATE = 2L;
	public static final Long PROCESSED_STATE = 3L;

	// 1 - new, 2 - processed, 3 - sent, ... whatever
	@Id
	@Column(name = "order_state_id")
	private Long orderStateId;

	@Column(name = "name")
	private String name;

	public Long getOrderStateId() {
		return orderStateId;
	}

	public void setOrderStateId(Long orderStateId) {
		this.orderStateId = orderStateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
