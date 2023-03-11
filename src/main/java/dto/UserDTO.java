
package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String Email;
    private String FirstName;
    private Long Id;
    private String LastName;
    private String Password;
    private String Phone;
    private Long UserStatus;
    private String Username;

}
