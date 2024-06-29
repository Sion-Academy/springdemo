package com.hitices.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "source")
public class DataSourceEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "detail")
    private String detail;
}
