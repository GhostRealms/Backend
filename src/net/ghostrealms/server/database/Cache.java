package net.ghostrealms.server.database;

import redis.clients.jedis.Jedis;

/**
 * Created by River on 03-Dec-14.
 * Interface for the Cache Server
 */
public class Cache {

    Jedis redis;

    public Cache(String redisHost) {
        redis = new Jedis(redisHost);
    }

    /**
     * Returns the Jedis object for the redis server
     * @deprecated to be used only when finer control is needed over cache functions
     * @return instance of the Redis server
     */
    @Deprecated
    public Jedis getRedis() {
        return this.redis;
    }

    /**
     * Set a value in the cache
     * @param key key for storage
     * @param value value to set
     */
    public void setValue(String key, String value) {
        redis.set(key, value);
    }

    /**
     * Retrieve a value from the cache
     * @param key of value
     * @return value of key
     */
    public String retrieve(String key) {
        return redis.get(key);
    }


}
