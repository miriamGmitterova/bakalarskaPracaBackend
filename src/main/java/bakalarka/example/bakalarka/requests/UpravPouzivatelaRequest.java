package bakalarka.example.bakalarka.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpravPouzivatelaRequest {
    private String user_name;
    private String telCislo;
    private String ulica;
    private String popisneCislo;
    private String mesto;
}
