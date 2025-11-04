package raisetech.student.management.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
    return ResponseEntity.badRequest()
        .body("リクエストのパラメータが正しくありません: " + e.getMessage());
  }

}
