package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Liek;
import bakalarka.example.bakalarka.repositories.LiekRepository;
import bakalarka.example.bakalarka.requests.UlozLiekRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LiekService {

    private final LiekRepository liekRepository;

    public void uloz(UlozLiekRequest request){
        Liek liek = new Liek();
        liek.setNazovLieku(request.getNazov());
        liek.setDodatocne_info(request.getDodatocneInfo());

        liekRepository.save(liek);
    }

    public List<Liek> getZoznam() {
        return liekRepository.findAll();
    }

    public Liek getLiek(UUID id) {
        if(!liekRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Liek s danym ID neexistuje");
        }
        return liekRepository.findById(id).get();
    }

    public void vymazLiek(UUID id) {

        if(!liekRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Liek s danym ID neexistuje");
        }
        liekRepository.deleteById(id);
    }

    public void uprav(UlozLiekRequest ulozLiekRequest, UUID id) {

        if(!liekRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Liek s danym ID neexistuje");
        }

        Liek liek = liekRepository.findById(id).get();
        liek.setNazovLieku(ulozLiekRequest.getNazov());
        liek.setDodatocne_info(ulozLiekRequest.getDodatocneInfo());

        liekRepository.save(liek);
    }
}
