package ca.mcmaster.cas735.tmt.customers.adapter;

import ca.mcmaster.cas735.tmt.customers.business.errors.AlreadyExistingException;
import ca.mcmaster.cas735.tmt.customers.business.errors.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception2HttpStatus {

    // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(NotFoundException e) {
        // Translate CustomerNotFoundException to Http 404 - NOT FOUND status code
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyExistingException.class)
    public ResponseEntity<String> handleConflict(AlreadyExistingException e) {
        // Translate AlreadyExistingException to Http 409 - CONFLICT status code
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        // Translate IllegalArgumentException to Http 422 - UNPROCESSABLE CONTENT status code
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }



}
