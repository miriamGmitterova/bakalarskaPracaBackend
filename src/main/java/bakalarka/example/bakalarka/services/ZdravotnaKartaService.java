package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.*;
import bakalarka.example.bakalarka.entity.enums.TypLekara;
import bakalarka.example.bakalarka.repositories.LekarRepository;

import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.repositories.ZdravotnaKartaRepository;
import bakalarka.example.bakalarka.requests.UlozKartuRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.NoSuchElementException;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class ZdravotnaKartaService {

    @Autowired
    private final ZdravotnaKartaRepository zdravotnaKartaRepository;

    private final LekarRepository lekarRepository;
    private final PouzivatelRepository pouzivatelRepository;
    private final UpozornenieService upozornenieService;


    public void uloz(@Valid UlozKartuRequest request) {


        Lekar lekar = lekarRepository.findById(request.getId_lekara())
                .orElseThrow(() -> new RuntimeException("Lekar not found"));
        Pouzivatel pouzivatel = pouzivatelRepository.findById(request.getId_pouzivatela())
                .orElseThrow(() -> new RuntimeException("Pouzivatel not found"));


        // Check if the doctor is a general practitioner
        if (TypLekara.VSEOBECNY_LEKAR.equals(lekar.getTypLekara())) {
            // Check if the user already has a general practitioner
            List<ZdravotnaKarta> existingCards = zdravotnaKartaRepository.findByPouzivatel(pouzivatel);
            boolean hasGeneralDoctor = existingCards.stream()
                    .map(ZdravotnaKarta::getLekar)
                    .map(Lekar::getTypLekara)
                    .anyMatch(type -> TypLekara.VSEOBECNY_LEKAR.equals(type));

            if (hasGeneralDoctor) {
                throw new IllegalStateException("User already has a general practitioner.");
            }
        }

        if (zdravotnaKartaRepository.existsByLekarAndPouzivatel(lekar, pouzivatel)) {
            throw new IllegalStateException("User is already assigned to this doctor.");
        }

        ZdravotnaKarta priradenie = new ZdravotnaKarta();

        priradenie.setPouzivatel(pouzivatelRepository.findById(request.getId_pouzivatela()).get());
        priradenie.setLekar(lekarRepository.findById(request.getId_lekara()).get());
        priradenie.setZalozenie(request.getZalozenie());

        zdravotnaKartaRepository.save(priradenie);

        Lekar doctor = priradenie.getLekar();

        Pouzivatel doctorUser = doctor.getPouzivatel();

        String message = "Pacient " + pouzivatel.getMeno() + " " + pouzivatel.getPriezvisko() + " si založil u vás kartu.";
        Upozornenie upozornenie = new Upozornenie();
        upozornenie.setPouzivatel(doctorUser);
        upozornenie.setSprava(message);
        upozornenieService.createNotification(upozornenie);


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

        priradenie.setPouzivatel(pouzivatelRepository.findById(request.getId_pouzivatela()).get());
        priradenie.setLekar(lekarRepository.findById(request.getId_lekara()).get());
        priradenie.setZalozenie(request.getZalozenie());

        zdravotnaKartaRepository.save(priradenie);

    }

    public List<Lekar> getDoctorsForPatient(UUID patientId) {

        Pouzivatel pouzivatel = pouzivatelRepository.findById(patientId).get();
        List<ZdravotnaKarta> cards = zdravotnaKartaRepository.findByPouzivatel(pouzivatel);
        return cards.stream()
                .map(ZdravotnaKarta::getLekar)
                .distinct()
                .collect(Collectors.toList());


    }

    public List<Pouzivatel> getPatientsForDoctor(UUID doctorId) {


        Lekar lekar = lekarRepository.findById(doctorId).get(); // Retrieve Lekar by ID
        List<ZdravotnaKarta> cards = zdravotnaKartaRepository.findByLekar(lekar);
        return cards.stream()
                .map(ZdravotnaKarta::getPouzivatel)
                .distinct()
                .collect(Collectors.toList());

    }

    public void deleteCard(UUID userId, UUID doctorId) {
        Pouzivatel user = pouzivatelRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Lekar doctor = lekarRepository.findById(doctorId)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found"));

        zdravotnaKartaRepository.deleteByUserIdAndDoctorId(userId, doctorId);

        // Send notification to the user
        String message = "Vaša karta u doktora " + doctor.getPouzivatel().getMeno() + " " + doctor.getPouzivatel().getPriezvisko() + " bola odstránená.";
        Upozornenie upozornenie = new Upozornenie();
        upozornenie.setPouzivatel(user);
        upozornenie.setSprava(message);
        upozornenieService.createNotification(upozornenie);

    }

}
