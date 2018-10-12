package com.integration.routes;

import com.integration.aggregation.DecimalsAggregationExpression;
import com.integration.aggregation.DecimalsAggregationStrategy;
import com.integration.endpoints.DecimalsSoapCXFEndpoint;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@Component
public class DecimalsRouteBuilder extends RouteBuilder {

    private static final String DECIMALS_TRANSFORMER = "decimalsTransformer";
    public static final String TIMER_ROUTE = "timerRoute";
    private static final String DIRECT_AGGREGATOR = "direct:aggregator";

    @Autowired
    private DecimalsSoapCXFEndpoint soapCXFEndpoint;

    @Autowired
    private DecimalsAggregationStrategy myAggregationStrategy;

    @Autowired
    private DecimalsAggregationExpression camelAggregatorExpression;

    @Override
    public void configure() throws Exception {
        from("timer://decimals?period=1000").routeId(TIMER_ROUTE)
                .to("http4://localhost:8081/decimals")
                .bean(DECIMALS_TRANSFORMER, "processJsonOutput")
                .to("direct:decimalList");

        from("direct:decimalList").routeId("processDecimalsRoute")
                .split().body()
                .multicast().to("direct:transformDecimals", "direct:answers")
                .end();

        from("direct:transformDecimals").routeId("writeDecimalsRoute")
                .bean(DECIMALS_TRANSFORMER, "transformDecimals")
                .to(DIRECT_AGGREGATOR);

        from("direct:answers").routeId("writeAnswerRoute")
                .to(soapCXFEndpoint)
                .bean(DECIMALS_TRANSFORMER, "transformAnswer")
                .to(DIRECT_AGGREGATOR);

        from(DIRECT_AGGREGATOR).routeId("aggregatorRoute").aggregate(camelAggregatorExpression)
                .aggregationStrategy(myAggregationStrategy).completionSize(2)
                .to("file:data/output?fileName=output.txt&fileExist=Append");
    }
}
