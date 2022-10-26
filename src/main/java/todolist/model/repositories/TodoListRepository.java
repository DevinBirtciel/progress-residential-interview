package todolist.model.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import todolist.model.entities.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, String> {
	Optional<TodoList> findByName(String name);
	Optional<TodoList> findAllByName(String name);
	Optional<TodoList> deleteByName(String name);
}
