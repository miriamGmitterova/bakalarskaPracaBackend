package bakalarka.example.bakalarka.repositories;

import bakalarka.example.bakalarka.entity.Lekar;
import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.entity.ZdravotnaKarta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ZdravotnaKartaRepository extends JpaRepository<ZdravotnaKarta, UUID> {
    boolean existsByLekarAndPouzivatel(Lekar lekar, Pouzivatel pouzivatel);
    List<ZdravotnaKarta> findByPouzivatel(Pouzivatel pouzivatel);
    List<ZdravotnaKarta> findByLekar(Lekar lekar);


    @Transactional
    @Modifying
    @Query("DELETE FROM ZdravotnaKarta z WHERE z.pouzivatel.id_pouzivatela = :userId AND z.lekar.id_lekara = :doctorId")
    void deleteByUserIdAndDoctorId(UUID userId, UUID doctorId);


}
