package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Pouzivatel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PouzivatelRepository extends JpaRepository<Pouzivatel, UUID> {

}
