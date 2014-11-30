package cz.fel.j3m.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import testutils.TestUtils;
import cz.fel.j3m.dao.BazaarDAO;
import cz.fel.j3m.model.BazaarOrder;
import cz.fel.j3m.model.BazaarProduct;
import cz.fel.j3m.model.Currency;
import cz.fel.j3m.model.OrderProduct;
import cz.fel.j3m.model.OrderState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/WEB-INF/context/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "txManager")
// extend the transactions to whole tests in order to roll back the tests
@Transactional
public class BazaarRESTServiceImplTest {
	
	private static final int ITERATIONS = 3;

	@Autowired
	private BazaarRESTService service;

	@Autowired
	private BazaarDAO dao;

	@Test
	public void testAddAndReadNewOrders() {
		Currency currency = getCurrency();
		OrderState newState = getOrderState(OrderState.NEW_STATE);
		OrderState sentState = getOrderState(OrderState.SENT_STATE);

		BazaarOrder o1 = TestUtils.getTestOrder(currency);
		BazaarOrder o2 = TestUtils.getTestOrder(currency);
		BazaarOrder o3 = TestUtils.getTestOrder(currency);
		List<BazaarOrder> orders = Arrays.asList(o1, o2, o3);
		
		for (BazaarOrder o : orders) {
			for (int i = 0; i < ITERATIONS; i++) {
				OrderProduct op = TestUtils.getTestOrderProduct(o, currency);
				dao.persist(op.getProduct());
				dao.persist(op);
			}
		}

		o1.setState(newState);
		o2.setState(newState);
		o3.setState(sentState);

		dao.persist(o1);
		dao.persist(o2);
		dao.persist(o3);
		dao.getEntityManager().flush();

		// import new orders
		service.createNewOrders(0L, orders);

		List<BazaarOrder> newOrders = service.getNewOrders();
		assertNotNull(newOrders);
		assertEquals(2, newOrders.size());
		for (BazaarOrder o : newOrders) {
			assertEquals(ITERATIONS, o.getProducts().size());
		}
		
		EntityManager em = dao.getEntityManager();
		TypedQuery<OrderProduct> allOps = em.createNamedQuery("OrderProduct.findAll", OrderProduct.class);
		assertEquals(orders.size() * ITERATIONS, allOps.getResultList().size());
		
		TypedQuery<BazaarProduct> allProds = em.createNamedQuery("BazaarProduct.findAll", BazaarProduct.class);
		assertEquals(orders.size() * ITERATIONS, allProds.getResultList().size());
	}

	@Test
	public void testUpdateOrder() {
		Currency currency = getCurrency();
		OrderState newState = getOrderState(OrderState.NEW_STATE);
		BazaarOrder order = TestUtils.getTestOrder(currency);
		order.setState(newState);

		dao.persist(order);
		dao.getEntityManager().flush();

		// castecny update - neni potreba konstruovat celou entitu
		BazaarOrder updatedOrder = new BazaarOrder();
		updatedOrder.setOrderId(order.getOrderId());
		updatedOrder.setState(getOrderState(OrderState.SENT_STATE));

		BazaarOrder o = service.updateOrder(0L, updatedOrder);
		assertNotNull(o);
		assertEquals(updatedOrder.getState(), o.getState());
		assertEquals(order.getEmail(), o.getEmail()); // treba
	}

	private Currency getCurrency() {
		Currency currency = TestUtils.getTestCurrency();
		dao.persist(currency);
		return currency;
	}

	private OrderState getOrderState(Long state) {
		// proste uz to mam v db, s tim se tu nebudu srat
		OrderState newState = dao.getEntityManager().find(OrderState.class,
				state);
		return newState;
	}

}
