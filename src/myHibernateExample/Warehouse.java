package myHibernateExample;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Warehouse {
	
	private int warehouseId;
	private String name;
	private List<ProductSKU> productsInInventory = new LinkedList<ProductSKU>();
	
	@Id
	@GeneratedValue
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToMany
	@JoinTable(name="Join_Product_Warehouse", 
			joinColumns={@JoinColumn(name="warehouseId")},
			inverseJoinColumns={@JoinColumn(name="productId")} )
	public List<ProductSKU> getProductsInInventory() {
		return productsInInventory;
	}
	public void setProductsInInventory(List<ProductSKU> productsInInventory) {
		this.productsInInventory = productsInInventory;
	}

}
