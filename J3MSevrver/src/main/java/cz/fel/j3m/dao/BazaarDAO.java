package cz.fel.j3m.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import cz.fel.j3m.model.BazaarOrder;
import cz.fel.j3m.model.OrderState;

public interface BazaarDAO {

	@Transactional(readOnly = true)
	public List<BazaarOrder> findAllOrders();
	
	@Transactional(readOnly = true)
	public List<OrderState> findAllStates();

	@Transactional(readOnly = true)
	public List<BazaarOrder> findOrdersByState(Long state);

	@Transactional
	public OrderState findOrderState(Long state);

	@Transactional
	public <E> void persist(E entity);

	@Transactional
	public void saveNewOrders(List<BazaarOrder> orders) throws Exception;

	@Transactional
	public BazaarOrder findBazaarOrder(Long orderId);

	@Transactional
	public BazaarOrder updateOrder(BazaarOrder order) throws Exception;

	public EntityManager getEntityManager();
	
	@Transactional
	public <E> E find(Object id, Class<E> clazz);

}
