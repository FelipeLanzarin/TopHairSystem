package br.ths.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="commerce_item")
public class CommerceItem {
	@Id
    @SequenceGenerator(name="SEQ_COMMERCE_ITEM_ID", sequenceName="GEN_COMMERCE_ITEM_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_COMMERCE_ITEM_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Integer quantity;
	@Column(name="unit_price")
	private Double unitPrice;
	private Double amount;
	private Double discount;
	@Column(name="cost_price")
	private Double costPrice;
	private String note;
	@Column(name="creation_date")
	private Date creationDate;
	@ManyToOne
	@JoinColumn(name="product_id", referencedColumnName="id")
	private Product product;
	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName="id")
	private Order order;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
}
