package todolist;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
@RequestMapping("/lists/v1")
class TodoListItemController {

	private final TodoListItemRepository repository;

	TodoListItemController(TodoListItemRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/todolists/{todoListName}/todolistitems/")
	EntityModel<TodoListItem> one(@PathVariable String todoListName) {

		TodoListItem todoListItem = repository.findByTodoListName(todoListName)
				.orElseThrow(() -> new TodoListItemNotFoundException(todoListName));

		return EntityModel.of(todoListItem,
				linkTo(methodOn(TodoListItemController.class).one(todoListItem.getName())).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	TodoListItem newTodoListItem(@RequestBody TodoListItem newTodoListItem) {
		return repository.save(newTodoListItem);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	EntityModel<TodoListItem> one(@PathVariable String todoListName, @PathVariable String todoListItemName) {

		TodoListItem todoListItem = repository.findByTodoListNameAndName(todoListName, todoListItemName)
				.orElseThrow(() -> new TodoListItemNotFoundException(todoListName, todoListItemName));
		
		return EntityModel.of(todoListItem,
					linkTo(methodOn(TodoListItemController.class).one(todoListItemName)).withSelfRel());
		
	}
	// end::get-single-item[]

	@PutMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	TodoListItem replaceTodoListItem(@RequestBody TodoListItem newTodoListItem, @PathVariable String todoListName, @PathVariable String todoListItemName) {

		return repository.findByTodoListNameAndName(todoListName, todoListItemName)
				.map(todoListItem -> {
					todoListItem.setName(newTodoListItem.getName());
					todoListItem.setDetails(newTodoListItem.getDetails());
					todoListItem.setTodoListName(newTodoListItem.getTodoListName());
					return repository.save(todoListItem);
				}).get();
	}

	@DeleteMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	void deleteTodoListItem(@PathVariable String todoListName, @PathVariable String todoListItemName) {
		repository.deleteByTodoListNameAndName(todoListName, todoListItemName);
	}
}
