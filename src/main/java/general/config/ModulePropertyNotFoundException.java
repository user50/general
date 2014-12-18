package general.config;

/**
 * Exception which indicates that required property by given name is absent.
 *
 * @see PropertiesProvider
 */
public class ModulePropertyNotFoundException extends RuntimeException {

    public static final String MESSAGE_PATTERN = "Property '%s' is absent!";

    public ModulePropertyNotFoundException(String properyName) {
        super(String.format(MESSAGE_PATTERN, properyName));
    }
}
