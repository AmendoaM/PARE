
package dao;

import factory.ConnectionFactory;
import modelo.Aluno;
import modelo.Sala;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private Connection connection;
    private Aluno aluno;
    
    
    
    public AlunoDAO(){
    this.connection = new ConnectionFactory().getConexaoMySQL();
    
    }
    
    public void cadastrar(Aluno aluno){
        String sql = "INSERT INTO aluno(aluno_nome,sala_id) VALUES(?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,aluno.getAlunoNome());
            stmt.setInt(2, aluno.getAlunoSalaId());
            stmt.execute();
            // Pegar chave primaria gerada pelo banco de dados mysql
            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()){
                int id = keys.getInt(1);
                aluno.setAlunoId(id);
            }
            stmt.close();
        }
        catch(SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public String getAlunoNome(int id)
    {
         String sql = "select aluno_nome from api.aluno where aluno_id = ?";
         String nome = "";
         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nome = rs.getString("aluno_nome");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nome;
    }
    
}
    
    

  
