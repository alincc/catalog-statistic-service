package no.nb.microservices.catalogstatistic.core.newspaper.repository;

import com.fasterxml.jackson.databind.JsonNode;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.ItemSearchResource;
import no.nb.microservices.catalogstatistic.core.item.repository.ItemRepository;
import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by alfredw on 9/16/15.
 */
@Repository
public class NewspaperStatisticRepository implements INewspaperStatisticRepository {

    public static final String AVIS_AGGREGATION = "series";
    private final ItemRepository searchRepository;
    private static final Logger LOG = LoggerFactory.getLogger(NewspaperStatisticRepository.class);

    @Autowired
    public NewspaperStatisticRepository(ItemRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public NewspaperStatisticAggregated getStatistics() {
        List<Newspaper> newspapers = getBinders();

        for(Newspaper newspaper : newspapers) {
            String firstScannedEditionDate = getEditionDate(newspaper.getTitle(), "date,asc");
            newspaper.setFirstScannedEditionDate(firstScannedEditionDate);

            String lastScannedEditionDate = getEditionDate(newspaper.getTitle(), "date,desc");
            newspaper.setLastScannedEditionDate(lastScannedEditionDate);
        }

        return new NewspaperStatisticAggregated(new PageImpl<>(newspapers));
    }

    private List<Newspaper> getBinders() {
        List<Newspaper> newspapers = new ArrayList<>();
        List<String> filters = new ArrayList<>();
        filters.add("mediatype:Aviser");

        ItemSearchResource searchResource = searchRepository.search("-dsadsasa", "",filters, null ,0, 1, new ArrayList<>(), AVIS_AGGREGATION);

        if(searchResource != null) {
            JsonNode aggregations = searchResource.getEmbedded().getAggregations();
            Iterator<JsonNode> iterator = aggregations.iterator();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                if (node.get("name").asText().equals("series")) {
                    Iterator<JsonNode> facetValues = node.get("facetValues").iterator();
                    while (facetValues.hasNext()) {
                        JsonNode facetvalue = facetValues.next();
                        newspapers.add(new Newspaper(facetvalue.get("key").asText(),facetvalue.get("count").asInt()));
                    }
                }
            }
        }
        return newspapers;
    }

    private String getEditionDate(String title, String sort) {
        String editionDate = "";
        ItemSearchResource searchResource = searchRepository.search("title:\"" + title + "\"", "", null, "metadata", 0, 1, Arrays.asList(sort), AVIS_AGGREGATION);
        if(searchResource != null) {
            for (ItemResource itemResource : searchResource.getEmbedded().getItems()) {
                if(itemResource.getMetadata().getOriginInfo().getIssued() != null) {
                    editionDate = formatEditionDate(itemResource.getMetadata().getOriginInfo().getIssued());
                }
            }
        }
        return editionDate;
    }

    private String formatEditionDate(String editionDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(editionDate);
            editionDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
        } catch (ParseException e) {
            LOG.error("Error parsing date: " + editionDate + ": " + e.getMessage());
            LOG.debug("Error parsing date: " + editionDate + ": " + e.getMessage(), e);
        }
        return editionDate;
    }

}