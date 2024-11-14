package bakalarka.example.bakalarka.entity;


import bakalarka.example.bakalarka.entity.enums.TypVysetrenia;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
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
@Table(name = "vysetrenia")
public class Vysetrenia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_vysetrenia;

    @Column(nullable = false, name = "nazovVysetrenia", columnDefinition = "TEXT")
    private String nazovVysetrenia;

    @Column(nullable = false, name = "typ_vysetrenia", length = 50)
    @Enumerated(EnumType.STRING)
    private TypVysetrenia typVysetrenia;

    @Column(nullable = false, name = "datum_vysetrenia")
    private LocalDateTime datum_vysetrenia;


    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pouzivatela")
    @JsonIgnore
    private Pouzivatel pouzivatel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lekara")
    @JsonIgnore
    private Lekar lekar;

    @JsonProperty("id_pouzivatela")
    public UUID getIdPouzivatela() {
        return pouzivatel != null ? pouzivatel.getId_pouzivatela(): null;
    }

    @JsonProperty("id_lekara")
    public UUID getIdLekara() {
        return lekar != null ? lekar.getId_lekara() : null;
    }
}
