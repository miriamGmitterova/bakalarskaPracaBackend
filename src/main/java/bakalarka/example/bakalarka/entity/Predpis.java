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
@Table(name = "predpis")
public class Predpis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_predpisu;

    @Column(nullable = false, name = "datum_predpisu")
    private LocalDateTime datum_predpisu;

    @Column(nullable = false, name = "bol_vybraty")
    private boolean bol_vybraty;

    @ManyToOne
    @JoinColumn(name = "id_lieku")
    private Liek liek;

    @ManyToOne
    @JoinColumn(name = "id_karty")
    private ZdravotnaKarta zdravotnaKarta;
}
