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
@Table(name = "rooms")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private long id;
	@Column(name = "room_number")
	private long roomNumber;
	private long floor;
	@Column(name = "free_spots")
	private long freeSpots;

	@ManyToOne
	@JoinColumn(name = "student_house", nullable = false)
	private StudentHouse studentHouse;

	public Room() {
		super();
	}

	public Room(long id) {
		super();
		this.id = id;
	}

	public Room(long roomNumber, long floor, long freeSpots, StudentHouse studentHouse) {
		super();
		this.roomNumber = roomNumber;
		this.floor = floor;
		this.freeSpots = freeSpots;
		this.studentHouse = studentHouse;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomNumber=" + roomNumber + ", floor=" + floor + ", freeSpots=" + freeSpots + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public long getFloor() {
		return floor;
	}

	public void setFloor(long floor) {
		this.floor = floor;
	}

	public long getFreeSpots() {
		return freeSpots;
	}

	public void setFreeSpots(long freeSpots) {
		this.freeSpots = freeSpots;
	}
}
