package io.fabric8;

import io.fabric8.kubernetes.api.model.EventList;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

public class EventsExample {
    public static void main(String[] args) {
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
            EventList eventList = client.v1().events().list();
            eventList.getItems().forEach(e -> System.out.println(e.getMessage()));
        }
    }
}
