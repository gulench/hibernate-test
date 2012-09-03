package test.hibernate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;



@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=false)
public class OrderPersistenceTests {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Order COPY = null;
	
	@Test
	@Transactional
	public void testSetupItemCategory() {
		Session session = sessionFactory.getCurrentSession();
		Item item = new Item("PROD", 88.99d);
		Category category = new Category("CAT1");
		

		session.save(category);
		session.save(item);

		ItemCategory itemCategory = new ItemCategory(category, item, "GULEN");
		session.save(itemCategory);
	}
	
	@Test
	@Transactional
	public void testSave() {
		Session session = sessionFactory.getCurrentSession();
		
		Order order = new Order();
		order.setCustomer("CUST");
		
		OrderItem item = new OrderItem((Item) session.load(Item.class, 1L), 12);
		item.setOrder(order);
		order.getItems().add(item);
		
		OrderItem item2 = new OrderItem((Item) session.load(Item.class, 1L), 22);
		item2.setOrder(order);
		order.getItems().add(item2);

		session.save(order);
		assertNotNull(order.getId());
		
		COPY = cloneOrder(order);
	}
	
	@Test
	@Transactional
	public void testMerge() {
		System.out.println("*** TESTING MERGE ***");
		
		Order order = cloneOrder(COPY);
		order.setCustomer("CUST2");
		
		for (OrderItem item: order.getItems()) {
			System.out.println("-- item: " + 
					// item.getItem().getId() + ": " + 
					item.getQuantity());
			if (item.getQuantity() == 12) {
				item.setQuantity(item.getQuantity() + 1);
			}
		}
		
		order = (Order) sessionFactory.getCurrentSession().merge(order);
		//sessionFactory.getCurrentSession().saveOrUpdate(order);
		
		// System.out.println("========================== Version: " + order.getVersion());
		COPY = cloneOrder(order);
	}

	/*
	@Test
	@Transactional
	public void testConcurrentModification() {
		System.out.println("*** TESTING CONCURRENT MODIFICATION ***");
		
		Order order = cloneOrder(COPY);
		order.setCustomer("CUST3");
		order.setVersion(1);
		
		order = (Order) sessionFactory.getCurrentSession().merge(order);
		//sessionFactory.getCurrentSession().saveOrUpdate(order);
	}
	*/

	private Order cloneOrder(Order order) {
		Order o = new Order();
		o.setId(order.getId());
		o.setVersion(order.getVersion());
		o.setCustomer(order.getCustomer());
		
		for (OrderItem item: order.getItems()) {
			OrderItem i = new OrderItem(item.getItem(), item.getQuantity());
			i.setId(item.getId());
			i.setVersion(item.getVersion());
			i.setOrder(o);
			o.getItems().add(i);
		}
		
		return o;
	}

/*
	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Order order = new Order();
		order.getItems().add(new Item());
		session.save(order);
		session.flush();
		assertNotNull(order.getId());
	}

	@Test
	@Transactional
	public void testSaveAndGet() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Order order = new Order();
		order.getItems().add(new Item());
		session.save(order);
		session.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		session.clear();
		Order other = (Order) session.get(Order.class, order.getId());
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}

	@Test
	@Transactional
	public void testSaveAndFind() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Order order = new Order();
		Item item = new Item();
		item.setProduct("foo");
		order.getItems().add(item);
		session.save(order);
		session.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		session.clear();
		Order other = (Order) session
				.createQuery( "select o from Order o join o.items i where i.product=:product")
				.setString("product", "foo").uniqueResult();
		assertEquals(1, other.getItems().size());
		assertEquals(other, other.getItems().iterator().next().getOrder());
	}
*/
}
