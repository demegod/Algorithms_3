import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * The GraphMaker class asks the user to enter the name of a file. 
 * The code should then read in the input file and produce the corresponding graph.
 * The first line of the file will be the number of vertices. 
 * The next lines will give the edges as a table where table(i,j) gives the 
 * edge weight between vertices i and j. 
 * The two provided files are in this format.
 */
//Edited by Dimitrios Vlahos
public class GraphMaker {
	public static DijkstraVertex vertices[];
	
	public AdjListGraph makeGraphFromFile(String fileName) throws FileNotFoundException {
		// Create a new directed AdjListGraph and read from the file to
		// add DijkstraVertex and Edge object to the graph.
		AdjListGraph graph = new AdjListGraph(true);
		File myFile = new File(fileName);
		Scanner file = new Scanner(myFile);
		
		int verticesNum = Integer.parseInt(file.nextLine());
		vertices = new DijkstraVertex[verticesNum];

		for (int i = 0; i < verticesNum; i++){
			DijkstraVertex toVertex = new DijkstraVertex(file.next());
			graph.addVertex(toVertex);
			vertices[i] = toVertex;
		}
		for (int i = 0; i < verticesNum; i++){
			DijkstraVertex fromVertex = new DijkstraVertex(file.next());
			for (int j = 0; j < verticesNum; j++){
				double edgeWeight = file.nextDouble();
				fromVertex.setDistance(edgeWeight);
				if (edgeWeight != 0){
					graph.addEdge(vertices[i], vertices[j], fromVertex.getDistance());
					
				}
			}
		}
		
		return graph;
	}
	public DijkstraVertex getVertex(String name){
		for (int i = 0; i < vertices.length; i++){
			if (vertices[i].getName().equals(name)){
				return vertices[i];
			}
		}
		return vertices[0];
	}
}
