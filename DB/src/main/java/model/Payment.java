package model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double sum;
	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "resident", nullable = false)
	private Resident resident;

	public Payment(Resident resident, double sum, LocalDate date) {
		super();
		this.resident = resident;
		this.sum = sum;
		this.date = date;
	}

	public Payment(long id) {
		super();
		this.id = id;
	}

	public Payment() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", sum=" + sum + ", date=" + date + "]";
	}
}
