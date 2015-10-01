package no.nb.microservices.catalogstatistic.rest.newspaper.assembler;

import no.nb.microservices.catalogstatistic.core.newspaper.model.Newspaper;
import no.nb.microservices.catalogstatistic.newspaper.model.NewspaperResource;
import org.springframework.hateoas.ResourceAssembler;

/**
 * Created by alfredw on 10/1/15.
 */
public class NewspaperResourceAssembler implements ResourceAssembler<Newspaper, NewspaperResource> {

    @Override
    public NewspaperResource toResource(Newspaper newspaper) {
        return new NewspaperResource(newspaper.getTitle(), newspaper.getFirstScannedEditionDate(), newspaper.getLastScannedEditionDate(), newspaper.getNumberOfEditions());
    }
}
