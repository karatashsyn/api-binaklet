package com.binaklet.binaklet.dto.responses.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
public class AddressDetailDTO {

    Long id;

    String addressText;

    Boolean isUserDefault;

    String addressTitle;

    String contactPhone;

}
