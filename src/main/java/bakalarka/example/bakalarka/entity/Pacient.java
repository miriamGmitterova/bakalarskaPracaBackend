package bakalarka.example.bakalarka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "pacient")
public class Pacient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_pacienta;

    @Column(nullable = false, name = "rodne_cislo", length = 10)
    private String rodne_cislo;

    @ManyToOne
    @JoinColumn(name = "id_pouzivatela")
    private Pouzivatel pouzivatel;
}
