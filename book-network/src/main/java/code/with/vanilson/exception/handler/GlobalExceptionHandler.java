package code.with.vanilson.exception.handler;

import code.with.vanilson.exception.ErrorResponse;
import code.with.vanilson.exception.RoleNotFoundException;
import code.with.vanilson.exception.TokenInvalidException;
import code.with.vanilson.exception.details.ErrorDetail;
import code.with.vanilson.util.TimeZoneUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

import static code.with.vanilson.util.http.HttpErrorCodes.BOOK_INVALID_DATA;
import static code.with.vanilson.util.http.HttpErrorCodes.INVALID_INPUT_DATA;
import static java.time.LocalDateTime.now;

/**
 * GlobalExceptionHandler
 * Handles various types of exceptions globally in the application and returns
 * structured error responses to the client.
 *
 * @author vamuhong
 * @version 1.1
 * @since 2024-07-05
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    /**
     * Handles and responds to RoleNotFoundException exceptions.
     * This exception is thrown when a requested resource is not found in the system.
     *
     * @param ex      The RoleNotFoundException that occurred.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorResponse object and an HTTP status of 404 (Not Found).
     * The ErrorResponse object contains specific error details about the not found resource.
     */
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RoleNotFoundException ex,
                                                                 HttpServletRequest request) {
        var path = request.getRequestURI();

        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                path
        );
    }

    /**
     * Handles and responds to TokenInvalidException exceptions.
     * This exception is thrown when the token provided is invalid or expired.
     *
     * @param ex      The TokenInvalidException that occurred.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorResponse object and an HTTP status of 400 (Bad Request).
     * The ErrorResponse object contains specific error details about the invalid token.
     */
    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTokenInvalidException(TokenInvalidException ex,
                                                                     HttpServletRequest request) {
        var path = request.getRequestURI();

        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                path
        );
    }

    /**
     * Handles and responds to general exceptions that are not explicitly caught by other exception handlers.
     *
     * @param ex      The exception that occurred.
     * @param request The HTTP request that triggered the exception.
     * @return A ResponseEntity containing an ErrorResponse object and an HTTP status of 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        var path = request.getRequestURI();

        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                path
        );
    }

    /**
     * Handles validation errors when method arguments fail validation.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest request) {
        List<ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> new ErrorDetail(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return buildErrorResponse(INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    /**
     * Constructs a ResponseEntity containing an ErrorResponse.
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, String path) {
        String traceId = UUID.randomUUID().toString();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .status(status.name())
                .zone(TimeZoneUtil.getRandomTimeZone())
                .errorCode(status.value())
                .path(path)
                .timestamp(now())
                .code(determineErrorCodeFromPath(path))
                .traceId(traceId)
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Determines the error code based on the request path.
     */
    private String determineErrorCodeFromPath(String path) {
        if (path.contains("/api/books/")) {
            return BOOK_INVALID_DATA;
        } else if (path.contains("/api/member/")) {
            return "MEMBER_INVALID_DATA";
        } else if (path.contains("/api/librarians/")) {
            return "LIBRARIAN_INVALID_DATA";
        } else if (path.contains("/api/admins/")) {
            return "ADMIN_INVALID_DATA";
        } else if (path.contains("/api/fines/")) {
            return "FINE_INVALID_DATA";
        } else {
            return "UNKNOWN_ERROR";
        }
    }
}