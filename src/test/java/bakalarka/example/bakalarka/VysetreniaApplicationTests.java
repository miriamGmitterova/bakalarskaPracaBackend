package bakalarka.example.bakalarka;

import bakalarka.example.bakalarka.entity.Pouzivatel;
import bakalarka.example.bakalarka.repositories.PouzivatelRepository;
import bakalarka.example.bakalarka.requests.AuthorizeRequest;
import bakalarka.example.bakalarka.requests.UlozPouzivatelaRequest;
import bakalarka.example.bakalarka.services.PouzivatelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class VysetreniaApplicationTests {
	@Autowired
	PouzivatelService pouzivatelService;
//	@Autowired
//	PouzivatelRepository pouzivatelRepository;
	@Test
	void contextLoads() {
		List<Pouzivatel> pouzivatels = pouzivatelService.getZoznam();
		for (Pouzivatel pouzivatel : pouzivatels) {
			System.out.println(pouzivatel.getMeno());
		}
//		System.out.println(String.valueOf(pouzivatels));
//		AuthorizeRequest authorizeRequest = new AuthorizeRequest();
//
//		authorizeRequest.setUser_name("tester");
//		authorizeRequest.setPasswords("tester");
//		System.out.println(String.valueOf(pouzivatelService.authorise(authorizeRequest)));

	}

}
