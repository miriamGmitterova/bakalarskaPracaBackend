package bakalarka.example.bakalarka.services;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.Upozornenie;
import bakalarka.example.bakalarka.entity.Vysetrenia;
import bakalarka.example.bakalarka.entity.enums.TypVysetrenia;
import bakalarka.example.bakalarka.repositories.LekarRepository;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;

import bakalarka.example.bakalarka.repositories.VysetreniaRepository;
import bakalarka.example.bakalarka.requests.UlozVysetrenieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class VysetreniaService {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d. M. yyyy H:mm");

    private final VysetreniaRepository vysetreniaRepository;
    private final PouzivatelRepository pouzivatelRepository;
    private final LekarRepository lekarRepository;

    private final UpozornenieService upozornenieService;

    private static final Logger logger = LoggerFactory.getLogger(VysetreniaService.class);


    public void uloz(UlozVysetrenieRequest ulozVysetrenieRequest) {


        if (ulozVysetrenieRequest.getDatum_vysetrenia().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Dátum je v minulosti");
        }

        Vysetrenia vysetrenia = new Vysetrenia();
        vysetrenia.setNazovVysetrenia(ulozVysetrenieRequest.getNazovVysetrenia());
        vysetrenia.setTypVysetrenia(ulozVysetrenieRequest.getTyp_vysetrenia());
        vysetrenia.setDatum_vysetrenia(ulozVysetrenieRequest.getDatum_vysetrenia());
//        vysetrenia.setPouzivatel(pouzivatelRepository.findById(ulozVysetrenieRequest.getId_pouzivatela()).get());
        vysetrenia.setLekar(lekarRepository.findById(ulozVysetrenieRequest.getId_lekara()).get());


        vysetreniaRepository.save(vysetrenia);
    }

    public void vymaz(UUID id) {

        if (!vysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }

        Vysetrenia vysetrenia = vysetreniaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Examination not found"));

        Pouzivatel pouzivatel = vysetrenia.getPouzivatel();
        if (pouzivatel != null) {
            logger.info("Creating notification for user: {}", pouzivatel.getId_pouzivatela());


            Upozornenie upozornenie = new Upozornenie();
            upozornenie.setPouzivatel(pouzivatel);
            upozornenie.setSprava("Vaše vyšetrenie " + vysetrenia.getNazovVysetrenia() + " na " +
                    vysetrenia.getDatum_vysetrenia().format(DATE_FORMATTER) + " bolo zrušené.");
            upozornenieService.createNotification(upozornenie);
        } else {
            logger.info("No user assigned to this examination.");
            vysetreniaRepository.deleteById(id);
        }

        vysetrenia.setPouzivatel(null);  // Set the user to null to cancel the appointment
        vysetreniaRepository.save(vysetrenia);
        vysetreniaRepository.deleteById(id);

    }
//        vysetreniaRepository.deleteById(id);


    public Vysetrenia get(UUID id) {
        if (!vysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }
        return vysetreniaRepository.findById(id).get();
    }

    public List<Vysetrenia> getZoznam() {
        return vysetreniaRepository.findAll();
    }

    public void uprav(UlozVysetrenieRequest request, UUID id) {
        if (!vysetreniaRepository.existsById(id)) {
            throw new NoSuchElementException("Chyba: Zoznam s danym ID neexistuje");
        }
        Vysetrenia vysetrenia = vysetreniaRepository.findById(id).get();

        vysetrenia.setNazovVysetrenia(request.getNazovVysetrenia());
        vysetrenia.setTypVysetrenia(request.getTyp_vysetrenia());
        vysetrenia.setDatum_vysetrenia(request.getDatum_vysetrenia());

        vysetreniaRepository.save(vysetrenia);
    }

    public List<Vysetrenia> getVysetreniaByDoctor(UUID doctorId) {
        Lekar lekar = lekarRepository.findById(doctorId).get();
        return vysetreniaRepository.findByLekar(lekar);
    }

    public void signUpForExamination(UUID vysetreniaId, UUID patientId) {
        Vysetrenia vysetrenia = vysetreniaRepository.findById(vysetreniaId)
                .orElseThrow(() -> new NoSuchElementException("Examination not found"));

        Pouzivatel patient = pouzivatelRepository.findById(patientId)
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));

        vysetrenia.setPouzivatel(patient);  // Assuming a patient can only have one examination
        vysetreniaRepository.save(vysetrenia);

        // Notify the doctor
        Lekar doctor = vysetrenia.getLekar();
        if (doctor != null) {
            Pouzivatel doctorUser = doctor.getPouzivatel();
            if (doctorUser != null) {
                Upozornenie doctorNotification = new Upozornenie();
                doctorNotification.setPouzivatel(doctorUser);
                doctorNotification.setSprava("Pacient: " + patient.getMeno() + " sa prihlásil na vyšetrenie " +
                        vysetrenia.getNazovVysetrenia() + " na " +
                        vysetrenia.getDatum_vysetrenia().format(DATE_FORMATTER) + ".");
                upozornenieService.createNotification(doctorNotification);
            }
        }
    }

    public List<Vysetrenia> getVysetreniaByPacient(UUID pouzivatelId) {
        Pouzivatel pouzivatel = pouzivatelRepository.findById(pouzivatelId).get();
        return vysetreniaRepository.findByPouzivatel(pouzivatel);
    }

    public void cancelAppointment(UUID id) {
        logger.info("Attempting to cancel appointment with ID: {}", id);

        Vysetrenia vysetrenia = vysetreniaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Examination not found"));


        Lekar doctor = vysetrenia.getLekar();



        // Notify the doctor
        if (doctor != null) {
            Pouzivatel doctorUser = doctor.getPouzivatel(); // Get the doctor user entity
            logger.info("Creating notification for doctor: {}", doctorUser.getId_pouzivatela());

            Upozornenie doctorNotification = new Upozornenie();
            doctorNotification.setPouzivatel(doctorUser);
            doctorNotification.setSprava("Vyšetrenie " + vysetrenia.getNazovVysetrenia() + " na " +
                    vysetrenia.getDatum_vysetrenia().format(DATE_FORMATTER) + " bolo zrušené pacientom.");
            upozornenieService.createNotification(doctorNotification);
        } else {
            logger.info("No doctor assigned to this examination.");
        }

        vysetrenia.setPouzivatel(null);  // Set the user to null to cancel the appointment
        vysetreniaRepository.save(vysetrenia);
        vysetreniaRepository.save(vysetrenia);
    }

    @Scheduled(cron = "0 * * * * ?") //every minute
    //@Scheduled(cron = "0 0 0 * * ?") // Every day at midnight
    public void notifyUsersForPreventiveExaminations() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minus(Period.ofYears(1));
        //LocalDateTime oneYearAgo = LocalDateTime.now().minus(Period.ofDays(1));

        List<Vysetrenia> examinations = vysetreniaRepository.findByDatum_vysetreniaBefore(oneYearAgo);

        for (Vysetrenia examination : examinations) {
            if (examination.getTypVysetrenia() == TypVysetrenia.PREVENTIVNA_PREHLIADKA) {
                Pouzivatel user = examination.getPouzivatel();
                if (user != null) {
                    Lekar doctor = examination.getLekar();
                    if (doctor != null) {
                        Upozornenie notification = new Upozornenie();
                        notification.setPouzivatel(user);
                        notification.setSprava(String.format(
                                "Je to už rok, čo ste mali poslednú preventívnu prehliadku u doktora %s. Je čas objednať sa na novú prehliadku!",
                                doctor.getPouzivatel().getMeno() + " " + doctor.getPouzivatel().getPriezvisko() // Adjust according to your `Lekar` entity
                        ));
                        upozornenieService.createNotification(notification);
                    }
                }
            }
        }
    }
    // Manual trigger method
    public void triggerNotificationForTesting() {
        notifyUsersForPreventiveExaminations();
    }


    }

