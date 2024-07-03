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
@Table(name = "terminy_vysetreni")
public class TerminVysetrenia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_terminu;

    @Column(nullable = false, name = "datum_vysetrenia")
    private LocalDateTime datum_vysetrenia;

    @ManyToOne
    @JoinColumn(name = "id_pacienta")
    private Pacient pacient;

    @ManyToOne
    @JoinColumn(name = "id_lekara")
    private Lekar lekar;

    @ManyToOne
    @JoinColumn(name = "id_vysetrenia")
    private ZoznamVysetreni zoznamVysetreni;

}
