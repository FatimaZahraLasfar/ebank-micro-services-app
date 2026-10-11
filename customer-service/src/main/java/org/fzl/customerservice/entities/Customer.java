package org.fzl.customerservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Customer implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        // If id is null, it's a new entity
        return id == null;
    }
}