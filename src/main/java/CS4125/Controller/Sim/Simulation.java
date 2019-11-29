package CS4125.Controller.Sim;


import CS4125.Model.Metrics.Metric;
import CS4125.Model.Vehicle.Vehicle;
import CS4125.Model.TrafficControl.*;
import CS4125.View.EventHandlers.UIController;
import javafx.scene.shape.Circle;
import CS4125.Model.TrafficControl.SimpleJunction;
import sun.java2d.pipe.SpanShapeRenderer;

import java.lang.reflect.Array;
import java.util.*;

public enum Simulation{

	INSTANCE;

	public List<ITCM> nodeList; // Having public breaks encapsulation - cannot have final due to it not being initialized before simulation
	private HashMap<String, Vehicle> routeMap;
	private List<Circle> circles;
	private int vehicleQuantity;
	private List<Vehicle> vehicles = new ArrayList<>();
	private static UIController controller;


	public void init(UIController controller) {
		this.nodeList = new ArrayList<ITCM>();
		this.routeMap = new HashMap<String, Vehicle>();
		this.vehicles = new ArrayList<>();
		this.vehicleQuantity = 0;
		this.controller = controller;
		this.circles = new ArrayList<>();
	}

	Simulation() {
	}

	public void run(){
		// HARCODED FOR NOW
		vehicleQuantity = 10;

		// HARDCODED FOR NOW
		// TODO: instantiate nodeList and routeMap
		defaultNodes();

//		for (int i = 1; i < vehicleQuantity; i++) {
//			createVehicle(nodeList.get(new Random().nextInt(nodeList.size() - 1)), nodeList.get(new Random().nextInt(nodeList.size() - 1)));
//		}

		for (ITCM itcm : nodeList) {
			controller.addNode(itcm);
			for (ITCM value : itcm.getAdjacent()) {
				controller.addEdge(itcm, value);
			}
		}
	}

	public void pause(){

	}

	public void reset(){

	}

	public void addNode(String type, String name, int x, int y) {
		ITCM n;
		switch (type) {
			case "SimpleJunction": n = new SimpleJunction(name,x,y,new ArrayList<ITCM>()); break;
			case "TrafficLights": n = new TrafficLights(new SimpleJunction(name,x,y,new ArrayList<ITCM>())); break;
			//case "Roundabout": n = new SimpleJunction(new Roundabout(x,y,new ArrayList<ITCM>())); break;
			default: n = new SimpleJunction(name,x,y,new ArrayList<ITCM>()); break;
		}
		nodeList.add(n);
		controller.addNode(n);
	}

	public void addEdge(String l1, String l2) {
		int l1index = -1;
		int l2index = -1;
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).getLabel().equals(l1)) { // NOTE!!!! STRING LABELS MUST BE UNIQUE OR THIS WILL NOT WORK
				l1index = i;
			}
			if (nodeList.get(i).getLabel().equals(l2)) {
				l2index = i;
			}
		}
		if (l1index != -1 && l2index != -1) {
			addAdjacent(nodeList.get(l1index), nodeList.get(l2index));
			controller.addEdge(nodeList.get(l1index), nodeList.get(l2index));
		} else {
			System.out.println("Labels not found");
		}
	}

	public void addAdjacent(ITCM n1, ITCM n2) {
		List<ITCM> n1list = n1.getAdjacent(); n1list.add(n2); // add n2 to adjacency list of n1
		List<ITCM> n2list = n2.getAdjacent(); n2list.add(n1); // add n1 to adjacency list of n2
		n1.setAdjacent(n1list);
		n2.setAdjacent(n2list);
	}


	public void defaultNodes(){
		//Existing Nodes & Adjacency lists - In future change to allow passing in a graph topology (e.g. CSV adjacency matrix)

		// adding to nodeList
		List<ITCM> adj = new ArrayList<ITCM>();
		TrafficLights flagpoles = new TrafficLights(new SimpleJunction("TrafficLights_flag",300,200, null));
		TrafficLights a = new TrafficLights(new SimpleJunction("TrafficLights_a",400,300, null));
		TrafficLights b = new TrafficLights(new SimpleJunction("TrafficLights_b",200,400, null));
		flagpoles.setAdjacent(new ArrayList<>(Arrays.asList(a,b)));
		a.setAdjacent(new ArrayList<>(Arrays.asList(flagpoles,b)));
		b.setAdjacent(new ArrayList<>(Arrays.asList(flagpoles,a)));

		nodeList.addAll(Arrays.asList(flagpoles, a, b));


//		adj.add(flagpoles);
//		TrafficLights libRoundabout = new TrafficLights(new SimpleJunction("TrafficLights_B",150,50, adj));	nodeList.add(libRoundabout); adj.clear();
//		adj.add(libRoundabout); adj.add(flagpoles);
//		TrafficLights leroRoundabout = new TrafficLights(new SimpleJunction("TrafficLights_C",100,250, adj));	nodeList.add(leroRoundabout);
//



//		TrafficLights stables = new TrafficLights(new SimpleJunction(1,2));	nodeList.add(stables);
//		TrafficLights eastGate = new TrafficLights(new SimpleJunction(1,2));	nodeList.add(eastGate);
//
//		// Setup adjacency lists for each of the original nodes
//		flagpoles.setAdjacent(Arrays.asList(new NodePair(libRoundabout, 3)));
//		libRoundabout.setAdjacent(Arrays.asList(new NodePair(flagpoles, 3), new NodePair(leroRoundabout, 1), new NodePair(stables, 2)));
//		leroRoundabout.setAdjacent(Arrays.asList(new NodePair(libRoundabout, 1)));
//		stables.setAdjacent(Arrays.asList(new NodePair(libRoundabout, 2), new NodePair(eastGate, 4)));
//		eastGate.setAdjacent(Arrays.asList(new NodePair(stables, 4)));

	}

	public void createVehicle(ITCM start, ITCM end){
		float xCoord = 1; // TODO:start.getX + start.getY
		float yCoord = 2; // TODO: end.getX + end.getY
		String xyCoords = String.valueOf(xCoord) + String.valueOf(yCoord);
		if (routeMap.containsKey(xyCoords)) {
			vehicles.add(routeMap.get(xyCoords).copy());
		} else {
			Vehicle newVehicle = new Vehicle(start, end);
			vehicles.add(newVehicle);
			routeMap.put(xyCoords, newVehicle);
		}
	}

	public void updateGraph(){

	}

	public Metric getMetrics(){
		Metric newMetric = new Metric();
		return newMetric.generateMetrics();
	}

	public List<ITCM> getNodeList(){
		return this.nodeList;
	}

	public List<Vehicle> getVehicles(){
		return this.vehicles;
	}

	public HashMap<String, Vehicle> getRouteMap(){
		return this.routeMap;
	}
}