package todolist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TodoListNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(TodoListNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String todoListNotFoundHandler(TodoListNotFoundException ex) {
		return ex.getMessage();
	}
}
