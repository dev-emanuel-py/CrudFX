package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaFXMLController implements Initializable {
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
        
        @FXML
        private TableColumn<Aluno, Integer> idCol;

        @FXML
        private TableColumn<Aluno, String> nomeCol;

        @FXML
        private TableView<Aluno> tabela;
        
        ObservableList<Aluno> list = FXCollections.observableArrayList();
	
        @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
            
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                iniciarTable();
                tabela.setItems(list);
		
	}
	
	
	@FXML
	public void actionSQLSelect (ActionEvent event) {
		try {
		DBUtil db = DBUtil.getInstance();
		PreparedStatement ps = db.getConnection().prepareStatement("SELECT * from teste");
		ResultSet rs = ps.executeQuery();
		System.out.println("");
		System.out.println("Lista :");
		System.out.println("");  
		while(rs.next()) {
			System.out.println(rs.getInt("id") + "-" + rs.getString("nome"));
		}
	}
		catch(Exception e) {
			System.out.println("Erro: " + e.toString());
		}
                iniciarTable();
		
	}
	
	@FXML
	public void actionSQLInsert(ActionEvent event) {
		
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO teste (id, nome) values (?, ?)");
			ps.setInt(1, Integer.parseInt(txtId.getText()));
			ps.setString(2, txtNome.getText());
			ps.execute();
		
		}
		catch(Exception e){
			System.out.println("Erro: " + e.toString());
		}
		iniciarTable();
		
	}
	
	@FXML
	public void actionSQLUpdate(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("UPDATE teste SET nome = ? WHERE id = ?");
			ps.setString(1, txtNome.getText());
			ps.setInt(2, Integer.parseInt(txtId.getText()));	
			ps.execute();
			
		}catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
                iniciarTable();
	}
        
        @FXML
	public void actionSQLDelete(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM teste WHERE id = ?");
			ps.setInt(1, Integer.parseInt(txtId.getText()));	
			ps.execute();
			
		}catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
                iniciarTable();
	}
	
	public void iniciarTable(){
            list.clear();
            try {
                DBUtil db = DBUtil.getInstance();
		PreparedStatement ps;
                ps = db.getConnection().prepareStatement("SELECT * from teste");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    list.add(
                            new Aluno(rs.getInt("id"), rs.getString("nome")
                            ));
                }
            } catch (SQLException ex) {
                Logger.getLogger(TelaFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
		
        }
	
	

        }
