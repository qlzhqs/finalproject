package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_direction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "direction")
    private String direction ;




}
