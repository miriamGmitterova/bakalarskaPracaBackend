package bakalarka.example.bakalarka.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "zdravotna_karta")
public class ZdravotnaKarta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_karty;

    @Column(nullable = false, name = "zalozenie")
    private LocalDateTime zalozenie;

    @Column(nullable = true, name = "odstranenie")
    private LocalDateTime odstranenie;

    @Column(nullable = true, name = "posledna_prehliadka")
    private LocalDateTime posledna_prehliadka;

    @OneToMany(mappedBy = "zdravotnaKarta")
    private Set<Predpis> predpis;

    @ManyToOne()
    @JoinColumn(name = "id_lekara")
    private Lekar lekar;

    @ManyToOne()
    @JoinColumn(name = "id_pacienta")
    private Pacient pacient;
}
