package todolist;

class TodoListItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3476218125071824546L;

	TodoListItemNotFoundException(Long id) {
		super("Could not find todo list item " + id);
	}
}
