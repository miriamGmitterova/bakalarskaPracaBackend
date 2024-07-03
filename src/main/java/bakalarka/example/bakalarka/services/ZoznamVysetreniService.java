package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Hodnotenie;
import bakalarka.example.bakalarka.entity.ZoznamVysetreni;
import bakalarka.example.bakalarka.repositories.ZoznamVysetreniRepository;
import bakalarka.example.bakalarka.requests.UlozHodnotenieRequest;
import bakalarka.example.bakalarka.requests.UlozVysetrenieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoznamVysetreniService {

    private final ZoznamVysetreniRepository zoznamVysetreniRepository;

    public void uloz(UlozVysetrenieRequest ulozVysetrenieRequest) {

        ZoznamVysetreni zoznamVysetreni = new ZoznamVysetreni();
        zoznamVysetreni.setDetailny_opis(ulozVysetrenieRequest.getDetailnyOpis());
        zoznamVysetreni.setDoba_trvania(ulozVysetrenieRequest.getDobaTrvania());
        zoznamVysetreni.setNazovVysetrenia(ulozVysetrenieRequest.getNazov());

        zoznamVysetreniRepository.save(zoznamVysetreni);
    }

    public void vymaz(UUID id) {

        if(!zoznamVysetreniRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }
        zoznamVysetreniRepository.deleteById(id);
    }

    public ZoznamVysetreni get(UUID id) {
        if(!zoznamVysetreniRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }
        return zoznamVysetreniRepository.findById(id).get();
    }

    public List<ZoznamVysetreni> getZoznam(){
        return zoznamVysetreniRepository.findAll();
    }

    public void uprav(UlozVysetrenieRequest request, UUID id){
        if(!zoznamVysetreniRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }
        ZoznamVysetreni zoznamVysetreni = zoznamVysetreniRepository.findById(id).get();

        zoznamVysetreni.setDetailny_opis(request.getDetailnyOpis());
        zoznamVysetreni.setDoba_trvania(request.getDobaTrvania());
        zoznamVysetreni.setNazovVysetrenia(request.getNazov());

        zoznamVysetreniRepository.save(zoznamVysetreni);
    }
}
