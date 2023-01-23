package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotEmpty(message = "name is mandatory")
    private String name;

    @NotEmpty(message = "description is mandatory")
    private String description;

    @NotEmpty(message = "json is mandatory")
    private String json;

    @NotEmpty(message = "template is mandatory")
    private String template;

    @NotEmpty(message = "sqlStr is mandatory")
    @Column(name = "sqlStr")
    private String sqlStr;

    @NotEmpty(message = "sqlPart is mandatory")
    @Column(name = "sqlPart")
    private String sqlPart;
}
