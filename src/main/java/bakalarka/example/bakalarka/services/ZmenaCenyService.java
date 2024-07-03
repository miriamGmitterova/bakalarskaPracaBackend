package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.ZdravotnaKarta;
import bakalarka.example.bakalarka.entity.ZmenaCeny;
import bakalarka.example.bakalarka.repositories.ZmenaCenyRepository;
import bakalarka.example.bakalarka.repositories.ZoznamVysetreniRepository;
import bakalarka.example.bakalarka.requests.UlozKartuRequest;
import bakalarka.example.bakalarka.requests.UlozZmenuCenyRequest;
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
public class ZmenaCenyService {

    private final ZmenaCenyRepository zmenaCenyRepository;
    private final ZoznamVysetreniRepository zoznamVysetreniRepository;

    public void uloz(@Valid UlozZmenuCenyRequest request) {

        if (request.getCenaDo().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dátum je v minulosti");
        }

        if (!zoznamVysetreniRepository.existsById(request.getVysetrenie())) {
            throw new NoSuchElementException("Chyba: Vysetrenie s danym ID neexistuje");
        }

        ZmenaCeny zmenaCeny = new ZmenaCeny();

        zmenaCeny.setCena(request.getCena());
        zmenaCeny.setZoznamVysetreni(zoznamVysetreniRepository.findById(request.getVysetrenie()).get());
        zmenaCeny.setCena_od(request.getCenaOd());
        zmenaCeny.setCena_do(request.getCenaDo());

        zmenaCenyRepository.save(zmenaCeny);
    }


    public List<ZmenaCeny> getZoznam() {
        return zmenaCenyRepository.findAll();
    }

    public void vymaz(UUID id) {

        if (!zmenaCenyRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zmena s danym ID neexistuje");
        }

        zmenaCenyRepository.deleteById(id);
    }

    public ZmenaCeny getZmena(UUID id) {
        if (!zmenaCenyRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zmena s danym ID neexistuje");
        }

        return zmenaCenyRepository.findById(id).get();
    }

    public void uprav(UUID id, UlozZmenuCenyRequest request) {
        if (!zmenaCenyRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zmena s danym ID neexistuje");
        }
        ZmenaCeny zmenaCeny = zmenaCenyRepository.findById(id).get();

        zmenaCeny.setCena(request.getCena());
        zmenaCeny.setZoznamVysetreni(zoznamVysetreniRepository.findById(request.getVysetrenie()).get());
        zmenaCeny.setCena_od(request.getCenaOd());
        zmenaCeny.setCena_do(request.getCenaDo());

        zmenaCenyRepository.save(zmenaCeny);

    }
}
