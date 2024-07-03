package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Hodnotenie;
import bakalarka.example.bakalarka.entity.ZoznamVysetreni;
import bakalarka.example.bakalarka.requests.UlozHodnotenieRequest;
import bakalarka.example.bakalarka.requests.UlozVysetrenieRequest;
import bakalarka.example.bakalarka.services.HodnotenieService;
import bakalarka.example.bakalarka.services.ZoznamVysetreniService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ZoznamVysetreniController {

    private final ZoznamVysetreniService zoznamVysetreniService;

    @PostMapping("/vysetrenie/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozVysetrenie(@RequestBody UlozVysetrenieRequest request) {
        zoznamVysetreniService.uloz(request);
    }

    @DeleteMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazVysetrenie(@PathVariable UUID id) {zoznamVysetreniService.vymaz(id);}

    @GetMapping("/vysetrenie/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<ZoznamVysetreni> getZoznamVysetreni() {
        return zoznamVysetreniService.getZoznam();
    }

    @GetMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ZoznamVysetreni getVysetrenie(@PathVariable UUID id) {
        return zoznamVysetreniService.get(id);
    }

    @PutMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravVysetrenie(@RequestBody UlozVysetrenieRequest request, @PathVariable UUID id){
        zoznamVysetreniService.uprav(request, id);
    }
}
