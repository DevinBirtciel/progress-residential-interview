package todolist;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lists/v1")
public class TodoListController {
	private final TodoListRepository repository;

	TodoListController(TodoListRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/todolists")
	CollectionModel<EntityModel<TodoList>> all() {

		List<EntityModel<TodoList>> todoLists = repository.findAll().stream()
				.map(todoList -> EntityModel.of(todoList,
						linkTo(methodOn(TodoListController.class).one(todoList.getName())).withSelfRel(),
						linkTo(methodOn(TodoListController.class).all()).withRel("todolists")))
				.collect(Collectors.toList());

		return CollectionModel.of(todoLists, linkTo(methodOn(TodoListController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/todolists")
	TodoList newTodoList(@RequestBody TodoList newTodoList) {
		return repository.save(newTodoList);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/todolists/{todoListName}")
	EntityModel<TodoList> one(@PathVariable String todoListName) {

		TodoList todoList = repository.findByName(todoListName)
				.orElseThrow(() -> new TodoListNotFoundException(todoListName));

		return EntityModel.of(todoList,
				linkTo(methodOn(TodoListController.class).one(todoListName)).withSelfRel(),
				linkTo(methodOn(TodoListController.class).all()).withRel("todolists"));
	}
	// end::get-single-item[]

	@PutMapping("/todolists/{todoListName}")
	TodoList replaceTodoList(@PathVariable String todoListName, @RequestBody TodoList newTodoList) {
	
		return repository.findByName(todoListName)
				.map(todoList -> {
					todoList.setName(newTodoList.getName());
					todoList.setDescription(newTodoList.getDescription());
					return repository.save(todoList);
				}).orElseGet(() -> {
			        return repository.save(newTodoList);
			      });
	}

	@DeleteMapping("/todolists/{todoListName}")
	void deleteTodoListItem(@PathVariable String todoListName) {
		repository.deleteByName(todoListName);
	}
}
