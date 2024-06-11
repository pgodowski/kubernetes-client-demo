package io.fabric8.demo.crd.v1alpha1;

import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Required;
import io.fabric8.generator.annotation.Default;

public class CredStore {

    @JsonPropertyDescription("Location of the Credential Store where to put provisioned service credenials into")
    private String url;

    @JsonPropertyDescription("Location of the key within Credentials Store where the credentials are available")
    private String path;

    @JsonPropertyDescription("The name of the Kubernetes Secret with the connection credentials")
    private String credentialsSecretName;

    @JsonPropertyDescription("The name of the Kubernetes ConfigMap with the connection string")
    private String connectionConfig;


}
