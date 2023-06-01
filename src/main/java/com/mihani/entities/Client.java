package com.mihani.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@DiscriminatorValue("CL")
public class Client extends User {
}
