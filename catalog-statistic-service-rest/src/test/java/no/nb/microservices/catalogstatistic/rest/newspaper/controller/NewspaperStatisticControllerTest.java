package no.nb.microservices.catalogstatistic.rest.newspaper.controller;

import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.newspaper.model.StatisticResource;
import no.nb.microservices.catalogstatistic.core.newspaper.service.INewspaperStatisticService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;

/**
 * Created by alfredw on 9/21/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewspaperStatisticControllerTest {

    @Mock
    private INewspaperStatisticService newspaperStatisticService;
    private NewspaperStatisticController newspaperStatisticController;

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/v1/statistic/newspaper");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);

        RequestContextHolder.setRequestAttributes(attributes);
    }

    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Before
    public void setup() {
        newspaperStatisticController = new NewspaperStatisticController(newspaperStatisticService);
    }

    @Test
    public void getStatistics() {
        List<Newspaper> newspapers = Arrays.asList(new Newspaper("Rana Blad", 18745, "02.04.1947", "18.09.2015"));
        NewspaperStatisticAggregated newspaperStatisticAggregated = new NewspaperStatisticAggregated(new PageImpl(newspapers));
        when(newspaperStatisticService.getStatistic()).thenReturn(newspaperStatisticAggregated);

        ResponseEntity<StatisticResource> statistics = newspaperStatisticController.getStatistics();

        assertThat(statistics.getBody().getEmbedded().getItems(), hasSize(1));
    }
}
