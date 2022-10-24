package todolist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface TodoListItemRepository extends JpaRepository<TodoListItem, String> {
	Optional<TodoListItem> findByTodoListName(String todoListName);
	Optional<TodoListItem> deleteByTodoListNameAndName(String todoListName, String name);
	Optional<TodoListItem> findByTodoListNameAndName(String todoListName, String name);
}
