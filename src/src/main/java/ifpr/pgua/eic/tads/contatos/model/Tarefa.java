package ifpr.pgua.eic.tads.contatos.model;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private Categoria categoria;
    
    public Tarefa(String titulo, String descricao, Categoria categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
    }
    
    public Tarefa(int id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return "Tarefa [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + "]";
    }
    
    
    
}
