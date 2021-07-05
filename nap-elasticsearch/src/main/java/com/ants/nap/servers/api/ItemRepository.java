package com.ants.nap.servers.api;

import com.ants.nap.beans.ItemEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<ItemEntity,Long> {
    List<ItemEntity> findByPriceBetween(double price1, double price2);
}
