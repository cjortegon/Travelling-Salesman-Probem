package logic;

import java.util.ArrayList;

import graph.Node;

public class Route {

	private boolean contains[];
	public ArrayList<Node> nodes;
	public long schedule;
	private double weight;

	public Route(int numberOfNodes, Node first) {
		this.contains = new boolean[numberOfNodes];
		this.nodes = new ArrayList<>();
		this.nodes.add(first);
		setContains();
	}

	public double getWeight() {return weight;}

	public Route(int numberOfNodes, ArrayList<Node> nodes, Node next) {
		this.contains = new boolean[numberOfNodes];
		this.nodes = new ArrayList<>();
		this.nodes.addAll(nodes);
		this.nodes.add(next);
		setContains();
	}

	private void setContains() {
		for (int i = 0; i < nodes.size(); i++) {
			this.contains[nodes.get(i).id] = true;
		}
	}

	public boolean isComplete() {
		for (int i = 0; i < contains.length; i++) {
			if(contains[i] == false){
				return false;
			}
		}
		return true;
	}

	public ArrayList<Route> continueRoute() {

		ArrayList<Route> routes = new ArrayList<>();

		Node lastNode = nodes.get(nodes.size()-1);
		for (int i = 0; i < lastNode.adjacencies.size(); i++) {
			if(!contains[lastNode.adjacencies.get(i).id]) {
				Route route = new Route(contains.length, nodes, lastNode.adjacencies.get(i));
				routes.add(route);
			}
		}
		
		// Print new routes
//		for (int i = 0; i < routes.size(); i++) {
//			System.out.println(routes.get(i).printWithObjectId());
//		}

		return routes;
	}

	public String printWithNames(String places[]) {
		String route = "";
		for (int i = 0; i < nodes.size(); i++) {
			route += places[nodes.get(i).id];
			if(i != nodes.size() - 1)
				route += ", ";
		}
		return "Route {"+route+"} w = "+weight;
	}

	public String printWithObjectId() {
		String route = "";
		for (int i = 0; i < nodes.size(); i++) {
			route += nodes.get(i).objectId;
			if(i != nodes.size() - 1)
				route += ", ";
		}
		return "Route {"+route+"} w = "+weight;
	}

	public void calculateWeight(double distances[][], double replaceInfiniteBy) {
		if(weight == 0) {
			for (int i = 1; i < distances.length; i++) {
				if(distances[nodes.get(i-1).id][nodes.get(i).id] == Double.MAX_VALUE)
					weight += replaceInfiniteBy;
				else
					weight += distances[nodes.get(i-1).id][nodes.get(i).id];
			}
		}
	}
	
	@Override
	public String toString() {

		String route = "";
		for (int i = 0; i < nodes.size(); i++) {
			route += nodes.get(i).id;
			if(i != nodes.size() - 1)
				route += ", ";
		}

		return "Route {"+route+"}";
	}
	
	public void repeated() {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i+1; j < nodes.size(); j++) {
				if(nodes.get(i).id == (nodes.get(j).id)) {
					System.out.println(">>>>>>>>> "+toString());
				}
			}
		}
	}
	
}
