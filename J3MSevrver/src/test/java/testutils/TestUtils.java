package testutils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import cz.fel.j3m.model.BazaarOrder;
import cz.fel.j3m.model.BazaarProduct;
import cz.fel.j3m.model.Currency;
import cz.fel.j3m.model.OrderProduct;
import cz.fel.j3m.model.Price;

public class TestUtils {

	public static BazaarOrder getTestOrder(Currency currency) {
		BazaarOrder order = new BazaarOrder();
		order.setCity("test mesto");
		order.setEmail("test mail");
		order.setFirstName("test first name");
		order.setSurname("test surname");
		order.setHouseNumber("test house number");
		order.setOrderDate(new Date());
		order.setZip("test zip");
		order.setOrderUrl("test order url");
		order.setOrderId(new Random().nextLong());

		Price price = getPrice(currency);
		order.setPrice(price);

		return order;
	}

	private static Price getPrice(Currency currency) {
		Price price = new Price();
		price.setAmount(new BigDecimal(100.90));
		price.setCurrency(currency);
		return price;
	}
	
	public static OrderProduct getTestOrderProduct(BazaarOrder o, Currency c) {
		BazaarProduct p = new BazaarProduct();
		p.setName("triko test");
		p.setPhotoUrl("test url");
		p.setPrice(getPrice(c));
		p.setProductSize("XL");
		p.setProductId(new Random().nextLong());
		
		OrderProduct op = new OrderProduct();
		op.setDiscount(BigDecimal.ZERO);
		op.setOrder(o);
		op.setPrice(p.getPrice());
		op.setProduct(p);
		op.setOrderProductId(new Random().nextLong());
		o.getProducts().add(op);
		
		return op;
	}

	public static Currency getTestCurrency() {
		Currency currency = new Currency();
		currency.setCurrencyCode("CZK");
		currency.setBid(BigDecimal.ONE);
		currency.setBidUpdated(new Date());
		return currency;
	}
}
