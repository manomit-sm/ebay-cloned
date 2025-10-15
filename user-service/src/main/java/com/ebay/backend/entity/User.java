package com.ebay.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public non-sealed class User extends BaseEntity {

    @Column(name = "azure_object_id", unique = true)
    private String azureObjectId;

    // Email / username
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Display name
    @Column(name = "display_name", nullable = false)
    private String displayName;

    // Password (optional, only if you plan local login)
    @Column(name = "password")
    private String password;


    // Account enabled / disabled
    @Column(name = "enabled")
    private boolean enabled = true;

}
