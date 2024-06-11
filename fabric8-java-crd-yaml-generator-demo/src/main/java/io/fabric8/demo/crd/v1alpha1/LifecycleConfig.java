package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class LifecycleConfig {


    @JsonPropertyDescription("Flag must be explicitly set to true before the resource can be decomissoned")
    private Boolean allowDeletion;

    @JsonPropertyDescription("Should the resource reconciliation be paused, e.g. for the maintenace purposes")
    private Boolean pauseReconciliation;


}
