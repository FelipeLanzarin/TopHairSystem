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

import br.ths.utils.OrderStatus;

@Entity
@Table(name="ths_order")
public class Order {
	@Id
    @SequenceGenerator(name="SEQ_ORDER_ID", sequenceName="GEN_ORDER_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_ORDER_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Date scheduler;
	private Double amount;
	private Double discount;
	@Column(name="sub_total_amount")
	private Double subTotalAmount;
	@Column(length=9999)
	private String description;
	@Column(name="creation_date")
	private Date creationDate;
	private String status;
	@Column(name="payment_status")
	private String paymentStatus;
	@Column(name="is_attendance")
	private Boolean isAttendance;
	@ManyToOne
	@JoinColumn(name="profile_id", referencedColumnName="id")
	private Profile profile;
	@ManyToOne
	@JoinColumn(name="employee_id", referencedColumnName="id")
	private Employee employee;
	
	public Order(){
		this.status = OrderStatus.OPEN;
		this.amount = 0.0d;
		this.subTotalAmount = 0.0d;
		this.discount = 0.0d;
		this.creationDate = new Date();
		this.paymentStatus = OrderStatus.WAITING_PAYMENT;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getScheduler() {
		return scheduler;
	}
	public void setScheduler(Date scheduler) {
		this.scheduler = scheduler;
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
	public Double getSubTotalAmount() {
		return subTotalAmount;
	}
	public void setSubTotalAmount(Double subTotalAmount) {
		this.subTotalAmount = subTotalAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Boolean getIsAttendance() {
		return isAttendance;
	}

	public void setIsAttendance(Boolean isAttendance) {
		this.isAttendance = isAttendance;
	}
}
