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
@Table(name="installment")
public class Installment {
	@Id
    @SequenceGenerator(name="SEQ_INSTALLMENT_ID", sequenceName="GEN_INSTALLMENT_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_INSTALLMENT_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double amount;
	@Column(name="amount_payed")
	private Double amountPayed;
	@Column(name="payment_date")
	private Date paymentDate;
	@Column(name="pay_date")
	private Date payDate;
	@Column(name="payment_method")
	private Date paymentMethod;
	@ManyToOne
	@JoinColumn(name="payment_id", referencedColumnName="id")
	private Payment payment;
	
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
	public Double getAmountPayed() {
		return amountPayed;
	}
	public void setAmountPayed(Double amountPayed) {
		this.amountPayed = amountPayed;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Date paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
