package model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "residents")
public class Resident {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "resident_id")
	private long id;

	private String name;
	private String sex;
	private int age;
	private String phone;
	@ManyToOne
	@JoinColumn(name = "resident_type", nullable = false)
	private ResidentType resType;
	@ManyToOne
	@JoinColumn(name = "room", nullable = false)
	private Room room;
	private double balance;
	@ManyToOne
	@JoinColumn(name = "faculty", nullable = false)
	private Faculty faculty;
	@ManyToOne
	@JoinColumn(name = "form_of_education", nullable = false)
	private FormOfEducation foe;
	@OneToMany(mappedBy = "resident", fetch = FetchType.EAGER)
	private Set<Payment> payments;

	public Resident() {
		super();
	}

	@Override
	public String toString() {
		return "Resident [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", phone=" + phone
				+ ", resType=" + resType + ", room=" + room + ", balance=" + balance + ", faculty=" + faculty + ", foe="
				+ foe + ", payments=" + payments + "]";
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Resident(String name, String sex, int age, String phone, ResidentType resType, Room room, Faculty faculty,
			FormOfEducation foe) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.resType = resType;
		this.room = room;
		this.faculty = faculty;
		this.foe = foe;
	}

	public Resident(long id) {
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ResidentType getResType() {
		return resType;
	}

	public void setResType(ResidentType resType) {
		this.resType = resType;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public FormOfEducation getFoe() {
		return foe;
	}

	public void setFoe(FormOfEducation foe) {
		this.foe = foe;
	}
}