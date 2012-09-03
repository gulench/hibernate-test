package test.hibernate.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemCategory {
	/*
	@Embeddable
	public static class Id implements Serializable {
		@Column
		private Long categoryId;
		@Column
		private Long itemId;
		
		public Id() {
			
		}
		
		public Id(Long categoryId, Long itemId) {
			super();
			this.categoryId = categoryId;
			this.itemId = itemId;
		}
		
	}
	
	@EmbeddedId
	private Id id = new Id();
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="categoryId"/*, insertable=false, updatable=false*/)
	private Category category;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="itemId"/*, insertable=false, updatable=false*/)
	private Item item;
	
	private String addedBy;
	private Date addedOn;
	
	public ItemCategory() {
		
	}
	
	public ItemCategory(Category category, Item item, String addedBy) {
		this.addedBy = addedBy;
		this.category = category;
		this.item = item;
		
		/*
		this.id.categoryId = category.getId();
		this.id.itemId = item.getId();
		*/
		category.getItemCategories().add(this);
		item.getItemCategories().add(this);
	}

	/*
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}
	*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}
	
	
// http://download.springsource.com/release/STS/3.0.0/dist/e4.2/spring-tool-suite-3.0.0.RELEASE-e4.2-win32-x86_64.zip
}
