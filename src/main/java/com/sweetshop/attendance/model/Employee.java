package com.sweetshop.attendance.model;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="workers")
public class Employee {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long  id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=true)
	private String role;
	
	@Column(nullable=true ,unique = true, length = 15)
	@Pattern(regexp = "^\\+?[0-9]{1,3}-?[0-9]{6,14}$", message = "Invalid phone number format")
	private String number;
	
	@Column(nullable=false)
	private Double salary;
	
	public Employee() {
	}
	public Employee( String name, String role, String number, Double salary) {
		this.name = name;
		this.role = role;
		this.number = number;
		this.salary = salary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
