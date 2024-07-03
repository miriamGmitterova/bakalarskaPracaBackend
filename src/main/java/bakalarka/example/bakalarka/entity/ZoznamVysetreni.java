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
@Table(name = "zoznam_vysetreni")
public class ZoznamVysetreni {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_vysetrenia;

    @Column(nullable = false, name = "nazovVysetrenia", columnDefinition = "TEXT")
    private String nazovVysetrenia;

    @Column(nullable = true, name = "detailny_opis", columnDefinition = "TEXT")
    private String detailny_opis;

    @Column(nullable = false, name = "doba_trvania")
    private Double doba_trvania;

    @OneToMany(mappedBy = "zoznamVysetreni", cascade = CascadeType.ALL)
    private Set<ZmenaCeny> zmenaCeny;

    @OneToMany(mappedBy = "zoznamVysetreni", cascade = CascadeType.ALL)
    private Set<TerminVysetrenia> terminVysetrenia;

}
