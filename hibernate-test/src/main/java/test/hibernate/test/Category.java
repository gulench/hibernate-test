package test.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;

	@OneToMany(mappedBy="category")
	private Set<ItemCategory> itemCategories = new HashSet<ItemCategory>();
	
	public Category() {
		
	}
	
	public Category(String name) {
		this.name = name;
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

	public Set<ItemCategory> getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(Set<ItemCategory> itemCategories) {
		this.itemCategories = itemCategories;
	}
	
	
}
