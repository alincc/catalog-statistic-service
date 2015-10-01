package no.nb.microservices.catalogstatistic.newspaper.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by alfredw on 9/16/15.
 */
public class StatisticResource extends ResourceSupport {

    private PageMetadata pageMetadata;
    private EmbeddedWrapper embedded;

    @JsonCreator
    public StatisticResource(@JsonProperty("page") PageMetadata pageMetadata,
                             @JsonProperty("_embedded") EmbeddedWrapper embedded) {
        this.pageMetadata = pageMetadata;
        this.embedded = embedded;
    }

    @JsonProperty("page")
    public PageMetadata getPageMetadata() {
        return pageMetadata;
    }

    public void setPageMetadata(PageMetadata pageMetadata) {
        this.pageMetadata = pageMetadata;
    }

    @JsonProperty("_embedded")
    public EmbeddedWrapper getEmbedded() {
        return embedded;
    }

    public void setEmbedded(EmbeddedWrapper embedded) {
        this.embedded = embedded;
    }
}
