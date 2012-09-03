package test.hibernate.test;

import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=false)
public class CopyUsingEvictTest {
	@Autowired
	SessionFactory sessionFactory;

	@Test
	@Transactional
	public void test() {
		Inventory inventory = new Inventory();
		inventory.setName("INV");
		
		Category category = new Category("HAZ");
		inventory.addCategory(category);
		Item item = new Item("GAS", 122D);
		inventory.addItem(item);
		Responsible responsible = new Responsible("OWNER");
		inventory.addResponsible(responsible);
		
		Session session = this.sessionFactory.getCurrentSession();
		/*
		session.save(category);
		session.save(item);
		*/
		Long inventoryId = (Long) session.save(inventory);
		session.flush();
		
		ItemCategory itemCategory = new ItemCategory(category, item, "GULEN");
		session.save(itemCategory);
		session.flush();

		inventory = (Inventory) session.get(Inventory.class, inventoryId);
		
		/*
		 * Evict
		 */
		System.out.println("*** Evicting ... ***");
		
		//session.setReadOnly(inventory, true);
		//session.delete(inventory);

		session.clear();
		// inventory.getCategories().clear();
/*
		session.evict(itemCategory);
//		session.evict(category);
//		session.evict(item);
//		session.evict(responsible);
		for (Category c: inventory.getCategories()) {
			session.evict(c);
		}
		session.evict(inventory.getCategories());
		session.evict(inventory);
*/
		
		session.flush();

		inventory.setId(null);
		item.setId(null);
		category.setId(null);
		responsible.setId(null);
		itemCategory.setId(null);
		
		//inventory.addCategory(category);
		/*
		session.save(category);
		session.save(item);
		*/
		
		/*
		 * MERGE: cascades, tries saving ItemCategory and fails, saying
		 *     save the transient instance before merging: test.hibernate.test.ItemCategory 
		 */
		session.save(inventory);
		session.save(item);
		session.save(category);
		session.save(responsible);
		session.save(itemCategory);
		inventory = (Inventory) session.merge(inventory);
		session.flush();
		
		session.evict(itemCategory);
		for (Category c: inventory.getCategories()) {
			session.evict(c);
		}
		session.evict(inventory.getCategories());
		session.evict(inventory);

		session.flush();
		
		//itemCategory = new ItemCategory(category, item, "JIMMY");
		//-session.save(itemCategory);
		
		
	}

}
