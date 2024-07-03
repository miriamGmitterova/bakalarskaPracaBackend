package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.TerminVysetrenia;
import bakalarka.example.bakalarka.entity.ZmenaCeny;
import bakalarka.example.bakalarka.requests.UlozTerminRequest;
import bakalarka.example.bakalarka.requests.UlozZmenuCenyRequest;
import bakalarka.example.bakalarka.services.TerminVysetreniaService;
import bakalarka.example.bakalarka.services.ZmenaCenyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ZmenaCenyController {

    private final ZmenaCenyService zmenaCenyService;

    @PostMapping("/cena/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozCenu(@RequestBody UlozZmenuCenyRequest ulozZmenuCenyRequest) {
        zmenaCenyService.uloz(ulozZmenuCenyRequest);
    }

    @GetMapping("/cena/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<ZmenaCeny> getZmenyCien() {
        return zmenaCenyService.getZoznam();
    }

    @DeleteMapping("/cena/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymaz(@PathVariable UUID id) {
        zmenaCenyService.vymaz(id);
    }

    @GetMapping("/cena/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ZmenaCeny getZmenuCeny(@PathVariable UUID id) {
        return zmenaCenyService.getZmena(id);
    }

    @PutMapping("/cena/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upravZmenuCeny(@PathVariable UUID id, @RequestBody UlozZmenuCenyRequest ulozZmenuCenyRequest) {
        zmenaCenyService.uprav(id, ulozZmenuCenyRequest);
    }
}
