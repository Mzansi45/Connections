package acsse.gui;


import acsse.datastructures.Company;
import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.VertexNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CompanyRegisterUI extends VBox {
	private HomePage homePage = null;
	Alert al = null;
	
	public void setHomePage(HomePage homePage) {
	    this.homePage = homePage;
	}

    public CompanyRegisterUI(Alert al, Company co) {
    	Label title = new Label("Register a Company");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        this.al = al;
        
        
    	
        // Create the form labels and fields
        Label nameLabel = new Label("Company Name:");
        TextField nameField = new TextField();

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        
        Label emailLabel = new Label("Email Address:");
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        Label cityLabel = new Label("City:");
        TextField cityField = new TextField();

        Label provinceLabel = new Label("Province:");
        
        ComboBox<String> provinceField = new ComboBox<>();
        provinceField.getItems().addAll(
            "Eastern Cape",
            "Free State",
            "Gauteng",
            "KwaZulu-Natal",
            "Limpopo",
            "Mpumalanga",
            "Northern Cape",
            "North West",
            "Western Cape"
        );
        
        provinceField.setPromptText("Choose Job Location");
        
        //TextField provinceField = new TextField();

        Label industryLabel = new Label("Industry:");
        TextField industryField = new TextField();

        Label sizeLabel = new Label("Company Size:");
        TextField sizeField = new TextField();
        
        
        //populate fields from co
        
        if(co!=null)
        {
        	nameField.setText(co.getName());
        	usernameField.setText(co.getUsername());
        	passwordField.setText(co.getPassword());
        	emailField.setText(co.getEmail());
        	phoneField.setText(co.getPhone());
        	cityField.setText(co.getCity());
        	provinceField.setValue(co.getLocation());
        	industryField.setText(co.getIndustry());
        	sizeField.setText(String.valueOf(co.getCompanySize()));
        }

        // Create the register button
        Button registerButton = new Button("Register");
        if(co!=null)
        {
        	registerButton.setText("Edit Company Info");
        }
        
        registerButton.setOnAction(event->{
        	Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");

        	String name = nameField.getText();
        	String username = usernameField.getText();
        	String password = passwordField.getText();
        	String email = emailField.getText();
        	String phone = phoneField.getText();
        	String city = cityField.getText();
        	String province = provinceField.getValue();
        	String industry = industryField.getText();
        	String size = sizeField.getText();

        	if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || city.isEmpty() || province.isEmpty() || industry.isEmpty() || size.isEmpty()) {
        	    Alert alert = new Alert(Alert.AlertType.WARNING);
        	    alert.setTitle("Empty Fields");
        	    alert.setHeaderText(null);
        	    alert.setContentText("Please fill in all fields.");
        	    alert.showAndWait();
        	} else {
        		
        		if(co!=null)
        		{
        			Vertex<VertexNode> vert = GraphHandler.findCompany(graph, co.getUsername(), co.getPassword());
        			
        			((Company)vert.getValue()).setName(nameField.getText());
        			((Company)vert.getValue()).setUsername(usernameField.getText());
        			((Company)vert.getValue()).setPassword(passwordField.getText());
        			((Company)vert.getValue()).setEmail(emailField.getText());
        			((Company)vert.getValue()).setPhone(phoneField.getText());
        			((Company)vert.getValue()).setCity(cityField.getText());
        			((Company)vert.getValue()).setLocation(provinceField.getValue());
        			((Company)vert.getValue()).setIndustry(industryField.getText());
        			((Company)vert.getValue()).setCompanySize(Integer.parseInt(size));

        			GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
        			
        			Alert alert = new Alert(AlertType.CONFIRMATION);
            	    alert.setTitle("Edit Completed");
            	    alert.setHeaderText(null);
            	    alert.setContentText("Company Info Edited Please login Again");
            	    alert.showAndWait();
            	    
            	    homePage.btnlogout.fire();
        		}else
        		{
             	    int id = graph.getVertices().size(); // generate a unique ID
            	    String contactInformation = "Email: " + email + ", Phone: " + phone;
            	    String location = province;
            	    int companySize = Integer.parseInt(size);

            	    Company company = new Company(id, password, username, name, contactInformation, location, industry, companySize,email,city,phone);
            	    Vertex<VertexNode> companyNode = new Vertex<>(company);

            	    graph.getVertices().add(companyNode);
            	    GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
            	    
            	    nameField.clear();
            	    usernameField.clear();
            	    passwordField.clear();
            	    emailField.clear();
            	    phoneField.clear();
            	    cityField.clear();
            	    provinceField.setValue(null);
            	    industryField.clear();
            	    sizeField.clear();
            	    
            	    Alert alert = new Alert(AlertType.CONFIRMATION);
            	    alert.setTitle("Register Successfully");
            	    alert.setHeaderText(null);
            	    alert.setContentText("Company Registered!!");
            	    alert.showAndWait();
            	    
            	   
        		}
        		
        		 al.close();
        	}
        });

        // Create the login button
        Button loginButton = new Button("Already have an account? Login");
        
        loginButton.setOnAction(event ->{
        	this.al.close();
        	homePage.btnLogin.fire();
        });

        // Create the form grid pane
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));
        formGrid.setAlignment(Pos.CENTER);
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(emailLabel, 0, 1);
        formGrid.add(emailField, 1, 1);
        formGrid.add(phoneLabel, 0, 2);
        formGrid.add(phoneField, 1, 2);
        formGrid.add(cityLabel, 0, 3);
        formGrid.add(cityField, 1, 3);
        formGrid.add(provinceLabel, 0, 4);
        formGrid.add(provinceField, 1, 4);
        formGrid.add(industryLabel, 0, 5);
        formGrid.add(industryField, 1, 5);
        formGrid.add(sizeLabel, 0, 6);
        formGrid.add(sizeField, 1, 6);
        formGrid.add(usernameLabel, 0, 7);
        formGrid.add(usernameField, 1, 7);
        formGrid.add(passwordLabel, 0, 8);
        formGrid.add(passwordField, 1, 8);

        // Create the buttons hbox
        HBox buttonsBox = new HBox();
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(registerButton, loginButton);

        if(co!=null)
        {
        	buttonsBox.getChildren().remove(loginButton);
        }
        
        // Add the form and buttons to the vbox
        this.getChildren().addAll(title,formGrid, buttonsBox);
        // Set the default spacing and alignment of the vbox
        this.setSpacing(20);
        VBox.setMargin(this, new Insets(10, 0, 200, 0));
        this.setAlignment(Pos.CENTER);
    }
}