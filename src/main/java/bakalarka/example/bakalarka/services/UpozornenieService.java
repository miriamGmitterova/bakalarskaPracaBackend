package bakalarka.example.bakalarka.services;


import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.Upozornenie;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.repositories.UpozornenieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UpozornenieService {

    private final PouzivatelRepository pouzivatelRepository;
    private static final Logger logger = LoggerFactory.getLogger(UpozornenieService.class);


    @Autowired
    private UpozornenieRepository upozornenieRepository;

    public Upozornenie createNotification(Upozornenie notification) {
        logger.info("Saving notification for user: {}", notification.getPouzivatel().getId_pouzivatela());

        return upozornenieRepository.save(notification);
    }

    public Optional<Upozornenie> getNotificationById(UUID id) {
        return upozornenieRepository.findById(id);
    }

    public List<Upozornenie> getNotificationsByUserId(UUID userId) {
        Pouzivatel pouzivatel = pouzivatelRepository.findById(userId).get();
        return upozornenieRepository.findByPouzivatel(pouzivatel);
    }

    public Upozornenie markAsRead(UUID id) {
        Optional<Upozornenie> notification = upozornenieRepository.findById(id);
        if (notification.isPresent()) {
            Upozornenie notif = notification.get();
            notif.setPrecitane(true);
            return upozornenieRepository.save(notif);
        }
        return null;
    }
}
