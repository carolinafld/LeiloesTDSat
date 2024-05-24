/**
 *
 * @author Carolina
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");
            
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            
            // Mensagem de sucesso
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
        }
        
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new conectaDAO().connectDB();
        
        String sql = "SELECT * FROM produtos";
        
        try {
            prep = this.conn.prepareStatement(sql);
            
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
            
            return listagem;
        } catch (Exception e) {
            System.out.println("Erro ao buscar lista de produtos: " + e.getMessage());
            return null;
        }
    }
    
    public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();
        
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        
        try {
            prep = conn.prepareStatement(sql);
            
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            
            prep.execute();
        } catch (Exception e) {
            System.out.println("Erro ao atualizar status do produto" + e.getMessage());
        }
    }
    
    
    
        
}

