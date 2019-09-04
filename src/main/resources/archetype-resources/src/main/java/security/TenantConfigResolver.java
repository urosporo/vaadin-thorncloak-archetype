#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides multi tenancy support to the service, by reading the tenant for the request header. The tenant value is then used to resolve
 * the tenant specific keycloak client config of this service from the class path resources.
 *
 * @author ${organizationName}
 * @since ${version}
 */
public class TenantConfigResolver implements KeycloakConfigResolver {

    private static final Logger LOG = LoggerFactory.getLogger(TenantConfigResolver.class);

    @Override
    public KeycloakDeployment resolve(final HttpFacade.Request facade) {

        try {

            final URI uri = new URI(facade.getURI());

            String tenant = uri.getHost().substring(0, uri.getHost().indexOf('.'));

            if (tenant.contains("-")) {
                tenant = tenant.substring(0, tenant.indexOf('-'));
            }

            LOG.info("Resolving ${applicationName} client config for tenant '{}'", tenant);

            final InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(String.format("keycloak-config/%s.json", tenant));

            if (is != null) {
                return buildDeployment(tenant, is);
            } else {
                LOG.warn("No ${applicationName} webclient config for tenant '{}'. Check if the client config is packaged inside the ${applicationName} webclient!", tenant);
            }
        } catch (final URISyntaxException e) {

            LOG.error("Unable to initialize keycloak for malformed uri '{}'.", facade.getURI(), e);
        }
        return null;
    }

    private KeycloakDeployment buildDeployment(final String tenant, final InputStream is) {

        try {
            return KeycloakDeploymentBuilder.build(is);
        } catch (final RuntimeException e) {
            LOG.error("Unable to initialize keycloak for tenant '{}'.", tenant, e);
        }
        return null;
    }
}