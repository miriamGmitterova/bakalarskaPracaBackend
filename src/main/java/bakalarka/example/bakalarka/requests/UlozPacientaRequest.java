package bakalarka.example.bakalarka.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UlozPacientaRequest {

    private UUID pouzivatel;
    private String rodneCislo;
}
