package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.TerminVysetrenia;
import bakalarka.example.bakalarka.requests.UlozTerminRequest;
import bakalarka.example.bakalarka.services.TerminVysetreniaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TerminVysetreniaController {

    private final TerminVysetreniaService terminVysetreniaService;

    @PostMapping("/termin/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozTermin(@RequestBody UlozTerminRequest ulozTerminRequest) {
        terminVysetreniaService.uloz(ulozTerminRequest);
    }

    @GetMapping("/termin/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<TerminVysetrenia> getZoznamTerminov() {
        return terminVysetreniaService.getZoznam();
    }

    @DeleteMapping("/termin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymaz(@PathVariable UUID id) {
        terminVysetreniaService.vymaz(id);
    }

    @GetMapping("/termin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TerminVysetrenia getTermin(@PathVariable UUID id) {
        return terminVysetreniaService.getTermin(id);
    }

    @PutMapping("/termin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upravTermin(@PathVariable UUID id, @RequestBody UlozTerminRequest ulozTerminRequest) {
        terminVysetreniaService.uprav(id, ulozTerminRequest);
    }
}
