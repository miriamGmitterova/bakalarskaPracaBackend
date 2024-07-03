package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Predpis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PredpisRepository extends JpaRepository<Predpis, UUID> {
}
