package dto.responses.user;

import dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.enums.UserRole;
import dto.responses.address.AddressDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@NoArgsConstructor(staticName = "build")
@AllArgsConstructor
@Data
@Builder
public class UserDetailDTO {

    Long id;

    UserRole role;

    String name;

    String email;

    AddressDetailDTO activeAddress;

    String phoneNumber;

    List<BasicItemDTO>  items;

    Date createdDate;



}
