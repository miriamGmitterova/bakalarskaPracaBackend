package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.Upozornenie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface UpozornenieRepository extends JpaRepository<Upozornenie, UUID> {
    List<Upozornenie> findByPouzivatel(Pouzivatel pouzivatel);
}
