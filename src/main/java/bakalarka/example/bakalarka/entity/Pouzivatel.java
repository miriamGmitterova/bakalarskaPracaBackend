package bakalarka.example.bakalarka.entity;


import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.Set;
import java.util.UUID;

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

    @Column(columnDefinition = "TEXT", nullable = true, name = "foto")
    private String foto;

    @Column(nullable = false, name = "priezvisko", length = 30)
    private String priezvisko;

    @Column(nullable = false, name = "tel_cislo", unique = true, length = 30)
    private String tel_cislo;

    @Column(nullable = false, name = "ulica", length = 50)
    private String ulica;

    @Column(nullable = false, name = "popisne_cislo", length = 10)
    private String popisne_cislo;

    @Column(nullable = false, name = "mesto", length = 30)
    private String mesto;

    @Column(nullable = false, name = "user_name", unique = true, length = 20)
    private String user_name;

    @Column(nullable = false, name = "heslo")
    private String password;

    @Column(nullable = false, name = "email",unique = true, length = 50)
    private String email;

//    @Column(nullable = false, name = "pouzivatelske_role")
//    private Set<PouzivatelskeRole> pouzivatelske_role;

    @Column(nullable = false, name = "pouzivatelske_role")
    @Enumerated(EnumType.STRING)
    private PouzivatelskeRole role;

//    @Column(nullable = false, name = "pouzivatelske_role", columnDefinition = "VARCHAR(10)")
//    private String pouzivatelskeRole;
//
//    public void setPouzivatelskeRole(String role) {
//        if (role != null) {
//            // You can add additional validation logic here if needed
//            if (role.equals("pacient") || role.equals("lekar") || role.equals("spravca")) {
//                this.pouzivatelskeRole = role;
//            } else {
//                // Throw an exception or handle invalid role value
//                throw new IllegalArgumentException("Invalid role value: " + role);
//            }
//        } else {
//            // Throw an exception or handle null role value
//            throw new IllegalArgumentException("Role value cannot be null");
//        }
//    }


    @JsonIgnore
    @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
    private Set<Pacient> pacienti;

    @JsonIgnore
    @OneToMany(mappedBy = "pouzivatel", cascade = CascadeType.ALL)
    private Set<Lekar> lekari;

}
