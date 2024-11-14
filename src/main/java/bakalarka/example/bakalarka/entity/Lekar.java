package bakalarka.example.bakalarka.entity;

import bakalarka.example.bakalarka.entity.enums.TypLekara;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
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

    @Column(nullable = false, name = "typ_lekara", length = 20)
    @Enumerated(EnumType.STRING)
    private TypLekara typLekara;

    @ManyToOne()
    @JoinColumn(name = "id_pouzivatela")
    private Pouzivatel pouzivatel;


    @OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ZdravotnaKarta> zdravotnaKarta;

    @OneToMany(mappedBy = "lekar", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Vysetrenia> vysetrenia;
}
