package bakalarka.example.bakalarka.entity;

import bakalarka.example.bakalarka.entity.enums.TypLekara;
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
@Table(name = "lekar")
public class Lekar {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_lekara;

    @Column(nullable = false, name = "specializacia",columnDefinition = "TEXT")
    private String specializacia;

    @Column(nullable = false, name = "typ_lekara")
    @Enumerated(EnumType.STRING)
    private TypLekara typLekara;

    @ManyToOne
    @JoinColumn(name = "id_pouzivatela")
    private Pouzivatel pouzivatel;

    @OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL)
    private Set<Hodnotenie> hodnotenie;

    @OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL)
    private Set<ZdravotnaKarta> zdravotnaKarta;

    @OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL)
    private Set<TerminVysetrenia> terminVysetrenia;
}
