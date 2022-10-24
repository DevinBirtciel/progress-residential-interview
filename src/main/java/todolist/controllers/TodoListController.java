package todolist.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todolist.model.entities.TodoList;
import todolist.model.entities.TodoListItem;
import todolist.model.exceptions.TodoListNotFoundException;
import todolist.model.repositories.TodoListRepository;

@RestController
@RequestMapping("/lists/v1")
public class TodoListController {
	private final TodoListRepository repository;

	public TodoListController(TodoListRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/todolists")
	public CollectionModel<EntityModel<TodoList>> all() {

		List<EntityModel<TodoList>> todoLists = repository.findAll().stream()
				.map(todoList -> EntityModel.of(todoList,
						linkTo(methodOn(TodoListController.class).all()).withRel("todolists")))
				.collect(Collectors.toList());

		return CollectionModel.of(todoLists, linkTo(methodOn(TodoListController.class).all()).withSelfRel());
	}

	@PostMapping("/todolists")
	public TodoList newTodoList(@RequestBody TodoList newTodoList) {
		return repository.save(newTodoList);
	}

	@GetMapping("/todolists/{todoListName}")
	public EntityModel<TodoList> one(@PathVariable String todoListName) {

		TodoList todoList = repository.findByName(todoListName)
				.orElseThrow(() -> new TodoListNotFoundException(todoListName));

		return EntityModel.of(todoList,
				linkTo(methodOn(TodoListController.class).one(todoListName)).withSelfRel(),
				linkTo(methodOn(TodoListController.class).all()).withRel("todolists"));
	}

	@PutMapping("/todolists/{todoListName}")
	public TodoList replaceTodoList(@PathVariable String todoListName, @RequestBody TodoList newTodoList) {
	
		return repository.findByName(todoListName)
				.map(todoList -> {
					todoList.setName(newTodoList.getName());
					todoList.setDescription(newTodoList.getDescription());
					return repository.save(todoList);
				}).orElseGet(() -> {
			        return repository.save(newTodoList);
			      });
	}

	@Transactional
	@DeleteMapping("/todolists/{todoListName}")
	public void deleteTodoListItem(@PathVariable String todoListName) {
		repository.deleteByName(todoListName);
	}
	
	@GetMapping("/todolists/{todoListName}/todolistitems")
	public Set<TodoListItem> getTodoListItems(@PathVariable String todoListName) {
		TodoList todoList = repository.findByName(todoListName)
				.orElseThrow(() -> new TodoListNotFoundException(todoListName));
		return todoList.getTodoListItems();
	}
}
