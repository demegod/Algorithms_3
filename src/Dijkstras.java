/*
 * Uses the GraphMaker to make the graph, asks the user for the source vertex 
 * and destination vertex, and then runs Dijkstra's algorithm. The shortest 
 * path length as well as the actual path should be printed to the screen, 
 * then the program should terminate. See the examples in assignment for the 
 * appropriate formatting.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

//Edited by Dimitrios Vlahos
public class Dijkstras {
	HashMap<DijkstraVertex, DijkstraVertex> parent = new HashMap<DijkstraVertex, DijkstraVertex>();

	// Constructor: prompt user to enter file name, then
	// call runShortestPath with the file name.
	public Dijkstras() throws FileNotFoundException {
		System.out.println("Enter the file name");
		Scanner in = new Scanner(System.in);
		runShortestPath(in.nextLine());

	}

	// Make a graph and run Dijkstra's algorithm.
	public void runShortestPath(String fileName) throws FileNotFoundException{
		// Create a new GraphMaker object and use it to make a new AdjListGraph.
		GraphMaker myGraph = new GraphMaker();
		AdjListGraph newGraph = myGraph.makeGraphFromFile(fileName);
		
		// Print the graph out and prompt the user to enter the starting 
		// and ending vertices.
		newGraph.print();
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the starting vertex");
		String start = in.nextLine();
		System.out.println("Enter the ending vertex");
		String end = in.nextLine();
		
		DijkstraVertex startVertex =  myGraph.getVertex(start);
		DijkstraVertex endVertex = myGraph.getVertex(end);
		
//		System.out.println(startVertex.getName());
//		System.out.println(endVertex.getName());
		
		
		// Call the shortestPath method with the graph and the source Vertex
		shortestPath(newGraph, startVertex);
		
		// Get the distance to the destination Vertex and print it out.
		System.out.println("The shortest path is " + endVertex.getDistance());
		
		// Find and print the path by following back from the destination Vertex to each
		// parent. Note that, in the shortestPath method, you'll have stored 
		// the parent for each Vertex in the HashMap declared at the top of this file).
		ArrayList<DijkstraVertex> test = new ArrayList<DijkstraVertex>();
		while(parent.containsKey(endVertex)){
			test.add(endVertex);
			endVertex = parent.get(endVertex);		
		}
		test.add(endVertex);
		for (int i = test.size() - 1; i >= 0; i --){
			System.out.print(test.get(i).getName() + "::");
		}
		
	}

	// Given the graph and source vertex, run Dijkstra's algorithm.
	public void shortestPath(AdjListGraph graph, DijkstraVertex source){
		// Initialize the distance to all the vertices in the graph to infinity,
		// except the source vertex, which should be 0.
		
		ArrayList<Vertex> myVertices = graph.getVertices();
		
		for(Vertex i : myVertices){
			DijkstraVertex dVertex = (DijkstraVertex)i;
			
			if (dVertex.equals(source)){
//				System.out.println("Found the source");
				dVertex.setDistance(0.0);
			}
			else{
				dVertex.setDistance(Double.POSITIVE_INFINITY);
			}
		}
		
		// Make a PriorityQueue (imported above in Java.Util.PriorityQueue)
		// of DijkstraVertex objects.
		PriorityQueue<DijkstraVertex> vertexQueue = new PriorityQueue<DijkstraVertex>();
		for (int i = 0 ; i < myVertices.size() ; i++){
			DijkstraVertex aVertex = (DijkstraVertex) myVertices.get(i);
			vertexQueue.add(aVertex);
		}
		
		// Keep looping as long as the priorty queue is not empty, doing the following:
		// - get the next closest Vertex from the priority queue
		// - get its adjacent vertices
		// - for each adjacent vertex, check if the distance to get there from the 
		//   current vertex would be shorter than its current distance. If so, remove
		//   it from the queue, update its distance, and re-add it. Keep track of
		//   the which vertex led to this vertex using the parent HashMap declared
		//   at the top of the file.
		while (vertexQueue.size() != 0){
			DijkstraVertex closest = vertexQueue.remove();
//			System.out.println(closest.getName() + " with distance equal " + closest.getDistance());
			Collection<Vertex> neighbours = closest.getAdjacentVertices();
			for (Vertex i : neighbours){
				DijkstraVertex dNeighbour = (DijkstraVertex)i;			
				if(dNeighbour.getDistance() > closest.getDistance()+closest.getEdgeWeight(dNeighbour)){
					vertexQueue.remove(dNeighbour);
					dNeighbour.setDistance(closest.getDistance()+closest.getEdgeWeight(dNeighbour));
					parent.remove(dNeighbour);
					parent.put(dNeighbour, closest);
					vertexQueue.add(dNeighbour);
				}
				
			}
		}
		
	}
}
