package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class KafkaClusterHA {

    @JsonPropertyDescription("Whether Kafka Cluster is configured with HA or not")
    @Required
    private Boolean enabled;

    @JsonPropertyDescription("Number of replicas")
    private int replicas;

    @JsonPropertyDescription("Should multi Availability Zones configuration be enabled")
    private Boolean multiAZ;

}
