package co.com.vision.prueba.domain.activity;

import co.com.vision.prueba.domain.Node;

/**
 * @author ftrianakast
 * @version 1.0
 */
public class Activity extends Node {

	private ActivityType activityType;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param activityType
	 */
	public Activity(String id, String name, String description,
			ActivityType activityType) {
		super(id, name, description);
		this.activityType = activityType;
	}

	/**
	 * Basic constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 */
	public Activity(String id, String name, String description) {
		super(id, name, description);
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
}