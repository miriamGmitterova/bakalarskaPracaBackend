package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.ZdravotnaKarta;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PacientRepository;
import bakalarka.example.bakalarka.repositories.ZdravotnaKartaRepository;
import bakalarka.example.bakalarka.requests.UlozKartuRequest;
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
public class ZdravotnaKartaService {

    private final ZdravotnaKartaRepository zdravotnaKartaRepository;
    private final PacientRepository pacientRepository;
    private final LekarRepository lekarRepository;

    public void uloz(@Valid UlozKartuRequest request) {

//        if (request.getZalozenie().isBefore(LocalDateTime.now())) {
//            throw new IllegalArgumentException("Dátum je v minulosti");
//        }
//
//        if (!pacientRepository.existsById(request.getPacient())) {
//            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
//        }
//
//        ZdravotnaKarta karta= new ZdravotnaKarta();
//
//        karta.setPacient(pacientRepository.findById(request.getPacient()).get());
//        karta.setZalozenie(request.getZalozenie());
//        karta.setPosledna_prehliadka(request.getPoslednaPrehliadka());
//
//        zdravotnaKartaRepository.save(karta);

        if (request.getZalozenie().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dátum je v minulosti");
        }

        if (!pacientRepository.existsById(request.getPacient())) {
            throw new NoSuchElementException("Chyba: Pacient s danym ID neexistuje");
        }

        if (!lekarRepository.existsById(request.getLekar())) {
            throw new NoSuchElementException("Chyba: Lekar s danym ID neexistuje");
        }

        ZdravotnaKarta priradenie = new ZdravotnaKarta();

        priradenie.setPacient(pacientRepository.findById(request.getPacient()).get());
        priradenie.setLekar(lekarRepository.findById(request.getLekar()).get());
        priradenie.setZalozenie(request.getZalozenie());

        zdravotnaKartaRepository.save(priradenie);
    }


    public List<ZdravotnaKarta> getZoznam() {
        return zdravotnaKartaRepository.findAll();
    }

    public void vymaz(UUID id) {

        if (!zdravotnaKartaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: rezervacia s danym ID neexistuje");
        }

        zdravotnaKartaRepository.deleteById(id);
    }

    public ZdravotnaKarta getKarta(UUID id) {
        if (!zdravotnaKartaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: rezervacia s danym ID neexistuje");
        }

        return zdravotnaKartaRepository.findById(id).get();
    }

    public void uprav(UUID id, UlozKartuRequest request) {
        if (!zdravotnaKartaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Priradenie s danym ID neexistuje");
        }
        ZdravotnaKarta priradenie = zdravotnaKartaRepository.findById(id).get();

        priradenie.setPacient(pacientRepository.findById(request.getPacient()).get());
        priradenie.setLekar(lekarRepository.findById(request.getLekar()).get());
        priradenie.setZalozenie(request.getZalozenie());

        zdravotnaKartaRepository.save(priradenie);

    }
}
