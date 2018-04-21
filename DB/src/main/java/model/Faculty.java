package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "faculty_id")
	private long id;

	private String faculty;

	public long getId() {
		return id;
	}

	public Faculty(String name) {
		super();
		this.faculty = name;
	}

	public Faculty() {
		super();
	}

	public void setId(long id) {
		this.id = id;
	}

	public Faculty(long id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Faculty [id=" + id + ", faculty=" + faculty + "]";
	}

	public String getName() {
		return faculty;
	}

	public void setName(String name) {
		this.faculty = name;
	}
}
