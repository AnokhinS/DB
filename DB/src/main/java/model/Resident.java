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
public class Resident implements Option {
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
	private ResidentType residentType;
	@ManyToOne
	@JoinColumn(name = "room", nullable = false)
	private Room room;
	private double balance;
	@ManyToOne
	@JoinColumn(name = "faculty", nullable = false)
	private Faculty faculty;
	@ManyToOne
	@JoinColumn(name = "form_of_education", nullable = false)
	private FormOfEducation formOfEducation;
	@OneToMany(mappedBy = "resident", fetch = FetchType.EAGER)
	private Set<Payment> payments;

	public Resident() {
		super();
	}

	@Override
	public String toString() {
		return "Resident [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", phone=" + phone
				+ ", resType=" + residentType + ", room=" + room + ", balance=" + balance + ", faculty=" + faculty
				+ ", foe=" + formOfEducation + ", payments=" + payments + "]";
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public Resident(String name, int age, String phone, String sex, ResidentType resType, FormOfEducation foe,
			Faculty faculty, Room room) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.phone = phone;
		this.residentType = resType;
		this.room = room;
		this.faculty = faculty;
		this.formOfEducation = foe;
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
		return residentType;
	}

	public void setResType(ResidentType resType) {
		this.residentType = resType;
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
		return formOfEducation;
	}

	public void setFoe(FormOfEducation foe) {
		this.formOfEducation = foe;
	}
}