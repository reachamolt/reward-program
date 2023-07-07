package adt.demo.rewardprogram.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {

	private BigDecimal amount;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@Id
	@GeneratedValue
	private Long id;

	private LocalDateTime purchaseDate;

	public TransactionRecord() {
		super();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

}
