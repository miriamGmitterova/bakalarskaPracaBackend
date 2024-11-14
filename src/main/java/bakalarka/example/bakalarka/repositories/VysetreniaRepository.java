package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.Vysetrenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VysetreniaRepository extends JpaRepository<Vysetrenia, UUID>{

 List<Vysetrenia> findByLekar(Lekar lekar);
 List<Vysetrenia> findByPouzivatel(Pouzivatel pouzivatel);


 @Query("SELECT v FROM Vysetrenia v WHERE v.datum_vysetrenia < :dateTime")
 List<Vysetrenia> findByDatum_vysetreniaBefore(@Param("dateTime") LocalDateTime dateTime);
}
