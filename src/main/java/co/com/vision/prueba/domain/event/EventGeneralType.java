package co.com.vision.prueba.domain.event;

public enum EventGeneralType {
	StartEvent {
		public String toString() {
			return "StartEvent";
		}
	},

	EndEvent {
		public String toString() {
			return "EndEvent";
		}
	},

	IntermediateEvent {
		public String toString() {
			return "IntermediateEvent";
		}
	}
}
