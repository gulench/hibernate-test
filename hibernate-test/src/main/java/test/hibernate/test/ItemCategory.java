package test.hibernate.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemCategory {
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
	
	@ManyToOne
	@JoinColumn(name="categoryId", insertable=false, updatable=false)
	private Category category;

	@ManyToOne
	@JoinColumn(name="itemId", insertable=false, updatable=false)
	private Item item;
	
	private String addedBy;
	private Date addedOn;
	
	public ItemCategory() {
		
	}
	
	public ItemCategory(Category category, Item item, String addedBy) {
		this.addedBy = addedBy;
		this.category = category;
		this.item = item;
		
		this.id.categoryId = category.getId();
		this.id.itemId = item.getId();
		
		category.getItemCategories().add(this);
		item.getItemCategories().add(this);
	}
// http://download.springsource.com/release/STS/3.0.0/dist/e4.2/spring-tool-suite-3.0.0.RELEASE-e4.2-win32-x86_64.zip
}
