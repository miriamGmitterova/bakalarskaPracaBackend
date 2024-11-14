package bakalarka.example.bakalarka.entity;


import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "pouzivatel")
public class Pouzivatel {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_pouzivatela;

    @Column(nullable = false, name = "meno", length = 20)
    private String meno;


    @Column(nullable = false, name = "priezvisko", length = 30)
    private String priezvisko;

    @Column(nullable = false, name = "tel_cislo", unique = true, length = 30)
    private String telCislo;

    @Column(nullable = false, name = "ulica", length = 50)
    private String ulica;

    @Column(nullable = false, name = "popisne_cislo", length = 10)
    private String popisneCislo;

    @Column(nullable = false, name = "mesto", length = 30)
    private String mesto;

    @Column(nullable = false, name = "user_name", unique = true, length = 20)
    private String user_name;

    @Column(nullable = false, name = "heslo")
    private String password;

    @Column(nullable = false, name = "email",unique = true, length = 50)
    private String email;



    @Column(nullable = false, name = "pouzivatelske_role", length = 20)
    @Enumerated(EnumType.STRING)
    private PouzivatelskeRole role;




    @JsonIgnore
    @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
    private Set<Pacient> pacienti;

    @JsonIgnore
    @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
    private Set<Lekar> lekari;

      @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
      private Set<ZdravotnaKarta> zdravotnaKarta;

      @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
      private Set<Vysetrenia> vysetrenia;

    @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
    private Set<Upozornenie> upozornenie;

}
