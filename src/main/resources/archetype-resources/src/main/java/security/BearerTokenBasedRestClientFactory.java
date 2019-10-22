#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.StreamSupport.stream;
import static org.eclipse.microprofile.config.ConfigProvider.getConfig;
import static org.eclipse.microprofile.rest.client.RestClientBuilder.newBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.cdi.annotation.NormalUIScoped;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.VaadinRequest;

import ch.dsent.onboarding.security.BearerTokenBasedRestClientFactory;
import ch.dsent.onboarding.security.HeaderPropagationFilter;

/**
 * The rest-client-factory to create instances of resteasy clients which provides a Bearer token, the language and the tenant in the header
 *
 * @author DSENT AG
 * @since 1.0.0
 */
@NormalUIScoped
public class BearerTokenBasedRestClientFactory implements LocaleChangeObserver {

    private static final long serialVersionUID = -1621057761083226783L;

    private static final Logger LOG = LoggerFactory.getLogger(BearerTokenBasedRestClientFactory.class);

    private final Map<String, String> headerEntriesToSet;

    private KeycloakSecurityContext securityContext;

    /**
     * Creates a new instance of the <code>BearerTokenBasedRestClientFactory</code>
     */
    public BearerTokenBasedRestClientFactory() {

        this.headerEntriesToSet = new HashMap<>();
    }

    /**
     * Creates an instance of a resteasy service-api for a given url and with enrichs the client with {@link HeaderPropagationFilter}
     *
     * @param <T>
     *            the service-api type
     * @param endpointURLConfigName
     *            the configuration-name of the endpoint url configuration to use for the client
     * @param serviceApiType
     *            the class-type of the service-api
     * @return an optional created client of the service-api (empty if an error occurs)
     */
    public <T> Optional<T> createInstance(final String endpointURLConfigName, final Class<T> serviceApiType) {

        if (!this.headerEntriesToSet.containsKey("tenant")) {
            initialSecurity();
        }

        try {

            final Optional<String> endpointURL = resolveEndpointURL(endpointURLConfigName, serviceApiType);

            if (endpointURL.isPresent()) {

                final URL url = new URL(endpointURL.get());

                final RestClientBuilder builder = newBuilder().baseUrl(url);
                builder.register(new HeaderPropagationFilter(this.headerEntriesToSet));

                return of(builder.build(serviceApiType));
            }

            return empty();

        } catch (final MalformedURLException e) {

            return empty();
        }

    }

    @SuppressWarnings("unchecked")
    private void initialSecurity() {

        final Principal userPrincipal = VaadinRequest.getCurrent().getUserPrincipal();

        if (userPrincipal instanceof KeycloakPrincipal) {

            this.securityContext = ((KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal).getKeycloakSecurityContext();

            this.headerEntriesToSet.put("tenant", this.securityContext.getRealm());
            this.headerEntriesToSet.put(HttpHeaders.AUTHORIZATION, "Bearer " + this.securityContext.getTokenString());
        }
    }

    private <T> Optional<String> resolveEndpointURL(final String endpointURLConfigName, final Class<T> serviceApiType) {

        final String tenantSpecificEndpointURLConfigName = this.securityContext.getRealm().toUpperCase() + "_" + endpointURLConfigName;

        if (stream(getConfig().getPropertyNames().spliterator(), false).anyMatch(tenantSpecificEndpointURLConfigName::equals)) {

            return getEndpointURL(tenantSpecificEndpointURLConfigName, serviceApiType);

        } else if (stream(getConfig().getPropertyNames().spliterator(), false).anyMatch(endpointURLConfigName::equals)) {

            return getEndpointURL(endpointURLConfigName, serviceApiType);
        } else {
            LOG.error("Couldn't find the end-point configuration {} of the service-api {}.", endpointURLConfigName, serviceApiType.getName());

            // TODO show error-page
        }
        return empty();
    }

    private <T> Optional<String> getEndpointURL(final String endpointURLPropertyName, final Class<T> serviceApiType) {

        final String endpointURL = getConfig().getValue(endpointURLPropertyName, String.class);

        LOG.debug("Use the end-point {} for the service-api {}.", endpointURL, serviceApiType.getName());

        return of(endpointURL);
    }

    @Override
    public void localeChange(final LocaleChangeEvent event) {

        this.headerEntriesToSet.put("language", event.getLocale().getLanguage());
    }
}