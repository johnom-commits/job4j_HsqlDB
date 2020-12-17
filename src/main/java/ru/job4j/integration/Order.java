package ru.job4j.integration;

import java.sql.Timestamp;
import java.util.Objects;

public class Order {
    private int id;

    private String name;

    private String description;

    private Timestamp created;

    public Order() {}

    public Order(int id, String name, String description, Timestamp created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public static Order of(String name, String description) {
        Order o = new Order();
        o.name = name;
        o.description = description;
        o.created = new Timestamp(System.currentTimeMillis());
        return o;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(name, order.name) &&
                Objects.equals(description, order.description) &&
                Objects.equals(created, order.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, created);
    }
}
