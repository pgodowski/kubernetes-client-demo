package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class KafkaClusterSpec {

    @JsonPropertyDescription("The version of the Kafka cluster")
    @Required
    private String version;

    @JsonPropertyDescription("HA configuration of the Kafka Cluster service")
    private KafkaClusterHA ha;

    @JsonPropertyDescription("Backup configuration of the Kafka Cluster service")
    private BackupConfig backupConfig;

    private CredStore credStore;

    @JsonPropertyDescription("Specifies the tenacy (sharing) of the Kafka Cluster")
    private Tenancy tenancy;

    @JsonPropertyDescription("Properties of the service lifecycle")
    private LifecycleConfig lifecycleConfiguration;

}
