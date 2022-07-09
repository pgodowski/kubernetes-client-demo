package io.fabric8;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ConfigMapAndSecretInformers {
    private static final Logger logger = LoggerFactory.getLogger(ConfigMapAndSecretInformers.class.getName());

    public static void main(String[] args) {
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
            SharedInformerFactory informerFactory = client.informers();
            SharedIndexInformer<ConfigMap> configMapInformer = informerFactory.sharedIndexInformerFor(ConfigMap.class, 1 * 60 * 1000);
            SharedIndexInformer<Secret> secretInformer = informerFactory.sharedIndexInformerFor(Secret.class, 1 * 60 * 1000);
            configMapInformer.addEventHandler(new ResourceEventHandler<ConfigMap>() {
                @Override
                public void onAdd(ConfigMap configMap) {
                    logger.info("ConfigMapHandler: {}, {} added", configMap.getMetadata().getName(), configMap.getMetadata().getResourceVersion());
                }

                @Override
                public void onUpdate(ConfigMap configMap, ConfigMap newResource) {
                    logger.info("ConfigMapHandler: {}, {} has been updated", newResource.getMetadata().getName(), configMap.getMetadata().getResourceVersion());
                }

                @Override
                public void onDelete(ConfigMap configMap, boolean b) {
                    logger.info("ConfigMapHandler: {}, {} deleted", configMap.getMetadata().getName(), configMap.getMetadata().getResourceVersion());
                }
            });
            secretInformer.addEventHandler(new ResourceEventHandler<Secret>() {
                @Override
                public void onAdd(Secret secret) {
                    logger.info("SecretHandler: {}, {} added", secret.getMetadata().getName(), secret.getMetadata().getResourceVersion());
                }

                @Override
                public void onUpdate(Secret secret, Secret newSecret) {
                    logger.info("SecretHandler: {}, {} has been updated", newSecret.getMetadata().getName(), secret.getMetadata().getResourceVersion());
                }

                @Override
                public void onDelete(Secret secret, boolean b) {
                    logger.info("SecretHandler: {}, {} deleted", secret.getMetadata().getName(), secret.getMetadata().getResourceVersion());
                }
            });

            Future<Void> startAllInformersFuture = informerFactory.startAllRegisteredInformers();
            startAllInformersFuture.get();

            Thread.sleep(30 * 60 * 1000);
        } catch (InterruptedException interruptedException) {
            logger.error("Interrupted", interruptedException);
            Thread.currentThread().interrupt();
        } catch (ExecutionException executionException) {
            logger.error("Error in starting informers", executionException);
        }
    }
}