package acsse.gui;

import java.io.FileNotFoundException;

import acsse.datastructures.Company;
import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.JobSeeker;
import acsse.datastructures.VertexNode;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Login extends BorderPane{
	private BooleanProperty loggedIn ;
	private VBox loginContainer = new VBox(20);
	private HomePage homePage;
	private Alert alert = null;
	
	public boolean getLoggedIn() {
        return loggedIn.get();
    }
    
    public void setLoggedIn(boolean value) {
        loggedIn.set(value);
    }
    
    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }
   

	public void setHomePage(HomePage homePage) {
	    this.homePage = homePage;
	}
    
    public void setLogin()
    {
    	loggedIn = new SimpleBooleanProperty(false);
		this.setPrefWidth(1100);
	    // Set up root pane
        
        //this.setStyle("-fx-background-color: #413030;");
		

        // Set up login container
       
        loginContainer.setAlignment(Pos.CENTER);
        loginContainer.setPrefWidth(400);
        loginContainer.setMaxWidth(400);
        loginContainer.setPadding(new Insets(20));

        loginContainer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-border-radius: 1px;");

        // Set up title
        Label title = new Label("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        //title.setTextFill(Color.WHITE);

        // Set up form fields
        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.BLACK);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.BLACK);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        
        Label isCompanyLabel = new Label("login As Company : ");
        CheckBox isCompany = new CheckBox();
        HBox c = new HBox(isCompanyLabel,isCompany);
        c.setSpacing(20);
        
        // Set up links
        HBox links = new HBox(10);
        links.setAlignment(Pos.CENTER_LEFT);
        links.setPrefWidth(400);
        links.setMaxWidth(400);
        Button register = new Button("Register(Do not have an account)");
        
        links.getChildren().addAll(register, new Hyperlink("Forgot Password"));
        links.setStyle("-fx-text-fill: white;");

        // Set up submit button
        Button submitButton = new Button("Login");
        submitButton.setStyle("-fx-background-color: #352c2c; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: transparent; -fx-cursor: hand;");
        submitButton.setPrefHeight(50);
        submitButton.setMaxWidth(Double.MAX_VALUE);
        
        submitButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "Please fill in all fields.");
                alert.showAndWait();
            } else if (isCompany.isSelected()) {
                Vertex<VertexNode> company = GraphHandler.findCompany(graph, username, password);
                if (company == null) {
                    Alert alert = new Alert(AlertType.ERROR, "Username and password do not match any company.");
                    alert.showAndWait();
                } else {
                    homePage.company = company;
                    homePage.companyPage = new CompanyPage((Company) company.getValue());
                    homePage.companyPage.setHomePage(homePage);
                    homePage.switchContainer(homePage.companyPage);
                    homePage.menuBar.getChildren().removeAll(homePage.btnLogin,homePage.btnRegisterCompany,homePage.btnRegisterUser);
                    homePage.menuBar.getChildren().add(homePage.btnlogout);
                    alert.close();
                }
            } else {
                Vertex<VertexNode> jseeker = GraphHandler.findJobSeeker(graph, username, password);
                if (jseeker == null) {
                    Alert alert = new Alert(AlertType.ERROR, "Username and password do not match any job seeker.");
                    alert.showAndWait();
                } else {
                    
                    try {
                    	homePage.jobSeeker = jseeker;
                        homePage.adverts = new JobAdverts((JobSeeker) jseeker.getValue());
                        homePage.adverts.setHomePage(homePage);
                        homePage.switchContainer(homePage.adverts);
                        homePage.menuBar.getChildren().removeAll(homePage.btnLogin,homePage.btnRegisterCompany,homePage.btnRegisterUser);
                        homePage.menuBar.getChildren().add(homePage.btnlogout);
                        alert.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        
        register.setOnAction(event ->{
        	
        });

        // Set up error message
        Label errorMessage = new Label("Incorrect Username/Password");
        errorMessage.setTextFill(Color.web("red"));
        errorMessage.setVisible(true);

        // Add elements to login container
        loginContainer.getChildren().addAll(title, new Separator(), usernameLabel, usernameField, passwordLabel, passwordField,c, new Separator(), submitButton);

        // Add login container to root pane
        this.setCenter(loginContainer);
        BorderPane.setMargin(loginContainer, new Insets(20, 20, 20, 20));
    }
	
	public Login(Alert alert)
	{
		this.alert = alert;
		setLogin();
	}
}
