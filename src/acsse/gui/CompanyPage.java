package acsse.gui;

import java.util.List;
import java.util.Optional;

import acsse.datastructures.Company;
import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.Job;
import acsse.datastructures.VertexNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CompanyPage extends VBox {
	private HomePage homePage = null;
	
	public void setHomePage(HomePage homePage) {
	    this.homePage = homePage;
	}
	
    public CompanyPage(Company company) {
        //super();

      
        // Create labels for the company information
        Label nameLabel = new Label("Name: " + company.getName());
        Label locationLabel = new Label("Location: " + company.getLocation());
        Label industryLabel = new Label("Industry: " + company.getIndustry());
        Label sizeLabel = new Label("Company Size: " + company.getCompanySize());
        Label idLabel = new Label("Company-ID: #"+ company.getId());

        // Create a region to fill the empty space
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        // Create an edit button
        Button editButton = new Button("Edit Profile");
        editButton.setPrefWidth(150);
        
        editButton.setOnAction(e->{
        	// Create the alert and set the graph as its content
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Register A Company");
            alert.setHeaderText(null);
            
            CompanyRegisterUI cr = new CompanyRegisterUI(alert,company);
            cr.setHomePage(homePage);
            cr.setPrefWidth(500);
            
            alert.getDialogPane().setContent(cr);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.CLOSE);
            

            // Show the alert
            alert.showAndWait();
        });
        
        Button addJob = new Button("Add Job");
        addJob.setPrefWidth(150);
      
        // Create a VBox to hold the labels and the edit button
        HBox buttons = new HBox(20,editButton,addJob);
        buttons.setAlignment(Pos.CENTER);
        HBox.setMargin(buttons, new Insets(10, 50, 10, 50));
        
        VBox vbox = new VBox(nameLabel,idLabel, locationLabel, industryLabel, sizeLabel,buttons);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        
        addJob.setOnAction(event->{
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Add A Job");
            alert.setHeaderText(null);
            JobRegister jobRegister = new JobRegister(alert);
            jobRegister.setHomePage(homePage);
            alert.getDialogPane().setContent(jobRegister);
            
            ButtonType closeButton = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(closeButton);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == closeButton) {
                alert.close();
            }
        });
        
        VBox companyJobs = createJobListForCompany(GraphHandler.readGraphFromFile("data/graphFile.graph"), company);

        
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        this.setSpacing(20);
        VBox.setMargin(vbox, new Insets(10, 50, 10, 50));
        VBox.setMargin(companyJobs, new Insets(10, 50, 10, 50));
        
        vbox.setStyle("-fx-background-color: transparent; -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius:20px;");
        this.getChildren().addAll(vbox,companyJobs);
    }
    
    public VBox createJobListForCompany(Graph<VertexNode> graph, Company company) {
        List<Vertex<VertexNode>> jobsPosted = GraphHandler.getJobsPostedByCompany(graph, company);
        

        VBox jobList = new VBox();

        for (Vertex<VertexNode> jobNode : jobsPosted) {
        	
        	//if(graph.getVertices().contains(jobNode))
        	{
        		Job job = (Job) jobNode.getValue();

                HBox jobBox = new HBox();
                jobBox.setPadding(new Insets(20));
                jobBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey;"
                		+ " -fx-border-width: 1px; -fx-border-radius: 10px;");
        		
                jobBox.setSpacing(20);
                System.out.println("count");

                Label jobIdLabel = new Label("Job ID: #" + job.getId());
                Label jobTitleLabel = new Label("Job Title: " + job.getTitle());
                Label jobLocationLabel = new Label("Location: " + job.getLocation());

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(event -> {
                    GraphHandler.removeJob(graph, job);
                    GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
                    jobList.getChildren().remove(jobBox);
                });

                jobBox.getChildren().addAll(jobIdLabel, jobTitleLabel, jobLocationLabel, deleteButton);
                jobList.getChildren().add(jobBox);
        	} 
        }

        return jobList;
    }

}
