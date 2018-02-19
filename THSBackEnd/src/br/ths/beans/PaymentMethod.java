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
@Table(name="payment_method")
public class PaymentMethod {
	@Id
    @SequenceGenerator(name="SEQ_PAYMENT_METHOD_ID", sequenceName="GEN_PAYMENT_METHOD_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_PAYMENT_METHOD_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double amount;
	@Column(name="creation_date")
	private Date creationDate;
	private String type;
	@ManyToOne
	@JoinColumn(name="installment_id", referencedColumnName="id")
	private Installment installment;
	@ManyToOne
	@JoinColumn(name="payment_id", referencedColumnName="id")
	private Payment payment;
	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName="id")
	private Order order;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Installment getInstallment() {
		return installment;
	}
	public void setInstallment(Installment installment) {
		this.installment = installment;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
