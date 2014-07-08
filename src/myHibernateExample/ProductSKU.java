package myHibernateExample;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ProductSKU  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int productId;
	private String name;
	
	private List<Warehouse> whereProductAvailable;
	

	@Id
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name="Join_Product_Warehouse", 
			joinColumns={@JoinColumn(name="productId")},
			inverseJoinColumns={@JoinColumn(name="warehouseId")})
	public List<Warehouse> getWhereProductAvailable() {
		return whereProductAvailable;
	}

	public void setWhereProductAvailable(List<Warehouse> whereProductAvailable) {
		this.whereProductAvailable = whereProductAvailable;
	}

	

}
