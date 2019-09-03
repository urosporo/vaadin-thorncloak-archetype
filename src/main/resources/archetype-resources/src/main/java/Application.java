#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.inject.Inject;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;

import ${package}.security.BearerTokenBasedRestClientFactory;

/**
 * This is the ${applicationName} application entry-point
 * 
 * @author ${organizationName}
 * @since ${version}
 */
@Route(value = "")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Application extends Span {

    @Inject
    private BearerTokenBasedRestClientFactory clientFactory;

    public Application() {

        // Example how-to call e rest-service

        final Button button = new Button(new Icon(VaadinIcon.ABACUS));

        button.setText(getTranslation("view.application.button.label"));

        add(button);

//        button.addClickListener(clickEvent -> {
//
//            this.clientFactory.createInstance("http://localhost:8083", AccountTypeSerivceApi.class).ifPresent(serviceClient -> {
//
//                final AccountTypeResponse response = serviceClient.get();
//
//                System.out.println(response);
//            });
//        });
    }
}
