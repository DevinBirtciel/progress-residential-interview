package todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(TodoListRepository repository) {

		return args -> {
			log.info("Preloading " + repository.save(new TodoListItem("Food", "go to grocery store")));
			log.info("Preloading " + repository.save(new TodoListItem("SRP", "pay electric bill")));
		};
	}
}
