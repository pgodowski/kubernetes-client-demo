package io.fabric8;

import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.api.model.GenericKubernetesResourceBuilder;
import io.fabric8.kubernetes.api.model.GenericKubernetesResourceList;
import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.json.JSONException;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RawCustomResourcesDemoBlog {
    private static final Logger logger = Logger.getLogger(RawCustomResourcesDemoBlog.class.getName());
    private static final CountDownLatch closeLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {

        try (final KubernetesClient client = new DefaultKubernetesClient()) {
            String namespace = "default";

            // Load CRD as object from YAML
            CustomResourceDefinition animalCrd = client.apiextensions().v1beta1().customResourceDefinitions()
                    .load(RawCustomResourcesDemoBlog.class.getResourceAsStream("/animals-crd.yml")).get();
            // Apply CRD object onto your Kubernetes cluster
            client.apiextensions().v1beta1().customResourceDefinitions().create(animalCrd);

            CustomResourceDefinitionContext animalCrdContext = new CustomResourceDefinitionContext.Builder()
                    .withName("animals.jungle.example.com")
                    .withGroup("jungle.example.com")
                    .withScope("Namespaced")
                    .withVersion("v1")
                    .withPlural("animals")
                    .build();

            // Creating from HashMap
            GenericKubernetesResource cr1 = client
                    .genericKubernetesResources(animalCrdContext)
                    .load(RawCustomResourcesDemoBlog.class.getResourceAsStream("/seal-cr.yml"))
                    .get();
            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).create(cr1);

            GenericKubernetesResource cr2 = new GenericKubernetesResourceBuilder()
                .withNewMetadata().withName("bison").endMetadata()
                .withAdditionalProperties(Collections.singletonMap("spec", Collections.singletonMap("image", "strong-bison-image")))
                .build();

            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).create(cr2);

            // Creating from Raw JSON String
            String crBasicString = "{" +
                    "  \"apiVersion\": \"jungle.example.com/v1\"," +
                    "  \"kind\": \"Animal\"," +
                    "  \"metadata\": {" +
                    "    \"name\": \"mongoose\"," +
                    "    \"namespace\": \"default\"" +
                    "  }," +
                    "  \"spec\": {" +
                    "    \"image\": \"my-silly-mongoose-image\"" +
                    "  }" +
                    "}";
            GenericKubernetesResource cr3 = client.genericKubernetesResources(animalCrdContext)
                .load(crBasicString)
                .get();
            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).create(cr3);


            // Listing Custom resources in a specific namespace
            GenericKubernetesResourceList animalList = client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).list();

            for (int index = 0; index < animalList.getItems().size(); index++) {
                GenericKubernetesResource currentItem = animalList.getItems().get(index);
                log(currentItem.getMetadata().getName());
            }

            // Updating a specific custom resource
            GenericKubernetesResource oldAnimal = client
                .genericKubernetesResources(animalCrdContext)
                .inNamespace(namespace)
                .withName("mongoose")
                .get();
            oldAnimal.setAdditionalProperties(Collections.singletonMap("spec", Collections.singletonMap("image", "my-silly-mongoose-image:v2")));
            oldAnimal.getMetadata().setLabels(Collections.singletonMap("updated", "true"));

            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).withName("mongoose").replace(oldAnimal);

            // Deleting a custom resource
            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).withName("seal").delete();

            // Watching a custom resource
            logger.info("Watching custom resources now, open for 10 minutes...");
            client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).watch(new Watcher<GenericKubernetesResource>() {
                @Override
                public void eventReceived(Action action, GenericKubernetesResource resource) {
                    try {
                        log(action + " : " + resource.getMetadata().getName());
                    } catch (JSONException exception) {
                        log("failed to parse object");
                    }
                }

                @Override
                public void onClose() { }

                @Override
                public void onClose(WatcherException e) {
                    log("Watcher onClose");
                    closeLatch.countDown();
                    if (e != null) {
                        log(e.getMessage());
                    }
                }
            });
            closeLatch.await(10, TimeUnit.MINUTES);
        }
    }

    private static void log(String action) {
        logger.info(action);
    }
}
