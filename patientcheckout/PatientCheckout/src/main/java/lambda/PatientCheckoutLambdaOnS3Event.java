package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lambda.entity.Patient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PatientCheckoutLambdaOnS3Event {

    // Amazon S3 client
    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    // Jackson ObjectMapper for JSON parsing
    private final ObjectMapper objectMapper = new ObjectMapper();
    // To publish message or Patient into SNS Topic
    private final AmazonSNS amazonSNS = AmazonSNSClientBuilder.defaultClient();
    /**
     * When we add json patient data event is triggered and we catch that event.
     * Lambda handler for S3 event
     */
    public void handleRequest(S3Event event, Context context){
        event.getRecords().forEach(record -> {
            String bucketName = record.getS3().getBucket().getName();
            String objectKey = record.getS3().getObject().getKey();
            List<Patient> patientList;

            try (S3Object s3Object = s3.getObject(bucketName, objectKey);
                 S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent()) {

                // Parse JSON array to list of Patient objects
                patientList = Arrays.asList(
                        objectMapper.readValue(s3ObjectInputStream, Patient[].class)
                );
                //Once json data of patient is uploaded in S3 and event is triggered we can see in log in coludwatch.
                System.out.println(patientList);
                patientList.forEach(patient -> {
                    try {
                        amazonSNS.publish(System.getenv("PATIENT_CHECKOUT_TOPIC"),
                                objectMapper.writeValueAsString(patient));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

                // TODO: Publish each patient to SNS or further processing
                patientList.forEach(patient -> {
                    // example: publish to SNS
                    // sns.publish(topicArn, objectMapper.writeValueAsString(patient));
                });

            } catch (IOException e) {
                // Handle exception properly
                context.getLogger().log("Error processing S3 object: " + e.getMessage());
            }
        });
    }
}
