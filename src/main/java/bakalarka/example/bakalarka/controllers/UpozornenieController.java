package bakalarka.example.bakalarka.controllers;

import bakalarka.example.bakalarka.entity.Upozornenie;
import bakalarka.example.bakalarka.services.UpozornenieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class UpozornenieController {

    @Autowired
    private UpozornenieService upozornenieService;

    @PostMapping
    public ResponseEntity<Upozornenie> createNotification(@RequestBody Upozornenie notification) {
        Upozornenie createdNotification = upozornenieService.createNotification(notification);
        return ResponseEntity.ok(createdNotification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Upozornenie> getNotificationById(@PathVariable UUID id) {
        return upozornenieService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Upozornenie>> getNotificationsByUserId(@PathVariable UUID userId) {
        List<Upozornenie> notifications = upozornenieService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Upozornenie> markAsRead(@PathVariable UUID id) {
        Upozornenie updatedNotification = upozornenieService.markAsRead(id);
        if (updatedNotification != null) {
            return ResponseEntity.ok(updatedNotification);
        }
        return ResponseEntity.notFound().build();
    }
}
