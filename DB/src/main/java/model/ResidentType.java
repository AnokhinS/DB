package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resident_types")
public class ResidentType {
	public ResidentType(long id) {
		super();
		this.id = id;
	}

	public ResidentType(String resident_type) {
		super();
		this.resident_type = resident_type;
	}

	public ResidentType() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resident_type_id")
	private long id;
	private String resident_type;

	@Override
	public String toString() {
		return "ResidentType [id=" + id + ", resident_type=" + resident_type + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResident_type() {
		return resident_type;
	}

	public void setResident_type(String resident_type) {
		this.resident_type = resident_type;
	}
}
