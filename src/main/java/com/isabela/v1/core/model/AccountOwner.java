package com.isabela.v1.core.model;

import com.isabela.v1.core.validator.Email;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "account_owner")
public class AccountOwner {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Setter(AccessLevel.NONE)
    @Column(name = "ao_id")
    private Long id;

    @Column(name = "ao_name")
    private String name;

    @Email
    @Column(name = "ao_email")
    private String email;

    @Column(name = "ao_password")
    private String password;
}
