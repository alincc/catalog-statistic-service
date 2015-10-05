package no.nb.microservices.catalogstatistic.core.newspaper.repository;

import com.fasterxml.jackson.databind.JsonNode;
import no.nb.microservices.catalogsearch.rest.model.search.SearchResource;
import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.core.search.repository.ISearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alfredw on 9/16/15.
 */
@Repository
public class NewspaperStatisticRepository implements INewspaperStatisticRepository {

    public static final String AVIS_AGGREGATION = "series";
    private final ISearchRepository searchRepository;
    private static final Logger LOG = LoggerFactory.getLogger(NewspaperStatisticRepository.class);

    @Autowired
    public NewspaperStatisticRepository(ISearchRepository searchRepository) {
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
        SearchResource searchResource = searchRepository.search("-dsadsasa&mediatype=Aviser", "", 0, 1, new ArrayList<>(), AVIS_AGGREGATION);

        if(searchResource != null) {
            searchResource.getEmbedded().getAggregations().stream()
                    .filter(aggregationResource -> AVIS_AGGREGATION.equals(aggregationResource.getName()))
                    .forEach(aggregationResource -> newspapers.addAll(aggregationResource.getFacetValues().stream()
                            .map(facetValueResource -> new Newspaper(facetValueResource.getKey(), facetValueResource.getCount()))
                            .collect(Collectors.toList())));
        }
        return newspapers;
    }

    private String getEditionDate(String title, String sort) {
        String editionDate = "";
        SearchResource searchResource = searchRepository.search("title:\"" + title + "\"", "", 0, 1, Arrays.asList(sort), AVIS_AGGREGATION);
        if(searchResource != null) {
            for (JsonNode jsonNode : searchResource.getEmbedded().getItems()) {
                editionDate = getDateIssued(jsonNode);
                if(!editionDate.isEmpty()) {
                    editionDate = formatEditionDate(title, editionDate);
                }
            }
        }
        return editionDate;
    }

    private String getDateIssued(JsonNode jsonNode) {
        String editionDate = "";
        if(jsonNode.has("metadata")) {
            JsonNode metadataNode = jsonNode.get("metadata");
            if(metadataNode.has("originInfo")) {
                JsonNode originInfoNode = metadataNode.get("originInfo");
                if(originInfoNode.has("issued")) {
                    editionDate = originInfoNode.get("issued").asText();
                }
            }
        }
        return editionDate;
    }

    private String formatEditionDate(String title, String editionDate) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(editionDate);
            editionDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
        } catch (ParseException e) {
            LOG.info("Cant get edition date for " + title + ": " + e.getMessage());
            LOG.debug("Cant get edition date for " + title + ": " + e.getMessage(), e);
        }
        return editionDate;
    }
}
