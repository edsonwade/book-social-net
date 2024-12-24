package code.with.vanilson.exception.details;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ErrorDetail
 *
 * @author vamuhong
 * @version 1.0
 * @since 2024-08-29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetail {
    private String field;         // The field or context in which the error occurred
    private String message;
}