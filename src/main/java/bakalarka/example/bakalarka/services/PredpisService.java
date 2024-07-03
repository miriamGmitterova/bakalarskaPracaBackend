package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Predpis;
import bakalarka.example.bakalarka.repositories.LiekRepository;
import bakalarka.example.bakalarka.repositories.PredpisRepository;
import bakalarka.example.bakalarka.repositories.ZdravotnaKartaRepository;
import bakalarka.example.bakalarka.requests.UlozPredpisRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Validated
public class PredpisService {
    private final PredpisRepository predpisRepository;
    private final LiekRepository liekRepository;
    private final ZdravotnaKartaRepository zdravotnaKartaRepository;

    public void uloz(@Valid UlozPredpisRequest request) {
        if (request.getDatumPredpisu().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dátum je v minulosti");
        }

        if (!liekRepository.existsById(request.getLiek())) {
            throw new NoSuchElementException("Chyba: Liek s danym ID neexistuje");
        }

        Predpis predpis = new Predpis();

        predpis.setLiek(liekRepository.findById(request.getLiek()).get());
        predpis.setZdravotnaKarta(zdravotnaKartaRepository.findById(request.getZdravotnaKarta()).get());
        predpis.setDatum_predpisu(request.getDatumPredpisu());
        predpis.setBol_vybraty(request.isVybraty());

        predpisRepository.save(predpis);
    }

    public List<Predpis> getZoznam() {
        return predpisRepository.findAll();
    }

    public void vymaz(UUID id) {
        if (!predpisRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Predpis s danym ID neexistuje");
        }
        predpisRepository.deleteById(id);
    }

    public Predpis getPredpis(UUID id){
        if (!predpisRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Predpis s danym ID neexistuje");
        }
        return predpisRepository.findById(id).get();
    }

    public void uprav(UUID id, UlozPredpisRequest ulozPredpisRequest){
        if (!predpisRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Predpis s danym ID neexistuje");
        }
        Predpis predpis = predpisRepository.findById(id).get();

        predpis.setLiek(liekRepository.findById(ulozPredpisRequest.getLiek()).get());
        predpis.setZdravotnaKarta(zdravotnaKartaRepository.findById(ulozPredpisRequest.getZdravotnaKarta()).get());
        predpis.setDatum_predpisu(ulozPredpisRequest.getDatumPredpisu());
        predpis.setBol_vybraty(ulozPredpisRequest.isVybraty());

        predpisRepository.save(predpis);
    }
}
