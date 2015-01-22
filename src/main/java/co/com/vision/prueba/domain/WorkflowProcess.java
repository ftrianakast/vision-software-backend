package co.com.vision.prueba.domain;

import java.util.List;

import co.com.vision.prueba.domain.activity.Activity;
import co.com.vision.prueba.domain.event.Event;

/**
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public class WorkflowProcess {

	private String name;

	private String description;

	private List<Activity> activities;

	private List<Event> events;

	private List<Transition> transitions;

	private String author;

	private String version;

	private String countryKey;

	/**
	 * WorkflowProcess
	 * 
	 * @param name
	 * @param description
	 * @param activities
	 * @param events
	 * @param transitions
	 * @param author
	 * @param version
	 * @param countryKey
	 */
	public WorkflowProcess(String name, String description,
			List<Activity> activities, List<Event> events,
			List<Transition> transitions, String author, String version,
			String countryKey) {
		super();
		this.name = name;
		this.description = description;
		this.activities = activities;
		this.events = events;
		this.transitions = transitions;
		this.author = author;
		this.version = version;
		this.countryKey = countryKey;
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

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
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

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
