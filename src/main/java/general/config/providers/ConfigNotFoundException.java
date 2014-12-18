package general.config.providers;

/**
 * Exception which indicates that required property by given name is absent.
 *
 * @see ConfigProvider
 */
public class ConfigNotFoundException extends RuntimeException {

    public static final String MESSAGE_PATTERN = "Property '%s' is absent!";

    public ConfigNotFoundException(String properyName) {
        super(String.format(MESSAGE_PATTERN, properyName));
    }
}
