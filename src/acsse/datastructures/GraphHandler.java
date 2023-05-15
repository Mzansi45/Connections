package acsse.datastructures;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import acsse.datastructures.Graph.Edge;
import acsse.datastructures.Graph.Vertex;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GraphHandler {
	
	/**
	 * Finds a job seeker in the graph based on the given username and password. using BFS Algorithm
	 *
	 * @param graph     The graph containing the vertices.
	 * @param username  The username of the job seeker.
	 * @param password  The password of the job seeker.
	 * @return The vertex representing the job seeker if found, or null if not found.
	 */
	public static Vertex<VertexNode> findJobSeeker(Graph<VertexNode> graph, String username, String password) {
	    Queue<Vertex<VertexNode>> queue = new LinkedList<>(graph.getVertices());
	    Vertex<VertexNode> returnNode = null;

	    while (!queue.isEmpty()) {
	        returnNode = getJobSeeker(queue, queue.poll(), username, password);

	        if (returnNode != null) {
	            return returnNode;
	        }
	    }

	    return null;
	}

	/**
	 * Recursive helper method to search for a job seeker starting from the given vertex.
	 *
	 * @param queue     The queue of vertices to be explored.
	 * @param vertex    The current vertex to check.
	 * @param username  The username of the job seeker.
	 * @param password  The password of the job seeker.
	 * @return The vertex representing the job seeker if found, or null if not found.
	 */
	private static Vertex<VertexNode> getJobSeeker(Queue<Vertex<VertexNode>> queue, Vertex<VertexNode> vertex, String username, String password) {
	    if (vertex.getValue() instanceof JobSeeker
	            && ((JobSeeker) vertex.getValue()).getPassword().equals(password)
	            && ((JobSeeker) vertex.getValue()).getUsername().equals(username)) {
	        return vertex;
	    }

	    for (Edge<VertexNode> edge : vertex.getEdges()) {
	        if (edge.getToVertex().getValue() instanceof JobSeeker
	                && ((JobSeeker) edge.getToVertex().getValue()).getPassword().equals(password)
	                && ((JobSeeker) edge.getToVertex().getValue()).getUsername().equals(username)) {
	            return edge.getToVertex();
	        }
	        queue.remove(edge.getToVertex());
	    }

	    return null; // Job seeker not found
	}


	/**
	 * This method finds the Company with the given username and password in the graph using BFS algorithm.
	 *
	 * @param graph     the graph containing the Companies
	 * @param username  the username of the Company to find
	 * @param password  the password of the Company to find
	 * @return          the Company with the given username and password, or null if not found
	 */
	public static Vertex<VertexNode> findCompany(Graph<VertexNode> graph, String username, String password) {
	    Queue<Vertex<VertexNode>> queue = new LinkedList<>(graph.getVertices());

	    while (!queue.isEmpty()) {
	        Vertex<VertexNode> currentVertex = queue.poll();

	        if (currentVertex.getValue() instanceof Company) {
	            Company company = (Company) currentVertex.getValue();
	            if (company.getUsername().equals(username) && company.getPassword().equals(password)) {
	                return currentVertex;
	            }
	        }

	        for (Edge<VertexNode> edge : currentVertex.getEdges()) {
	            Vertex<VertexNode> adjacentVertex = edge.getToVertex();
	            if (!queue.contains(adjacentVertex)) {
	                queue.offer(adjacentVertex);
	            }
	        }
	    }

	    return null; // Company not found
	}

	
	public static List<Vertex<VertexNode>> getJobsRelatedToJobSeeker(Graph<VertexNode> graph, JobSeeker jobSeeker) {
	    List<Vertex<VertexNode>> jobs = new ArrayList<>();
	    
	    Vertex<VertexNode> jobSeekerNode = GraphHandler.findJobSeeker(graph, jobSeeker.getUsername(), jobSeeker.getPassword());

	    // Loop through all edges connected to the company vertex
	    for (Edge<VertexNode> edge : jobSeekerNode.getEdges()) {
	        // Check if the edge is outgoing from the company vertex and the target vertex is a job vertex
	        if (edge.getFromVertex() == jobSeekerNode && (edge.getToVertex().getValue() instanceof Job)) {
	            // Add the job vertex to the list
	            jobs.add(edge.getToVertex());
	        }
	    }

	    return jobs;
	}
	
	public static void addCompanyJobEdges(Graph<VertexNode> graph, Vertex<VertexNode> companyNode, Vertex<VertexNode> jobNode) {
		Edge<VertexNode> companyToJob = new Edge<>(0,companyNode,jobNode);
		Edge<VertexNode> jobToCompany = new Edge<>(0,jobNode,companyNode);
		
		companyNode.getEdges().add(companyToJob);
		jobNode.getEdges().add(jobToCompany);
		
		graph.getEdges().add(jobToCompany);
		graph.getEdges().add(companyToJob);	
	}
	
	public static void printEdges(Graph<VertexNode> graph) {
	    for (Edge<VertexNode> edge : graph.getEdges()) {
	        Vertex<VertexNode> from = edge.getFromVertex();
	        Vertex<VertexNode> to = edge.getToVertex();

	        String fromLabel = "";
	        String toLabel = "";

	        if (from.getValue() instanceof Job) {
	            fromLabel = ((Job) from.getValue()).getTitle();
	        } else if (from.getValue() instanceof JobSeeker) {
	            fromLabel = ((JobSeeker) from.getValue()).getName();
	        } else if (from.getValue() instanceof Company) {
	            fromLabel = ((Company) from.getValue()).getName();
	        }

	        if (to.getValue() instanceof Job) {
	            toLabel = ((Job) to.getValue()).getTitle();
	        } else if (to.getValue() instanceof JobSeeker) {
	            toLabel = ((JobSeeker) to.getValue()).getName();
	        } else if (to.getValue() instanceof Company) {
	            toLabel = ((Company) to.getValue()).getName();
	        }

	        System.out.println(fromLabel + " to " + toLabel);
	    }
	}

	public static List<Vertex<VertexNode>> getJobsPostedByCompany(Graph<VertexNode> graph, Company company) {
	    List<Vertex<VertexNode>> jobs = new ArrayList<>();
	    
	    Vertex<VertexNode> companyNode = GraphHandler.findCompany(graph,company.getUsername(), company.getPassword());

	    // Loop through all edges connected to the company vertex
	    for (Edge<VertexNode> edge : companyNode.getEdges()) {
	    	
	        // Check if the edge is outgoing from the company vertex and the target vertex is a job vertex
	        if (edge.getFromVertex() == companyNode && (edge.getToVertex().getValue() instanceof Job)) {
	            // Add the job vertex to the list
	            jobs.add(edge.getToVertex());
	        }
	    }

	    return jobs;
	}
	
	public static boolean isEqualsTo(Vertex<VertexNode> first, Vertex<VertexNode> second) {
	    if (first.getValue() != second.getValue()) { // check if same instance
	        return false;
	    } else 
	    {
	    	if(first.getValue()  instanceof JobSeeker && second.getValue()  instanceof JobSeeker)
	    	{
	    		if(((JobSeeker) first.getValue()).getId() != ((JobSeeker) second.getValue()).getId())
	    		{
	    			return false;
	    		}
	    	}
	    	else if(first.getValue() instanceof Job && second.getValue() instanceof Job)
	    	{
	    		if(((Job) first.getValue()).getId() != ((Job) second.getValue()).getId())
	    		{
	    			return false;
	    		}
	    	}else
	    	{
	    		if(((Company) first.getValue()).getId() != ((Company) second.getValue()).getId())
	    		{
	    			return false;
	    		}
	    	}
	    }
	    	
	    if (first == null || second == null) { // check if either one is null
	        return false;
	    } 
	    
	    if (!first.getValue().equals(second.getValue())) { // check value if the same
	        return false;
	    }
	    
	    return true;
	}

	public static void connectJobSeekerToJob(Graph<VertexNode> graph, Vertex<VertexNode> jobSeeker, Vertex<VertexNode> job) {
	    boolean edgeExists = false;
	    
	    // Check if there is already an edge between the job seeker and the job
	    for (Edge<VertexNode> edge : jobSeeker.getEdges()) {
	        if (edge.getToVertex().getValue().equals(job.getValue())) {
	            edgeExists = true;
	            break;
	        }
	    }
	    
	    // If the edge doesn't exist, create it and add it to both nodes' edge lists and the graph's edge list
	    if (!edgeExists) {
	        Edge<VertexNode> jobSeekerToJobEdge = new Edge<>(0, jobSeeker, job);
	        jobSeeker.addEdge(jobSeekerToJobEdge);
	        graph.getEdges().add(jobSeekerToJobEdge);
	        
	        Edge<VertexNode> jobToJobSeekerEdge = new Edge<>(0, job, jobSeeker);
	        job.addEdge(jobToJobSeekerEdge);
	        graph.getEdges().add(jobToJobSeekerEdge);
	    }
	}

	public static void removeNodeFromGraph(Graph<VertexNode> graph, VertexNode node1) {
	    Vertex<VertexNode> node = null;
	    
	    if (node1 instanceof Company) {
	        node = findCompany(graph, ((Company) node1).getUsername(), ((Company) node1).getPassword());
	    } else if (node1 instanceof JobSeeker) {
	        node = findJobSeeker(graph, ((JobSeeker) node1).getUsername(), ((JobSeeker) node1).getPassword());
	    }else
	    {
	    	node = getJobVertexById(graph, ((Job)node1).getId());
	    }
	    
	    // Remove all edges that have a relationship with the node
	    Iterator<Edge<VertexNode>> edgeIterator = graph.getEdges().iterator();
	    while (edgeIterator.hasNext()) {
	        Edge<VertexNode> edge = edgeIterator.next();
	        if (edge.getFromVertex() == node || edge.getToVertex() == node) {
	            edgeIterator.remove();
	        }
	    }
	    
	    // Remove the node from the graph's list of vertices
	    graph.getVertices().remove(node);
	}
	
	public static void removeJob(Graph<VertexNode> graph, VertexNode node1)
	{
		Vertex<VertexNode> node = getJobVertexById(graph, ((Job)node1).getId());
		ArrayList<Edge<VertexNode>> edgesToRemove = new ArrayList<>();
		
		for(Edge<VertexNode> edge :graph.getEdges())
		{
			if(edge.getFromVertex().x == node.x && edge.getFromVertex().y == node.y ||
					edge.getToVertex().x == node.x && edge.getToVertex().y == node.y)
			{
				edgesToRemove.add(edge);	
				ArrayList<Edge<VertexNode>> edgesToRemove1 = new ArrayList<>();
				for(Edge<VertexNode> ed :edge.getFromVertex().getEdges())
				{
					if(ed.getFromVertex().x == node.x && ed.getFromVertex().y == node.y ||
					ed.getToVertex().x == node.x && ed.getToVertex().y == node.y)
					{
						edgesToRemove1.add(ed);
					}
				}
				
				edge.getFromVertex().getEdges().removeAll(edgesToRemove1);
			}
		}
		
		ArrayList<Vertex<VertexNode>> nd = new  ArrayList<>();
		for(Vertex<VertexNode> n:graph.getVertices())
		{
			if(n.x == node.x && n.y==node.y)
			{
				nd.add(n);
			}
		}
		
		graph.getVertices().removeAll(nd);
		
		graph.getEdges().removeAll(edgesToRemove);
	}

	public static Vertex<VertexNode> getJobVertexById(Graph<VertexNode> graph, int jobId) {
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (vertex.getValue() instanceof Job) {
	            Job job = (Job) vertex.getValue();
	            if (job.getId() == jobId) {
	                return vertex;
	            }
	        }
	    }
	    return null;
	}
	
	/**
	* Removes the vertex with the given value from the graph. Also removes all edges
	* connected to the vertex.
	* @param graph The graph from which to remove the vertex.
	* @param value The value of the vertex to remove.
	*/
	public static void deleteVertex(Graph<VertexNode> graph, VertexNode value) {
	    // Find the vertex with the given value
	    Vertex<VertexNode> vertex = null;
	    for (Vertex<VertexNode> v : graph.getVertices()) {
	        if (v.getValue().equals(value)) {
	            vertex = v;
	            break;
	        }
	    }

	    if (vertex != null) {
	        // Remove all edges connected to the vertex
	        List<Edge<VertexNode>> edgesToRemove = new ArrayList<>();
	        for (Edge<VertexNode> e : graph.getEdges()) {
	            if (e.getFromVertex().equals(vertex) || e.getToVertex().equals(vertex)) {
	                edgesToRemove.add(e);
	            }
	        }
	        
	        for (Edge<VertexNode> e : edgesToRemove) {
	            graph.getEdges().remove(e);
	        }

	        // Remove the vertex itself
	        graph.getVertices().remove(vertex);
	    }
	}

	
	/**
	 * This method reads a graph from a file.
	 * 
	 * @param filePath  the path to the file containing the graph
	 * @return          the graph read from the file, or null if there was an error reading the file
	 */
	public static Graph<VertexNode> readGraphFromFile(String filePath){
		try
		{
		    FileInputStream fileInputStream = new FileInputStream(filePath);
		    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		    
		    @SuppressWarnings("unchecked")
		    
			Graph<VertexNode> graph = (Graph<VertexNode>) objectInputStream.readObject();
		    
		    objectInputStream.close();
		    return graph;
		}catch(IOException e)
		{
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	}

	/**
	 * This method writes a graph to a file.
	 * 
	 * @param graph     the graph to write to a file
	 * @param filePath  the path to the file to write the graph to
	 * @throws IOException  if there was an error writing to the file
	 */
	public static void writeGraphToFile(Graph<VertexNode> graph, String filePath){	
		try
		{
		    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		    objectOutputStream.writeObject(graph);
		    objectOutputStream.close();
		}catch(IOException e)
		{
			e.printStackTrace();
			return;
		}

	}
	
	
	/**
	* Adds a job seeker to the graph and connects them to any jobs in the graph that match their criteria.
	* @param graph the graph to add the job seeker to
	* @param jobSeeker the job seeker to add to the graph
	*/
	public static void addJobSeekerToGraph(Graph<VertexNode> graph, JobSeeker jobSeeker) {
	    // Find jobs that match the job seeker's criteria
	    HashSet<Vertex<VertexNode>> jobs = new HashSet<>();
	    HashSet<Vertex<VertexNode>> visited = new HashSet<>();
	    
	    searchJobs(graph, visited, jobs, jobSeeker);
	  
	    // Create job seeker vertex
	    Vertex<VertexNode> jobseekerNode = new Vertex<>(jobSeeker);
	  
	    // Loop through all the jobs and create edges between the job and the job seeker vertex
	    Set<Vertex<VertexNode>> processedJobs = new HashSet<>();
	    for (Vertex<VertexNode> jobNode : jobs) {
	        if (!processedJobs.contains(jobNode)) {
	            processedJobs.add(jobNode);          
	            connectJobSeekerToJob(graph, jobseekerNode, jobNode);
	        }
	    }
	  
	    // Add the job seeker vertex to the graph
	    graph.getVertices().add(jobseekerNode);
	    writeGraphToFile(graph,"data/graphFile.graph");
	}
	
	public static void traverseGraph(Graph<VertexNode> graph) {
	    Set<VertexNode> visited = new HashSet<>();
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (!visited.contains(vertex.getValue())) {
	            traverseVertex(vertex, visited);
	        }
	        if (visited.size() == graph.getVertices().size()) {
	            break;
	        }
	    }
	}


	private static void traverseVertex(Vertex<VertexNode> vertex, Set<VertexNode> visited) {
	    visited.add(vertex.getValue());
	    

	    List<Edge<VertexNode>> edges = vertex.getEdges();
	    for (Edge<VertexNode> edge : edges) {
	        Vertex<VertexNode> neighbor = edge.getToVertex();
	        if (!visited.contains(neighbor.getValue())) {
	            traverseVertex(neighbor, visited);
	        }
	    }
	}
	
	/**
	 * Recursively searches a graph of vertices for jobs that match a given job seeker's criteria.
	 * The function stores the matching jobs in a Set of Jobs.
	 * @param graph The graph of vertices to search.
	 * @param visited A Set of already visited vertices to prevent revisiting the same vertex.
	 * @param jobs A Set of Jobs that match the job seeker's criteria.
	 * @param jobSeeker The JobSeeker object that specifies the job search criteria.
	 */
	public static void searchJobs(Graph<VertexNode> graph, Set<Vertex<VertexNode>> visited, Set<Vertex<VertexNode>> jobs, JobSeeker jobSeeker) {
	    // Get the list of vertices in the graph
	    List<Vertex<VertexNode>> vertices = graph.getVertices();
	    // Loop through all the vertices and perform the search on each one
	    for (Vertex<VertexNode> vertex : vertices) {
	        if (!visited.contains(vertex)) {
	            searchJobsHelper(vertex, visited, jobs, jobSeeker);
	        }
	    }
	}

	/**
	 * Helper function that recursively searches a graph of vertices for jobs that match a given job seeker's criteria.
	 * The function stores the matching jobs in a Set of Jobs.
	 * @param vertex The starting vertex of the search.
	 * @param visited A Set of already visited vertices to prevent revisiting the same vertex.
	 * @param jobs A Set of Jobs that match the job seeker's criteria.
	 * @param jobSeeker The JobSeeker object that specifies the job search criteria.
	 */
	private static void searchJobsHelper(Vertex<VertexNode> vertex, Set<Vertex<VertexNode>> visited, Set<Vertex<VertexNode>> jobs, JobSeeker jobSeeker) {
	    // Add the current vertex to the visited Set to prevent revisiting it
	    visited.add(vertex);
	    // Check if the current vertex is a Job object and if it matches the job seeker's criteria
	    VertexNode node = vertex.getValue();
	    if (node instanceof Job) {
	        Job job = (Job) node;
	        if ((job.getLocation().equals(jobSeeker.getProvince())
	                || job.getExperienceLevel().equals(jobSeeker.getEducationLevel())
	                || job.getTitle().equals(jobSeeker.getJobTitle())
	                || jobSeeker.getSkills().stream().anyMatch(job.getRequirements()::contains))
	                && !jobs.contains(vertex)) {
	            // If the Job matches the job seeker's criteria and is not already in the jobs Set, add it to the jobs Set
	        	
	        	boolean jobExists = false;
	        	for (Vertex<VertexNode> j : jobs) {
	        	    if (job.getId() == ((Job) j.getValue()).getId()) {
	        	        jobExists = true;
	        	        break;
	        	    }
	        	}

	        	if (!jobExists) {
	        	    jobs.add(vertex);
	        	}

	        }
	    }
	    // Recursively search all unvisited neighbor vertices
	    for (Edge<VertexNode> edge : vertex.getEdges()) {
	        Vertex<VertexNode> neighbor = edge.getToVertex();
	        if (!visited.contains(neighbor)) {
	            searchJobsHelper(neighbor, visited, jobs, jobSeeker);
	        }
	    }
	}



	
	/**
	 * Searches a graph for vertices containing a value of an instance of JobSeeker and returns a list of those vertices.
	 * @param graph The graph to search.
	 * @return A list of vertices containing a value of an instance of JobSeeker.
	 */
	public static List<Vertex<VertexNode>> searchJobSeekers(Graph<VertexNode> graph) {
	    List<Vertex<VertexNode>> jobSeekerVertices = new ArrayList<>();
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        VertexNode node = vertex.getValue();
	        if (node instanceof JobSeeker) {
	            jobSeekerVertices.add(vertex);
	        }
	    }
	    return jobSeekerVertices;
	}

	public static Vertex<VertexNode> findCompanyById(Graph<VertexNode> graph, int companyId) {
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (vertex.getValue() instanceof Company && ((Company) vertex.getValue()).getId() == companyId) {
	            return vertex;
	        }
	    }
	    return null;
	}



	/**
	 * Adds a vertex to a graph if it does not already exist in the graph.
	 * The function also adds all edges connected to the vertex to the graph's edges Set.
	 * @param graph The graph to add the vertex to.
	 * @param vertex The vertex to add to the graph.
	 */
	public static void addVertexToGraph(Graph<VertexNode> graph, Vertex<VertexNode> vertex) {
	    // Check if the vertex already exists in the graph
	    if (!graph.getVertices().contains(vertex)) {
	        // If the vertex does not exist in the graph, add it to the graph's vertices Set
	        graph.getVertices().add(vertex);
	        // Add all edges connected to the vertex to the graph's edges Set
	        for (Edge<VertexNode> edge : vertex.getEdges()) {
	            graph.getEdges().add(edge);
	        }
	    }
	}
	
	public static void connectCompanyJobSeeker(Graph<VertexNode> graph, Company company, JobSeeker jobSeeker) {
	    Vertex<VertexNode> companyNode = findCompanyById(graph, company.getId());
	    Vertex<VertexNode> jobSeekerNode = findJobSeeker(graph, jobSeeker.getUsername(), jobSeeker.getPassword());

	    // Check if the connection already exists
	    for (Edge<VertexNode> edge : companyNode.getEdges()) {
	        if (edge.getToVertex().equals(jobSeekerNode)) {
	            // Show alert and return if the connection already exists
	            Alert alert = new Alert(Alert.AlertType.ERROR, "You have Already Applied for this Job.", ButtonType.OK);
	            alert.showAndWait();
	            return;
	        }
	    }

	    // Create edges to connect company and job seeker nodes
	    Edge<VertexNode> companyToJobSeekerEdge = new Edge<>(0, companyNode, jobSeekerNode);
	    Edge<VertexNode> jobSeekerToCompanyEdge = new Edge<>(0, jobSeekerNode, companyNode);

	    // Add the edges to the company and job seeker nodes
	    companyNode.getEdges().add(companyToJobSeekerEdge);
	    jobSeekerNode.getEdges().add(jobSeekerToCompanyEdge);

	    // Add the edges to the graph
	    graph.getEdges().add(companyToJobSeekerEdge);
	    graph.getEdges().add(jobSeekerToCompanyEdge);

	    // Write the updated graph to file
	    GraphHandler.writeGraphToFile(graph, "data/graphFile.graph");
	}
	
	public static Company findCompanyByJob(Graph<VertexNode> graph, Job job) {
		List<Vertex<VertexNode>> jobs = getAllJobVertices(graph); 
		
		Vertex<VertexNode> jobNode = null;
		
	    for(Vertex<VertexNode> v:jobs)
	    {
	    	if(((Job) v.getValue()).getId() == job.getId())
	    	{
	    		jobNode = v;
	    	}
	    }
	    
	    for(Edge<VertexNode> edge:jobNode.getEdges())
	    {
	    	if(edge.getToVertex().getValue() instanceof Company)
	    	{
	    		return (Company) edge.getToVertex().getValue();
	    	}
	    }
	    
	    return null;
	}



	public static List<Vertex<VertexNode>> getAllJobVertices(Graph<VertexNode> graph) {
	    List<Vertex<VertexNode>> jobVertices = new ArrayList<>();
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (vertex.getValue() instanceof Job) {
	            jobVertices.add(vertex);
	        }
	    }
	    return jobVertices;
	}

	public static List<Vertex<VertexNode>> searchJobs(Graph<VertexNode> graph, String searchString) {
	    List<Vertex<VertexNode>> matchingJobs = new ArrayList<>();

	    // Split search string into separate words
	    String[] searchWords = searchString.split(" ");

	    // Loop through all job vertices in the graph
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (vertex.getValue() instanceof Job) {
	            Job job = (Job) vertex.getValue();

	            // Check if any of the search words match job criteria
	            boolean jobMatchesSearch = false;
	            for (String word : searchWords) {
	                if (job.getTitle().toLowerCase().contains(word.toLowerCase()) ||
	                        job.getDescription().toLowerCase().contains(word.toLowerCase()) ||
	                        job.getLocation().toLowerCase().contains(word.toLowerCase()) ||
	                        job.getExperienceLevel().toLowerCase().contains(word.toLowerCase()) ||
	                        job.getRequirements().toLowerCase().contains(word.toLowerCase()) ||
	                        job.getCompany().toLowerCase().contains(word.toLowerCase())) {
	                    jobMatchesSearch = true;
	                    break;
	                }
	            }

	            // If job matches search, add its vertex to the list of matching jobs
	            if (jobMatchesSearch) {
	                matchingJobs.add(vertex);
	            }
	        }
	    }

	    return matchingJobs;
	}

	public static Job getJobById(Graph<VertexNode> graph, int jobId) {
	    for (Vertex<VertexNode> vertex : graph.getVertices()) {
	        if (vertex.getValue() instanceof Job) {
	            Job job = (Job) vertex.getValue();
	            if (job.getId() == jobId) {
	                return (Job) vertex.getValue();
	            }
	        }
	    }
	    return null;
	}
	
}
