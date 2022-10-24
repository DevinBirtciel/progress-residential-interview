package todolist;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TodoListItem {

	private @Id @GeneratedValue Long id;
	private String name;
	private String details;
	private String todoListName;

	public TodoListItem() {}

	public TodoListItem(String name, String details, String todoListName) {
		this.name = name;
		this.details = details;
		this.todoListName = todoListName;
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

	public String getTodoListName() {
		return todoListName;
	}

	public void setTodoListName(String todoListName) {
		this.todoListName = todoListName;
	}

	@Override
	public String toString() {
		return "TodoListItem [id=" + id + ", name=" + name + ", details=" + details + ", todoListName=" + todoListName
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(details, id, name, todoListName);
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
				&& Objects.equals(name, other.name) && Objects.equals(todoListName, other.todoListName);
	}
}
