package no.nb.microservices.catalogstatistic.core.newspaper.model;

import org.springframework.data.domain.Page;

/**
 * Created by alfredw on 9/21/15.
 */
public class NewspaperStatisticAggregated {
    private Page<Newspaper> page;

    public NewspaperStatisticAggregated(Page<Newspaper> page) {
        this.page = page;
    }

    public Page<Newspaper> getPage() {
        return page;
    }

    public void setPage(Page<Newspaper> page) {
        this.page = page;
    }
}
