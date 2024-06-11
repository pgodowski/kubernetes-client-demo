package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;


// https://github.com/fabric8io/kubernetes-client/blob/main/doc/CRD-generator.md

@Version("v1")
@Group("operator.ibm.com")
public class KafkaCluster
    extends CustomResource<KafkaClusterSpec, KafkaClusterStatus>
    implements Namespaced { }
