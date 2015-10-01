package no.nb.microservices.catalogstatistic.core.newspaper.service;

import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.core.newspaper.repository.INewspaperStatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alfredw on 9/21/15.
 */
@Service
public class NewspaperStatisticService implements INewspaperStatisticService {

    private final INewspaperStatisticRepository newspaperStatisticRepository;

    @Autowired
    public NewspaperStatisticService(INewspaperStatisticRepository newspaperStatisticRepository) {
        this.newspaperStatisticRepository = newspaperStatisticRepository;
    }

    @Override
    public NewspaperStatisticAggregated getStatistic() {
        return newspaperStatisticRepository.getStatistics();
    }
}
