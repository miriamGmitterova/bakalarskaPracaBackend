package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.TerminVysetrenia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TerminVysetreniaRepository extends JpaRepository<TerminVysetrenia, UUID> {
}
