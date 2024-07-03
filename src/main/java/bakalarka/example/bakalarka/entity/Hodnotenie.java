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
@Table(name = "hodnotenie")
public class Hodnotenie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_hodnotenia;

    @Column(nullable = false, name = "vytvorene")
    private LocalDateTime vytvorene;

    @Column(nullable = false, name = "hviezdicky")
    private Integer hviezdicky;

    @Column(nullable = true, name = "text", columnDefinition = "TEXT")
    private String dodatocny_text;

    @ManyToOne()
    @JoinColumn(name = "id_pacienta")
    private Pacient pacient;

    @ManyToOne()
    @JoinColumn(name = "id_lekara")
    private Lekar lekar;
}
