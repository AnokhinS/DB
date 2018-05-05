package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty implements Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "faculty_id")
	private int id;

	private String faculty;

	public Faculty() {
		super();
	}

	public Faculty(int id) {
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
	public String toString() {
		return "Faculty [id=" + id + ", faculty=" + faculty + "]";
	}

	public void setName(String name) {
		this.faculty = name;
	}

	@Override
	public String getName() {
		return faculty;
	}

}
