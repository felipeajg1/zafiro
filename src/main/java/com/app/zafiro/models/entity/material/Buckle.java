package com.app.zafiro.models.entity.material;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Buckle extends Material {

    private String material;
    private String number;
}
