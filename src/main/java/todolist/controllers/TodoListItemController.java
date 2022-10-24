package todolist.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import todolist.model.exceptions.TodoListItemNotFoundException;
import todolist.model.exceptions.TodoListNotFoundException;
import todolist.model.repositories.TodoListItemRepository;
import todolist.model.repositories.TodoListRepository;

@RestController
@RequestMapping("/lists/v1")
public class TodoListItemController {

	private final TodoListItemRepository todoListItemRepository;
	private final TodoListRepository todoListRepository;

	public TodoListItemController(TodoListItemRepository todoListItemRepository, TodoListRepository todoListRepository) {
		this.todoListItemRepository = todoListItemRepository;
		this.todoListRepository = todoListRepository;
	}
	
	@GetMapping("/todolistitems")
	public CollectionModel<EntityModel<TodoListItem>> all() {

		List<EntityModel<TodoListItem>> todoListItems = todoListItemRepository.findAll().stream()
				.map(todoListItem -> EntityModel.of(todoListItem,
						linkTo(methodOn(TodoListItemController.class).all()).withRel("todolistitems")))
				.collect(Collectors.toList());

		return CollectionModel.of(todoListItems, linkTo(methodOn(TodoListItemController.class).all()).withSelfRel());
	}

	@PostMapping("/todolists/{todoListName}/todolistitems")
	public TodoListItem newTodoListItem(@Valid @RequestBody TodoListItem newTodoListItem, @NotNull @PathVariable String todoListName) {
		TodoList todoList = todoListRepository.findByName(todoListName).orElseThrow(() -> new TodoListNotFoundException(todoListName));
		newTodoListItem.setTodoList(todoList);
		return todoListItemRepository.save(newTodoListItem);
	}


	@GetMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	public EntityModel<TodoListItem> one(@NotNull @PathVariable String todoListName, @NotNull @PathVariable String todoListItemName) {
		TodoList todoList = todoListRepository.findByName(todoListName).orElseThrow(() -> new TodoListNotFoundException(todoListName));
		TodoListItem todoListItem = todoListItemRepository.findByTodoListAndName(todoList, todoListItemName)
				.orElseThrow(() -> new TodoListItemNotFoundException(todoListName, todoListItemName));
		
		return EntityModel.of(todoListItem,
					linkTo(methodOn(TodoListItemController.class).one(todoListItemName, todoListItemName)).withSelfRel());
		
	}

	@PutMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	public TodoListItem replaceTodoListItem(@Valid @RequestBody TodoListItem newTodoListItem, @NotNull @PathVariable String todoListName, @NotNull @PathVariable String todoListItemName) {

		TodoList todoList = todoListRepository.findByName(todoListName).orElseThrow(() -> new TodoListNotFoundException(todoListName));
		return todoListItemRepository.findByTodoListAndName(todoList, todoListItemName)
				.map(todoListItem -> {
					todoListItem.setName(newTodoListItem.getName());
					todoListItem.setDetails(newTodoListItem.getDetails());
					todoListItem.setTodoList(todoList);
					return todoListItemRepository.save(todoListItem);
				}).orElseGet(() -> {
			        return todoListItemRepository.save(newTodoListItem);
			      });
	}

	@Transactional
	@DeleteMapping("/todolists/{todoListName}/todolistitems/{todoListItemName}")
	public void deleteTodoListItem(@NotNull @PathVariable String todoListName, @NotNull @PathVariable String todoListItemName) {
		TodoList todoList = todoListRepository.findByName(todoListName).orElseThrow(() -> new TodoListItemNotFoundException(todoListName, todoListItemName));
		TodoListItem todoListItem = todoListItemRepository.findByTodoListAndName(todoList, todoListItemName).get();
		todoListItemRepository.delete(todoListItem);
	}
}
