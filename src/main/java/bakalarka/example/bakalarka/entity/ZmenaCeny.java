package bakalarka.example.bakalarka.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "zmena_ceny")
public class ZmenaCeny {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_zmeny;

    @Column(nullable = false, name = "cena")
    private Double cena;

    @Column(nullable = false, name = "cena_od")
    private LocalDateTime cena_od;

    @Column(nullable = true, name = "cena_do")
    private LocalDateTime cena_do;

    @ManyToOne
    @JoinColumn(name = "id_vysetrenia")
    private ZoznamVysetreni zoznamVysetreni;
}
