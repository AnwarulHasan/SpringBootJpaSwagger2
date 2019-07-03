package com.kgate.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String name;
@OneToMany(targetEntity=Employee.class,cascade=CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true,mappedBy="department")
private List<Employee> employees=new ArrayList<>();

public List<Employee> getEmployees() {
	return employees;
}
public void setEmployees(List<Employee> employees) {
	this.employees = employees;
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
@Override
public String toString() {
	return "Department [id=" + id + ", name=" + name + "]";
}
}
