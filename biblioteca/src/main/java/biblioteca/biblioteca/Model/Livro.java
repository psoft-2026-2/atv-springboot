package biblioteca.biblioteca.Model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@Table(name = "tb_livro")
@AllArgsConstructor
@NoArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ds_isbn", nullable = false, unique = true)
    @JsonProperty("isbn")
    private String isbn;

    @Column(name = "ds_titulo", nullable = false)
    @JsonProperty("titulo")
    private String titulo;

    @Column(name = "nr_ano", nullable = false)
    @JsonProperty("ano")
    private int ano;

    @Column(name = "ds_edicao", nullable = false)
    @JsonProperty("edicao")
    private int edicao;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @JsonProperty("autor")
    private Autor autor;
}