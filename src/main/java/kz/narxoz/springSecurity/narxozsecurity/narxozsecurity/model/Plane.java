package kz.narxoz.springSecurity.narxozsecurity.narxozsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "p_plane")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "number")
    private String number;

    @Column(name = "place")
    private String place;

    @ManyToOne(fetch = FetchType.EAGER)
    private Direction direction;





}
