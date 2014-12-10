package cz.fel.j3m.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.fel.j3m.dao.BazaarDAO;
import cz.fel.j3m.model.BazaarOrder;
import cz.fel.j3m.model.Currency;
import cz.fel.j3m.model.OrderState;
import cz.fel.j3m.model.Price;
import cz.fel.j3m.model.Transport;

@Singleton
@Component
@Path(value = "/")
public class BazaarRESTServiceImpl implements BazaarRESTService {

	private static final Log log = LogFactory
			.getLog(BazaarRESTServiceImpl.class);

	@Autowired
	private BazaarDAO dao;

	@Override
	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello(@QueryParam("msg") String msg) {
		return "AHOJ!" + msg;
	}

	@Override
	@GET
	@Path("/neworders")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<BazaarOrder> getNewOrders() {
		return dao.findOrdersByState(OrderState.NEW_STATE);
	}

	// TODO userId
	@Override
	@PUT
	@Path("/addorders")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createNewOrders(@QueryParam("clientId") Long clientId,
									List<BazaarOrder> newOrders) {
		if (newOrders == null || newOrders.isEmpty()) {
			throw badRequestException();
		}

		try {
			dao.saveNewOrders(newOrders);
		} catch (Exception e) {
			log.error(e);
			throw internalServerError();
		}

		return Response.ok().build();
	}

	@Override
	@GET
	@Path("/order/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BazaarOrder getOrder(@PathParam("id") Long orderId) {
		BazaarOrder order = dao.findBazaarOrder(orderId);
		if (order == null) {
			throw notFoundException(orderId);
		}

		return order;
	}

	@Override
	@PUT
	@Path("/order")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BazaarOrder updateOrder(	@QueryParam("clientId") Long clientId,
											BazaarOrder order) {
		if (order == null) {
			throw badRequestException();
		} else if (order.getState() == null) {
			throw badRequestException("Order state must be set.");
		}

		try {
			return dao.updateOrder(order);
		} catch (Exception e) {
			log.error(e);
			throw internalServerError(e);
		}
	}

	@GET
	@Path("/import")
	public void importData() {
		
		createOrderState("Zaplacená", 1L);
		createOrderState("Odeslaná", 2L);
		createOrderState("Vyřešená", 3L);
		createOrderState("Pozastavená", 4L);
		createOrderState("Objednávka zrušena", 6L);
		
		Currency czk = new Currency();
		czk.setBid(BigDecimal.ONE);
		czk.setCurrencyCode("CZK");
		czk.setBidUpdated(new Date());
		dao.persist(czk);
		
		Transport t1 = new Transport();
		t1.setTransportId(1L);
		t1.setName("Osobní odběr");
		t1.setPrice(new Price(BigDecimal.ZERO, czk));
		
		Transport t2 = new Transport();
		t2.setTransportId(2L);
		t2.setName("Česká pošta");
		t2.setPrice(new Price(new BigDecimal(70), czk));
		
		Transport t3 = new Transport();
		t3.setTransportId(3L);
		t3.setName("PPL");
		t3.setPrice(new Price(new BigDecimal(120), czk));
		
		dao.persist(t1);
		dao.persist(t2);
		dao.persist(t3);
	}

	private void createOrderState(String name, Long id) {
		OrderState s = new OrderState();
		s.setName(name);
		s.setOrderStateId(id);
		dao.persist(s);
	}
	
	private InternalServerErrorException internalServerError() {
		return new InternalServerErrorException("Oops, something went wrong!");
	}

	private InternalServerErrorException internalServerError(Exception e) {
		return new InternalServerErrorException("Oops, something went wrong!",
				e);
	}

	private BadRequestException badRequestException() {
		return badRequestException("Empty request body is not accepted.");
	}
	
	private BadRequestException badRequestException(String msg) {
		return new BadRequestException(msg);
	}

	private NotFoundException notFoundException(Long orderId) {
		return new NotFoundException("Order with ID=" + orderId
				+ " does not exist.");
	}

}
