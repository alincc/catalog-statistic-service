package no.nb.microservices.catalogstatistic.core.newspaper.service;

import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;

/**
 * Created by alfredw on 9/21/15.
 */
public interface INewspaperStatisticService {
    NewspaperStatisticAggregated getStatistic();
}
