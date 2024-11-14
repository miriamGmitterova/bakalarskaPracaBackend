package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.UlozLekaraRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LekarService {

    private final LekarRepository lekarRepository;
    private final PouzivatelRepository pouzivatelRepository;

    public void ulozLekara(UlozLekaraRequest ulozLekaraRequest){

        if (!pouzivatelRepository.existsById(ulozLekaraRequest.getPouzivatel())) {
            throw new NoSuchElementException("Chyba: pouzivatel s danym ID neexistuje");
        }

        Lekar lekar = new Lekar();

        lekar.setPouzivatel(pouzivatelRepository.findById(ulozLekaraRequest.getPouzivatel()).get());

        lekar.setSpecializacia(ulozLekaraRequest.getSpecializacia());

        lekar.setTypLekara(ulozLekaraRequest.getTypLekara());

        lekarRepository.save(lekar);
    }

    public Lekar getLekar(UUID id) {

        if(!lekarRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Lekar s danym ID neexistuje");
        }
        return lekarRepository.findById(id).get();
    }

    public List<Lekar> getZoznam() {
        return lekarRepository.findAll();
    }

    public void vymazLekara(UUID id) {
        if(!lekarRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Lekar s danym ID neexistuje");
        }
        lekarRepository.deleteById(id);
    }

    public void upravLekara(UUID id, UlozLekaraRequest ulozLekaraRequest){
        if(!lekarRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Lekar s danym ID neexistuje");
        }
        Lekar lekar = lekarRepository.findById(id).get();

        lekar.setSpecializacia(ulozLekaraRequest.getSpecializacia());

        lekar.setTypLekara(ulozLekaraRequest.getTypLekara());


        lekarRepository.save(lekar);

    }
}
