package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Liek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LiekRepository extends JpaRepository<Liek, UUID> {
}
