package todolist.model.exceptions;

public class TodoListNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1501229854683359697L;

	public TodoListNotFoundException(String todoListName) {
		super("Could not find todo list " + todoListName);
	}
}
