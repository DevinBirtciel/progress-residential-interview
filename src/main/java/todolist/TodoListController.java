package todolist;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class TodoListController {

	private final TodoListRepository repository;

	TodoListController(TodoListRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/todolistitems")
	CollectionModel<EntityModel<TodoListItem>> all() {

		List<EntityModel<TodoListItem>> todoListItems = repository.findAll().stream()
				.map(todoListItem -> EntityModel.of(todoListItem,
						linkTo(methodOn(TodoListController.class).one(todoListItem.getId())).withSelfRel(),
						linkTo(methodOn(TodoListController.class).all()).withRel("todoListItems")))
				.collect(Collectors.toList());

		return CollectionModel.of(todoListItems, linkTo(methodOn(TodoListController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/todolistitems")
	TodoListItem newTodoListItem(@RequestBody TodoListItem newTodoListItem) {
		return repository.save(newTodoListItem);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/todolistitems/{id}")
	EntityModel<TodoListItem> one(@PathVariable Long id) {

		TodoListItem todoListItem = repository.findById(id) //
				.orElseThrow(() -> new TodoListItemNotFoundException(id));

		return EntityModel.of(todoListItem, //
				linkTo(methodOn(TodoListController.class).one(id)).withSelfRel(),
				linkTo(methodOn(TodoListController.class).all()).withRel("todoListItems"));
	}
	// end::get-single-item[]

	@PutMapping("/todolistitems/{id}")
	TodoListItem replaceTodoListItem(@RequestBody TodoListItem newTodoListItem, @PathVariable Long id) {

		return repository.findById(id) //
				.map(todoListItem -> {
					todoListItem.setName(newTodoListItem.getName());
					todoListItem.setDetails(newTodoListItem.getDetails());
					return repository.save(todoListItem);
				}) //
				.orElseGet(() -> {
					newTodoListItem.setId(id);
					return repository.save(newTodoListItem);
				});
	}

	@DeleteMapping("/todolistitems/{id}")
	void deleteTodoListItem(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
