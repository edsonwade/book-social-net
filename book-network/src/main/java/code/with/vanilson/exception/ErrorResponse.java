package code.with.vanilson.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ErrorResponse represents the structure of an error response returned by the API.
 * It contains details about the error such as a message, error code, timestamp, and more.
 * This object is serialized to JSON to be returned in API responses.
 *
 * @author vamuhong
 * @version 1.1
 * @since 2024-12-24
 */
@Data
@JsonPropertyOrder(value = {"message", "status", "errorCode", "path", "zone", "timestamp", "code", "traceId"})
public class ErrorResponse {

    private String message;
    private String status;
    private String zone;
    private int errorCode;
    private String path;
    private LocalDateTime timestamp;
    private String code;
    private String traceId;

    /**
     * Private constructor to enforce the use of the builder pattern for object creation.
     * This prevents direct instantiation and ensures better control over the object creation.
     *
     * @param message   The error message.
     * @param status    The status of the error (e.g., "ERROR").
     * @param zone      The zone or region of the application (e.g., "US").
     * @param errorCode The error code associated with the error (e.g., 404).
     * @param path      The path that caused the error (e.g., "/api/v1/resource").
     * @param timestamp The timestamp when the error occurred.
     * @param code      A code associated with the error for easier identification.
     * @param traceId   The trace ID for tracking the error through distributed systems.
     */
    @Builder
    public ErrorResponse(String message, String status, String zone, int errorCode, String path,
                         LocalDateTime timestamp, String code, String traceId) {
        this.message = message;
        this.status = status;
        this.zone = zone;
        this.errorCode = errorCode;
        this.path = path;
        this.timestamp = timestamp;
        this.code = code;
        this.traceId = traceId;
    }
}
