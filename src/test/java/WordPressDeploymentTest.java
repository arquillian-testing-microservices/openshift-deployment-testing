import io.fabric8.kubernetes.api.model.v2_6.Service;
import io.fabric8.openshift.api.model.DeploymentConfig;
import io.fabric8.openshift.client.OpenShiftClient;
import java.io.IOException;
import java.net.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.arquillian.cube.kubernetes.annotations.Named;
import org.arquillian.cube.kubernetes.annotations.PortForward;
import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.arquillian.cube.requirement.ArquillianConditionalRunner;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(ArquillianConditionalRunner.class)
@RequiresOpenshift
public class WordPressDeploymentTest {

    @Named("wordpress-mysql-example")
    @PortForward
    @ArquillianResource
    Service wordpress;

    @Named("wordpress-mysql-example")
    @PortForward
    @ArquillianResource
    Service mysql;

    @Named("wordpress-mysql-example")
    @PortForward
    @ArquillianResource
    URL url;

    @Named("wordpress-mysql-example")
    @PortForward
    @ArquillianResource
    DeploymentConfig wordpressExample;

    @ArquillianResource
    OpenShiftClient client;

    @Test
    public void verify_openshift_client_should_not_be_null() throws IOException {
        assertThat(client).isNotNull();
    }

    @Test
    public void verify_wordpress_service_should_not_be_null() throws IOException {
        assertThat(wordpress).isNotNull();
        assertThat(wordpress.getSpec()).isNotNull();
        assertThat(wordpress.getSpec().getPorts()).isNotNull();
        assertThat(wordpress.getSpec().getPorts()).isNotEmpty();
    }

    @Test
    public void verify_mysql_service_should_not_be_null() throws IOException {
        assertThat(mysql).isNotNull();
        assertThat(mysql.getSpec()).isNotNull();
        assertThat(mysql.getSpec().getPorts()).isNotNull();
        assertThat(mysql.getSpec().getPorts()).isNotEmpty();
    }

    @Test
    public void verify_wordpress_deployment_config_should_not_be_null() throws IOException {
        assertThat(wordpressExample).isNotNull();
        assertThat(wordpressExample.getStatus()).hasFieldOrPropertyWithValue("availableReplicas", 1);
    }

    @Test
    public void verify_wordpress__should_not_be_null() throws IOException {
        assertThat(wordpressExample).isNotNull();
        assertThat(wordpressExample.getStatus()).hasFieldOrPropertyWithValue("availableReplicas", 1);
    }

    @Test
    public void verify_route_is_configured_and_wordpress_service_is_accessible() throws IOException {
        assertThat(url).isNotNull();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        Response response = okHttpClient.newCall(request).execute();

        assertThat(response).isNotNull();
        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body().string()).contains("Welcome to WordPress");
    }
}
