package test.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Inventory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="inventory", orphanRemoval=true)
	private Set<Item> items = new HashSet<Item>();
	@OneToMany(cascade=CascadeType.ALL, mappedBy="inventory", orphanRemoval=true)
	private Set<Category> categories = new HashSet<Category>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="inventory", orphanRemoval=true)
	private Set<Responsible> owners = new HashSet<Responsible>();

	
	public void addItem(Item item) {
		this.getItems().add(item);
	}
	
	public void addCategory(Category category) {
		this.getCategories().add(category);
	}
	
	public void addResponsible(Responsible responsible) {
		this.getOwners().add(responsible);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		// this.categories = categories;
		this.getCategories().clear();
		this.getCategories().addAll(categories);
	}

	public Set<Responsible> getOwners() {
		return owners;
	}

	public void setOwners(Set<Responsible> owners) {
		this.owners = owners;
	}

}
