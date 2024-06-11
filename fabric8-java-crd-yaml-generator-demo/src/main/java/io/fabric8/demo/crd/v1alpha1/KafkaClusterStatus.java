package io.fabric8.demo.crd.v1alpha1;


import com.fasterxml.jackson.annotation.JsonPropertyDescription;


public class KafkaClusterStatus {

    @JsonPropertyDescription("The reference to the Secret name which contains the connection string and credentials to connect to the Kafka cluster")
    private CredStore credentialsStore;

    @JsonPropertyDescription("The status representation of the service provisioning. Fully available when status says Ready")
    private String status;


}
