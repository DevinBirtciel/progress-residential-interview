package todolist;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class TodoList implements Serializable{
	private static final long serialVersionUID = -6056842047378848517L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
	private Long id;
	
	private String name;
	private String description;
	
//	@OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
//    private Set<TodoListItem> todoListItems;

	public TodoList() {
	}
	
	public TodoList(String name, String description) {
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Set<TodoListItem> getTodoListItems() {
//		return todoListItems;
//	}
//
//	public void setTodoListItem(Set<TodoListItem> todoListItem) {
//		this.todoListItems = todoListItem;
//	}

	@Override
	public String toString() {
		return "TodoList [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TodoList other = (TodoList) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
}
