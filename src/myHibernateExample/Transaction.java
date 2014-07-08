package myHibernateExample;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

@Entity
public class Transaction {

	private int id;
	private Customer c;
	private ProductSKU p;
	private int quantity;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="customerId")
	public Customer getC() {
		return c;
	}
	public void setC(Customer c) {
		this.c = c;
	}
	
	// 1 SKU => n transaction
	
	@ManyToOne
	@JoinColumn(name="productId")
	public ProductSKU getP() {
		return p;
	}
	public void setP(ProductSKU p) {
		this.p = p;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
