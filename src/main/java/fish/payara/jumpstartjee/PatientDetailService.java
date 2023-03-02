package fish.payara.jumpstartjee;

import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import jakarta.transaction.Transactional;

@Transactional
@ApplicationScoped
public class PatientDetailService {

	@PersistenceContext
	private EntityManager em;

	public PatientEntity getPatientDetails(Long patient_id) {
		return em.find(PatientEntity.class, patient_id);
	}

	public PatientEntity savePatientDetails(PatientEntity patientEntity) {
		em.persist(patientEntity);
		return patientEntity;
	}

	public List<PatientEntity> allPatientsByNameAndId() {
		return em
				.createQuery("select p.patient_id, p.firstname, p.lastname, p.upcomingAppointment from PatientEntity p",
						PatientEntity.class)
				.getResultList();
	}

	public List<PatientEntity> patientsAppointmentForDay(Date appointmentDate) {
		return em.createQuery(
				"select p.patient_id, p.firstname, p.lastname from PatientEntity p where p.upcomingAppointment between :appointmentDate and :appointmentDate",
				PatientEntity.class).setParameter("appointmentDate", appointmentDate, TemporalType.TIMESTAMP)
				.getResultList();
	}
	
	
	public void updateAppointment(@Observes AddAppointmentEvent addAppointmentEvent) {
		System.out.println("--------------------------------------"+addAppointmentEvent.getPatientEntity());
		em.merge(addAppointmentEvent.getPatientEntity());
	}
	

}
