package com.automation.graph;


public class Edge {

    protected String id;
    protected Node from;
    protected Node to;
    protected String label;

    Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    @Override
    public String toString() {
        return from + "-" +to;
    }

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass()) return false ;
        Edge other = (Edge) obj;
        return  from.equals(other.from) && to.equals(other.to);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}