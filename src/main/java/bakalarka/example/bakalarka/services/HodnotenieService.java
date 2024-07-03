package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Hodnotenie;
import bakalarka.example.bakalarka.repositories.HodnotenieRepository;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.requests.UlozHodnotenieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HodnotenieService {

    private final HodnotenieRepository hodnotenieRepository;
    private final PacientRepository pacientRepository;
    private final LekarRepository lekarRepository;

    public void uloz(UlozHodnotenieRequest request) {

        if (!pacientRepository.existsById(request.getPacient())) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        if (!lekarRepository.existsById(request.getLekar())) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }
        Hodnotenie hodnotenie = new Hodnotenie();
        hodnotenie.setHviezdicky(request.getHviezdicky());
        hodnotenie.setDodatocny_text(request.getDodatocnyText());
        hodnotenie.setPacient(pacientRepository.findById(request.getPacient()).get());
        hodnotenie.setLekar(lekarRepository.findById(request.getLekar()).get());
        hodnotenie.setVytvorene(LocalDateTime.now());

        hodnotenieRepository.save(hodnotenie);
    }

    public void vymaz(UUID id) {

        if(!hodnotenieRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Hodnotenie s danym ID neexistuje");
        }
        hodnotenieRepository.deleteById(id);
    }

    public Hodnotenie get(UUID id) {
        if(!hodnotenieRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Hodnotenie s danym ID neexistuje");
        }
        return hodnotenieRepository.findById(id).get();
    }

    public List<Hodnotenie> getZoznam(){
        return hodnotenieRepository.findAllByOrderByVytvoreneDesc();
    }

    public void uprav(UlozHodnotenieRequest request, UUID id){
        if(!hodnotenieRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Hodnotenie s danym ID neexistuje");
        }
        Hodnotenie hodnotenie = hodnotenieRepository.findById(id).get();

        hodnotenie.setHviezdicky(request.getHviezdicky());
        hodnotenie.setDodatocny_text(request.getDodatocnyText());
        hodnotenie.setLekar(lekarRepository.findById(request.getLekar()).get());

        hodnotenieRepository.save(hodnotenie);
    }
}
