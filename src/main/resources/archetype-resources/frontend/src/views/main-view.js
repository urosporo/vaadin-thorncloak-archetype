import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-app-layout/src/vaadin-app-layout.js';
import '@vaadin/vaadin-app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/vaadin-tabs/src/vaadin-tabs.js';

/**
 * `main-view`
 *
 * MainView element.
 *
 * @customElement
 * @polymer
 */
class MainView extends PolymerElement {

    static get template() {
        return html`
            <style include="shared-styles">
                :host {
                    display: block;
                }
            </style>
<vaadin-app-layout id="mv_appLayout">
 <vaadin-drawer-toggle slot="navbar" id="mv_drawerToggle"></vaadin-drawer-toggle>
 <vaadin-tabs slot="drawer" id="mv_tabs" orientation="vertical" theme="minimal" style="margin: 0 auto; flex: 1;" id="drawerTabs"></vaadin-tabs>
</vaadin-app-layout>
        `;
    }

    static get is() {
        return 'main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MainView.is, MainView);
