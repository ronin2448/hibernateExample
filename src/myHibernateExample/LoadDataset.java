package myHibernateExample;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class LoadDataset {
	
	Map<String, List<String>> genderToFirstNames;
	List<String> lastNames;
	List<String> productsNames;
	List<String> streetNameList;
	List<String> cityNameList;
	
	public LoadDataset() {
		
		genderToFirstNames = new TreeMap<String, List<String>>();
		genderToFirstNames.put("M", new LinkedList<String>());
		genderToFirstNames.put("F", new LinkedList<String>());
		
		genderToFirstNames.get("M").add("Bob");
		genderToFirstNames.get("M").add("John");
		genderToFirstNames.get("M").add("Jeff");
		genderToFirstNames.get("M").add("Albert");
		genderToFirstNames.get("M").add("Carlos");
		genderToFirstNames.get("M").add("Jacob");
		genderToFirstNames.get("M").add("Daniel");
		genderToFirstNames.get("M").add("Frederick");
		genderToFirstNames.get("M").add("Jose");
		genderToFirstNames.get("M").add("Hans");
		genderToFirstNames.get("M").add("Cody");
		genderToFirstNames.get("M").add("Zeus");

		
		genderToFirstNames.get("F").add("Ana");
		genderToFirstNames.get("F").add("Maria");
		genderToFirstNames.get("F").add("Mary");
		genderToFirstNames.get("F").add("Stefanie");
		genderToFirstNames.get("F").add("Sophia");
		genderToFirstNames.get("F").add("Evelyn");
		genderToFirstNames.get("F").add("Lupe");
		genderToFirstNames.get("F").add("Betty");
		genderToFirstNames.get("F").add("Lisa");
		genderToFirstNames.get("F").add("Helen");
		genderToFirstNames.get("F").add("Vicky");
		
		lastNames = new LinkedList<String>();
		
		lastNames.add("Smith");
		lastNames.add("Perry");
		lastNames.add("Johnson");
		lastNames.add("Wang");
		lastNames.add("Stevenson");
		lastNames.add("Hardy");
		lastNames.add("Diaz");
		lastNames.add("Tucker");
		lastNames.add("Wayne");
		
		streetNameList = new LinkedList<String>();
		streetNameList.add("Main Street");
		streetNameList.add("Manor Road");
		streetNameList.add("Broadway Avenue");
		streetNameList.add("Sunset Boulevard");

		cityNameList = new LinkedList<String>();
		cityNameList.add("Austin");
		cityNameList.add("San Antonio");
		cityNameList.add("Houston");
		cityNameList.add("Dallas");
		
		
		
	}
	
	
	public Customer generateRandomCustomer(int i) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random r = new Random(System.currentTimeMillis());
		r.nextInt();
		
		Customer c = new Customer();
		c.setId(i);
		
		String gender = "F";
		if(r.nextBoolean()) {
			gender = "M";
		}
		
		c.setGender(gender);
		c.setFirst_name(this.genderToFirstNames.get(gender).get(r.nextInt(genderToFirstNames.get(gender).size())));
		c.setLast_name(this.lastNames.get(r.nextInt(this.lastNames.size())));
		c.setAge(r.nextInt(85)+12);
		
		int streetNum = r.nextInt(9999);
		String streetName = streetNameList.get(r.nextInt(streetNameList.size()));
		String cityName = cityNameList.get(r.nextInt(cityNameList.size()));
		
		Address shipping = new Address();
		shipping.setStreetNumber(streetNum);
		shipping.setStreetName(streetName);
		shipping.setCity(cityName);
		shipping.setZipCode("78746");

		Address billing = new Address();
		billing.setStreetNumber(streetNum);
		billing.setStreetName(streetName);
		billing.setCity(cityName);
		billing.setZipCode("78746");
		
		c.setShippingAddress(shipping);
		c.setBillingAddress(billing);
		
		return c;
		
	}
	
	
	public static List<String> readFile(String file, boolean hasHeader) {
		
		List<String> out = new LinkedList<String>();
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            out.add(line);
	            line = br.readLine();
	        }
	        
	    } catch(IOException e) {
	    	System.out.println(e.toString());
	    }
	    return out;

	}

	public static void main(String[] args) {
		
		int numCustomers = 10;
		int numStores = 10;
		int numTransactionsPerCustomer = 5;
		int numProducts = 30;
				
		LoadDataset dsBuilder = new LoadDataset();
		
		
		//AnnotationConfiguration config = new AnnotationConfiguration();

		Configuration config = new Configuration();
		config.addAnnotatedClass(Customer.class);
		config.addAnnotatedClass(Transaction.class);
		config.addAnnotatedClass(ProductSKU.class);
		config.addAnnotatedClass(Warehouse.class);
		config.configure("hibernate.cfg.xml");
		
		new SchemaExport(config).create(true, true);

		// To send something into the DB create a SessionFactory
		SessionFactory factory = config.configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();

		
		List<Customer> cList = new LinkedList<Customer>();
		for(int i = 0 ; i < numCustomers; i++ ) {
			Customer c = dsBuilder.generateRandomCustomer(i);
			System.out.println(c.getFirst_name() + " " + c.getLast_name()  + " " + c.getAge());
			cList.add(c);
		}
		
		Warehouse w = new Warehouse();
		w.setName("MCL SW");
		w.setWarehouseId(11);
		w.setProductsInInventory(new LinkedList<ProductSKU>());
		
		List<ProductSKU> productList = new LinkedList<ProductSKU>();
		for(int i = 0 ; i < numProducts; i++) {
			ProductSKU p  = new ProductSKU();
			p.setProductId(i);
			p.setName("Product " + i);
			p.setWhereProductAvailable(new LinkedList<Warehouse>());
			session.save(p);
			productList.add(p);
			w.getProductsInInventory().add(p);
			p.getWhereProductAvailable().add(w);
		}
		
		session.save(w);
		
		int tID = 0;
		Random r = new Random(System.currentTimeMillis());
		for(Customer c : cList) {
			List<Transaction> tList = new LinkedList<Transaction>();
			for(int i =0; i < numTransactionsPerCustomer; i++) {
				Transaction t = new Transaction();
				t.setC(c);
				t.setId(tID);
				t.setP(productList.get(r.nextInt(productList.size())));
				tID++;
				t.setQuantity(r.nextInt(30));
				tList.add(t);
				session.save(t);
			}
			c.setTransactions(tList);
			
			session.save(c);

		}
		
		System.out.println("Done");
		session.getTransaction().commit();
		session.close();

	}

}
