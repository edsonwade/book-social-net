package code.with.vanilson.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * MessageUtil
 *
 * @author vamuhong
 * @version 1.0
 * @since 2024-12-24
 */
@Component
@SuppressWarnings("unused")
public class MessageUtil {
    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}