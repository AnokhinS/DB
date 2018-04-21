package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "form_of_education")
public class FormOfEducation {
	public FormOfEducation(long id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "FormOfEducation [id=" + id + ", foe=" + foe + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "foe_id")
	private long id;
	private String foe;

	public FormOfEducation(String foe) {
		super();
		this.foe = foe;
	}

	public FormOfEducation() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFoe() {
		return foe;
	}

	public void setFoe(String foe) {
		this.foe = foe;
	}
}
