package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RuleName domain object
 * 
 * @author trimok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rulename")
public class RuleName {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /**
     * name
     */
    @NotEmpty(message = "name is mandatory")
    private String name;

    /**
     * description
     */
    @NotEmpty(message = "description is mandatory")
    private String description;

    /**
     * json
     */
    @NotEmpty(message = "json is mandatory")
    private String json;

    /**
     * template
     */
    @NotEmpty(message = "template is mandatory")
    private String template;

    /**
     * sqlStr
     */
    @NotEmpty(message = "sqlStr is mandatory")
    @Column(name = "sqlStr")
    private String sqlStr;

    /**
     * sqlPart
     */
    @NotEmpty(message = "sqlPart is mandatory")
    @Column(name = "sqlPart")
    private String sqlPart;

    /**
     * equals
     */
    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;

	if (!(o instanceof RuleName))
	    return false;

	RuleName other = (RuleName) o;

	return id != null &&
		id.equals(other.getId());
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode() {
	return getClass().hashCode();
    }
}
