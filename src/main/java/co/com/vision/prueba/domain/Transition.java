package co.com.vision.prueba.domain;

public class Transition {

	private String name;

	private Node from;

	private Node to;

	/**
	 * Default constructor
	 * 
	 * @param name
	 * @param from
	 * @param to
	 */
	public Transition(String name, Node from, Node to) {
		super();
		this.name = name;
		this.from = from;
		this.to = to;
	}

	/**
	 * 
	 * @param from
	 * @param to
	 */
	public Transition(Node from, Node to) {
		this.from = from;
		this.to = to;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}
}
