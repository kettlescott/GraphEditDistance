package com.automation.hadoop;



public class GraphCompareResult {

    private String testName ;
    private double dist ;

    public GraphCompareResult (String testName, double dist){
        this.testName = testName ;
        this.dist = dist ;
    }

    double getDist (){
        return dist ;
    }

    String getTestName (){
        return testName;
    }
}
