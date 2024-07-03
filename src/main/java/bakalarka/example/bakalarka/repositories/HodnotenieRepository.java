package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Hodnotenie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HodnotenieRepository extends JpaRepository<Hodnotenie, UUID> {
    List<Hodnotenie> findAllByOrderByVytvoreneDesc();
}

