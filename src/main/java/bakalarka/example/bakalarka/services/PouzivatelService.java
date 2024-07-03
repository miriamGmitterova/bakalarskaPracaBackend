package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pacient;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.enums.PouzivatelskeRole;
import bakalarka.example.bakalarka.entity.enums.TypLekara;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.AuthorizeRequest;
import bakalarka.example.bakalarka.requests.UlozLekaraRequest;
import bakalarka.example.bakalarka.requests.UlozPacientaRequest;
import bakalarka.example.bakalarka.requests.UlozPouzivatelaRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bakalarka.example.bakalarka.services.LekarService;
import bakalarka.example.bakalarka.services.PacientService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        pouzivatel.setTel_cislo(ulozPouzivatelaRequest.getTelCislo());
        pouzivatel.setUlica(ulozPouzivatelaRequest.getUlica());
        pouzivatel.setPopisne_cislo(ulozPouzivatelaRequest.getPopisneCislo());
        pouzivatel.setMesto(ulozPouzivatelaRequest.getMesto());
        pouzivatel.setFoto(ulozPouzivatelaRequest.getFoto());

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

//        boolean isUsernameAvailable = checkUsernameAvailability(ulozPouzivatelaRequest.getUser_name());
//
//        if (!isUsernameAvailable) {
//            throw new ValidationException("Username is already taken");
//        }

//        kontrolaNovehoPouzivatela(ulozPouzivatelaRequest);
//
//
//        Pouzivatel pouzivatel = new Pouzivatel();
//        pouzivatel.setEmail(ulozPouzivatelaRequest.getEmail());
//        pouzivatel.setPriezvisko(ulozPouzivatelaRequest.getPriezvisko());
//        pouzivatel.setMeno(ulozPouzivatelaRequest.getMeno());
//        pouzivatel.setUser_name(ulozPouzivatelaRequest.getUser_name());
//        pouzivatel.setFoto(ulozPouzivatelaRequest.getFoto());
//        pouzivatel.setTel_cislo(ulozPouzivatelaRequest.getTelCislo());
//        pouzivatel.setUlica(ulozPouzivatelaRequest.getUlica());
//        pouzivatel.setPopisne_cislo(ulozPouzivatelaRequest.getPopisneCislo());
//        pouzivatel.setPsc(ulozPouzivatelaRequest.getPsc());
//
//        pouzivatel.setRole(ulozPouzivatelaRequest.getRole());
//
//        pouzivatel.setPassword(passwordEncoder.encode(ulozPouzivatelaRequest.getHeslo()));
//
//        pouzivatelRepository.save(pouzivatel);

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

    public void upravPouzivatela(UUID id, UlozPouzivatelaRequest ulozPouzivatelaRequest) {

        if (!pouzivatelRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        Pouzivatel pouzivatel = pouzivatelRepository.findById(id).get();

        pouzivatel.setEmail(ulozPouzivatelaRequest.getEmail());
        pouzivatel.setPriezvisko(ulozPouzivatelaRequest.getPriezvisko());
        pouzivatel.setMeno(ulozPouzivatelaRequest.getMeno());
        pouzivatel.setPassword(ulozPouzivatelaRequest.getHeslo());
        pouzivatel.setUser_name(ulozPouzivatelaRequest.getUser_name());

        pouzivatel.setFoto(ulozPouzivatelaRequest.getFoto());
        pouzivatel.setTel_cislo(ulozPouzivatelaRequest.getTelCislo());
        pouzivatel.setUlica(ulozPouzivatelaRequest.getUlica());
        pouzivatel.setPopisne_cislo(ulozPouzivatelaRequest.getPopisneCislo());
        pouzivatel.setMesto(ulozPouzivatelaRequest.getMesto());
        pouzivatel.setRole(ulozPouzivatelaRequest.getRole());


        pouzivatelRepository.save(pouzivatel);
    }

    public List<Pouzivatel> getZoznam() {
        List<Pouzivatel> pouzivatels = new ArrayList<>();
        Streamable.of(pouzivatelRepository.findAll()).forEach(pouzivatels::add);
        return pouzivatels;
    }

    private void kontrolaNovehoPouzivatela(UlozPouzivatelaRequest request) {
        Boolean existujuciMail = pouzivatelRepository.findAll().stream().anyMatch(pouzivatel -> pouzivatel.getEmail().equals(request.getEmail()));

        if (existujuciMail) {
            throw new ValidationException("Pouzivatel s danym mailom uz existuje");
        }


        Boolean existujuceUsername = pouzivatelRepository.findAll().stream().anyMatch(pouzivatel -> pouzivatel.getUser_name().equals(request.getUser_name()));

        if (existujuceUsername) {
            throw new ValidationException("Pouzivatel s danym UserName uz existuje");
        }

    }

    public Pouzivatel authorise(AuthorizeRequest request) throws Exception {


//        String username = request.getUser_name();
//        String password = request.getPassword();
//
//        List<Pouzivatel> zoznamPouzivatelov = pouzivatelRepository.findAll();
//
//        for (Pouzivatel p : zoznamPouzivatelov) {
//            if (p.getUser_name().equals(username)) {
//                // Check if the password matches using PasswordEncoder
//                if (passwordEncoder.matches(password, p.getPassword())) {
//                    return p; // Return the user if both username and password match
//                } else {
//                    throw new IllegalArgumentException("Incorrect password for username: " + username);
//                }
//            }
//        }
//
//        throw new NoSuchElementException("User not found with username: " + username);
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

//        for (Pouzivatel p : zoznamPouzivatelov) {
//            if (p.getUser_name().equals(username)) {
//                return p;
//            }
//        }
//
//        throw new NoSuchElementException("User not found with username: " + username);
//    }

