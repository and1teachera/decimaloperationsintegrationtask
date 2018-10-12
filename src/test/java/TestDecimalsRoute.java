import com.integration.Application;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * @author Angel Zlatenov
 */

@ActiveProfiles("test")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class)
public class TestDecimalsRoute {

    @EndpointInject(uri = MOCK_RESULT)
    private MockEndpoint resultEndpoint;

    @Autowired
    private CamelContext camelContext;

    @EndpointInject(uri = MOCK_TIMER)
    private ProducerTemplate producer;

    private static final String MOCK_RESULT = "mock:result";
    private static final String MOCK_TIMER = "direct:mock-timer";

    @Test
    public void testConfigure() throws InterruptedException {


        final File file = new File("data/output");
        assertTrue(file.isDirectory());
        assertEquals(1, file.listFiles().length);
    }
}
