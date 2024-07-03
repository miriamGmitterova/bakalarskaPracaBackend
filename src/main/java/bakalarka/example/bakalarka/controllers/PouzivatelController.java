package bakalarka.example.bakalarka.controllers;


import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pacient;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.AuthorizeRequest;
import bakalarka.example.bakalarka.requests.UlozPouzivatelaRequest;
import bakalarka.example.bakalarka.services.PouzivatelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/pouzivatel")
public class PouzivatelController {

    private final PouzivatelService pouzivatelService;

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @Autowired
    private PacientRepository pacientRepository;

    @Autowired
    private LekarRepository lekarRepository;

//    @PostMapping("/uloz")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void ulozPouzivatela(@RequestBody UlozPouzivatelaRequest ulozPouzivatelaRequest) {
//        pouzivatelService.ulozPouzivatela(ulozPouzivatelaRequest);
//
//    }
@PostMapping("/uloz")
@ResponseStatus(HttpStatus.CREATED)
public ResponseEntity<?> registerUser(@RequestBody UlozPouzivatelaRequest ulozPouzivatelaRequest) {
    // Create and save the Pouzivatel entity
//    Pouzivatel newUser = new Pouzivatel();
//    newUser.setMeno(ulozPouzivatelaRequest.getMeno());
//    newUser.setPriezvisko(ulozPouzivatelaRequest.getPriezvisko());
//    newUser.setTel_cislo(ulozPouzivatelaRequest.getTelCislo());
//    newUser.setUlica(ulozPouzivatelaRequest.getUlica());
//    newUser.setPopisne_cislo(ulozPouzivatelaRequest.getPopisneCislo());
//    newUser.setPsc(ulozPouzivatelaRequest.getPsc());
//    newUser.setUser_name(ulozPouzivatelaRequest.getUser_name());
//    newUser.setPassword(ulozPouzivatelaRequest.getHeslo());
//    newUser.setEmail(ulozPouzivatelaRequest.getEmail());
//    newUser.setRole(ulozPouzivatelaRequest.getRole());
//
//    Pouzivatel savedUser = pouzivatelRepository.save(newUser);
//
//    // Save additional information based on the role
//    if (savedUser.getRole() == PouzivatelskeRole.PACIENT) {
//        Pacient newPacient = new Pacient();
//        newPacient.setRodne_cislo(ulozPouzivatelaRequest.getRodneCislo());
//        newPacient.setPouzivatel(savedUser);
//        pacientRepository.save(newPacient);
//    } else if (savedUser.getRole() == PouzivatelskeRole.LEKAR) {
//        Lekar newLekar = new Lekar();
//        newLekar.setSpecializacia(ulozPouzivatelaRequest.getSpecializacia());
//        newLekar.setTypLekara(ulozPouzivatelaRequest.getTypLekara());
//        newLekar.setPouzivatel(savedUser);
//        lekarRepository.save(newLekar);
//    }
//
//    return ResponseEntity.ok("User registered successfully");

//    pouzivatelService.registerUser(ulozPouzivatelaRequest);
//    return ResponseEntity.ok("User registered successfully");
//    try {
//        pouzivatelService.registerUser(ulozPouzivatelaRequest);
//        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
//    } catch (RuntimeException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

//    pouzivatelService.registerUser(ulozPouzivatelaRequest);
//    System.out.println("Received Photo: " + ulozPouzivatelaRequest.getFoto()); // Add this line in the controller method
//    return ResponseEntity.ok("User registered successfully");

    System.out.println("Received Registration Data: " + ulozPouzivatelaRequest); // Log request data
    try {
        pouzivatelService.registerUser(ulozPouzivatelaRequest);
        return ResponseEntity.ok("User registered successfully");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registration: " + e.getMessage());
    }
}



    @GetMapping("/skuska")
    public String skuska() {
        return "skuska";
    }

//    @GetMapping("/checkUsername/{username}")
//    public boolean checkUsernameAvailability(@PathVariable String username) {
//        return pouzivatelService.checkUsernameAvailability(username);
//    }

    @GetMapping("/zoznam")
    @ResponseStatus(HttpStatus.OK)
    public List<Pouzivatel> getZoznamPouzivatelov() {
        return pouzivatelService.getZoznam();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pouzivatel getPouzivatel(@PathVariable UUID id) {

        return pouzivatelService.getPouzivatel(id);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vymazPouzivatela(@PathVariable UUID id) {
        pouzivatelService.vymazPouzivatela(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void upravPouzivatela(@PathVariable UUID id, UlozPouzivatelaRequest ulozPouzivatelaRequest) {

        pouzivatelService.upravPouzivatela(id, ulozPouzivatelaRequest);
    }

    @PostMapping("/authorise")
    @ResponseStatus(HttpStatus.OK)
    public Pouzivatel auuthorize(@RequestBody AuthorizeRequest request) throws Exception {

        return pouzivatelService.authorise(request);
    }

}
