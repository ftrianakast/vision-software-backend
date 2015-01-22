package co.com.vision.prueba.domain;

import java.util.List;

/**
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public class WorkflowProcess {

	private String id;
	
	private String name;

	private String description;

	private List<Node> activities;

	private List<Node> events;

	private List<Transition> transitions;

	private String author;

	private String version;

	private String countryKey;

	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param activities
	 * @param events
	 * @param transitions
	 * @param author
	 * @param version
	 * @param countryKey
	 */
	public WorkflowProcess(String id, String name, String description,
			List<Node> activities, List<Node> events,
			List<Transition> transitions, String author, String version,
			String countryKey) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.activities = activities;
		this.events = events;
		this.transitions = transitions;
		this.author = author;
		this.version = version;
		this.countryKey = countryKey;
	}

	public WorkflowProcess() {

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

	public List<Node> getActivities() {
		return activities;
	}

	public void setActivities(List<Node> activities) {
		this.activities = activities;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCountryKey() {
		return countryKey;
	}

	public void setCountryKey(String countryKey) {
		this.countryKey = countryKey;
	}

	public List<Node> getEvents() {
		return events;
	}

	public void setEvents(List<Node> events) {
		this.events = events;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
