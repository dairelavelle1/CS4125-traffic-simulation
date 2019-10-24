package Sim;

import TrafficControl.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Simulation{

	public ArrayList<Node> nodeList; // Having public breaks encapsulation - cannot have final due to it not being initialized before simulation
	private HashMap<String, Vehicle> routeMap;
	private int vehicleQuantity;
	private ArrayList<Vehicle> vehicles = new ArrayList<>();

	public void run(){
		// HARCODED FOR NOW
		vehicleQuantity = 10;
		Node start = new Node(10,10);
		Node end = new Node(10,5);

		// HARDCODED FOR NOW
		// TODO: instantiate nodeList and routeMap
		createNodes();
		for (int i = 1; i < vehicleQuantity; i++) {
			// TODO: initialize random start and end node
			createVehicle(start, end);
		}
	}

	public void pause(){

	}

	public void reset(){

	}

	public void createNodes(){
		// HARDCODED FOR NOW
		Random r = new Random();
		float random1;
		float random2;
		int numberOfNodes = 50;
		// HARDCODED FOR NOW
		for (int i = 0; i < numberOfNodes; i++) {
			random1 = 0 + r.nextFloat() * (50 - 0);
			random2 = 0 + r.nextFloat() * (50 - 0);
			Node node = new Node(random1, random2);
			nodeList.add(node);
		}
	}

	public void createVehicle(Node start, Node end){
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

	public ArrayList<Node> getNodeList(){
		return this.nodeList;
	}

	public HashMap<String, Vehicle> getRouteMap(){
		return this.routeMap;
	}
}