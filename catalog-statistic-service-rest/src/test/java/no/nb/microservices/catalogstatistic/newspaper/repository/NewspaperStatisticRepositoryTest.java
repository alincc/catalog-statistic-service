package no.nb.microservices.catalogstatistic.newspaper.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nb.microservices.catalogitem.rest.model.ItemSearchResource;
import no.nb.microservices.catalogstatistic.core.item.repository.ItemRepository;
import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.core.newspaper.model.NewspaperStatisticAggregated;
import no.nb.microservices.catalogstatistic.core.newspaper.repository.INewspaperStatisticRepository;
import no.nb.microservices.catalogstatistic.core.newspaper.repository.NewspaperStatisticRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by alfredw on 9/17/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewspaperStatisticRepositoryTest {

    @Mock
    private ItemRepository searchRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getStatistics() throws IOException {
        INewspaperStatisticRepository repository = new NewspaperStatisticRepository(searchRepository);
        ItemSearchResource aggregationResponse = getSearchResourceResponseEntity("no/nb/microservices/catalogstatistic/core/newspaper/repository/AvisAggregationSearchResponse.json");
        ItemSearchResource firstEditionResponse = getSearchResourceResponseEntity("no/nb/microservices/catalogstatistic/core/newspaper/repository/RanaBladFirstEditionSearchResponse.json");
        ItemSearchResource lastEditionResponse = getSearchResourceResponseEntity("no/nb/microservices/catalogstatistic/core/newspaper/repository/RanaBladLastEditionSearchResponse.json");
        when(searchRepository.search(anyString(), anyString(), anyList(), anyString(), anyInt(), anyInt(), anyList(), anyString())).thenReturn(aggregationResponse, firstEditionResponse, lastEditionResponse);

        NewspaperStatisticAggregated newspaperStatisticAggregated = repository.getStatistics();

        assertNotNull(newspaperStatisticAggregated);
        assertThat(newspaperStatisticAggregated.getPage().getContent(), hasSize(1));
        Newspaper newspaper = newspaperStatisticAggregated.getPage().getContent().get(0);
        assertEquals("Rana Blad", newspaper.getTitle());
        assertEquals(18745, newspaper.getNumberOfEditions());
        assertEquals("02.04.1947", newspaper.getFirstScannedEditionDate());
        assertEquals("18.09.2015", newspaper.getLastScannedEditionDate());
    }

    private ItemSearchResource getSearchResourceResponseEntity(String pathToFile) throws IOException {
        String file = getClass().getClassLoader().getResource(pathToFile).getFile();
        String jsonString = new String(Files.readAllBytes(Paths.get(file)));
        return objectMapper.readValue(jsonString, ItemSearchResource.class);
    }
}
