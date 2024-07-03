package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.TerminVysetrenia;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.TerminVysetreniaRepository;
import bakalarka.example.bakalarka.repositories.ZoznamVysetreniRepository;
import bakalarka.example.bakalarka.requests.UlozTerminRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class TerminVysetreniaService {

    private final TerminVysetreniaRepository terminVysetreniaRepository;
    private final PacientRepository pacientRepository;
    private final LekarRepository lekarRepository;
    private final ZoznamVysetreniRepository zoznamVysetreniRepository;

    public void uloz(@Valid UlozTerminRequest request) {
        if (request.getDatumVysetrenia().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dátum je v minulosti");
        }

        if (!pacientRepository.existsById(request.getPacient())) {
            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
        }

        if (!lekarRepository.existsById(request.getLekar())) {
            throw new NoSuchElementException("Chyba: Lekar s danym ID neexistuje");
        }

        TerminVysetrenia termin = new TerminVysetrenia();

        termin.setPacient(pacientRepository.findById(request.getPacient()).get());
        termin.setLekar(lekarRepository.findById(request.getLekar()).get());
        termin.setDatum_vysetrenia(request.getDatumVysetrenia());
        termin.setZoznamVysetreni(zoznamVysetreniRepository.findById(request.getVysetrenie()).get());

        terminVysetreniaRepository.save(termin);
    }

    public List<TerminVysetrenia> getZoznam() {
        return terminVysetreniaRepository.findAll();
    }

    public void vymaz(UUID id) {
        if (!terminVysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Termin s danym ID neexistuje");
        }
        terminVysetreniaRepository.deleteById(id);
    }

    public TerminVysetrenia getTermin(UUID id){
        if (!terminVysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Termin s danym ID neexistuje");
        }
        return terminVysetreniaRepository.findById(id).get();
    }

    public void uprav(UUID id, UlozTerminRequest ulozTerminRequest){
        if (!terminVysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Termin s danym ID neexistuje");
        }
        TerminVysetrenia termin = terminVysetreniaRepository.findById(id).get();

        termin.setPacient(pacientRepository.findById(ulozTerminRequest.getPacient()).get());
        termin.setLekar(lekarRepository.findById(ulozTerminRequest.getLekar()).get());
        termin.setDatum_vysetrenia(ulozTerminRequest.getDatumVysetrenia());
        termin.setZoznamVysetreni(zoznamVysetreniRepository.findById(ulozTerminRequest.getVysetrenie()).get());

        terminVysetreniaRepository.save(termin);
    }
}
