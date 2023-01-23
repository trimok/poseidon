package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotEmpty(message = "Username is mandatory")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s])^.{8,}", message = "password must have at least 8 characters, must contain at least 1 capital letter, 1 digit, and 1 special character")
    private String password;

    @NotEmpty(message = "FullName is mandatory")
    private String fullname;

    @NotEmpty(message = "Role is mandatory")
    private String role;

    @Override
    public boolean equals(Object o) {
	if (this == o)
	    return true;

	if (!(o instanceof User))
	    return false;

	User other = (User) o;

	return id != null &&
		id.equals(other.getId());
    }

    @Override
    public int hashCode() {
	return getClass().hashCode();
    }

}
