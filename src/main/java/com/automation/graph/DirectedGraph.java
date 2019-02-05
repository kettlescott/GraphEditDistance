package com.automation.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectedGraph {

    protected String id;
    protected List<Node> nodes;
    protected HashMap<String, List<Edge>> edges;


    public DirectedGraph(String id) {
        this();
        this.id = id;
    }
    public DirectedGraph() {
        nodes = new ArrayList<>();
        edges = new HashMap<>();
    }

    void addNode(Node node) {
        if(!edges.containsKey(node.getLabel())) {
            edges.put(node.getLabel(), new ArrayList<>());
        }
        nodes.add(node);
    }

    int getSize() {
        return nodes.size();
    }

    public HashMap<String, List<Edge>> getEdges() {
        return edges;
    }

    void addEdge(Edge edge) {
        edges.get(edge.getFrom().getLabel()).add(edge);
    }

    List<Edge> getEdges(Node node) {
        return getEdges(node.getLabel());
    }

    private List<Edge> getEdges(String nodeId) {
        return edges.get(nodeId);
    }

    Node getNode(int i) {
        return nodes.get(i);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public String getId() {
        return id;
    }


}