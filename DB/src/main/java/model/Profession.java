package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professions")
public class Profession {
	public Profession(String profession) {
		super();
		this.profession = profession;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profession_id")
	private long id;
	private String profession;

	public Profession() {
		super();
	}

	public Profession(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return profession;
	}

	public void setName(String profession) {
		this.profession = profession;
	}

	@Override
	public String toString() {
		return "Profession [id=" + id + ", profession=" + profession + "]";
	}
}
