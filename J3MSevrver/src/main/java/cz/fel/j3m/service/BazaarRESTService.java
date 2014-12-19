package cz.fel.j3m.service;

import java.util.List;

import javax.ws.rs.core.Response;

import cz.fel.j3m.model.BazaarOrder;

public interface BazaarRESTService {

	/**
	 * Test method.
	 * @param msg query parameter
	 * @return "AHOJ!" + msg
	 */
	public String getHello(String msg);

	/**
	 * Returns all orders with order status = NEW.
	 * @return new orders
	 */
	public List<BazaarOrder> getNewOrders();

	/**
	 * Adds new orders from bazaar to REST API database.
	 * @param newOrders
	 * @param clientId client authentication
	 * @return response
	 */
	public Response createNewOrders(Long clientId, List<BazaarOrder> newOrders);

	/**
	 * Returns detail of specific BazaarOrder.
	 * @param orderId order ID
	 * @return
	 */
	public BazaarOrder getOrder(Long orderId);

	/**
	 * Updates order status.
	 * @param order to be updated
	 * @return
	 */
	public BazaarOrder updateOrder(Long clientId, BazaarOrder order);

	List<BazaarOrder> getAllOrders();

}