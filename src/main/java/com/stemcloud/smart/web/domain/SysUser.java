package com.stemcloud.smart.web.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Belongs to smart-sensor
 * Author: liye on 2017/8/9
 * Description:
 */
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sysUser")
    private Set<SysRole> sysRoles = new HashSet<SysRole>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sysUser")
    public Set<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(Set<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }
}
