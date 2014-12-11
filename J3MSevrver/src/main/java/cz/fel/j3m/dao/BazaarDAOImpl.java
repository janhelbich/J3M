package cz.fel.j3m.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import cz.fel.j3m.model.BazaarOrder;
import cz.fel.j3m.model.OrderState;
import cz.fel.j3m.model.Payment;
import cz.fel.j3m.model.Transport;

@Repository  
public class BazaarDAOImpl implements BazaarDAO {

	@PersistenceContext
	private EntityManager entityManger;

	@Override
	public List<BazaarOrder> findAllOrders() {
		TypedQuery<BazaarOrder> q = entityManger.createNamedQuery(
				"BazaarOrder.findAll", BazaarOrder.class);

		return q.getResultList();
	}

	@Override
	public List<BazaarOrder> findOrdersByState(Long state) {
		TypedQuery<BazaarOrder> q = entityManger.createNamedQuery(
				"BazaarOrder.findByState", BazaarOrder.class);
		q.setParameter("state", state);

		return q.getResultList();
	}

	@Override
	public List<OrderState> findAllStates() {
		TypedQuery<OrderState> q = entityManger.createNamedQuery(
				"OrderState.findAll", OrderState.class);
		return q.getResultList();
	}

	@Override
	public OrderState findOrderState(Long state) {
		TypedQuery<OrderState> q = entityManger.createNamedQuery(
				"OrderState.findByState", OrderState.class);
		q.setParameter("state", state);

		List<OrderState> states = q.getResultList();
		if (!states.isEmpty()) {
			return states.get(0);
		}
		return null;
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManger;
	}

	@Override
	public <E> void persist(E entity) {
		entityManger.persist(entity);
	}

	@Override
	public void saveNewOrders(List<BazaarOrder> orders) throws Exception {
		for (BazaarOrder order : orders) {
			BazaarOrder find = entityManger.find(BazaarOrder.class,
					order.getOrderId());

			if (find == null) {
				entityManger.persist(order);
			} else if (find.getState().getOrderStateId() < OrderState.SENT_STATE) {
				entityManger.merge(order);
			}
		}
	}

	@Override
	public BazaarOrder findBazaarOrder(Long orderId) {
		return entityManger.find(BazaarOrder.class, orderId);
	}

	@Override
	public BazaarOrder updateOrder(BazaarOrder order) throws Exception {
		BazaarOrder managed = entityManger.find(BazaarOrder.class,
				order.getOrderId());

		if (managed == null) {
			throw new EntityNotFoundException();
		}

		// yeah - musim rozebrat, protoze muze prijit order napr.
		managed.setState(order.getState());
		if (notEmpty(order.getCity())) {
			managed.setCity(order.getCity());
		}
		if (notEmpty(order.getCustomerNote())) {
			managed.setCustomerNote(order.getCustomerNote());
		}
		if (notEmpty(order.getEmail())) {
			managed.setEmail(order.getEmail());
		}
		if (notEmpty(order.getFirstName())) {
			managed.setFirstName(order.getFirstName());
		}
		if (notEmpty(order.getHouseNumber())) {
			managed.setHouseNumber(order.getHouseNumber());
		}
		if (notEmpty(order.getOrderUrl())) {
			managed.setOrderUrl(order.getOrderUrl());
		}
		if (notEmpty(order.getStreet())) {
			managed.setStreet(order.getStreet());
		}
		if (notEmpty(order.getSurname())) {
			managed.setSurname(order.getSurname());
		}
		if (notEmpty(order.getZip())) {
			managed.setZip(order.getZip());
		}
		if (order.getPayment() != null && managed.getPayment() == null) {
			Payment payment = order.getPayment();
			managed.setPayment(payment);
			entityManger.persist(payment);
		}
		if (order.getTransport() != null && managed.getTransport() == null) {
			Transport transport = order.getTransport();
			managed.setTransport(transport);
			entityManger.persist(transport);
		}

		return entityManger.merge(managed);
	}
	
	

	@Override
	public <E> E find(Object id, Class<E> clazz) {
		return entityManger.find(clazz, id);
	}

	public boolean notEmpty(String s) {
		return s != null && !"".equals(s);
	}

}
