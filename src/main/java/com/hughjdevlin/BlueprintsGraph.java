/**
 * A Blueprints graph
 * (therefore a digraph and a multi-graph)
 * implemented as a Neo4j graph
 * 
 * http://www.tinkerpop.com/docs/javadocs/blueprints/2.2.0/
 * http://github.com/tinkerpop/blueprints/wiki
 * 
 * 2012-03-15 HJD
 */
package com.hughjdevlin;

import java.io.File;
import java.io.IOException;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

/**
 * @author hugh
 *
 */
public class BlueprintsGraph extends Neo4jGraph {
	private final String directory; // directory for disk file version of Neo4j graph
	
	/**
	 * construct from Neo4j database on disk in directory
	 * @param directory
	 */
	public BlueprintsGraph(final String directory) {
		super(directory);
		this.directory = directory;
	}
	
	public static void delete(String directory) {
		try {
			FileUtils.deleteDirectory(new File(directory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * shutdown and optionally clean up graph database files;
	 * clean-up after shutdown is useful in unit tests
	 * @see com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph#shutdown()
	 */
	public void shutdown(boolean delete) {
		shutdown(); // serialize & close
		if(delete)
			delete(directory);
	}
	
	/**
	 * @param iterable
	 * @return size
	 */
	private int size(Iterable<?> iterable) {
		return IteratorUtils.toList(iterable.iterator()).size();
	}
	
	public int edgeCount() {
		return size(getEdges());
	}
	
	public int vertexCount() {
		return size(getVertices());
	}
	
	/**
	 * Wasserman & Faust 4.7
	 * @return mean degree
	 */
	public double meanDegree() {
		return (double) edgeCount() / vertexCount();
	}
	
	public String getDescription() {
		String result = 
			"Vertices=" + vertexCount() + "\n" +
			"Dyads=" + (vertexCount() * vertexCount()) + "\n" +
			"Possible directed edges (single-mode)=" + (2 * vertexCount() * vertexCount()) + "\n" +
			"Edges=" + edgeCount() + "\n" +
			"Mean degree=" + meanDegree() + "\n";
		return result;
	}

	/* 
	 * Neo4j ignores id suggestions;
	 * feature ignoresSuppliedIds: true
	 * (non-Javadoc)
	 * @see com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph#addEdge(java.lang.Object, com.tinkerpop.blueprints.Vertex, com.tinkerpop.blueprints.Vertex, java.lang.String)
	 */
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		return super.addEdge(null, outVertex, inVertex, label);
	}

	/* 
	 * Neo4j ignores id suggestions;
	 * feature ignoresSuppliedIds: true
	 * (non-Javadoc)
	 * @see com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph#addVertex(java.lang.Object)
	 */
	public Vertex addVertex() {
		return super.addVertex(null);
	}

}
