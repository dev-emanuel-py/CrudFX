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
        private Button deleteButton;
        
        @FXML
        private Button updateButton;
        
        @FXML
        private TableColumn<Aluno, Integer> ColTableId;

        @FXML
        private TableColumn<Aluno, String> ColTableNome;

        @FXML
        private TableView<Aluno> tableAluno;
        
        ObservableList<Aluno> list = FXCollections.observableArrayList();
	
        @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
                
		ColTableNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		ColTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
                insertButton.setDisable(true);
                deleteButton.setDisable(true);
                updateButton.setDisable(true);
                iniciarTableView();
                tableAluno.setItems(list);
		
	}
	
	
	@FXML	public void actionSQLInsert(ActionEvent event) {
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
		iniciarTableView();
		
	}
	
	@FXML
	public void actionSQLUpdate(ActionEvent event) {
            Alert a = new Alert(AlertType.ERROR);
            Alert s = new Alert(AlertType.INFORMATION);
		try {
                    try{
                        Integer id = Integer.valueOf(txtId.getText());
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("UPDATE teste SET nome = ? WHERE id = ?");                      
                            ps.setString(1, txtNome.getText());
                            ps.setInt(2, id);	
                            ps.execute();
                        s.setTitle("OK");
                        s.setHeaderText("Usuario atualizado com sucesso");
                        s.showAndWait();
                    }catch(NumberFormatException e){
                            a.setTitle("Erro");
                            a.setHeaderText("Id não é inteiro");
                            a.showAndWait();
                        }
		}catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
                iniciarTableView();
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
                iniciarTableView();
	}
	
	public void iniciarTableView(){
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
        private void moverDados(MouseEvent event) {
        Aluno a = tableAluno.getSelectionModel().getSelectedItem();
        txtId.setText(a.getId().toString());
	txtNome.setText(a.getNome());
        System.out.println("id" + a.getId() );
        System.out.println("nome" + a.getNome());
        deleteButton.setDisable(false);
    }
        
        @FXML
        public void desabilitarBotao(){
            boolean botaoInsert;
            boolean botaoDelete;
            boolean botaoUpdate;
            Integer id = Integer.valueOf(txtId.getText());
            botaoInsert = (txtId.getText().isEmpty() | txtNome.getText().isEmpty());
            insertButton.setDisable(botaoInsert);
            for(Aluno a: list){
                   if(a.getId().equals(id)){
                       insertButton.setDisable(true);
                   }
            }
            
            botaoUpdate = (txtId.getText().isEmpty() | txtNome.getText().isEmpty());
            updateButton.setDisable(botaoUpdate);
            
            botaoDelete = (txtId.getText().isEmpty());
            deleteButton.setDisable(botaoDelete);
            
        }
	
	

        }
