package com.binaklet.binaklet.dto.responses.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
public class BasicItemDTO {
    String name;
    Float price;
    String banner;
}
