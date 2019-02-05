package com.automation.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TestGraphJson {

    private List<HashMap<String,Object>> edges;

    private String testName ;

    public TestGraphJson (){
       edges = new LinkedList<>();
    }

    public void setTestName (String testName){
        this.testName = testName ;
    }

    public String getTestName (){
        return testName ;
    }

    public void addEdges (HashMap<String,Object> edge){
       edges.add(edge);
    }

    public List<HashMap<String,Object>> getEdges (){
        return edges ;
    }
}
