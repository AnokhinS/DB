package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "personal")
public class Personal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_id")
	private long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "profession", nullable = false)
	private Profession profession;

	@ManyToOne
	@JoinColumn(name = "student_house", nullable = false)
	private StudentHouse studentHouse;

	public Personal() {
		super();
	}

	public Personal(String name, Profession profession, StudentHouse studentHouse) {
		super();
		this.name = name;
		this.profession = profession;
		this.studentHouse = studentHouse;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public StudentHouse getStudentHouse() {
		return studentHouse;
	}

	public void setStudentHouse(StudentHouse studentHouse) {
		this.studentHouse = studentHouse;
	}

	@Override
	public String toString() {
		return "Personal [id=" + id + ", name=" + name + ", profession=" + profession + "]";
	}
}