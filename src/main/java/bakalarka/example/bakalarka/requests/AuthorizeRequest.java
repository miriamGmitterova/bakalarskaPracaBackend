package bakalarka.example.bakalarka.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizeRequest {
    private String user_name;
    private String password;
}
