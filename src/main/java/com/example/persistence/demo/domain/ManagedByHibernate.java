package com.example.persistence.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "managed_by_hibernate", catalog = "jpa_hibernate")
public class ManagedByHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
