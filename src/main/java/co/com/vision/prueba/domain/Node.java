package co.com.vision.prueba.domain;

/**
 * 
 * @author ftrianakast
 * @version 1.0
 */
public abstract class Node {

	private String id;
	
	private String name;

	private String description;


	/**
	 * Default constructor
	 * @param id
	 * @param name
	 * @param description
	 */
	public Node(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
