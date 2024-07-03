package bakalarka.example.bakalarka.requests;

import bakalarka.example.bakalarka.entity.Pacient;
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

    private UUID pacient;
    private UUID lekar;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime zalozenie;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime odstranenie;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime poslednaPrehliadka;
}
