package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.AutorResponseDto;
import biblioteca.biblioteca.Dto.LivroPostPutDto;
import biblioteca.biblioteca.Dto.LivroResponseDto;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.AutorRepository;
import biblioteca.biblioteca.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroCrudService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public List<LivroResponseDto> buscarTodosLivros(String busca) {
        List<Livro> livros;
        if (busca != null && !busca.isEmpty()) {
            livros = livroRepository.findByTituloContainingIgnoreCaseOrIsbnContainingIgnoreCase(busca, busca);
        } else {
            livros = livroRepository.findAll();
        }
        return livros.stream().map(this::toDto).collect(Collectors.toList());
    }

    public LivroResponseDto buscarLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
        return toDto(livro);
    }

    public LivroResponseDto criarLivro(LivroPostPutDto dto) {
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));

        Livro livro = Livro.builder()
                .isbn(dto.getIsbn())
                .titulo(dto.getTitulo())
                .ano(dto.getAno())
                .edicao(dto.getEdicao())
                .autor(autor)
                .build();

        Livro salvo = livroRepository.save(livro);
        return toDto(salvo);
    }

    public LivroResponseDto atualizarLivro(Long id, LivroPostPutDto dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));

        livro.setIsbn(dto.getIsbn());
        livro.setTitulo(dto.getTitulo());
        livro.setAno(dto.getAno());
        livro.setEdicao(dto.getEdicao());
        livro.setAutor(autor);

        Livro atualizado = livroRepository.save(livro);
        return toDto(atualizado);
    }

    public void removerLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com ID: " + id);
        }
        livroRepository.deleteById(id);
    }

    private LivroResponseDto toDto(Livro livro) {
        AutorResponseDto autorDto = null;
        if (livro.getAutor() != null) {
            autorDto = AutorResponseDto.builder()
                    .id(livro.getAutor().getId())
                    .nomeAutor(livro.getAutor().getNomeAutor())
                    .nacionalidade(livro.getAutor().getNacionalidade())
                    .build();
        }

        return LivroResponseDto.builder()
                .id(livro.getId())
                .isbn(livro.getIsbn())
                .titulo(livro.getTitulo())
                .ano(livro.getAno())
                .edicao(livro.getEdicao())
                .autor(autorDto)
                .build();
    }
}