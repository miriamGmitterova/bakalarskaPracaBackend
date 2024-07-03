package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Liek;
import bakalarka.example.bakalarka.requests.UlozLiekRequest;
import bakalarka.example.bakalarka.services.LiekService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LiekController {

    private final LiekService liekService;

    @PostMapping("/liek/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozLiek(@RequestBody UlozLiekRequest request) {
        liekService.uloz(request);
    }

    @GetMapping("/liek/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Liek> getZoznam() {
        return liekService.getZoznam();
    }

    @GetMapping("/liek/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Liek getLiek(@PathVariable UUID id) {
        return liekService.getLiek(id);
    }

    @DeleteMapping("/liek/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazLiek(@PathVariable UUID id) {
            liekService.vymazLiek(id);
    }

    @PutMapping("/liek/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravLiek(@PathVariable UUID id, @RequestBody UlozLiekRequest request) {
        liekService.uprav(request, id);
    }
}
