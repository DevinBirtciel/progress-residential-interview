package todolist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class TodoListItemNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(TodoListItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(TodoListItemNotFoundException ex) {
		return ex.getMessage();
	}
}
