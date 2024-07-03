package bakalarka.example.bakalarka.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UlozHodnotenieRequest {

    private UUID pacient;
    private UUID lekar;
    private Integer hviezdicky;
    private String dodatocnyText;


}
