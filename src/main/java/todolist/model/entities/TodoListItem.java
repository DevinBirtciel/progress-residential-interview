package todolist.model.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "todolistitem")
public class TodoListItem implements Serializable{

	private static final long serialVersionUID = 7342498686998558846L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
	private Long id;
	
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Details are required")
	private String details;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "todolist_id") 
	private TodoList todoList;
	
	public TodoListItem() {
		
	}
	
	public TodoListItem(String name, String details, TodoList todoList) {
		this.name = name;
		this.details = details;
		this.todoList = todoList;
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
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public TodoList getTodoList() {
		return todoList;
	}
	
	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}

	@Override
	public String toString() {
		return "TodoListItem [id=" + id + ", name=" + name + ", details=" + details + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(details, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoListItem other = (TodoListItem) obj;
		return Objects.equals(details, other.details) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
}
