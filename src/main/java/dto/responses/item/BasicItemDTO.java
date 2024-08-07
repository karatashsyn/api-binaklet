package dto.responses.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(staticName = "build")
@AllArgsConstructor
@Data
@Builder
public class BasicItemDTO {
    String name;
    Float price;
    List<String> images;
}
