package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.requests.UlozLekaraRequest;
import bakalarka.example.bakalarka.services.LekarService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lekar")
public class LekarController {

    private final LekarService lekarService;

    @PostMapping("/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozLekara(@RequestBody UlozLekaraRequest ulozLekaraRequest) {
        lekarService.ulozLekara(ulozLekaraRequest);
    }

    @GetMapping("/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Lekar> getZoznamLekarov() {
        return lekarService.getZoznam();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lekar getLekara(@PathVariable UUID id) {
        return lekarService.getLekar(id);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazLekara(@PathVariable UUID id) {
        lekarService.vymazLekara(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravLekara(@PathVariable UUID id, UlozLekaraRequest ulozLekaraRequest) {
        lekarService.upravLekara(id, ulozLekaraRequest);
    }
}
