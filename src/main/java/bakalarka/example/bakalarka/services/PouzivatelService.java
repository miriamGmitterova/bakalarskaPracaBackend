package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pacient;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.*;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PouzivatelService {


    private final PouzivatelRepository pouzivatelRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private LekarRepository lekarRepository;

    @Autowired
    private PacientRepository pacientRepository;

    public void registerUser(UlozPouzivatelaRequest ulozPouzivatelaRequest) {
        try {
        checkUserAvailability(ulozPouzivatelaRequest);

        // Create Pouzivatel entity
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setEmail(ulozPouzivatelaRequest.getEmail());
        pouzivatel.setPriezvisko(ulozPouzivatelaRequest.getPriezvisko());
        pouzivatel.setMeno(ulozPouzivatelaRequest.getMeno());
        pouzivatel.setUser_name(ulozPouzivatelaRequest.getUser_name());
        pouzivatel.setPassword(passwordEncoder.encode(ulozPouzivatelaRequest.getHeslo()));
        pouzivatel.setRole(ulozPouzivatelaRequest.getRole());
        pouzivatel.setTelCislo(ulozPouzivatelaRequest.getTelCislo());
        pouzivatel.setUlica(ulozPouzivatelaRequest.getUlica());
        pouzivatel.setPopisneCislo(ulozPouzivatelaRequest.getPopisneCislo());
        pouzivatel.setMesto(ulozPouzivatelaRequest.getMesto());


        // Save Pouzivatel
        Pouzivatel savedPouzivatel = pouzivatelRepository.save(pouzivatel);

        // Save additional role-specific data
        if (savedPouzivatel.getRole() == PouzivatelskeRole.PACIENT) {
            Pacient pacient = new Pacient();
            pacient.setRodne_cislo(ulozPouzivatelaRequest.getRodneCislo());
            pacient.setPouzivatel(savedPouzivatel);
            pacientRepository.save(pacient);
        } else if (savedPouzivatel.getRole() == PouzivatelskeRole.LEKAR) {
            Lekar lekar = new Lekar();
            lekar.setSpecializacia(ulozPouzivatelaRequest.getSpecializacia());
            lekar.setTypLekara(ulozPouzivatelaRequest.getTypLekara());
            lekar.setPouzivatel(savedPouzivatel);
            lekarRepository.save(lekar);
        }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }


    }

    private void checkUserAvailability(UlozPouzivatelaRequest request) {
        // Check if email already exists
        boolean emailExists = pouzivatelRepository.findAll().stream()
                .anyMatch(pouzivatel -> pouzivatel.getEmail().equals(request.getEmail()));

        if (emailExists) {
            throw new ValidationException("User with the given email already exists");
        }

        // Check if username already exists
        boolean usernameExists = pouzivatelRepository.findAll().stream()
                .anyMatch(pouzivatel -> pouzivatel.getUser_name().equals(request.getUser_name()));

        if (usernameExists) {
            throw new ValidationException("User with the given username already exists");
        }
    }




    public Pouzivatel getPouzivatel(UUID id) {

        if (!pouzivatelRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        return pouzivatelRepository.findById(id).get();
    }


    public void vymazPouzivatela(UUID id) {
        if (!pouzivatelRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        pouzivatelRepository.deleteById(id);
    }

    public void upravPouzivatela(UUID id, UpravPouzivatelaRequest upravPouzivatelaRequest) {

        // Check if username already exists
        boolean usernameExists = pouzivatelRepository.findAll().stream()
                .anyMatch(pouzivatel -> pouzivatel.getUser_name().equals(upravPouzivatelaRequest.getUser_name()));

        if (usernameExists) {
            throw new ValidationException("User with the given username already exists");
        }

        if (!pouzivatelRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        Pouzivatel pouzivatel = pouzivatelRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje")
        );

        // Debug output for UpravPouzivatelaRequest
        System.out.println("Updating Pouzivatel with ID: " + pouzivatel.getId_pouzivatela());
        System.out.println("User name: " + upravPouzivatelaRequest.getUser_name());
        System.out.println("Tel. cislo: " + upravPouzivatelaRequest.getTelCislo());
        System.out.println("Ulica: " + upravPouzivatelaRequest.getUlica());
        System.out.println("Popisne cislo: " + upravPouzivatelaRequest.getPopisneCislo());
        System.out.println("Mesto: " + upravPouzivatelaRequest.getMesto());

        pouzivatel.setUser_name(upravPouzivatelaRequest.getUser_name());
        pouzivatel.setTelCislo(upravPouzivatelaRequest.getTelCislo());
        pouzivatel.setUlica(upravPouzivatelaRequest.getUlica());

        // Debug output before setting new values
        System.out.println("Old cislo value: " + pouzivatel.getPopisneCislo());
        pouzivatel.setPopisneCislo(upravPouzivatelaRequest.getPopisneCislo());
        System.out.println("New cislo value: " + pouzivatel.getPopisneCislo());

        System.out.println("Old Mesto value: " + pouzivatel.getMesto());
        pouzivatel.setMesto(upravPouzivatelaRequest.getMesto());
        System.out.println("New Mesto value: " + pouzivatel.getMesto());

        // Save the updated pouzivatel entity
        pouzivatelRepository.save(pouzivatel);


    }

    public List<Pouzivatel> getZoznam() {
        List<Pouzivatel> pouzivatels = new ArrayList<>();
        Streamable.of(pouzivatelRepository.findAll()).forEach(pouzivatels::add);
        return pouzivatels;
    }


    public Pouzivatel authorise(AuthorizeRequest request) throws Exception {

        String username = request.getUser_name();
        String password = request.getPassword();

        Pouzivatel pouzivatel = pouzivatelRepository.findAll().stream()
                .filter(p -> p.getUser_name().equals(username))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));

        if (passwordEncoder.matches(password, pouzivatel.getPassword())) {
            return pouzivatel; // Return the user if password matches
        } else {
            throw new IllegalArgumentException("Incorrect password for username: " + username);
        }


    }
}

