package no.nb.microservices.catalogstatistic.core.item.repository;

import no.nb.microservices.catalogitem.rest.model.ItemSearchResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by alfredw on 9/21/15.
 */
@FeignClient("catalog-item-service")
public interface ItemRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/catalog/v1/search")
    ItemSearchResource search(@RequestParam("q") String q,
                              @RequestParam("fields") String fields,
                              @RequestParam("filter") List<String> filter,
                              @RequestParam("expand") String expand,
                              @RequestParam("page") int pageNumber,
                              @RequestParam("size") int pageSize,
                              @RequestParam("sort") List<String> sort,
                              @RequestParam("aggs") String aggs);

}
