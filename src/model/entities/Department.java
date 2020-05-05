package model.entities;

import java.io.Serializable;

public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;

	public Department() {
	}

	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// get
	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	// set
	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
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

		if (id == null) {

			if (other.id != null)

				return false;

		} else if (!id.equals(other.id))

			return false;

		return true;

	}

	@Override

	public String toString() {

		return "Department [id=" + id + ", name=" + name + "]";

	}

}
