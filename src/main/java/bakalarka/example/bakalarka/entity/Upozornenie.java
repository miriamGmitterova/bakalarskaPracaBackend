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
@Table(name = "upozornenie")
public class Upozornenie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_upozornenia;

    @ManyToOne()
    @JoinColumn(name = "id_pouzivatela")
    @JsonIgnore
    private Pouzivatel pouzivatel;

    @Column(name = "sprava", nullable = false)
    private String sprava;

    @Column(name = "precitane", nullable = false)
    private Boolean precitane = false;

    @Column(name = "vytvorene", nullable = false)
    private LocalDateTime vytvorene = LocalDateTime.now();
}
