package dto.responses.address;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(staticName = "build")
@AllArgsConstructor
@Data
@Builder
public class AddressDetailDTO {

    Long id;


    String addressText;

    Boolean isUserDefault;

    String addressTitle;


    String contactPhone;

}
