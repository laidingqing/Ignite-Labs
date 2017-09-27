package com.node.data.cachestore;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.node.model.User;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.resources.SpringResource;
import org.springframework.data.cassandra.core.CassandraOperations;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.Date;

/**
 * Created by skylai on 2017/9/27.
 */
public class UserStore extends CacheStoreAdapter<Long, User> {

    @SpringResource(resourceName = "cqlTemplate")
    private CassandraOperations cqlTemplate;

    @Override
    public User load(Long key) throws CacheLoaderException {
        ResultSet resultSet = cqlTemplate.getSession().execute(
                QueryBuilder.select().from("USERS")
                        .where(QueryBuilder.eq("id", key)).getQueryString());
        Row row = resultSet.one();
        if (row != null) {
            User.builder()
                    .name(row.get("name", String.class))
                    .email(row.get("email", String.class))
                    .creationDate(row.get("creationDate", Date.class))
                    .status(row.get("status", String.class))
                    .id(row.get("id", Long.class))
                    .build();
        }
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        if (load(entry.getKey()) == null)
            cqlTemplate.execute(QueryBuilder.insertInto("USERS")
                    .value("id", entry.getValue().getId())
                    .value("name", entry.getValue().getName())
                    .value("email", entry.getValue().getEmail())
                    .value("creationDate", entry.getValue().getCreationDate())
                    .value("status", entry.getValue().getStatus())
            );
        else
            cqlTemplate.execute(QueryBuilder.update("USERS")
                    .with(QueryBuilder.set("name", entry.getValue().getName()))
                    .and(QueryBuilder.set("email", entry.getValue().getEmail()))
                    .and(QueryBuilder.set("creationDate", entry.getValue().getCreationDate()))
                    .and(QueryBuilder.set("status", entry.getValue().getStatus()))
                    .where(QueryBuilder.eq("id", entry.getKey())));
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        cqlTemplate.execute(QueryBuilder.delete().from("USERS").where(QueryBuilder.eq("id", key)));
    }
}
