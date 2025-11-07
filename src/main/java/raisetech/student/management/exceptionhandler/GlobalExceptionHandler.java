package raisetech.student.management.exceptionhandler;

import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException e) {
    return ResponseEntity.badRequest()
        .body("リクエストのパラメータが正しくありません: " + e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    List<Map<String, String>> errors = e.getBindingResult().getFieldErrors().stream()
        .map(error -> Map.of(
            "field", error.getField(),
            "message", error.getDefaultMessage()
        ))
        .toList();

    Map<String, Object> body = Map.of(
        "error", "入力値が不正です",
        "details", errors
    );

    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {

    return ResponseEntity.badRequest().body("リクエスト形式に問題があります：" + e.getMessage());

  }


}
