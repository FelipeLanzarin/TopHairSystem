package br.ths.beans;

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
@Table(name="image")
public class Image {
	@Id
    @SequenceGenerator(name="SEQ_IMAGE_ID", sequenceName="GEN_IMAGE_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_IMAGE_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private String path;
	@Column(length=9999)
	private String description;
	@Column(name="partOfProcess")
	private String partOfProcess;
	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName="id")
	private Order order;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPartOfProcess() {
		return partOfProcess;
	}
	public void setPartOfProcess(String partOfProcess) {
		this.partOfProcess = partOfProcess;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
