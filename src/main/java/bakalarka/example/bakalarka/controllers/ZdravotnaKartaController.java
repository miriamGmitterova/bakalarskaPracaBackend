package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.*;
import bakalarka.example.bakalarka.requests.UlozKartuRequest;
import bakalarka.example.bakalarka.services.ZdravotnaKartaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ZdravotnaKartaController {

    private final ZdravotnaKartaService zdravotnaKartaService;



    @PostMapping("/karta/uloz")
    public ResponseEntity<?> assignDoctorToPatient(@RequestBody UlozKartuRequest request) {
        try {
            zdravotnaKartaService.uloz(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/karta/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<ZdravotnaKarta> getZoznamKariet() {
        return zdravotnaKartaService.getZoznam();
    }

    @DeleteMapping("/karta/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
   public ResponseEntity<?>  vymaz(@PathVariable UUID id) {
//        zdravotnaKartaService.vymaz(id);

        try {
            zdravotnaKartaService.vymaz(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

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

    @GetMapping("/patients/{patientId}/doctors")
    public List<Lekar> getDoctorsForPatient(@PathVariable UUID patientId) {
        return zdravotnaKartaService.getDoctorsForPatient(patientId);
    }

    @GetMapping("/doctors/{doctorId}/patients")
    public List<Pouzivatel> getPatientsForDoctor(@PathVariable UUID doctorId) {
        return zdravotnaKartaService.getPatientsForDoctor(doctorId);
    }

    @DeleteMapping("/karta/{userId}/{doctorId}")
    public ResponseEntity<String> deleteCard(@PathVariable UUID userId, @PathVariable UUID doctorId) {
        try {
            zdravotnaKartaService.deleteCard(userId, doctorId);
            return new ResponseEntity<>("Card deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting card: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
