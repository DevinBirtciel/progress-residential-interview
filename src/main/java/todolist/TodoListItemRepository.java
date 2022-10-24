package todolist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;

interface TodoListItemRepository extends JpaRepository<TodoListItem, String> {
	Optional<TodoListItem> deleteByTodoList(TodoList todoList);
	Optional<List<EntityModel<TodoListItem>>> findByTodoList(TodoList todoList);
	Optional<TodoListItem> findByTodoListAndName(TodoList todoList, String todoListItemName);
}
