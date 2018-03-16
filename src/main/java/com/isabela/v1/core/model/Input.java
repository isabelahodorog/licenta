package com.isabela.v1.core.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "input")
public class Input {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Setter(AccessLevel.NONE)
    @Column(name = "i_id")
    private Long id;

    @Column(name = "i_type")
    private String type;

    @Column(name = "i_doc_no")
    private Long docNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_p_id")
    private Provider provider;

    @Column(name = "i_p_id", insertable = false, updatable = false)
    private Long providerId;

    @Column(name = "i_release_date")
    private Date releaseDate;

    @Column(name = "i_due_date")
    private Date dueDate;

    @Column(name = "i_value")
    private Double value;

    @Column(name = "i_tva")
    private Double tva;

    @Column(name = "i_total")
    private Double total;

    @Column(name = "i_to_pay")
    private Double toPay;

    public void setProvider(Provider provider) {
        if(provider != null) {
            this.providerId = provider.getId();
        }
        this.provider = provider;
    }
}
