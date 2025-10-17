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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "azure_id", unique = true)
    private String azureId;

    // Email / username
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private boolean status;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String photo;

    @PrePersist
    public void prePersist() {
        this.status = false;
    }


}
