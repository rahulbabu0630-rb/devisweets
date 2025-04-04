package com.sweetshop.attendance.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Attendance")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "employee_id" , nullable = false)
	private Employee employee;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private Double salary;
	
	
	

	public Attendance() {
	}

	public Attendance(Long employeeId, String status2, LocalDate date2) {
	}

	public Attendance(Employee employee, LocalDate date, String status, Double salary) {
		this.employee = employee;
		this.date = date;
		this.status = status;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
	
}
