package sellist.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sellist.wrapper.Metadata;
import sellist.wrapper.ResponseWrapper;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Global exception handler that wraps exceptions in ResponseWrapper with error details.
 */
@ControllerAdvice
public class GlobalExceptionHandler
{

private final String apiVersion;

public GlobalExceptionHandler(
        @Value("${api.version:1.0.0}")
        String apiVersion)
    {
    this.apiVersion = apiVersion;
    }

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ResponseWrapper<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex,
                                                                                WebRequest request)
    {
    String errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ErrorResponse error = new ErrorResponse(
            "Validation Failed",
                                            errors,
                                            "VALIDATION_ERROR",
                                            Instant.now().toEpochMilli()
    );

    Metadata meta = new Metadata(extractRequestId(request), Instant.now().toEpochMilli(), apiVersion);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(meta, error));
    }

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ResponseWrapper<ErrorResponse>> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                                     WebRequest request)
    {
    ErrorResponse error = new ErrorResponse(
            "Bad Request",
                                            ex.getMessage(),
                                            "ILLEGAL_ARGUMENT",
                                            Instant.now().toEpochMilli()
    );

    Metadata meta = new Metadata(extractRequestId(request), Instant.now().toEpochMilli(), apiVersion);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(meta, error));
    }

@ExceptionHandler(Exception.class)
public ResponseEntity<ResponseWrapper<ErrorResponse>> handleGenericException(Exception ex, WebRequest request)
    {
    ErrorResponse error = new ErrorResponse(
            "Internal Server Error",
            ex.getMessage() != null ? ex.getMessage() : "No message available",
            "INTERNAL_ERROR",
            Instant.now().toEpochMilli()
    );
    Metadata meta = new Metadata(extractRequestId(request), Instant.now().toEpochMilli(), apiVersion);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>(meta, error));
    }

private String extractRequestId(WebRequest request)
    {
    String rid = request.getHeader("X-Request-Id");
    if (rid != null && !rid.isEmpty())
        return rid;
    return java.util.UUID.randomUUID().toString();
    }
}
