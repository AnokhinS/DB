package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professions")
public class Profession implements Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profession_id")
	private int id;
	private String profession;

	public Profession() {
		super();
	}

	public Profession(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
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
