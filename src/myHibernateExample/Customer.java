package myHibernateExample;

import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Customer {
	
	private int id;
	
	private String first_name;
	private String last_name;
	private String gender;
	private int age;
	
	private Address shippingAddress;
	private Address billingAddress;
	
	private List<Transaction> transactions;
	
	public Customer() {
		shippingAddress = new Address();
		billingAddress = new Address();
	}

	@Id
	@GeneratedValue
	@Column(name="customerId")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Embedded
	@AttributeOverrides( {
		@AttributeOverride(name="streetNumber" , column=@Column(name="shipping_streetNumber")),
		@AttributeOverride(name="streetName" , column=@Column(name="shipping_streetName")),
		@AttributeOverride(name="zipCode" , column=@Column(name="shipping_zipCode")),
		@AttributeOverride(name="city" , column=@Column(name="shipping_city")),
		@AttributeOverride(name="county" , column=@Column(name="shipping_county")),
		@AttributeOverride(name="country" , column=@Column(name="shipping_country"))
	})
	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Embedded 
	@AttributeOverrides( {
		@AttributeOverride(name="streetNumber" , column=@Column(name="billing_streetNumber")),
		@AttributeOverride(name="streetName" , column=@Column(name="billing_streetName")),
		@AttributeOverride(name="zipCode" , column=@Column(name="billing_zipCode")),
		@AttributeOverride(name="city" , column=@Column(name="billing_city")),
		@AttributeOverride(name="county" , column=@Column(name="billing_county")),
		@AttributeOverride(name="country" , column=@Column(name="billing_country"))
	})
	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	@OneToMany(targetEntity=Transaction.class , mappedBy="c", fetch=FetchType.EAGER)
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	

}
