package no.nb.microservices.catalogstatistic.core.newspaper.repository;

import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;

/**
 * Created by alfredw on 9/16/15.
 */
public interface INewspaperStatisticRepository {
    NewspaperStatisticAggregated getStatistics();
}
