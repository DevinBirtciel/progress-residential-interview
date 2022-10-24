package todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabases {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabases.class);
	private static final String DEFAULT_LIST_NAME = "Marvelous Masterpieces";
	
	@Bean
	CommandLineRunner initTodoListItemDatabase(TodoListRepository todoListRepository, TodoListItemRepository todoListItemRepository) {
		TodoList todoList = new TodoList(DEFAULT_LIST_NAME, "A list of Marvel movies");
		TodoListItem ironMan = new TodoListItem("Iron Man", "directed by Jon Favreau", todoList);
		TodoListItem thor = new TodoListItem("Thor", "directed by Kenneth Branagh", todoList);
		TodoListItem antMan = new TodoListItem("Ant-Man", "directed by Peyton Reed", todoList);

		return args -> {
			log.info("Preloading " + todoListRepository.save(todoList));
			log.info("Preloading " + todoListItemRepository.save(ironMan));
			log.info("Preloading " + todoListItemRepository.save(thor));
			log.info("Preloading " + todoListItemRepository.save(antMan));
		};
	}
}
