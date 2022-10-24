package todolist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface TodoListItemRepository extends JpaRepository<TodoListItem, String> {
	Optional<TodoListItem> deleteByTodoList(TodoList todoList);
	Optional<TodoListItem> findByTodoListAndName(TodoList todoList, String todoListItemName);
}
