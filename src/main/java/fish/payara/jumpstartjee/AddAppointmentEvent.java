package fish.payara.jumpstartjee;

public class AddAppointmentEvent {

	private PatientEntity patientEntity;

	public AddAppointmentEvent(PatientEntity patientEntity) {
		super();
		this.patientEntity = patientEntity;
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

}
