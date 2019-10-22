#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.LoggerFactory;

import com.vaadin.cdi.annotation.VaadinServiceEnabled;
import com.vaadin.cdi.annotation.VaadinServiceScoped;
import com.vaadin.flow.i18n.I18NProvider;

/**
 * The default implementation of {@link I18NProvider} to provide labels for this application
 *
 * @author ${organizationName}
 * @since ${version}
 */
@VaadinServiceScoped
@VaadinServiceEnabled
public class DefaultI18NProvider implements I18NProvider, ComponentConfigurationProvider {

    private static final String BUNDLE_PREFIX = "labels";

    @Override
    public List<Locale> getProvidedLocales() {

        // extend the list to return, get it from a system-configuration or get it from a configuration-service

        return Arrays.asList(Locale.ENGLISH);
    }

    @Override
    public String getTranslation(final String key, final Locale locale, final Object... params) {

        if (key == null) {
            LoggerFactory.getLogger(DefaultI18NProvider.class.getName()).warn("Got lang request for key with null value!");
            return "";
        }

        final ResourceBundle bundle = readProperties(locale);

        String value;

        try {

            value = bundle.getString(key.toLowerCase());

        } catch (final MissingResourceException e) {

            LoggerFactory.getLogger(DefaultI18NProvider.class.getName()).warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    @Override
    public <T extends ComponentView> boolean isUsed(final Class<T> viewType, final String componentId) {

        // TODO null-safety check

        return readProperties(Locale.getDefault()).containsKey(viewType.getSimpleName().toLowerCase() + "." + componentId.toLowerCase() + ".label");
    }

    private ResourceBundle readProperties(final Locale locale) {

        // TODO implement tenant-staged reading (e.g. 1th sygnum-labels.properties, 2th labels.properties)

        final ClassLoader classLoader = DefaultI18NProvider.class.getClassLoader();

        return ResourceBundle.getBundle(BUNDLE_PREFIX, locale, classLoader);
    }
}
