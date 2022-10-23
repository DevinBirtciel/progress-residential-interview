package todolist;

import org.springframework.data.jpa.repository.JpaRepository;

interface TodoListRepository extends JpaRepository<TodoListItem, Long> {

}
