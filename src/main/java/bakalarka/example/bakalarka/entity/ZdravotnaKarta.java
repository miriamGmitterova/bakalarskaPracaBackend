package bakalarka.example.bakalarka.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "zdravotna_karta")
public class ZdravotnaKarta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_karty;

    @Column(nullable = false, name = "zalozenie")
    private LocalDateTime zalozenie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lekara")
    @JsonIgnore
    private Lekar lekar;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pouzivatela")
    @JsonIgnore
    private Pouzivatel pouzivatel;


}
