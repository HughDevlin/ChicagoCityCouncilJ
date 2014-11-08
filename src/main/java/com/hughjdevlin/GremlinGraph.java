/*
 * Gremlin
 * delegation
 * http://www.tinkerpop.com/
 * http://www.tinkerpop.com/javadocs/3.0.0.M4/full/
 * 
 */
package com.hughjdevlin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.gremlin.process.T;
import com.tinkerpop.gremlin.structure.Edge;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.io.GraphReader;
import com.tinkerpop.gremlin.structure.io.graphml.GraphMLReader;
import com.tinkerpop.gremlin.structure.io.graphml.GraphMLWriter;
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class GremlinGraph {
	private final String file; // file version of graph
	protected Graph g; // graph
	
	/**
	 * constructor
	 * @param directory
	 */
	public GremlinGraph(final String file) {
		this.file = file;
		this.g = TinkerGraph.open();
	}
	
	private TinkerGraph castIdsToLong(Graph g) {
		TinkerGraph result = TinkerGraph.open();
		for(Vertex v : g.V().toList()) {
			Vertex newVertex = result.addVertex(T.id, Long.valueOf(v.id().toString()));
			for(String key : v.keys()) {
				newVertex.property(key, v.property(key).value());
			}
		}
		for(Edge e : g.E().toList()) {
			Long outId = Long.valueOf(e.outV().toList().iterator().next().id().toString());
			Vertex out = result.v(outId);
			Long inId = Long.valueOf(e.inV().toList().iterator().next().id().toString());
			Vertex in = result.v(inId);
			Edge newEdge = out.addEdge(e.label(), in, T.id, Long.valueOf(e.id().toString()));
			for(String key : e.keys()) {
				newEdge.property(key, e.property(key).value());
			}
		}
		return result;
	}
	public void read() throws FileNotFoundException, IOException {
		final GraphReader reader = GraphMLReader.build().create();
		try (final InputStream is = new FileInputStream(file)) {
		    reader.readGraph(is, g);
		}
		g = castIdsToLong(g);
	}
	
	public void write() throws FileNotFoundException, IOException {
		try (final OutputStream os = new FileOutputStream(file)) {
		    GraphMLWriter.build().create().writeGraph(os, g);
		}
	}
	
	public void close() throws Exception {
		g.close();
	}
	
	public Map<String, Object> getDescription() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("vertices", g.V().toList().size());
		result.put("edges", g.E().toList().size());
		return result;
	}
	
	public String descriptionToString(Map<String, Object> description) {
		String result = new String();
		for (Map.Entry<String, Object> entry : description.entrySet()) {
			result += entry.getKey() + "=" + entry.getValue() + "\n";
		}
		return result;
	}
	
}
