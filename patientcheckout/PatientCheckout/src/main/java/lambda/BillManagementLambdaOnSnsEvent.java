package lambda;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lambda.entity.Patient;

public class BillManagementLambdaOnSnsEvent {

    // Jackson ObjectMapper for JSON parsing
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void handleRequest(SNSEvent snsEvent){
            snsEvent.getRecords().forEach(snsRecord -> {

                try {
                    Patient patient = objectMapper.readValue(snsRecord.getSNS().getMessage(), Patient.class);
                    System.out.println("Received Patient Object : "+patient);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            });
    }
}
