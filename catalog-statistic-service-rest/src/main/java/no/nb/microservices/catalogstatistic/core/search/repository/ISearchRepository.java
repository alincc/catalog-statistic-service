package no.nb.microservices.catalogstatistic.core.search.repository;

import no.nb.microservices.catalogsearch.rest.model.search.SearchResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by alfredw on 9/21/15.
 */
@FeignClient("catalog-search-service")
public interface ISearchRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/catalog/v1/search")
    SearchResource search(@RequestParam("q") String q, @RequestParam("fields") String fields, @RequestParam("page") int pageNumber,
                          @RequestParam("size") int pageSize, @RequestParam("sort") List<String> sort, @RequestParam("aggs") String aggs);

}
