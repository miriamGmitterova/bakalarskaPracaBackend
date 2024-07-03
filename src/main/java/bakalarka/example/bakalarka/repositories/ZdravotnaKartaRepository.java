package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.ZdravotnaKarta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZdravotnaKartaRepository extends JpaRepository<ZdravotnaKarta, UUID> {
}
