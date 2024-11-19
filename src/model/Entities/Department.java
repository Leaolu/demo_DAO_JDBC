package model.Entities;

import java.io.Serializable;
import java.util.Objects;

public class Department implements Serializable{
	//Serializable default number
	private static final long serialVersionUID = 1L;
	//Department atributes 
	private Integer id;
	private String name;
	//Empty constructor
	public Department() {
	}
	//Constructor using fields
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	//Getters and Setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	//Hashcode and Equals comparing only by the id
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(id, other.id);
	}
	//toString function
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}
	
	
}
