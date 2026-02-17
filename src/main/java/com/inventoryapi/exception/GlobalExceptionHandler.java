package com.inventoryapi.exception;

import com.inventoryapi.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  // 1. Manejo de Recurso No Encontrado (404)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDTO> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  // 2. Manejo de Stock Insuficiente (400 Bad Request o 409 Conflict)
  @ExceptionHandler(InsufficientStockException.class)
  public ResponseEntity<ErrorDTO> handleInsufficientStock(InsufficientStockException ex) {
    ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Business Rule Violation", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  // 3. Manejo de Datos Inválidos (400)
  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<ErrorDTO> handleInvalidData(InvalidDataException ex) {
    ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Invalid Data", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  // 4. Manejo de Falta de Permisos (403 Forbidden - ROLE_USER queriendo borrar)
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorDTO> handleAccessDenied(AccessDeniedException ex) {
    ErrorDTO error = new ErrorDTO(HttpStatus.FORBIDDEN.value(), "Forbidden", "No tienes permisos para realizar esta acción.");
    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
  }

  // 5. Manejo Genérico (Para cualquier otra cosa que explote, 500)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex) {
    ErrorDTO error = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", "Ocurrió un error inesperado.");
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}