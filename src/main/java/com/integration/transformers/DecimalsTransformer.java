package com.integration.transformers;

import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.example.math.Add;
import org.example.math.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */

@Component
public class DecimalsTransformer {

    private static final Logger log = LoggerFactory
            .getLogger(DecimalsTransformer.class);

    public void processJsonOutput(final String jsonOutput, final Exchange exchange) {
        if (jsonOutput == null || jsonOutput.length() == 0) {
            log.error("The output from the service is null");
            return;
        }
        final List<Map<String, Double>> list = new GsonBuilder().create().fromJson(jsonOutput, List.class);
        final List<Add> result = list.stream().map(this::createAddFromStringMap).collect(Collectors.toList());
        exchange.getIn().setBody(result);
    }

    private Add createAddFromStringMap(final Map<String, Double> stringStringMap) {
        final Add add = new Add();
        add.setX(stringStringMap.get("x"));
        add.setY(stringStringMap.get("y"));
        return add;
    }

    public void transformDecimals(final Exchange exchange) {
        final Message in = exchange.getIn();
        final Add add = in.getBody(Add.class);
        in.setBody(add.getX() + " + " + add.getY() + " = ");
    }

    public void transformAnswer(final Exchange exchange) {
        final Message in = exchange.getIn();
        final Answer answer = in.getBody(Answer.class);
        in.setBody(String.valueOf(answer.getResult()) + "\n");
    }
}
