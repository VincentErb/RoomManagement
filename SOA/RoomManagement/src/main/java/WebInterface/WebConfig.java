package WebInterface;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class WebConfig extends ResourceConfig {

	public WebConfig() {
        packages("WebInterface");
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/jsp");
        register(JspMvcFeature.class);
    }
}
