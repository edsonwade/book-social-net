package code.with.vanilson.util.constants;

public class Constant {
    // Token-related constants
    public static final int ACTIVATION_TOKEN_LENGTH = 6;

    // Time-related constants
    public static final int MINUTES = 15;

    // Other constants can be added as needed
    public static final String USER_ROLE_NAME = "USER";

    // Prevent instantiation of the class since it is just for constants
    private Constant() {
        throw new UnsupportedOperationException("Constant class should not be instantiated");
    }

}
