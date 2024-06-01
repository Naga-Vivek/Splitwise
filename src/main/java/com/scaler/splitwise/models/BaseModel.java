package com.scaler.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedAt;

}

/*
@Temporal converts back and forth between the Java Date or Calendar type
and the corresponding SQL type (DATE, TIME, or TIMESTAMP) when storing or retrieving data from the database.

@Temporal(TemporalType.TIMESTAMP) stores both the date and time.

@CreatedAt automatically populates the field with the timestamp when the entity is first persisted
 (i.e., when it’s saved to the database

@LastModifiedDate annotation is used to automatically capture the date and time when an entity was last updated.
This is particularly useful for auditing purposes, ensuring transparency, and providing traceability for modifications in your data

JPA automatically populate dates for createdDate & LastModifiedDate ,
but for that use @EntityListeners(AuditingEntityListener.class) on top of class having those attributes (here BaseModel)
             and @EnableJpaAuditing on main class (along with @SpringBootApplication)

 */
