package no.nb.microservices.catalogstatistic.newspaper.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by alfredw on 9/16/15.
 */
public class NewspaperResource extends ResourceSupport {

    private final String title;
    private final String firstScannedEditionDate;
    private final String lastScannedEditionDate;
    private final long numberOfEditions;

    @JsonCreator
    public NewspaperResource(@JsonProperty("title") String title,
                             @JsonProperty("firstScannedEditionDate") String firstScannedEditionDate,
                             @JsonProperty("lastScannedEditionDate") String lastScannedEditionDate,
                             @JsonProperty("numberOfEditions") long numberOfEditions) {
        this.title = title;
        this.firstScannedEditionDate = firstScannedEditionDate;
        this.lastScannedEditionDate = lastScannedEditionDate;
        this.numberOfEditions = numberOfEditions;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstScannedEditionDate() {
        return firstScannedEditionDate;
    }

    public String getLastScannedEditionDate() {
        return lastScannedEditionDate;
    }

    public long getNumberOfEditions() {
        return numberOfEditions;
    }
}
