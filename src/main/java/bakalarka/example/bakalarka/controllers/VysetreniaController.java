package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Vysetrenia;

import bakalarka.example.bakalarka.requests.UlozVysetrenieRequest;

import bakalarka.example.bakalarka.services.VysetreniaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class VysetreniaController {

    private final VysetreniaService vysetreniaService;

    @PostMapping("/vysetrenie/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public void ulozVysetrenie(@RequestBody UlozVysetrenieRequest request) {
        vysetreniaService.uloz(request);
    }

    @DeleteMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazVysetrenie(@PathVariable UUID id) {
        vysetreniaService.vymaz(id);}

    @GetMapping("/vysetrenie/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Vysetrenia> getZoznamVysetreni() {
        return vysetreniaService.getZoznam();
    }

    @GetMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vysetrenia getVysetrenie(@PathVariable UUID id) {
        return vysetreniaService.get(id);
    }

    @PutMapping("/vysetrenie/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravVysetrenie(@RequestBody UlozVysetrenieRequest request, @PathVariable UUID id){
        vysetreniaService.uprav(request, id);
    }

    @GetMapping("/vysetrenie/zoznam/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vysetrenia> getVysetreniByDoctor(@PathVariable UUID doctorId) {
        return vysetreniaService.getVysetreniaByDoctor(doctorId);
    }

    @PostMapping("/vysetrenie/{id}/signUp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signUpForExamination(@PathVariable UUID id, @RequestParam UUID patientId) {
        vysetreniaService.signUpForExamination(id, patientId);
    }

    @GetMapping("/vysetrenie/zoznam/pacient/{pouzivatelId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Vysetrenia> getVysetreniByPacient(@PathVariable UUID pouzivatelId) {
        return vysetreniaService.getVysetreniaByPacient(pouzivatelId);
    }

    @PutMapping("/vysetrenie/{id}/zrusit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void zrusitVysetrenie(@PathVariable UUID id) {
        vysetreniaService.cancelAppointment(id);
    }

    @GetMapping("/test/notify")
    public ResponseEntity<String> testNotification() {
        vysetreniaService.triggerNotificationForTesting();
        return ResponseEntity.ok("Notification test triggered.");
    }


}
