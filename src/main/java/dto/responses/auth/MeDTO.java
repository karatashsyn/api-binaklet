package dto.responses.user;

import com.binaklet.binaklet.DTOs.CartDto;
import dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.enums.UserRole;
import dto.responses.address.AddressDetailDTO;
import dto.responses.item.ItemDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// Public user DTO that can be seen by another user.
@NoArgsConstructor(staticName = "build")
@AllArgsConstructor(staticName = "build")
@Data
public class MeDTO {

    Long id;

    UserRole role;

    String name;

    String email;

    String phoneNumber;

    List<AddressDetailDTO> addresses;

    CartDto cart;

    List<ItemDetailDTO>  items;

    Date createdDate;



}
