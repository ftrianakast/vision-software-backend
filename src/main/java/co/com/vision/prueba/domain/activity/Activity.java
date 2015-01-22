package co.com.vision.prueba.domain.activity;

import java.util.Optional;

import co.com.vision.prueba.domain.Node;
import co.com.vision.prueba.domain.NodeType;

/**
 * @author ftrianakast
 * @version 1.0
 */
public class Activity extends Node {

	private Optional<ActivityType> activityType;

	/**
	 * Default constructor
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @param activityType
	 */
	public Activity(String id, String name, String description,
			Optional<ActivityType> activityType) {
		super(id, name, description, NodeType.ACTIVITY);
		this.activityType = activityType;
	}

	public Optional<ActivityType> getActivityType() {
		return activityType;
	}

	public void setActivityType(Optional<ActivityType> activityType) {
		this.activityType = activityType;
	}
}