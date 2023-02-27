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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TelaFXMLController implements Initializable {
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
        
        @FXML
        private Button insertButton;
        
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
                insertButton.setDisable(true);
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
		Alert a = new Alert(AlertType.ERROR);
                Alert s = new Alert(AlertType.INFORMATION);
		try {
                        String nometeste = txtNome.getText();
                        String idteste = txtId.getText();
                        if(nometeste.isEmpty() || idteste.isEmpty()){
                            a.setTitle("Erro");
                            a.setHeaderText("Digite os valores corretamente");
                            a.showAndWait();
                            return;
                        }
                        else{
                       
                        try{
                        Integer id = Integer.valueOf(idteste);
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO teste (id, nome) values (?, ?)");
			try{
                        ps.setInt(1, id);
			ps.setString(2, txtNome.getText());
			ps.execute();
                        s.setTitle("OK");
                        s.setHeaderText("Usuario cadastrado com sucesso");
                        s.showAndWait();
                        }
                        catch(Exception e){
                            a.setTitle("Erro");
                            a.setHeaderText("Id em uso, insira outro !");
                            a.showAndWait();
                            
                        }
                        }
                        catch(NumberFormatException e){
                            a.setTitle("Erro");
                            a.setHeaderText("Id não é inteiro");
                            a.showAndWait();
                        }
                        }
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
        
        @FXML
        private void onMouseClick(MouseEvent event) {
        Aluno aluno = tabela.getSelectionModel().getSelectedItem();
        txtId.setText(aluno.getId().toString());
	txtNome.setText(aluno.getNome());
        System.out.println("id " + aluno.getId() );
        System.out.println("nome " + aluno.getNome());
    }
        
        @FXML
        public void desabilitarBotao(){
            boolean botao = false;
            
            botao = (txtId.getText().isEmpty() | txtNome.getText().isEmpty());
            insertButton.setDisable(botao);
            System.out.println("palmeiras");
        }
	
	

        }
