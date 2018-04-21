package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student_house")
public class StudentHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_house_id")
	private long id;
	private String address;
	@OneToMany(mappedBy = "studentHouse", fetch = FetchType.EAGER)
	private Set<Room> rooms;
	@OneToMany(mappedBy = "studentHouse", fetch = FetchType.EAGER)
	private Set<Personal> personal;

	public StudentHouse() {
		super();
	}

	public StudentHouse(String address) {
		super();
		this.address = address;
	}

	public StudentHouse(long id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "StudentHouse [id=" + id + ", address=" + address + ", rooms=" + rooms + ", personal=" + personal + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}
}
