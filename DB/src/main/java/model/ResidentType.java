package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resident_types")
public class ResidentType implements Option, Comparable<ResidentType> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resident_type_id")
	private long id;
	@Column(name = "resident_type")
	private String residentType;

	public ResidentType(long id) {
		super();
		this.id = id;
	}

	public ResidentType() {
		super();
	}

	@Override
	public String toString() {
		return "ResidentType [id=" + id + ", resident_type=" + residentType + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return residentType;
	}

	public void setName(String resident_type) {
		this.residentType = resident_type;
	}

	@Override
	public int compareTo(ResidentType o) {
		return this.getName().compareTo(o.getName());
	}
}
