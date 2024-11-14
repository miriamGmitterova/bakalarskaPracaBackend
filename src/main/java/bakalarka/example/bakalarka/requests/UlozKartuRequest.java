package bakalarka.example.bakalarka.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Validated
public class UlozKartuRequest {

    private UUID id_pouzivatela;
    private UUID id_lekara;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTC")
    private LocalDateTime zalozenie;


}
