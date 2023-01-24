package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Rating domain object
 * 
 * @author trimok
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating")
public class Rating {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /**
     * moodysRating
     */
    @NotEmpty(message = "moodysRating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;

    /**
     * sandPRating
     */
    @NotEmpty(message = "sandPRating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;

    /**
     * fitchRating
     */
    @NotEmpty(message = "fitchRating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;

    /**
     * orderNumber
     */
    @NotNull(message = "orderNumber is mandatory")
    @Column(name = "orderNumber")
    private Integer orderNumber;

    /**
     * equals
     */
    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;

	if (!(o instanceof Rating))
	    return false;

	Rating other = (Rating) o;

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
