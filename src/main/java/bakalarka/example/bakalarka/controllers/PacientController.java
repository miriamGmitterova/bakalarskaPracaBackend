package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pacient;
import bakalarka.example.bakalarka.requests.UlozLekaraRequest;
import bakalarka.example.bakalarka.requests.UlozPacientaRequest;
import bakalarka.example.bakalarka.services.LekarService;
import bakalarka.example.bakalarka.services.PacientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pacient")
public class PacientController {

    private final PacientService pacientService;

    @PostMapping("/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozPacienta(@RequestBody UlozPacientaRequest ulozPacientaRequest) {
        pacientService.ulozPacienta(ulozPacientaRequest);
    }

    @GetMapping("/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Pacient> getZoznamPacientov() {
        return pacientService.getZoznam();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pacient getPacient(@PathVariable UUID id) {
        return pacientService.getPacient(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazPacienta(@PathVariable UUID id) {
        pacientService.vymazPacienta(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravPacienta(@PathVariable UUID id, UlozPacientaRequest ulozPacientaRequest) {
        pacientService.upravPacienta(id, ulozPacientaRequest);
    }
}
