package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class BackupConfig {

    @JsonPropertyDescription("Is the backup of the service enable / configured")
    @Required
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnbled(Boolean enabled) {
        this.enabled = enabled;
    }

}
