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
	CommandLineRunner initTodoListDatabase(TodoListRepository repository) {
		TodoList inputList = new TodoList("Marvelous Masterpieces", "A list of Marvel movies");

		return args -> {
			log.info("Preloading " + repository.save(inputList));
		};
	}
	
	@Bean
	CommandLineRunner initTodoListItemDatabase(TodoListItemRepository repository) {
		TodoListItem ironMan = new TodoListItem("Iron Man", "directed by Jon Favreau", DEFAULT_LIST_NAME);
		TodoListItem thor = new TodoListItem("Thor", "directed by Kenneth Branagh", DEFAULT_LIST_NAME);
		TodoListItem antMan = new TodoListItem("Ant-Man", "directed by Peyton Reed", DEFAULT_LIST_NAME);

		return args -> {
			log.info("Preloading " + repository.save(ironMan));
			log.info("Preloading " + repository.save(thor));
			log.info("Preloading " + repository.save(antMan));
		};
	}
}
