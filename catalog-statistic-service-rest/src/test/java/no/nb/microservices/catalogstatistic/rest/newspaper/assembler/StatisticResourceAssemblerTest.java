package no.nb.microservices.catalogstatistic.rest.newspaper.assembler;

import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.newspaper.model.StatisticResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by alfredw on 10/1/15.
 */
public class StatisticResourceAssemblerTest {

    private StatisticResourceAssembler statisticResourceAssembler;

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/v1/statistic/newspaper");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);

        RequestContextHolder.setRequestAttributes(attributes);

        statisticResourceAssembler = new StatisticResourceAssembler();
    }

    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void whenOnAnyPageReturnValueShouldHaveAPageElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(2);

        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNotNull("The page element should not be null", statisticResource.getPageMetadata());
        assertEquals("The number should be 2", 2, statisticResource.getPageMetadata().getNumber());
        assertEquals("The size should be 10", 10, statisticResource.getPageMetadata().getSize());
        assertEquals("The total elements should be 1000", 1000, statisticResource.getPageMetadata().getTotalElements());
        assertEquals("The total pages should be 100", 100, statisticResource.getPageMetadata().getTotalPages());
    }

    @Test
    public void whenOnAnyPageReturnValueShouldHaveASelfLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(0);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertEquals("Should have a self-referential link element", "self", statisticResource.getId().getRel());
    }

    @Test
    public void whenOnFirstPageThenReturnValueShouldNotHaveAPreviousLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(0);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNull(statisticResource.getLink(Link.REL_PREVIOUS));
    }

    @Test
    public void whenOnLastPageThenReturnValueShouldNotHaveANextLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(100);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNull(statisticResource.getLink(Link.REL_NEXT));
    }

    @Test
    public void whenNotOnFirstPageThenReturnValueShouldHaveAFirstLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(2);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNotNull(statisticResource.getLink(Link.REL_FIRST));
    }

    @Test
    public void whenNotOnLastPageThenReturnValueShouldHaveALastLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(10);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNotNull(statisticResource.getLink(Link.REL_LAST));
    }

    @Test
    public void whenItNotOnLastPageThenReturnValueShouldHaveALastLinkElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(10);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertNotNull(statisticResource.getLink(Link.REL_LAST));
    }

    @Test
    public void whenSearchResultHasItemsThenReturnValueShouldHaveItemsElement() {
        NewspaperStatisticAggregated newspaperStatisticAggregated = createStatisticAggregated(0);
        StatisticResource statisticResource = statisticResourceAssembler.toResource(newspaperStatisticAggregated);
        assertEquals("Items should have 2 items", 2, statisticResource.getEmbedded().getItems().size());
    }

    private NewspaperStatisticAggregated createStatisticAggregated(int currentPage) {
        List<Newspaper> newspapers = new ArrayList<>();
        newspapers.add(new Newspaper("Rana Blad", 18753, "02.04.1947", "29.09.2015"));
        newspapers.add(new Newspaper("Aftenposten", 93748, "14.05.1860", "29.09.2015"));
        Page<Newspaper> page = new PageImpl<>(newspapers, new PageRequest(currentPage, 10), 1000);

        return new NewspaperStatisticAggregated(page);
    }
}