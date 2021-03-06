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

	private NodeType type;
	

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param type
	 */
	public Node(String id, String name, String description, NodeType type) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
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

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}
}
