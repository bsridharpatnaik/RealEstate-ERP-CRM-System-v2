package com.ec.common.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "security_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NonNull
    @Column(unique = true, name = "user_name")
    private String userName;

    @NonNull
    private String password;

    @NonNull
    private boolean status;

    @NonNull
    private boolean passwordExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns =
            {@JoinColumn(name = "userId", referencedColumnName = "userId")}, inverseJoinColumns =
            {@JoinColumn(name = "role_name", referencedColumnName = "name")})
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_tenant_mapping", joinColumns =
            {@JoinColumn(name = "userId", referencedColumnName = "userId")}, inverseJoinColumns =
            {@JoinColumn(name = "mapping_id", referencedColumnName = "mapping_id")})
    Set<UserTenantMapping> tenantList = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<UserTenantMapping> getTenantList() {
        return tenantList;
    }

    public void setTenantList(Set<UserTenantMapping> tenantList) {
        this.tenantList = tenantList;
    }
}
