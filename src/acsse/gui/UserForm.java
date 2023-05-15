package acsse.gui;

import java.util.Arrays;
import java.util.List;

import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.JobSeeker;
import acsse.datastructures.VertexNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserForm extends VBox {
	 private final TextField nameField = new TextField();
	    private final TextField usernameField = new TextField();
	    private final TextField passwordField = new TextField();
	    private final TextField emailField = new TextField();
	    private final TextField phoneField = new TextField();
	    private final ComboBox<String> provinceComboBox = new ComboBox<>();
	    private final TextField educationLevelField = new TextField();
	    private final TextField workExperienceField = new TextField();
	    private final TextField skillsField = new TextField();
	    private final TextField jobTitleField = new TextField();
	    private final TextField industryField = new TextField();
    
    private HomePage homePage;
    private Alert alert = null;

    public UserForm(Alert alert, JobSeeker person) {
    	this.setStyle("-fx-background-color: #413030;");
        this.setPrefWidth(600);
        this.alert =alert;
        
        if(person !=null)
        {
        	// person is a JobSeeker object

        	nameField.setText(person.getName());
        	usernameField.setText(person.getUsername());
        	passwordField.setText(person.getPassword());
        	emailField.setText(person.getEmail());
        	phoneField.setText(person.getPhone());
        	provinceComboBox.setValue(person.getProvince());
        	educationLevelField.setText(person.getEducationLevel());
        	workExperienceField.setText(person.getWorkExperience());
        	skillsField.setText(String.join(",", person.getSkills()));
        	jobTitleField.setText(person.getJobTitle());
        	industryField.setText(person.getIndustry());
        }

        setSpacing(10);
        setPadding(new Insets(10));
        VBox.setMargin(this, new Insets(20, 20, 20, 20));
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);-fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

        Label nameLabel = new Label("Name:");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        Label emailLabel = new Label("Email:");
        Label phoneLabel = new Label("Phone:");
        Label provinceLabel = new Label("Province:");
        Label educationLevelLabel = new Label("Education Level:");
        Label workExperienceLabel = new Label("Work Experience (low, middle, high):");
        Label skillsLabel = new Label("Skills (comma separated):");
        Label jobTitleLabel = new Label("Job Title:");
        Label industryLabel = new Label("Industry:");

        provinceComboBox.getItems().addAll("Eastern Cape", "Free State", "Gauteng", "KwaZulu-Natal", "Limpopo", "Mpumalanga", "Northern Cape", "North West", "Western Cape");
        provinceComboBox.setPromptText("Enter your province");
        
        Button login = new Button("Login(Already have an account)");
        login.setPrefWidth(250);
        login.setPrefHeight(20);

        login.setOnAction(event -> {
        	this.alert.close();
            homePage.btnLogin.fire();     
        });

        Button register = new Button("Register");
        
        if(person !=null)
        {
        	register.setText("Edit Profile");
        }
        
        register.setOnAction(event->{
        	// Check if all fields are filled
            if (nameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || 
                emailField.getText().isEmpty() || phoneField.getText().isEmpty()  || 
                provinceComboBox.getValue() == null || educationLevelField.getText().isEmpty() || 
                workExperienceField.getText().isEmpty() || skillsField.getText().isEmpty() || jobTitleField.getText().isEmpty() || 
                industryField.getText().isEmpty()) {
                    
                    // Show an alert if any of the fields are empty
                    Alert mes = new Alert(Alert.AlertType.ERROR, "Please fill all the fields");
                    mes.showAndWait();
                    
            } else {
                // Create a job seeker object if all fields are filled
                List<String> skills = Arrays.asList(skillsField.getText().split(","));
                Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
                
                if(person !=null)
                {
                	Vertex<VertexNode> vert = GraphHandler.findJobSeeker(graph, person.getUsername(), person.getPassword());
                	
                	((JobSeeker)vert.getValue()).setName(nameField.getText());
                	
                	((JobSeeker)vert.getValue()).setUsername(usernameField.getText());
                	((JobSeeker)vert.getValue()).setPassword(passwordField.getText());
                	((JobSeeker)vert.getValue()).setEmail(emailField.getText());
                	((JobSeeker)vert.getValue()).setPhone(phoneField.getText());
                	((JobSeeker)vert.getValue()).setProvince(provinceComboBox.getValue());
                	((JobSeeker)vert.getValue()).setEducationLevel(educationLevelField.getText());
                	((JobSeeker)vert.getValue()).setWorkExperience(workExperienceField.getText());
                	((JobSeeker)vert.getValue()).setSkills(Arrays.asList(skillsField.getText().split(",")));
                	((JobSeeker)vert.getValue()).setJobTitle(jobTitleField.getText());
                	((JobSeeker)vert.getValue()).setIndustry(industryField.getText());
                	
                	GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
                	 Alert mes = new Alert(Alert.AlertType.CONFIRMATION, "Information Edit! Please Login Again");
                     mes.showAndWait();
                     homePage.btnlogout.fire();

                }else
                {
                	JobSeeker jobSeeker = new JobSeeker(graph.getVertices().size(), usernameField.getText(), passwordField.getText(),
                            nameField.getText(), emailField.getText(), phoneField.getText(), provinceComboBox.getValue(),
                            educationLevelField.getText(), workExperienceField.getText(), skills, jobTitleField.getText(), 
                            industryField.getText(), "");
                    GraphHandler.addJobSeekerToGraph(graph, jobSeeker);
                        
                    Alert mes = new Alert(Alert.AlertType.CONFIRMATION, "Thank you for registering");
                    mes.showAndWait();
                }
                
                this.alert.close();
            }
        });
        
        register.setPrefWidth(100);
        register.setPrefHeight(20);

        HBox buttons = new HBox(20, register, login);
        
        if(person!=null)
        {
        	buttons.getChildren().remove(login);
        }
        
        HBox.setMargin(login, new Insets(10, 10, 10, 10));
        HBox.setMargin(register, new Insets(10, 10, 10, 10));
        VBox.setMargin(buttons, new Insets(10, 10, 10, 10));
        buttons.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();

        vbox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");
        vbox.setPrefWidth(500);

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        VBox.setMargin(vbox, new Insets(10, 10, 20, 10));

        vbox.getChildren().addAll(
                nameLabel, nameField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                phoneLabel, phoneField,
                emailLabel, emailField,
                provinceLabel, provinceComboBox,
                workExperienceLabel, workExperienceField,
                skillsLabel, skillsField,
                educationLevelLabel,educationLevelField,
                industryLabel,industryField,
                jobTitleLabel,jobTitleField
                
               
        );
         
        VBox.setMargin(this, new Insets(10, 10, 20, 10));
        
       
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true); // to make the width of the scroll pane same as the container
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // to always show vertical scroll bar

        scrollPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0), transparent;");

        this.setMaxHeight(600);
        
        Label title = new Label("User Register Page");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        this.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(title ,scrollPane,buttons);
    }
    

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }


    public String getName() {
        return nameField.getText();
    }

    public String getWorkExperience() {
        return workExperienceField.getText();
    }

    public String[] getSkills() {
        return skillsField.getText().split(",");
    }
}

