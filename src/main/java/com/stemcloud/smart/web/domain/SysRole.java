package com.stemcloud.smart.web.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private Set<SysResource> sysResources = new HashSet<SysResource>();

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

    public Set<SysResource> getSysResources() {
        return sysResources;
    }

    public void setSysResources(Set<SysResource> sysResources) {
        this.sysResources = sysResources;
    }
}
