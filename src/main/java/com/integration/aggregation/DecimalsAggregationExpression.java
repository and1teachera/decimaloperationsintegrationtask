package com.integration.aggregation;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.springframework.stereotype.Component;

/**
 * @author Angel Zlatenov
 */
@Component
public class DecimalsAggregationExpression implements Expression {
    @Override
    public <T> T evaluate(final Exchange exchange, final Class<T> type) {
        return (T) Integer.toString((Integer) exchange.getProperties().get("CamelSplitIndex"));
    }
}
