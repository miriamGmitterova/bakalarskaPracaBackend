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
public class UlozTerminRequest {

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime datumVysetrenia;

    private UUID pacient;
    private UUID lekar;

    private UUID vysetrenie;
}
