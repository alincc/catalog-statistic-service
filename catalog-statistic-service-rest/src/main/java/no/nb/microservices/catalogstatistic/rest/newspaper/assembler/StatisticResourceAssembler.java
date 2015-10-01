package no.nb.microservices.catalogstatistic.rest.newspaper.assembler;


import no.nb.microservices.catalogstatistic.newspaper.model.EmbeddedWrapper;
import no.nb.microservices.catalogstatistic.newspaper.model.NewspaperResource;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.newspaper.model.StatisticResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alfredw on 9/21/15.
 */
public class StatisticResourceAssembler implements ResourceAssembler<NewspaperStatisticAggregated, StatisticResource> {

    private NewspaperResourceAssembler assembler = new NewspaperResourceAssembler();
    private final HateoasPageableHandlerMethodArgumentResolver pageableResolver = new HateoasPageableHandlerMethodArgumentResolver();

    @Override
    public StatisticResource toResource(NewspaperStatisticAggregated newspaperStatisticAggregated) {
        PageMetadata metadata = asPageMetadata(newspaperStatisticAggregated.getPage());

        EmbeddedWrapper embedded = new EmbeddedWrapper();
        List<NewspaperResource> items = newspaperStatisticAggregated.getPage().getContent().stream().map(assembler::toResource).collect(Collectors.toList());
        embedded.setItems(items);

        StatisticResource statisticResource = new StatisticResource(metadata, embedded);
        return addPaginationLinks(statisticResource, newspaperStatisticAggregated.getPage());
    }

    private StatisticResource addPaginationLinks(StatisticResource statisticResource, Page<?> page) {

        UriTemplate base = new UriTemplate(ServletUriComponentsBuilder.fromCurrentRequest().build().toString());

        if (page.hasPrevious()) {
            statisticResource.add(createLink(base, new PageRequest(0, page.getSize(), page.getSort()), Link.REL_FIRST));
        }

        if (page.hasPrevious()) {
            statisticResource.add(createLink(base, page.previousPageable(), Link.REL_PREVIOUS));
        }

        statisticResource.add(createLink(base, null, Link.REL_SELF));

        if (page.hasNext()) {
            statisticResource.add(createLink(base, page.nextPageable(), Link.REL_NEXT));
        }

        if (page.hasNext()) {

            int lastIndex = page.getTotalPages() == 0 ? 0 : page.getTotalPages() - 1;

            statisticResource.add(createLink(base, new PageRequest(lastIndex, page.getSize(), page.getSort()), Link.REL_LAST));
        }

        return statisticResource;
    }

    private Link createLink(UriTemplate base, Pageable pageable, String rel) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(base.expand());
        pageableResolver.enhance(builder, null, pageable);

        return new Link(new UriTemplate(builder.build().toString()), rel);
    }

    private static <T> PageMetadata asPageMetadata(Page<T> page) {

        return new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }
}
