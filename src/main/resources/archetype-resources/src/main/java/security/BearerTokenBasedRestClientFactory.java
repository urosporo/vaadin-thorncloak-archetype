#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.eclipse.microprofile.rest.client.RestClientBuilder.newBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;

import com.vaadin.cdi.annotation.NormalUIScoped;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.server.VaadinRequest;

import ch.dsent.security.HeaderPropagationFilter;

/**
 * The rest-client-factory to create instances of resteasy clients which provides a Bearer token, the language and the tenant in the header
 *
 * @author ${organizationName}
 * @since ${version}
 */
@NormalUIScoped
public class BearerTokenBasedRestClientFactory implements LocaleChangeObserver {

    private static final long serialVersionUID = -1621057761083226783L;

    private final Map<String, String> headerEntriesToSet;

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
     * @param endPointUrl
     *            the endpoint url to use for the client
     * @param serviceApiType
     *            the class-type of the service-api
     * @return an optional created client of the service-api (empty if an error occurs)
     */
    public <T> Optional<T> createInstance(final String endPointUrl, final Class<T> serviceApiType) {

        if (!this.headerEntriesToSet.containsKey("tenant")) {
            initialSecurity();
        }

        try {

            final URL url = new URL(endPointUrl);

            final RestClientBuilder builder = newBuilder().baseUrl(url);
            builder.register(new HeaderPropagationFilter(this.headerEntriesToSet));

            return of(builder.build(serviceApiType));

        } catch (final MalformedURLException e) {

            return empty();
        }

    }

    private void initialSecurity() {

        final Principal userPrincipal = VaadinRequest.getCurrent().getUserPrincipal();

        if (userPrincipal instanceof KeycloakPrincipal) {

            @SuppressWarnings("unchecked")
            final KeycloakSecurityContext securityContext = ((KeycloakPrincipal<KeycloakSecurityContext>) userPrincipal).getKeycloakSecurityContext();

            this.headerEntriesToSet.put("tenant", securityContext.getRealm());
            this.headerEntriesToSet.put(HttpHeaders.AUTHORIZATION, "Bearer " + securityContext.getTokenString());
        }
    }

    @Override
    public void localeChange(final LocaleChangeEvent event) {

        this.headerEntriesToSet.put("language", event.getLocale().getLanguage());
    }
}