package todolist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface TodoListRepository extends JpaRepository<TodoList, String> {
	Optional<TodoList> findByName(String name);
	Optional<TodoList> deleteByName(String name);
}
