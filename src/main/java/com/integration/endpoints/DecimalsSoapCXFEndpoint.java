package com.integration.endpoints;

import org.apache.camel.component.cxf.CxfEndpoint;
import org.example.math.WsMath;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */

@Component
public class DecimalsSoapCXFEndpoint extends CxfEndpoint {

    public DecimalsSoapCXFEndpoint() {
        this.setServiceClass(WsMath.class);
        this.setWsdlURL("http://localhost:8082/math/sum?wsdl");
    }
}
