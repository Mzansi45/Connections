package acsse.gui;

import java.io.FileNotFoundException;

import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Edge;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.VertexNode;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomePage extends VBox{
	
	//containers in the main container
	HBox menuBar = new HBox();
	
	//switchable containers
	JobAdverts adverts = null;
	CompanyPage companyPage = null;
	
	Button btnJobPage = new Button("Job Page");
	Button btnlogout = new Button("Logout");
	Button btnCompanyPage = new Button("Company Page");
	Button btnRegisterCompany = new Button("Register Company");
	Button btnRegisterUser = new Button("Register Job Seeker");
	Button btnLogin = new Button("Login");
	Button btnViewGraph = new Button("View Graph");
	
	//logic
	boolean isLoggedIn = false;
	Vertex<VertexNode> company = null;
	Graph<VertexNode> graph = null;
	Vertex<VertexNode> jobSeeker = null;
	
	public void switchContainer(Node newContainer) {
        if (getChildren().contains(newContainer)) {
            // If the new container is already displayed, remove it
            //getChildren().remove(newContainer);
        } else {
            // Otherwise, remove the other containers and add the new one
            getChildren().removeAll(adverts,companyPage);
            getChildren().add(newContainer);
        }
    }
	
	public HomePage() throws FileNotFoundException
	{    
		this.setStyle("-fx-background-color: #c9cdd4;-fx-width:2000px;");	
		VBox.setMargin(menuBar, new Insets(10, 10, 10, 10));
		this.getChildren().addAll(menuBar);
		
		
		//read graph on file if its not empty
		if((graph = GraphHandler.readGraphFromFile("data/graphFile.graph")) == null)
		{
			//create and write graph to file if file is empty
			graph = new Graph<>();
			GraphHandler.writeGraphToFile(graph,"data/graphFile.graph");
		}
		
		setMenuBar();
	}
	
	public void setMenuBar()
	{		
		
		btnRegisterUser.setOnAction(event ->{
			// Create the alert and set the graph as its content
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Register As Job Seeker");
            alert.setHeaderText(null);
           
            UserForm userForm = new UserForm(alert,null);
            userForm.setHomePage(this);
            
            userForm.setPrefHeight(600);
            ScrollPane scrollPane = new ScrollPane(userForm);
            scrollPane.setFitToWidth(true); // to make the width of the scroll pane same as the container
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // to always show vertical scroll bar

            scrollPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0), transparent;");
            // add the scroll pane to the container
           
            alert.getDialogPane().setContent(scrollPane);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.CLOSE);

            // Show the alert
            alert.showAndWait();
		});
		
		btnLogin.setOnAction(event ->{
			// Create the alert and set the graph as its content
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            Login login = new Login(alert);
            login.setPrefWidth(500);
            login.setHomePage(this);
            
            alert.getDialogPane().setContent(login);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.CLOSE);
            
            // Show the alert
            alert.showAndWait();
		});
	
		btnRegisterCompany.setOnAction(event ->{
			// Create the alert and set the graph as its content
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Register A Company");
            alert.setHeaderText(null);
            
            CompanyRegisterUI cr = new CompanyRegisterUI(alert,null);
            cr.setHomePage(this);
            cr.setPrefWidth(500);
            
            alert.getDialogPane().setContent(cr);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.CLOSE);
            

            // Show the alert
            alert.showAndWait();
		});
	
		btnlogout.setOnAction(event ->{
			menuBar.getChildren().addAll(btnLogin,btnRegisterUser,btnRegisterCompany);	
			menuBar.getChildren().remove(btnlogout);
			
			getChildren().removeAll(adverts,companyPage);
			adverts = null;
			companyPage = null;
		});
		
		btnViewGraph.setOnAction(event->{
			// Create the alert and set the graph as its content
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Graph Visualization");
            alert.setHeaderText(null);
            Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
            alert.getDialogPane().setContent(new GraphView(graph));
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.CLOSE);

            // Show the alert
            alert.showAndWait();
		});
		
		menuBar.getChildren().addAll(btnLogin,btnRegisterUser,btnRegisterCompany,btnViewGraph);	
		menuBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");
		
		HBox.setMargin(btnRegisterCompany, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnRegisterUser, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnLogin, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnViewGraph, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnlogout, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnJobPage, new Insets(10, 10, 10, 10));
		HBox.setMargin(btnCompanyPage, new Insets(10, 10, 10, 10));
		
		menuBar.setSpacing(20);
	}
	
}
