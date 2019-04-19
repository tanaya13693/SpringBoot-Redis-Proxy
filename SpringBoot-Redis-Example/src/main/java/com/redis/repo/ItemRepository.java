package com.redis.repo;

import com.redis.model.Item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class ItemRepository {

	@Value("${redis.expiry}")
	private int expiry;
	
    public static final String KEY = "ITEM";
    private RedisTemplate<String,Item> redisTemplate;
    private HashOperations hashOperations;

    public ItemRepository(RedisTemplate<String, Item> redisTemplate) {
    	redisTemplate.expire(KEY, 15, TimeUnit.SECONDS);
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /*Getting all Items from tSable*/
    public Map<Integer,Item> getAllItems(){
        return hashOperations.entries(KEY);
    	//return redisTemplate.opsForValue().
    }

    /*Getting a specific item by item id from table*/
    public Item getItem(int itemId){
        return (Item) hashOperations.get(KEY,itemId);
        //return redisTemplate.opsForValue().get(itemId);
    }

    /*Adding an item into redis database*/
    public void addItem(Item item){
    	
    	redisTemplate.opsForValue().set(String.valueOf(item.getId()), item, expiry, TimeUnit.SECONDS);
        hashOperations.put(KEY,item.getId(),item);
        
    }

    /*delete an item from database*/
    public void deleteItem(int id){
        hashOperations.delete(KEY,id);
    }

    /*update an item from database*/
    public void updateItem(Item item){
        addItem(item);
    }
}
