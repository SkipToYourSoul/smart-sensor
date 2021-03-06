package com.stemcloud.smart.web.domain.security;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/9
 * Description:
 */
@Entity
@Table(name = "zdc_sys_resource")
public class SysResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String resourceUrl;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
