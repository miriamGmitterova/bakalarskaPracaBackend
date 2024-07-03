package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Lekar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LekarRepository extends JpaRepository<Lekar, UUID> {
}
