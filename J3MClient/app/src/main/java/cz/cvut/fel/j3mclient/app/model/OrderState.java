package cz.cvut.fel.j3mclient.app.model;

import java.io.Serializable;

public class OrderState implements Serializable {

	public static final Long NEW_STATE = 1L;
	public static final Long SENT_STATE = 2L;
	public static final Long PROCESSED_STATE = 3L;

	private Long orderStateId;

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
