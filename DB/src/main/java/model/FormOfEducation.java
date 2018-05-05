package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "form_of_education")
public class FormOfEducation implements Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "foe_id")
	private int id;
	private String foe;

	public FormOfEducation(int id) {
		super();
		this.id = id;
	}

	public FormOfEducation() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return foe;
	}

	public void setName(String foe) {
		this.foe = foe;
	}

	@Override
	public String toString() {
		return "FormOfEducation [id=" + id + ", foe=" + foe + "]";
	}
}
