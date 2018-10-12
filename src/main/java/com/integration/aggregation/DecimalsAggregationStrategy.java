package com.integration.aggregation;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@Component
public class DecimalsAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(final Exchange oldExchange, final Exchange newExchange) {
        if (oldExchange != null) {
            newExchange.getIn().setBody((String) oldExchange.getIn().getBody() + newExchange.getIn().getBody());
        }
        return newExchange;
    }
}
