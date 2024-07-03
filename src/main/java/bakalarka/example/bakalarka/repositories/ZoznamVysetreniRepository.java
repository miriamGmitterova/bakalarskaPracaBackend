package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.ZoznamVysetreni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZoznamVysetreniRepository extends JpaRepository<ZoznamVysetreni, UUID>{
}
