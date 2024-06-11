package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class Tenancy {

    @JsonPropertyDescription("Is the requestor accepting to get a shared Kafka Cluster")
    @Default("true")
    private Boolean multiTenant;


}
