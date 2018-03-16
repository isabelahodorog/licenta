package com.isabela.v1.core.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Setter(AccessLevel.NONE)
    @Column(name = "p_id")
    private Long id;

    @Column(name = "p_name")
    private String name;

    @Column(name = "p_fiscal_code")
    private String fiscalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_a_id")
    private Address address;

    @Column(name = "p_a_id", insertable = false, updatable = false)
    private Long addressId;

    @Column(name = "p_bank_account")
    private String bankAccount;

    public void setAddress(Address address) {
        if(address != null) {
            this.addressId = address.getId();
        }
        this.address = address;
    }

}
