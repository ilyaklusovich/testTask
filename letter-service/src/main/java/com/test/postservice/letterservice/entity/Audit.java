package com.test.postservice.letterservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "audit")
@Data
@Builder
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "entity_type")
    private String entityType;
    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "entity_json")
    private String entityJson;
    @Column(name = "created_at")
    private Date createdAt;

    public Audit(String entityType, String operationType, String entity_json, Date created_at) {
        this.entityType = entityType;
        this.operationType = operationType;
        this.entityJson = entity_json;
        this.createdAt = created_at;
    }
}
