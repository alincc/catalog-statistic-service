package no.nb.microservices.catalogstatistic.rest.newspaper.assembler;

import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.newspaper.model.NewspaperResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;

/**
 * Created by alfredw on 10/1/15.
 */
public class NewspaperResourceAssemblerTest {

    @Before
    public void init() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/catalog/v1/statistic/newspaper");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);

        RequestContextHolder.setRequestAttributes(attributes);
    }

    @After
    public void cleanUp() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    public void toResource() {
        Newspaper newspaper = new Newspaper("Rana Blad", 18753, "02.04.1947", "29.09.2015");
        NewspaperResourceAssembler newspaperResourceAssembler = new NewspaperResourceAssembler();
        NewspaperResource newspaperResource = newspaperResourceAssembler.toResource(newspaper);
        assertEquals(newspaper.getTitle(), newspaperResource.getTitle());
        assertEquals(newspaper.getNumberOfEditions(), newspaperResource.getNumberOfEditions());
        assertEquals(newspaper.getFirstScannedEditionDate(), newspaperResource.getFirstScannedEditionDate());
        assertEquals(newspaper.getLastScannedEditionDate(), newspaperResource.getLastScannedEditionDate());

    }
}