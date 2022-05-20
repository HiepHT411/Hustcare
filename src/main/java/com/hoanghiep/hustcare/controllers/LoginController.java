package com.hoanghiep.hustcare.controllers;

import java.io.IOException;

import com.hoanghiep.hustcare.models.user.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends AnchorPane {
	
	public LoginController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	 @FXML
	 private TextField username;
	 @FXML
	 private PasswordField password;
	 
	 @FXML
	    private void login() throws IOException
	    {
	        
	        String user = username.getText();
	        String pass = password.getText();
	       
	        System.out.println(user +" " + pass);
	        
	        User tmpUser = new User();
	        
	        

}
