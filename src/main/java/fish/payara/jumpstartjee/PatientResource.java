package fish.payara.jumpstartjee;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/patient")
public class PatientResource {

	@Inject
	private PatientDetailService patientDetailService;

	@Inject
	private Event<AddAppointmentEvent> addAppointmentEvent;

	// Get patient for patient_id
	@GET
	@Path("/{patient_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PatientEntity getPatientDetails(@PathParam("patient_id") Long patient_id) {
		var details = patientDetailService.getPatientDetails(patient_id);
		return details;
	}

	// Post patient
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PatientEntity savePatientDetails(PatientEntity patientEntity) {
		return patientDetailService.savePatientDetails(patientEntity);
	}

	@GET
	@Path("/allPatients")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientEntity> allPatients() {
		return patientDetailService.allPatientsByNameAndId();
	}

	@GET
	@Path("/appointments/{appointmentDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PatientEntity> appointments(@PathParam("appointmentDate") String appointmentDate) {
		Date date = parseStringDate(appointmentDate);

		return patientDetailService.patientsAppointmentForDay(date);
	}

	private Date parseStringDate(String appointmentDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date;
		try {
			date = format.parse(appointmentDate);
		} catch (ParseException e) {
			date = Date.from(Instant.now());
		}
		return date;
	}

	@PATCH
	@Path("/{patient_id}/{appointmentDate}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PatientEntity bookAppointment(PatientEntity patient, @PathParam("patient_id") Long patient_id,
			@PathParam("appointmentDate") String appointmentDate) throws Exception {
		if (patient.getPatient_id() != patient_id) {
			throw new Exception("Incorrect patient details, please ensure you sent the correct patient_id in the requests");
		}
		Date date = parseStringDate(appointmentDate);

		PatientEntity toBeUpdatedPatient = patientDetailService.getPatientDetails(patient.getPatient_id());
		toBeUpdatedPatient.setUpcomingAppointment(date);

		addAppointmentEvent.fire(new AddAppointmentEvent(toBeUpdatedPatient));
		return patientDetailService.getPatientDetails(patient_id);
	}

}
