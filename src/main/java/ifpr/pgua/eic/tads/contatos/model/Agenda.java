package ifpr.pgua.eic.tads.contatos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.tads.contatos.model.daos.ContatoDAO;

public class Agenda {
    private ArrayList<Contato> lista;

    private ArrayList<Tarefa> tarefas;

    private FabricaConexoes fabricaConexao;
    private ContatoDAO contatoDao;

    public Agenda(FabricaConexoes fabricaConexao, ContatoDAO contatoDao) {
        lista = new ArrayList<>();
        tarefas = new ArrayList<>();
        this.fabricaConexao = fabricaConexao;
        this.contatoDao = contatoDao;
    }

    public ArrayList<Tarefa> getTarefas() {
        tarefas.clear();
        try {
            Connection con = fabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM oo_tarefas");

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");

                Tarefa tarefa = new Tarefa(id, titulo, descricao);

                tarefas.add(tarefa);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Problema ao fazer seleção!! " + e.getMessage());
        }
        return tarefas;
    }

    public Resultado<List<Contato>> getLista() {
        return contatoDao.listar(); 
    }

    public String cadastrarTarefa(String titulo, String descricao) {

        if (titulo.isBlank() || titulo.isEmpty()) {
            return "Nome inválido!";
        }

        if (descricao.isBlank() || descricao.isEmpty()) {
            return "Telefone inválido!";
        }

        Tarefa tarefa = new Tarefa(titulo, descricao);

        try {
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("INSERT INTO oo_tarefas(titulo,descricao) VALUES (?,?)");

            pstm.setString(1, tarefa.getTitulo());
            pstm.setString(2, tarefa.getDescricao());
            
            pstm.executeUpdate();

            tarefas.add(tarefa);
            con.close();
            return "Tarefa cadastrada!";
        } catch (SQLException e) {
            return "Problema ao conectar " + e.getMessage();
        }
    }

    public String cadastrar(String nome, String telefone, String email) {

        if (nome.isBlank() || nome.isEmpty()) {
            return "Nome inválido!";
        }

        if (telefone.isBlank() || telefone.isEmpty()) {
            return "Telefone inválido!";
        }

        if (email.isBlank() || email.isEmpty()) {
            return "E-mail inválido!";
        }

        if (buscar(nome) == null) {

            Contato contato = new Contato(nome, telefone, email);

            Resultado<Contato> resultado = contatoDao.criar(contato);   

            return resultado.getMsg();

        } else {
            return "Erro! Dados já cadastrados!";
        }

    }

    public Contato buscar(String nome) {

        for (Contato c : lista) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return null;
    }

    public Contato buscarNomeMaisComprido() {

        Contato maiorNome = lista.get(0);

        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i).getNome().length() > maiorNome.getNome().length()) {
                maiorNome = lista.get(i);
            }
        }
        return maiorNome;
    }

    public String listar() {
        String texto = "Contatos Cadastrados:";

        for (Contato c : lista) {
            texto += c.toString() + "<br/>";
        }

        return texto;
    }
}
