package net.koko.data;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

import com.couchbase.client.java.query.N1qlQueryResult;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.stream.Stream;

import static com.couchbase.client.java.query.Select.select;

@Component
public class SimpleAccess {

    Cluster cluster = CouchbaseCluster.create();

    @PostConstruct
    public void postConstruct() {
        System.setProperty("com.couchbase.queryEnabled", "true");
        this.addEvents();
    }

    @PreDestroy
    public void preDestroy() {
        this.removeEvents();
        cluster.disconnect();
    }

    public void addEvents() {
        Bucket bucket = bucket();
        JsonObject event1 = createEvent("Party", "All day all night", "Home", "Blah street 123", true);
        JsonObject event2 = createEvent("Party", "All night all day", "Club", "Rock street 123", true);
        bucket.upsert(JsonDocument.create("event1", event1));
        bucket.upsert(JsonDocument.create("event2", event2));
    }

    public void removeEvents() {
        Bucket bucket = bucket();
        bucket.remove("event1");
        bucket.remove("event2");
    }

    public Stream<JsonObject> fetchEvents() {
        N1qlQueryResult events = bucket().query(select("*").from("events"));
        return events.allRows().stream().map(r -> r.value());
    }

    private Bucket bucket() {
        return cluster.openBucket("events", "administrator");
    }

    private JsonObject createEvent(String name, String description, String locationType, String address, Boolean reservationRequired) {
        return JsonObject.empty()
                .put("name", name)
                .put("description", description)
                .put("location_type", locationType)
                .put("address", address)
                .put("reservation_required", reservationRequired);
    }

}
