package no.nb.microservices.catalogstatistic.rest.newspaper.controller;

import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.newspaper.model.StatisticResource;
import no.nb.microservices.catalogstatistic.core.newspaper.service.INewspaperStatisticService;
import no.nb.microservices.catalogstatistic.rest.newspaper.assembler.StatisticResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alfredw on 9/21/15.
 */
@RestController
@RequestMapping(value = "/statistic/newspaper")
public class NewspaperStatisticController {

    private INewspaperStatisticService newspaperStatisticService;

    @Autowired
    public NewspaperStatisticController(INewspaperStatisticService newspaperStatisticService) {
        this.newspaperStatisticService = newspaperStatisticService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StatisticResource> getStatistics() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = newspaperStatisticService.getStatistic();
        StatisticResource statisticResource = new StatisticResourceAssembler().toResource(newspaperStatisticAggregated);
        return new ResponseEntity<>(statisticResource, HttpStatus.OK);
    }
}
