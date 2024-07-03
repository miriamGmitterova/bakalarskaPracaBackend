package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pacient;
import bakalarka.example.bakalarka.entity.enums.TypLekara;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.UlozLekaraRequest;
import bakalarka.example.bakalarka.requests.UlozPacientaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PacientService {

    private final PacientRepository pacientRepository;
    private final PouzivatelRepository pouzivatelRepository;

    public void ulozPacienta(UlozPacientaRequest ulozPacientaRequest){

        if (!pouzivatelRepository.existsById(ulozPacientaRequest.getPouzivatel())) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        Pacient pacient = new Pacient();

        pacient.setPouzivatel(pouzivatelRepository.findById(ulozPacientaRequest.getPouzivatel()).get());

        pacient.setRodne_cislo(ulozPacientaRequest.getRodneCislo());

        pacientRepository.save(pacient);
    }

    public Pacient getPacient(UUID id) {

        if(!pacientRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
        }
        return pacientRepository.findById(id).get();
    }

    public List<Pacient> getZoznam() {
        return pacientRepository.findAll();
    }

    public void vymazPacienta(UUID id) {
        if(!pacientRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
        }
        pacientRepository.deleteById(id);
    }

    public void upravPacienta(UUID id, UlozPacientaRequest ulozPacientaRequest){
        if(!pacientRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
        }
        Pacient pacient = pacientRepository.findById(id).get();

        pacient.setRodne_cislo(ulozPacientaRequest.getRodneCislo());

        pacientRepository.save(pacient);
    }
}
