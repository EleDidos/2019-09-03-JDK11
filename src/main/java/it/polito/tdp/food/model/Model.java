package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;		//nomi delle tipologie
	private SimpleWeightedGraph <String, DefaultWeightedEdge> graph;
	
	private List <String> vertici;
	
	private Integer calorie;
	
	private List <String> best;
	private Integer pesoMax=0;
	
	private Integer N;//passi max
	
	
	public Model() {
		dao=new FoodDao ();
		
	}

	
	
	public List<String> getVertici() {
		
		return vertici;
	}
	
	public void creaGrafo(Integer calorie) {
		this.calorie=calorie;
		graph= new SimpleWeightedGraph <>(DefaultWeightedEdge.class);
		
		vertici = dao.getVertici(calorie);
		Graphs.addAllVertices(graph, vertici);
		
		Collections.sort(vertici);
		for(String s: vertici)
			System.out.println(s);
		
		for(Arco a : dao.getArchi(vertici)) {
			Graphs.addEdge(graph, a.getP1(), a.getP2(), a.getPeso());
		}
	
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	
	public String getVicini(String p) {
		String result = "";
		
		List <DefaultWeightedEdge> archiVicini = new ArrayList <DefaultWeightedEdge>();
		for(DefaultWeightedEdge e: graph.incomingEdgesOf(p)) {
			if(!archiVicini.contains(e)) {
				String vicino = Graphs.getOppositeVertex(graph, e, p);
				archiVicini.add(e);
				result+=vicino+" - "+graph.getEdgeWeight(e)+"\n";
			}	
		}
		for(DefaultWeightedEdge e: graph.outgoingEdgesOf(p)) {
			if(!archiVicini.contains(e)) {
				String vicino2 = Graphs.getOppositeVertex(graph, e, p);
				archiVicini.add(e);
				result+=vicino2+" - "+graph.getEdgeWeight(e)+"\n";
			}	
		}
		return result;
	}



	public SimpleWeightedGraph<String, DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	
	public List <String> trovaPercorso(String partenza, Integer N){
		this.N=N;
		best=new ArrayList <String>();
		List <String> parziale = new ArrayList <String>();
		parziale.add(partenza);
		cerca(parziale);
		return best;
	}
	
	private void cerca(List <String> parziale) {
		if(parziale.size()==N) {
			if(best.size()==0 || calcolaPeso(parziale)>pesoMax) {
				best=new ArrayList <String> (parziale);
				pesoMax=calcolaPeso(parziale);
				return;
			}
		}
		
		for(String p: Graphs.neighborListOf(graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	
	private Integer calcolaPeso(List <String> lista) {
		Integer peso=0;
		
		for(int i=0;i<lista.size()-1;i++) {
			String p1=lista.get(i);
			String p2=lista.get(i+1);
			DefaultWeightedEdge e=graph.getEdge(p1, p2);
			peso+=(int)graph.getEdgeWeight(e);
		}
		return peso;
	}
	
	public Integer getPeso() {
		return pesoMax;
	}
	
	
	
	
	
}
