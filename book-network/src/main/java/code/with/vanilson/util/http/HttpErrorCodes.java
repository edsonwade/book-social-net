package code.with.vanilson.util.http;

/**
 * HttpErrorCodes
 * <p>
 * This class contains error codes for different types of errors that may occur in the application.
 * These error codes can be used to identify the specific type of error in the response to the client.
 *
 * @author vamuhong
 * @version 1.0
 * @since 2024-12-24
 */
@SuppressWarnings("all")
public class HttpErrorCodes {
    // HTTP status-related error codes
    public static final String NOT_FOUND = "NOT_FOUND"; // 404
    public static final String BAD_REQUEST = "BAD_REQUEST"; // 400
    public static final String CONFLICT = "CONFLICT"; // 409
    public static final String UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY"; // 422
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE"; // 503
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR"; // 500

    // Generic error messages
    public static final String UNEXPECTED_ERROR_OCCURRED = "An unexpected error occurred";
    public static final String CONSTRAINT_VIOLATIONS_OCCURRED = "Constraint violations occurred.";
    public static final String INVALID_INPUT_DATA = "Invalid input data.";

    // Domain-specific error codes (e.g., for book-related errors)
    public static final String BOOK_INVALID_DATA = "BOOK_INVALID_DATA";

    // Private constructor to prevent instantiation
    private HttpErrorCodes() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
}
