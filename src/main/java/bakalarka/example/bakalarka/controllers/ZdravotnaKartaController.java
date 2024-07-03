package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.TerminVysetrenia;
import bakalarka.example.bakalarka.entity.ZdravotnaKarta;
import bakalarka.example.bakalarka.requests.UlozKartuRequest;
import bakalarka.example.bakalarka.requests.UlozTerminRequest;
import bakalarka.example.bakalarka.services.TerminVysetreniaService;
import bakalarka.example.bakalarka.services.ZdravotnaKartaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ZdravotnaKartaController {

    private final ZdravotnaKartaService zdravotnaKartaService;

    @PostMapping("/karta/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozKartu(@RequestBody UlozKartuRequest ulozKartuRequest) {
        zdravotnaKartaService.uloz(ulozKartuRequest);
    }

    @GetMapping("/karta/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<ZdravotnaKarta> getZoznamKariet() {
        return zdravotnaKartaService.getZoznam();
    }

    @DeleteMapping("/karta/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymaz(@PathVariable UUID id) {
        zdravotnaKartaService.vymaz(id);
    }

    @GetMapping("/karta/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ZdravotnaKarta getKarta(@PathVariable UUID id) {
        return zdravotnaKartaService.getKarta(id);
    }

    @PutMapping("/karta/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upravKartu(@PathVariable UUID id, @RequestBody UlozKartuRequest ulozKartuRequest) {
         zdravotnaKartaService.uprav(id, ulozKartuRequest);
    }
}
