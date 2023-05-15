package acsse.gui;

import acsse.datastructures.Company;
import acsse.datastructures.Graph;
import acsse.datastructures.Graph.Edge;
import acsse.datastructures.Graph.Vertex;
import acsse.datastructures.GraphHandler;
import acsse.datastructures.Job;
import acsse.datastructures.JobSeeker;
import acsse.datastructures.VertexNode;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;



public class GraphView extends Pane{
	public GraphView(Graph<VertexNode> graph)
	{   

		GraphHandler.printEdges(graph);
		for(Vertex<VertexNode> ve: graph.getVertices())
		{
		    Circle circle = new Circle();
		    circle.setRadius(10);
		    circle.setCenterX(ve.x);
		    circle.setCenterY(ve.y);
		    
		    if(ve.getValue() instanceof JobSeeker)
		    {
		        circle.setFill(Color.BLACK);
		        Label label = new Label(((JobSeeker)ve.getValue()).getName());
		        label.setLayoutX(ve.x + 10); // adjust x position of label
		        label.setLayoutY(ve.y-5); // adjust y position of label
		        this.getChildren().add(label);
		    }
		    else if(ve.getValue() instanceof Job)
		    {
		        circle.setFill(Color.GREEN);
		        Label label = new Label(((Job)ve.getValue()).getTitle());
		        label.setLayoutX(ve.x + 10); // adjust x position of label
		        label.setLayoutY(ve.y-5); // adjust y position of label
		        this.getChildren().add(label);
		    }
		    else
		    {
		        circle.setFill(Color.BLUE);
		        Label label = new Label(((Company)ve.getValue()).getName());
		        label.setLayoutX(ve.x + 10); // adjust x position of label
		        label.setLayoutY(ve.y-5); // adjust y position of label
		        this.getChildren().add(label);
		    }
		    this.getChildren().add(circle);
		}


	    Line line = new Line();
	    line.setStartX(0);
	    line.setEndX(800);
	    line.setStartY(550);
	    line.setEndY(550);
	    this.getChildren().add(line);


	    // Add circles at the bottom of the graph to represent the different nodes
	    Circle jobseekerCircle = new Circle(50, 570, 10, Color.BLACK);
	    Circle jobCircle = new Circle(150, 570, 10, Color.GREEN);
	    Circle companyCircle = new Circle(250, 570, 10, Color.BLUE);
	    getChildren().addAll(jobseekerCircle, jobCircle, companyCircle);

	    // Add labels to the circles
	    Label jobseekerLabel = new Label("JobSeeker");
	    Label jobLabel = new Label("Job");
	    Label companyLabel = new Label("Company");
	    jobseekerLabel.setLayoutX(65);
	    jobseekerLabel.setLayoutY(565);
	    jobLabel.setLayoutX(165);
	    jobLabel.setLayoutY(565);
	    companyLabel.setLayoutX(265);
	    companyLabel.setLayoutY(565);
	    getChildren().addAll(jobseekerLabel, jobLabel, companyLabel);

	    // Draw the edges
	    for(Edge<VertexNode> edge: graph.getEdges())
	    {
	    	Vertex<VertexNode> from = edge.getFromVertex();
	 	    Vertex<VertexNode> to = edge.getToVertex();
	        
	 	    double startX = from.x;
		    double startY = from.y;
		    double endX = to.x;
		    double endY = to.y;
		    
		    Line edgeLine = new Line(startX, startY, endX, endY); 
		    edgeLine.setStroke(Color.GRAY);
		    
		    edgeLine.setStrokeWidth(0.5);
		    edgeLine.setOpacity(0.5);

	        // Calculate midpoint for the edge label
	        double midX = (startX + endX) / 2.0;
	        double midY = (startY + endY) / 2.0;

	        // Create and position the edge label
	        String label = edge.relationship;
	        Label edgeLabel = new Label(label);
	        edgeLabel.setLayoutX(midX);
	        edgeLabel.setLayoutY(midY);
	        
	        this.getChildren().addAll(edgeLine,edgeLabel);
	    }   
	}    
}
