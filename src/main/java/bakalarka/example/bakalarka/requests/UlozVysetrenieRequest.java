package bakalarka.example.bakalarka.requests;

import bakalarka.example.bakalarka.entity.enums.TypVysetrenia;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UlozVysetrenieRequest {

    private String nazovVysetrenia;
    private TypVysetrenia typ_vysetrenia;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime datum_vysetrenia;

    private UUID id_lekara;


}
