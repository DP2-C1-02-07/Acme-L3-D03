
package acme.features.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Audit;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int masterId = super.getRequest().getData("id", int.class);
		final Audit audit = this.repository.findOneAuditById(masterId);
		final Auditor auditor = audit == null ? null : audit.getAuditor();
		final boolean status = super.getRequest().getPrincipal().hasRole(auditor) || audit != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Audit object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;
		//		final int courseId = super.getRequest().getData("course", int.class);
		//		final Collection<Course> courses = this.repository.findOneCourseById(courseId);
		//		final SelectChoices choices;
		//		choices = SelectChoices.from(courses, "code", object.getCourse());
		final Tuple tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		//		tuple.put("course", choices.getSelected().getKey());
		//		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}
}
