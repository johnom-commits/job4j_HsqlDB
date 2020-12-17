package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();
    OrdersStore store;

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
        store = new OrdersStore(pool);
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        store.clear();
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();

        assertEquals(1, all.size());
        assertEquals("description1", all.get(0).getDescription());
    }

    @Test
    public void whenSaveOrderAndUpdateIt() {
        store.clear();
        Order order = Order.of("name1", "description1");
        store.save(order);

        Order newOrder = Order.of("name2", "description2");
        store.update(order, newOrder);
        Order storeById = store.findById(1);

        assertEquals("description2", storeById.getDescription());
        assertEquals("name2", storeById.getName());
    }

    @Test
    public void whenSaveOrderAndFindByName() {
        store.clear();
        Order order = Order.of("name1", "description1");
        store.save(order);

        List<Order> list = store.findByName("name1");
        assertEquals(1, list.size());
        assertEquals("description1", list.get(0).getDescription());
    }
}