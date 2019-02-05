package com.automation.graph;

import java.util.List;


public class Gsimilarity {
    private double[][] costMatrix;
    protected final double SUBSTITUTE_COST;
    protected final double INSERT_COST;
    protected final double DELETE_COST;
    private DirectedGraph g1, g2;

    public Gsimilarity(DirectedGraph g1, DirectedGraph g2, double subCost, double insCost, double delCost) {
        this.SUBSTITUTE_COST = subCost;
        this.INSERT_COST = insCost;
        this.DELETE_COST = delCost;
        this.g1 = g1;
        this.g2 = g2;
        this.costMatrix = createCostMatrix();
    }

    public Gsimilarity(DirectedGraph g1, DirectedGraph g2) {
        this(g1, g2, 3, 1, 1);
    }

    public double getNormalizedDistance() {
        double graphLength = (g1.getSize() + g2.getSize()) /2;
        return getDistance() / graphLength;
    }


    public double getDistance() {
        int[][] assignment = HungarianAlgorithm.hgAlgorithm(this.costMatrix, "min");

        double sum = 0;
        for (int i= 0; i< assignment.length; i++){
            sum =  (sum + costMatrix[assignment[i][0]][assignment[i][1]]);
        }

        return sum;
    }

    public double[][] getCostMatrix() {
        if(costMatrix==null) {
            this.costMatrix = createCostMatrix();
        }
        return costMatrix;
    }

    public double[][] createCostMatrix() {
        int n = g1.getNodes().size();
        int m = g2.getNodes().size();
        double[][] costMatrix = new double[n+m][n+m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                costMatrix[i][j] = getSubstituteCost(g1.getNode(i), g2.getNode(j));
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                costMatrix[i+n][j] = getInsertCost(i, j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costMatrix[j][i+m] = getDeleteCost(i, j);
            }
        }

        return costMatrix;
    }

    private double getInsertCost(int i, int j) {
        if(i == j) {
            return INSERT_COST;
        }
        return Double.MAX_VALUE;
    }

    private double getDeleteCost(int i, int j) {
        if(i == j) {
            return  DELETE_COST;
        }
        return Double.MAX_VALUE;
    }

    public double getSubstituteCost(Node node1, Node node2) {
        double diff = getRelabelCost(node1, node2)  + getEdgeDiff(node1, node2) ;
        return diff * SUBSTITUTE_COST;
    }

    public double getRelabelCost(Node node1, Node node2) {
        if(!node1.equals(node2)) {
            return SUBSTITUTE_COST ;
        }
        return 0 ;
    }


    public double getEdgeDiff(Node node1, Node node2) {
        List<Edge> edges1 = g1.getEdges(node1);
        List<Edge> edges2 = g2.getEdges(node2);
        if(edges1.size() == 0 || edges2.size() == 0) {
            return Math.max(edges1.size(), edges2.size()) ;
        }

        int n = edges1.size();
        int m = edges2.size();
        double[][] edgeCostMatrix = new double[n+m][m+n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                edgeCostMatrix[i][j] = getEdgeEditCost(edges1.get(i), edges2.get(j));
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                edgeCostMatrix[i+n][j] = getEdgeInsertCost(i, j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edgeCostMatrix[j][i+m] = getEdgeDeleteCost(i, j);
            }
        }

        int[][] assignment = HungarianAlgorithm.hgAlgorithm(edgeCostMatrix, "min");
        double sum = 0;
        for (int i=0; i<assignment.length; i++){
            sum += edgeCostMatrix[assignment[i][0]][assignment[i][1]];
        }

        return sum / ((n+m));
    }



    public double getEdgeInsertCost(int i, int j) {
        if(i == j) {
            return  INSERT_COST;
        }
        return Double.MAX_VALUE;
    }

    public double getEdgeDeleteCost(int i, int j) {
        if(i == j) {
            return  DELETE_COST;
        }
        return Double.MAX_VALUE;
    }

    public double getEdgeEditCost(Edge edge1, Edge edge2) {
        return edge1.equals(edge2) ? 0 : 1;
    }

}
