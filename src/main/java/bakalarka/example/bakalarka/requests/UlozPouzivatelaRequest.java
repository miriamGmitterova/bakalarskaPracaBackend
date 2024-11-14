package bakalarka.example.bakalarka.requests;

import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;
import bakalarka.example.bakalarka.entity.enums.TypLekara;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UlozPouzivatelaRequest {

    private String meno;
    private String priezvisko;
    private String email;
    private String user_name;
    private String heslo;
    private String telCislo;
    private String ulica;
    private String popisneCislo;
    private String mesto;
    private PouzivatelskeRole role;


    private String rodneCislo;


    private String specializacia;
    private TypLekara typLekara;
}
