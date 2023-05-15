package acsse.gui;


import java.util.List;

import javax.swing.JOptionPane;

import acsse.datastructures.Company;
import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Edge;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.Job;
import acsse.datastructures.JobSeeker;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class JobRegister extends VBox{
	 private HomePage homePage;
	 
	 public JobRegister(Alert al)
	 {
		 Label pageTitle = new Label("Add a Job");
		 pageTitle.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		 Label lblTitle = new Label("Job Title:");
	        TextField txtTitle = new TextField();

	        Label lblProvince = new Label("Job Location:");
	        ComboBox<String> provinceSelect = new ComboBox<>();
	        provinceSelect.getItems().addAll(
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
	        provinceSelect.setPromptText("Choose Job Location");

	        Label lblRequirements = new Label("Job Requirements:");
	        TextField txtRequirements = new TextField();

	        Label lblSalary = new Label("Salary:");
	        TextField txtSalary = new TextField();

	        Label lblDescription = new Label("Job Description:");
	        TextField txtDescription = new TextField();

	        Label lblExperienceLevel = new Label("Experience Level:");
	        TextField txtExperienceLevel = new TextField();

	        Button btnAddJob= new Button("Add Job");

	        // Register button action
	        btnAddJob.setOnAction(e -> {
	            if (txtTitle.getText().isEmpty() || provinceSelect.getValue() == null ||
	                txtRequirements.getText().isEmpty() || txtSalary.getText().isEmpty() ||
	                txtDescription.getText().isEmpty() || txtExperienceLevel.getText().isEmpty()) {
	                // Prompt user to enter missing information
	                JOptionPane.showMessageDialog(null, "Missing information");
	                
	                
	            } else {
	                // Register the job posting
	            	Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
	            	//Vertex<VertexNode> companyNode = homePage.company;
	            	Vertex<VertexNode> companyNode = GraphHandler.findCompanyById(graph, ((Company) homePage.company.getValue()).getId());
	           	
	            	
	            	Company company =null;
	            	if(companyNode.getValue() instanceof Company)
	            	{
	            		company = (Company) companyNode.getValue();
	            	}
	            	
	            	//create a job node
	            	Job job = new Job(graph.getVertices().size(),txtTitle.getText(),txtDescription.getText(),
	            			company.getName(),Double.parseDouble(txtSalary.getText()),txtRequirements.getText(),
	            			provinceSelect.getValue(),txtExperienceLevel.getText(),0,null);
	            	Vertex<VertexNode> jobNode = new Vertex<>(job);
	            	
	            	//create company and job edges
	            	GraphHandler.addCompanyJobEdges(graph, companyNode, jobNode);
	            	GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
	            	graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
	            	
	            	System.out.println(companyNode.getEdges().size());
	            	
	            	List<Vertex<VertexNode>> jobseekers = GraphHandler.searchJobSeekers(graph);
	            	
	            	for(Vertex<VertexNode> jobseeker: jobseekers)
	            	{
	            		if (jobseeker.getValue() instanceof JobSeeker) {
	            	        JobSeeker person = (JobSeeker) jobseeker.getValue();
	            	        
	            	        if (job.getLocation().equals(person.getProvince())
	            	                || job.getExperienceLevel().equals(person.getEducationLevel())
	            	                || job.getTitle().equals(person.getJobTitle())
	            	                || person.getSkills().stream().anyMatch(job.getRequirements()::contains)) {
	            	            // If the Job matches the job seeker's criteria, link job with jobseeker
	            	        	
	            	        	Edge<VertexNode> JobseekerEdge = new Edge<>(0,jobseeker,jobNode);
	            	        	Edge<VertexNode> JobEdge = new Edge<>(0,jobNode,jobseeker);
	            	        	
	            	        	jobseeker.getEdges().add(JobseekerEdge);
	            	        	jobNode.getEdges().add(JobEdge);
	            	        	
	            	        	graph.getEdges().add(JobEdge);
	            	        	graph.getEdges().add(JobseekerEdge);
	            	        }
	            	    }
	            	}
	            	
	            	//add jobnode to graph vertices
	            	graph.getVertices().add(jobNode);
	            	
	            	//save graph to files
	            	GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
	            	
	            	Alert alert = new Alert(AlertType.CONFIRMATION);
	           	    alert.setTitle("Job Addition");
	           	    alert.setHeaderText(null);
	           	    alert.setContentText("Job Has Been Added!!");
	           	    alert.showAndWait();
	           	    
	           	    String username = ((Company) homePage.company.getValue()).getUsername();
	           	    String password =  ((Company) homePage.company.getValue()).getPassword();
	           	    
		           	homePage.company = GraphHandler.findCompany(GraphHandler.readGraphFromFile("data/graphFile.graph"), username, password);
		           	homePage.getChildren().remove(homePage.companyPage);
	                homePage.companyPage = new CompanyPage((Company) homePage.company.getValue());
	                homePage.companyPage.setHomePage(homePage);
	                //homePage.switchContainer(homePage.companyPage);
	                homePage.getChildren().add(homePage.companyPage);
	                alert.close();
	                al.close();
	            }
	        });
	        

	        HBox buttons = new HBox(10, btnAddJob);
	        buttons.setPadding(new Insets(30,0,10,0));
	        buttons.setAlignment(Pos.CENTER);

	        GridPane grid = new GridPane();
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(10));
	        grid.addRow(0, lblTitle, txtTitle);
	        grid.addRow(1, lblProvince, provinceSelect);
	        grid.addRow(2, lblRequirements, txtRequirements);
	        grid.addRow(3, lblSalary, txtSalary);
	        grid.addRow(4, lblDescription, txtDescription);
	        grid.addRow(5, lblExperienceLevel, txtExperienceLevel);

	        VBox root = new VBox(pageTitle,grid, buttons);
	        
	        
	        root.setAlignment(Pos.CENTER);
	        root.setPrefWidth(400);
	        root.setMaxWidth(400);
	        root.setPadding(new Insets(30));

	        root.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);-fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");
	        
	        this.setAlignment(Pos.CENTER);
	        this.setPrefHeight(USE_PREF_SIZE);
	        
	        VBox.setMargin(this, new Insets(10, 0, 200, 0));
	        this.getChildren().add(root);
	 }
	 
	 public void setHomePage(HomePage homePage) {
	     this.homePage = homePage;
	 }
}
