package biblioteca.biblioteca.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LivroResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("ano")
    private int ano;

    @JsonProperty("edicao")
    private int edicao;

    @JsonProperty("autor")
    private AutorResponseDto autor;
}