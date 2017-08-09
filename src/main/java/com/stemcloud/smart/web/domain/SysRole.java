package com.stemcloud.smart.web.domain;

import javax.persistence.*;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/9
 * Description:
 */
@Entity
@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
