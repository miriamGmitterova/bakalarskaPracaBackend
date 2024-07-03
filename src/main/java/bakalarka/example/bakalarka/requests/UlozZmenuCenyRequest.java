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
public class UlozZmenuCenyRequest {

    private UUID vysetrenie;
    private Double cena;

    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime cenaOd;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime cenaDo;
}
