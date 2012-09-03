package test.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String product;
	@Column
	private double price;
	
	@ManyToOne
	private Inventory inventory;

	@OneToMany(mappedBy="item"/*, cascade=CascadeType.ALL*/)
	private Set<ItemCategory> itemCategories = new HashSet<ItemCategory>();

	public Item() {
	}

	public Item(String product, double price) {
		this.product = product;
		this.price = price;
	}
	
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<ItemCategory> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(Set<ItemCategory> itemCategories) {
		this.itemCategories = itemCategories;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	
}