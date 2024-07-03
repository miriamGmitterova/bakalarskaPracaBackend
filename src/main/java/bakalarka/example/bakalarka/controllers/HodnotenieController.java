package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Hodnotenie;
import bakalarka.example.bakalarka.requests.UlozHodnotenieRequest;
import bakalarka.example.bakalarka.services.HodnotenieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class HodnotenieController {

    private final HodnotenieService hodnotenieService;

    @PostMapping("/hodnotenie/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozHodnotenie(@RequestBody UlozHodnotenieRequest request) {
        hodnotenieService.uloz(request);
    }

    @DeleteMapping("/hodnotenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazHodnotenie(@PathVariable UUID id) {hodnotenieService.vymaz(id);}

    @GetMapping("/hodnotenie/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Hodnotenie> getZoznamHodnoteni() {
        return hodnotenieService.getZoznam();
    }

    @GetMapping("/hodnotenie/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Hodnotenie getHodnotenie(@PathVariable UUID id) {
        return hodnotenieService.get(id);
    }

    @PutMapping("/hodnotenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravHodnotenie(@RequestBody UlozHodnotenieRequest request, @PathVariable UUID id){
        hodnotenieService.uprav(request, id);
    }
}
