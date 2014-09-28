package org.vaadin.cloudbox.hellocluster;

import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import javax.servlet.annotation.WebInitParam;

@Theme("valo")
@SuppressWarnings("serial")
@PreserveOnRefresh
public class HelloClusterUI extends UI {

    private Label clickCounterLabel;
    private long clickCounter;

    @WebServlet(value = "/*", asyncSupported = true, initParams = {
        @WebInitParam(name = "disable-xsrf-protection", value = "true"),
        @WebInitParam(name = "syncIdCheck", value = "false")}
    )
    @VaadinServletConfiguration(productionMode = false, ui = HelloClusterUI.class)

    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        layout.addComponent(new Label("Session id: " + request.getWrappedSession().getId()));
        layout.addComponent(new Label("Created: " + new Date()));

        InetAddress h = getCurrentHost();
        layout.addComponent(new Label("Host: " + h.getHostName()));
        layout.addComponent(new Label("Address: " + h.getHostAddress()));

        Button button = new Button("Say hello");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                InetAddress h = getCurrentHost();
                Notification.show("Greetings from " + h.getHostName() + " (" + h.getHostAddress() + ")");
                clickCounter++;
                clickCounterLabel.setValue("Clicks: " + clickCounter);
            }

        });
        layout.addComponent(button);
        layout.addComponent(clickCounterLabel = new Label("Clicks: " + clickCounter));
    }

    private InetAddress getCurrentHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException ignored) {
        }
        return InetAddress.getLoopbackAddress();
    }

}
