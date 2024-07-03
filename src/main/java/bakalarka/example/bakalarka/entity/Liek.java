package bakalarka.example.bakalarka.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "liek")
public class Liek {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_lieku;

    @Column(nullable = false, name = "nazovLieku", columnDefinition = "TEXT")
    private String nazovLieku;

    @Column(nullable = true, name = "dodatocne_info", columnDefinition = "TEXT")
    private String dodatocne_info;

    @OneToMany(mappedBy = "liek", cascade = CascadeType.ALL)
    private Set<Predpis> predpis;
}
