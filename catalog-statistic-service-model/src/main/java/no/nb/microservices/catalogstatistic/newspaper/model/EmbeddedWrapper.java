package no.nb.microservices.catalogstatistic.newspaper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredw on 9/16/15.
 */
public class EmbeddedWrapper {
    private List<NewspaperResource> items = new ArrayList<>();

    public List<NewspaperResource> getItems() {
        return items;
    }

    public void setItems(List<NewspaperResource> items) {
        this.items = items;
    }
}
