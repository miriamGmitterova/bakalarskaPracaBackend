package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Predpis;
import bakalarka.example.bakalarka.requests.UlozPredpisRequest;
import bakalarka.example.bakalarka.services.PredpisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PredpisController {

    private final PredpisService predpisService;

    @PostMapping("/predpis/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozPredpis(@RequestBody UlozPredpisRequest ulozPredpisRequest) {
        predpisService.uloz(ulozPredpisRequest);
    }

    @GetMapping("/predpis/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Predpis> getZoznamPredpisov() {
        return predpisService.getZoznam();
    }

    @DeleteMapping("/predpis/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymaz(@PathVariable UUID id) {
        predpisService.vymaz(id);
    }

    @GetMapping("/predpis/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Predpis getPredpis(@PathVariable UUID id) {
        return predpisService.getPredpis(id);
    }

    @PutMapping("/predpis/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void upravPredpis(@PathVariable UUID id, @RequestBody UlozPredpisRequest ulozPredpisRequest) {
        predpisService.uprav(id, ulozPredpisRequest);
    }
}
