package todolist.model.exceptions;

public class TodoListItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3476218125071824546L;

	public TodoListItemNotFoundException(String todoListName) {
		super("Could not find todo list items from list " + todoListName);
	}
	
	public TodoListItemNotFoundException(String todoListName, String todoListItemName) {
		super("Could not find todo list item " + todoListItemName + " from list " + todoListName);
	}
}
