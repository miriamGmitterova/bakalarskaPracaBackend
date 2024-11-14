package bakalarka.example.bakalarka.controllers;



import bakalarka.example.bakalarka.entity.Pouzivatel;

import bakalarka.example.bakalarka.requests.AuthorizeRequest;
import bakalarka.example.bakalarka.requests.UlozPouzivatelaRequest;
import bakalarka.example.bakalarka.requests.UpravPouzivatelaRequest;
import bakalarka.example.bakalarka.services.PouzivatelService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/pouzivatel")
public class PouzivatelController {

    private final PouzivatelService pouzivatelService;



    @PostMapping("/uloz")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@RequestBody UlozPouzivatelaRequest ulozPouzivatelaRequest) {


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
    public ResponseEntity<String> updateUserProfile(
            @PathVariable UUID id,
            @RequestBody UpravPouzivatelaRequest upravPouzivatelaRequest) {
        try {
            pouzivatelService.upravPouzivatela(id, upravPouzivatelaRequest);
            return new ResponseEntity<>("User profile updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user profile: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authorise")
    @ResponseStatus(HttpStatus.OK)
    public Pouzivatel auuthorize(@RequestBody AuthorizeRequest request) throws Exception {

        return pouzivatelService.authorise(request);
    }

}