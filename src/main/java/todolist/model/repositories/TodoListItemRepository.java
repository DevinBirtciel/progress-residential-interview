package todolist.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.model.entities.TodoList;
import todolist.model.entities.TodoListItem;

public interface TodoListItemRepository extends JpaRepository<TodoListItem, String> {
	Optional<TodoListItem> deleteByTodoList(TodoList todoList);
	Optional<TodoListItem> findByTodoListAndName(TodoList todoList, String todoListItemName);
}
