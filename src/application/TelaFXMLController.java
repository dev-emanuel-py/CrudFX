package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
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
    
	ObservableList<Fighter> fighterList = FXCollections.observableArrayList();
        
        @FXML
        private Button buttonInsert;
        
        @FXML
        private Button buttonDelete;
        
        @FXML
        private Button buttonUpdate;
    
	@FXML
	private TextField matriculaTexto;
        
        @FXML
        private TableView<Fighter> fighterTable;
	
	@FXML
	private TextField nomeTexto;
        
        @FXML
        private TableColumn<Fighter, Integer> matriculaColuna;

        @FXML
        private TableColumn<Fighter, String> nomeColuna;

        
        
	
        @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
            
                buttonInsert.setDisable(true);
                buttonDelete.setDisable(true);
                buttonUpdate.setDisable(true);
		nomeColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
		matriculaColuna.setCellValueFactory(new PropertyValueFactory<>("matricula"));
                
                initializeTableFighter();
                fighterTable.setItems(fighterList);
		
	}
        
        @FXML
        private void moveTableToTxt(MouseEvent event) {
        Fighter a = fighterTable.getSelectionModel().getSelectedItem();
        matriculaTexto.setText(a.getMatricula().toString());
	nomeTexto.setText(a.getNome());
        buttonDelete.setDisable(false);
    }
        
        @FXML
        public void ButtonDisable(){
            boolean verifyInsert;
            boolean verifyDelete;
            boolean verifyUpdate;
            
            verifyInsert = (matriculaTexto.getText().isEmpty() |
                    nomeTexto.getText().isEmpty());
            buttonInsert.setDisable(verifyInsert);
            
            verifyUpdate = (matriculaTexto.getText().isEmpty() | 
                    nomeTexto.getText().isEmpty());
            buttonUpdate.setDisable(verifyUpdate);
            
            verifyDelete = (matriculaTexto.getText().isEmpty());
            buttonDelete.setDisable(verifyDelete);
            for(Fighter a: fighterList){
                   if(a.getMatricula().equals(Integer.valueOf(matriculaTexto.getText()))){
                       buttonInsert.setDisable(true);
                   } 
            }
        }
	
	
	@FXML	public void actionSQLInsert(ActionEvent event) {
		Alert erro = new Alert(AlertType.ERROR);
		try {
                        String verifyNome = nomeTexto.getText();
                        String verifyMatricula = matriculaTexto.getText();
                        if(verifyNome.isEmpty() || verifyMatricula.isEmpty()){
                            erro.setTitle("Erro !");
                            erro.setHeaderText("Digite de forma devida !");
                            erro.showAndWait();
                            return;
                        }
                        else{
                       
                        try{
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO  (id, nome) values (?, ?)");
			try{
                        ps.setInt(1, Integer.parseInt(verifyMatricula));
			ps.setString(2, nomeTexto.getText());
			ps.execute();
                        }
                        catch(Exception e){
                            erro.setTitle("Erro !");
                            erro.setHeaderText("A matricula esta sendo utilizada!");
                            erro.showAndWait();
                            
                        }
                        }
                        catch(Exception e){
                            erro.setTitle("Erro !");
                            erro.setHeaderText("Matricula não é um valor inteiro");
                            erro.showAndWait();
                        }
                        }
		}
		catch(Exception e){
			System.out.println("Erro: " + e.toString());
		}
		initializeTableFighter();
		
	}
	
	@FXML
	public void actionSQLUpdate(ActionEvent event) {
            Alert erro = new Alert(AlertType.ERROR);
           
		try {
                    try{
                        Boolean verify = false;
			DBUtil db = DBUtil.getInstance();
			for(Fighter figther: fighterList){
                                if(figther.getMatricula().equals(Integer.valueOf(matriculaTexto.getText()))){
                                    verify = true;
                                    
                                } 
                         }
                        if(verify){
                            PreparedStatement ps = db.getConnection().prepareStatement("UPDATE  SET nome = ? WHERE id = ?");                                               
                            ps.setString(1, nomeTexto.getText());
                            ps.setInt(2, Integer.parseInt(matriculaTexto.getText()));	
                            ps.execute();
                            
                        }
                        else{
                                    buttonUpdate.setDisable(true);
                                    erro.setTitle("Erro !");
                                    erro.setHeaderText("Matricula não existe");
                                    erro.showAndWait();
                        }
                        
                    }catch(Exception e){
                            erro.setTitle("Erro !");
                            erro.setHeaderText("Matricula não é um valor inteiro");
                            erro.showAndWait();
                        }
		}catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
                initializeTableFighter();
	}
        
        @FXML
	public void actionSQLDelete(ActionEvent event) {
            Alert erro = new Alert(AlertType.ERROR);
            
		try {
                    try{
                        Boolean ver = false;
			DBUtil db = DBUtil.getInstance();
                        for(Fighter aluno: fighterList){
                                if(aluno.getMatricula().equals(Integer.valueOf(matriculaTexto.getText()))){
                                    ver = true;
                                    
                                } 
                         }
                        if(ver){
                            PreparedStatement ps = db.getConnection().prepareStatement("DELETE FROM  WHERE id = ?");
                            ps.setInt(1, Integer.parseInt(matriculaTexto.getText()));	
                            ps.execute();
                            
                        }
                        else{
                                    buttonDelete.setDisable(true);
                                    erro.setTitle("Erro !");
                                    erro.setHeaderText("Matricula não existe");
                                    erro.showAndWait();
                        }
			
          
                    }catch(NumberFormatException e){
                            erro.setTitle("Erro !");
                            erro.setHeaderText("Matricula não é um valor inteiro");
                            erro.showAndWait();
                        }
		}catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
                initializeTableFighter();
	}
	
	
        
     
        
        public void initializeTableFighter(){
            fighterList.clear();
            try {
                DBUtil db = DBUtil.getInstance();
		PreparedStatement ps;
                ps = db.getConnection().prepareStatement("SELECT * from ");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    fighterList.add(new Fighter(rs.getInt("matricula"), rs.getString("nome")));
                }
            } catch(Exception e){
			System.out.println("Erro: " + e.toString());
	}
		
        }
       
	

        }
