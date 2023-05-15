package acsse.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Stack;

import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Edge;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.Job;
import acsse.datastructures.JobSeeker;
import acsse.datastructures.VertexNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class JobAdverts extends HBox {
	// containers on the body
	VBox left = new VBox();
	VBox middle = new VBox();
	VBox right = new VBox();

	// containers on the left
	VBox profile = new VBox();
	VBox filters = new VBox();

	// containers on the middle
	HBox searchBar = new HBox();
	ListView<HBox> jobs;
	
	private HomePage homePage;
	private JobSeeker jobSeeker;
	
	public void setHomePage(HomePage homePage) {
	    this.homePage = homePage;
	}

	public JobAdverts(JobSeeker jobSeeker) throws FileNotFoundException {
		this.getChildren().addAll(left, middle, right);
		this.jobSeeker = jobSeeker;

		left.getChildren().addAll(profile, filters);
		middle.getChildren().addAll(searchBar);
		// right.getChildren().addAll(jobInformation);

		middle.setPrefWidth(400);
		left.setSpacing(20);
		right.setPrefWidth(400);
		
		// Create a ScrollPane and set its content to the VBox
				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setContent(this);

				// Set the preferred size of the ScrollPane
				scrollPane.setPrefSize(400, 400);

				// Set the vertical scrollbar policy to always show
				scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


		setJobProfile("");
		setProfile();
		setBody();
		setSearchField();
		setJobFilter();
		setRight();
	}
	
	public void setJobFilter()
	{
		
		filters.setSpacing(10);
		filters.setPadding(new Insets(20));

		Label title = new Label("Job Filters");
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

		Label expLabel = new Label("Experience Level:");

		CheckBox lowExp = new CheckBox("Low");
		CheckBox middleExp = new CheckBox("Middle");
		CheckBox highExp = new CheckBox("High");

		HBox expFilter = new HBox();
		expFilter.setSpacing(10);
		expFilter.getChildren().addAll(expLabel, lowExp, middleExp, highExp);

		Label locationLabel = new Label("Location:");

		CheckBox easternCape = new CheckBox("Eastern Cape");
		CheckBox freeState = new CheckBox("Free State");
		CheckBox gauteng = new CheckBox("Gauteng");
		CheckBox kzn = new CheckBox("KwaZulu-Natal");
		CheckBox limpopo = new CheckBox("Limpopo");
		CheckBox mpumalanga = new CheckBox("Mpumalanga");
		CheckBox northernCape = new CheckBox("Northern Cape");
		CheckBox northWest = new CheckBox("North West");
		CheckBox westernCape = new CheckBox("Western Cape");

		VBox locationFilter = new VBox();
		locationFilter.getChildren().addAll(locationLabel, easternCape, freeState, gauteng, kzn, limpopo, mpumalanga, northernCape, northWest, westernCape);

		Button applyFilter = new Button("Apply Filter");
		
		applyFilter.setOnAction(event ->{
			// get the search query 
			String selected = "";
			if (lowExp.isSelected()) {
			    selected += "Low ";
			}
			if (middleExp.isSelected()) {
			    selected += "Middle ";
			}
			if (highExp.isSelected()) {
			    selected += "High ";
			}
			if (easternCape.isSelected()) {
			    selected += "Eastern Cape ";
			}
			if (freeState.isSelected()) {
			    selected += "Free State ";
			}
			if (gauteng.isSelected()) {
			    selected += "Gauteng ";
			}
			if (kzn.isSelected()) {
			    selected += "KwaZulu-Natal ";
			}
			if (limpopo.isSelected()) {
			    selected += "Limpopo ";
			}
			if (mpumalanga.isSelected()) {
			    selected += "Mpumalanga ";
			}
			if (northernCape.isSelected()) {
			    selected += "Northern Cape ";
			}
			if (northWest.isSelected()) {
			    selected += "North West ";
			}
			if (westernCape.isSelected()) {
			    selected += "Western Cape ";
			}

			// remove the extra space at the end of the string
			if (!selected.isEmpty()) {
			    selected = selected.substring(0, selected.length() - 1);
			}
	        
	        try {
				setJobProfile(selected);
				setRight();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		filters.getChildren().addAll(title, expFilter, locationFilter, applyFilter);
		
		filters.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey; "
				+ "-fx-border-width: 1px; -fx-border-radius: 10px;");
		filters.setAlignment(Pos.CENTER);
	}

	public void setBody() {
		VBox.setMargin(this, new Insets(0, 0, 10, 10));

		// body margins
		HBox.setMargin(left, new Insets(0, 0, 10, 0));
		HBox.setMargin(middle, new Insets(0, 10, 10, 10));
		HBox.setMargin(right, new Insets(0, 0, 10, 0));

		// styling containers
		// left
		VBox.setMargin(profile, new Insets(0, 0, 0, 0));
		VBox.setMargin(filters, new Insets(0, 0, 0, 0));
		// left.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);
		// -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

		// middle
		VBox.setMargin(searchBar, new Insets(0, 10, 0, 0));
		VBox.setMargin(jobs, new Insets(10, 10, 10, 0));
		// left.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);
		// -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

		// right
	}

	private List<Vertex<VertexNode>> getAvailableJobs(String searchQuery) {
		
		List<Vertex<VertexNode>> jobList = GraphHandler.searchJobs(GraphHandler.readGraphFromFile("data/graphFile.graph"), searchQuery);
	    
	    
	    return jobList;
	}

	public void setRight() {
		
		// set company image and job description

		jobs.setOnMouseClicked(event -> {
			HBox selectedHBox = jobs.getSelectionModel().getSelectedItem();
			VBox summaryBox = new VBox();
			ImageView picture = null;
			Label jobId = null;

			for (Node node : selectedHBox.getChildren()) {
				if (node instanceof ImageView) {
					// Create a new ImageView with the same image
					picture = new ImageView(((ImageView) node).getImage());
					summaryBox.getChildren().add(picture);
				} else if (node instanceof Label) {
					// Create a new Label with the same text
					jobId = new Label("Job ID: " + ((Label) node).getText());
					summaryBox.getChildren().add(jobId);
					break;
				}
			}
			
			String jid = jobId.getText();

			picture.setFitWidth(100);
			picture.setFitHeight(100);
			VBox.setMargin(picture, new Insets(20));

			jobId.setStyle("-fx-font-size:20px");
			VBox.setMargin(jobId, new Insets(10));

			summaryBox.setStyle(
					"-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

			Button apply = new Button("Apply");
			apply.setOnAction(ee -> {
				GraphHandler.connectCompanyJobSeeker(GraphHandler.readGraphFromFile("data/graphFile.graph"),
						GraphHandler.findCompanyByJob(GraphHandler.readGraphFromFile("data/graphFile.graph"), 
								GraphHandler.getJobById(GraphHandler.readGraphFromFile("data/graphFile.graph"), Integer.parseInt(jid.substring(9)))), jobSeeker);
				
				 Alert mes = new Alert(Alert.AlertType.CONFIRMATION, "Thank you for applying");
                 mes.showAndWait();
			});
			
			VBox.setMargin(apply, new Insets(10));

			summaryBox.setAlignment(Pos.CENTER);
			summaryBox.setBorder(Border.stroke(Color.BLACK));

			Job job = GraphHandler.getJobById(GraphHandler.readGraphFromFile("data/graphFile.graph"), Integer.parseInt(jobId.getText().substring(9)));
			Label experience = new Label("Experience Level: " + job.getExperienceLevel());
			Label jobTitle = new Label("Title: " + job.getTitle());
			Label companyName = new Label("Company name: "+ job.getCompany());
			
			Label experience1 = new Label("Experience Level: " + job.getExperienceLevel());
			Label jobTitle1 = new Label("Title: " + job.getTitle());
			Label companyName1 = new Label("Company name: "+ job.getCompany());

			experience.setStyle("-fx-font-size:15px");
			VBox.setMargin(experience, new Insets(10));
			jobTitle.setStyle("-fx-font-size:15px");
			VBox.setMargin(jobTitle, new Insets(5));
			companyName.setStyle("-fx-font-size:15px");
			VBox.setMargin(companyName, new Insets(5));

			summaryBox.getChildren().addAll(companyName1,jobTitle1, experience1, apply);
			
			// containers on the right
			VBox jobInformation = new VBox();
			//set up detailed work
			
			jobInformation.setStyle(
					"-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

			jobInformation.getChildren().addAll(companyName,jobTitle,experience);
			
			//Salary
			Label salary = new Label("Monthly salary: R"+job.getSalary());
			salary.setStyle("-fx-font-size:15px");
			VBox.setMargin(salary, new Insets(5));
					
			//Requirements
			Label requirements = new Label("Job requirements: "+ job.getRequirements());
			requirements.setStyle("-fx-font-size:15px");
			VBox.setMargin(requirements, new Insets(5));
			requirements.setWrapText(true);
			
			//location
			Label location = new Label("Location: "+ job.getLocation());
			location.setStyle("-fx-font-size:15px");
			VBox.setMargin(location, new Insets(5));
			
			//open Positions
			Label openPositions = new  Label("Open Positions: " + job.getPositionsOpen());
			openPositions.setStyle("-fx-font-size:15px");
			VBox.setMargin(openPositions, new Insets(5));
			
			Button apply1 = new Button("Apply");
			apply1.setOnAction(e ->{
				
				GraphHandler.connectCompanyJobSeeker(GraphHandler.readGraphFromFile("data/graphFile.graph"),
						GraphHandler.findCompanyByJob(GraphHandler.readGraphFromFile("data/graphFile.graph"), job), jobSeeker);
				
				 Alert mes = new Alert(Alert.AlertType.CONFIRMATION, "Thank you for applying");
                 mes.showAndWait();
			});
			
			jobInformation.getChildren().addAll(salary,requirements,location,openPositions,apply1);
			
			VBox.setMargin(jobInformation, new Insets(10, 10, 10, 10));
			VBox.setMargin(apply1, new Insets(10,10,10,10));
			VBox.setMargin(summaryBox, new Insets(10,10,10,10));
			
			jobInformation.setAlignment(Pos.CENTER);
			jobInformation.setBorder(Border.stroke(Color.BLACK));
			
			if (right.getChildren().isEmpty()) {
				right.getChildren().addAll(summaryBox,jobInformation);
			} else {
				this.getChildren().remove(right);
				right = null;
				right = new VBox();
				right.getChildren().addAll(summaryBox,jobInformation);
				this.getChildren().add(right);
			}

		});

	}


	public void setProfile() {
		// set profile image
		try {
			Image image = new Image(new FileInputStream(new File("data", "pic1.png")));
			ImageView picture = new ImageView(image);

			// create a circle shape with the same size as the image
			Circle clip = new Circle();
			clip.setRadius(Math.min(image.getWidth(), image.getHeight()) / 2.0);
			clip.setCenterX(image.getWidth() / 2.0);
			clip.setCenterY(image.getHeight() / 2.0);

			// set the clip property of the ImageView
			picture.setClip(clip);

			Label name_Surname = new Label("Name: " + this.jobSeeker.getName());
			Label profession = new Label("Profession: "+ this.jobSeeker.getJobTitle());

			Button editProfile = new Button("Edit Profile");
			Button deleteP = new Button("Delete Profile");
			
			deleteP.setOnAction(event->{
				Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
				
				GraphHandler.removeNodeFromGraph(graph, jobSeeker);
				GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
				homePage.btnlogout.fire();
			});
			
			editProfile.setOnAction(event ->{
				// Create the alert and set the graph as its content
	            Alert alert = new Alert(AlertType.NONE);
	            alert.setTitle("Register As Job Seeker");
	            alert.setHeaderText(null);
	           
	            UserForm userForm = new UserForm(alert,jobSeeker);
	            userForm.setHomePage(homePage);
	            
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

			profile.setAlignment(Pos.CENTER);
			profile.setBorder(Border.stroke(Color.BLACK));

			profile.setStyle(
					"-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

			VBox.setMargin(picture, new Insets(10));
			VBox.setMargin(name_Surname, new Insets(10));
			VBox.setMargin(profession, new Insets(10));
			VBox.setMargin(editProfile, new Insets(10));

			profile.getChildren().addAll(picture, name_Surname, profession, editProfile,deleteP);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSearchField() throws FileNotFoundException {
		// create a TextField
		TextField textField = new TextField();
		textField.setPromptText("Search");
		textField.setFont(Font.font("Verdana", 12));
		textField.setMinHeight(30);
		textField.setMinWidth(400);
		searchBar.getChildren().add(textField);

		// create a search Button with a search icon
		Button searchButton = new Button();
		searchButton.setOnAction(event -> {
	        // get the search query entered by the user
	        String searchQuery = textField.getText().toLowerCase();
	        try {
				setJobProfile(searchQuery);
				setRight();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
		
		searchButton.setGraphic(new ImageView(new Image(new FileInputStream(new File("data/search.png")))));

		((ImageView) searchButton.getGraphic()).setFitWidth(20);
		((ImageView) searchButton.getGraphic()).setFitHeight(20);

		HBox.setMargin(textField, new Insets(10, 10, 10, 10));
		HBox.setMargin(searchButton, new Insets(10, 10, 10, 10));

		searchBar.getChildren().add(searchButton);
		searchBar.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.0); -fx-border-color: gray; -fx-border-width: 1px; -fx-border-radius: 10px;");

	}

	
	public void setJobProfile(String searchQuery) throws FileNotFoundException {
		jobs = null;
		jobs = new ListView<HBox>();
		Graph<VertexNode> graph = GraphHandler.readGraphFromFile("data/graphFile.graph");
		
		jobs.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.0);-fx-border-color: none; -fx-border-width: 0px; -fx-border-radius: 0px;");
		// set the list of jobs
		
		
		List<Vertex<VertexNode>> js =  null;
		
		if(searchQuery.length()<=0)
		{
			 js =  GraphHandler.getJobsRelatedToJobSeeker(graph, this.jobSeeker);
		}else
		{
			js = getAvailableJobs(searchQuery);
		}
		
		Stack<Job> relatedJobs = new Stack<>();
		for(Vertex<VertexNode> vertex : js)
		{
			relatedJobs.add((Job) vertex.getValue());
		}
		
		jobs.setCellFactory(new Callback<ListView<HBox>, ListCell<HBox>>() {
			@Override
			public ListCell<HBox> call(ListView<HBox> jobs) {
				return new ListCell<>() {
					@Override
					protected void updateItem(HBox jobHBox, boolean empty) {
						super.updateItem(jobHBox, empty);

						if (jobHBox != null) {
							setGraphic(jobHBox);
							setStyle(
									"-fx-background-color: rgba(255, 255, 255, 0.0);-fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");
						} else {
							// set the background color of the selected cell
							jobs.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
								if (newVal != null && newVal.equals(jobHBox)) {
									setStyle("-fx-background-color: ##5a5b5e; -fx-text-fill: green;");
								}
							});
						}
					}
				};
			}
		});

		// Add the jobs to the ListView
		while (!relatedJobs.empty()) {
			Job job = relatedJobs.pop();

			HBox jProfile = new HBox();
			ImageView companyImage = new ImageView(new Image(new FileInputStream(new File("data", "company.png"))));
			companyImage.setFitHeight(50);
			companyImage.setFitWidth(50);

			Label jobId = new Label("#" + job.getId());
			jobId.setStyle("-fx-font-size:20px");
			Label jobTitle = new Label(job.getTitle());
			jobTitle.setStyle("-fx-font-size:20px; ");
			Label salary = new Label("Salary: R" + String.valueOf(job.getSalary()));
			salary.setStyle("-fx-font-size:20px;");

			HBox.setMargin(companyImage, new Insets(5, 5, 5, 5));
			HBox.setMargin(jobTitle, new Insets(15, 10, 0, 10));
			HBox.setMargin(salary, new Insets(15, 10, 0, 10));
			HBox.setMargin(jobId, new Insets(15, 10, 0, 10));

			// jProfile.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);
			// -fx-border-color: grey; -fx-border-width: 1px; -fx-border-radius: 10px;");

			jProfile.getChildren().addAll(companyImage, jobId, jobTitle, salary);
			jobs.getItems().add(jProfile);
			
		}
		
		if(middle.getChildren().isEmpty())
		{
			middle.getChildren().add(jobs);
		}
		else
		{
			middle.getChildren().clear();
			middle.getChildren().addAll(searchBar,jobs);
		}
		
		

	}

}
