package bakalarka.example.bakalarka.requests;

import bakalarka.example.bakalarka.entity.enums.TypLekara;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UlozLekaraRequest {

    private UUID pouzivatel;
    private String specializacia;

    private TypLekara typLekara;

}
