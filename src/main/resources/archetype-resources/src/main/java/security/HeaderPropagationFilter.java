#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * The {@link ClientRequestFilter} to propagate a set of header-entries on rest-service calls
 *
 * @author ${organizationName}
 * @since ${version}
 */
public class HeaderPropagationFilter implements ClientRequestFilter {

    private final Map<String, String> headerEntriesToSet;

    /**
     * Constructs and initializes an object of type {@link HeaderPropagationFilter}
     *
     * @param headerEntriesToSet the entries key-value to set in the header of a rest-service call
     */
    public HeaderPropagationFilter(final Map<String, String> headerEntriesToSet) {

        if (headerEntriesToSet != null) {
            this.headerEntriesToSet = headerEntriesToSet;
        } else {
            this.headerEntriesToSet = new HashMap<>();
        }
    }

    @Override
    public void filter(final ClientRequestContext reqContext) throws IOException {

        for (final Entry<String, String> headerEntryToSet : this.headerEntriesToSet.entrySet()) {

            reqContext.getHeaders().add(headerEntryToSet.getKey(), headerEntryToSet.getValue());
        }
    }
}