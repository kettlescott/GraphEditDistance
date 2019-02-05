package com.automation.graph;

import com.google.gson.Gson;

import java.util.HashMap;


public class GraphConverter {

    public static DirectedGraph toDirectedGraph (String graph){
        Gson gson = new Gson() ;
        TestGraphJson json = gson.fromJson(graph, TestGraphJson.class);
        HashMap<String,Node> nodes = new HashMap<>();
        DirectedGraph dg = new DirectedGraph(json.getTestName()) ;
        for (HashMap<String,Object> edge : json.getEdges()) {
             String from = edge.get("from").toString() ;
             String   to = edge.get("to").toString() ;
             if (!nodes.containsKey(from)) {
                 Node fromNode = new Node(from);
                 nodes.put(from,fromNode) ;
                 dg.addNode(fromNode);
             }
             if (!nodes.containsKey(to)) {
                 Node toNode = new Node(to);
                 nodes.put(to,toNode);
                 dg.addNode(toNode);
             }
             dg.addEdge(new Edge(nodes.get(from), nodes.get(to)));
        }
        return dg ;
    }
}
