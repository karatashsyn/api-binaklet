package dto.requests.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateItemRequest {

        @NotBlank(message = "Ürün ismi boş olamaz.")
        @Size(min = 2, message = "Ürün ismi en az 2 karakter uzunluğunda olmalı.")
        private String name;

        @NotBlank(message = "Marka bilgisi boş olamaz.")
        private String brand;

        @NotBlank(message = "Açıklama boş olamaz.")
        private String description;

        @NotBlank(message = "Kategori ID boş olamaz.")
        private Long categoryId;

        @NotNull(message = "Yükseklik boş olamaz.")
        private Float height;

        @NotNull(message = "Genişlik boş olamaz.")
        private Float width;

        @NotNull(message = "Derinlik boş olamaz.")
        private Float depth;

        @NotNull(message = "Ağırlık boş olamaz.")
        private Float mass;

        //TODO: Validate that.
        private MultipartFile[] images;

        @NotNull(message = "Fiyat boş olamaz.")
        private Float price;
}