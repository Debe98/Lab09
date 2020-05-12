package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Map <Integer, Country> paesi = new HashMap<Integer, Country>();
	BordersDAO dao;
	Graph <Country, DefaultEdge> grafo;
	int anno = 0;
	
	public Model() {
		dao = new BordersDAO();
	}

	
	public Graph <Country, DefaultEdge> getConfini(int anno) throws Exception {
		if (anno > 2016 || anno < 1816) {
			throw new Exception("Inserisci un anno tra il 1816 e il 2016");
		}
		grafo = new SimpleGraph <> (DefaultEdge.class);
		loadPaesi();
		this.anno = anno;
		List <Border> confini= dao.getConfini(anno);
		for (Border b : confini) {
			grafo.addEdge(paesi.get(b.getState1no()), paesi.get(b.getState2no()));
		}
		return grafo;
	}
	
	public void loadPaesi() {
		dao.loadAllCountries(paesi);
		//System.out.print(paesi.size());
		for (Country c : paesi.values())
			if (!grafo.containsVertex(c))
				grafo.addVertex(c);
	}
	
	public List<Country> getPaesi() {
		return dao.loadAllCountries();
	}


	public List<Country> getVicini(Country c) throws Exception {
		if (grafo == null)
			throw new Exception("Prima scegli un anno!");
		List<Country> result = new LinkedList<Country>();
		
		GraphIterator <Country, DefaultEdge> iter = new BreadthFirstIterator<Country, DefaultEdge>(grafo, c);
		iter.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				System.out.println(e.getEdge()); 
				//non serviva, ma l'ho fatto comunque
				
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		while (iter.hasNext()) {
			result.add(iter.next());
		}
		return result;
	}
	
	public List<Country> getViciniRicorsivo(Country c) throws Exception {
		if (grafo == null)
			throw new Exception("Prima scegli un anno!");
		List<Country> result = new LinkedList<Country>();
		Set<Country> research = new HashSet<Country>();
		ricorsioneVicini(c, result, research);
		return result;
	}
	/*
	public List<Country> getViciniIterativo(Country c) throws Exception {
		if (grafo == null)
			throw new Exception("Prima Scegli un anno!");
		List<Country> result = new LinkedList<Country>();
		//List.add
		
		return result;
	}
	*/
	private void ricorsioneVicini(Country c, List<Country> lista, Set <Country> insieme) {
		//Graphs.neighborListOf(grafo, c) ---> per lista!
				
		lista.add(c);
		insieme.add(c);
		
		for (Country p : Graphs.neighborSetOf(grafo, c)) {
			if (!insieme.contains(p)) {
				ricorsioneVicini(p, lista, insieme);
			}
		}
	}
	
	public int getAnno() {
		return anno;
	}
}
