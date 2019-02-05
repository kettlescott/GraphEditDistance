package com.automation.hadoop;


import com.automation.graph.DirectedGraph;
import com.automation.graph.GraphConverter;
import com.automation.graph.Gsimilarity;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.PriorityQueue;


public class GraphMapper extends Mapper<Object, Text, Text, DoubleWritable> {

    private PriorityQueue<GraphCompareResult> pq = new PriorityQueue<>(10, (a, b) -> Double.compare(b.getDist(), a.getDist()));

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
         String target = context.getConfiguration().get("TargetGraph") ;
         DirectedGraph target_graph = GraphConverter.toDirectedGraph(target) ;
         DirectedGraph src_grapph = GraphConverter.toDirectedGraph(value.toString()) ;
         double distance = new Gsimilarity(src_grapph,target_graph).getNormalizedDistance();
         Text testName = new Text() ;
         testName.set(src_grapph.getId());
         int k = context.getConfiguration().getInt("topK", 1) ;
         GraphCompareResult graphCompareResult = new GraphCompareResult(src_grapph.getId(),distance);
         pq.offer(graphCompareResult);
         while (pq.size() > k){
            pq.poll() ;
         }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        while (!pq.isEmpty()) {
            GraphCompareResult graphCompareResult = pq.poll() ;
            Text testName = new Text() ;
            DoubleWritable dist  = new DoubleWritable();
            testName.set(graphCompareResult.getTestName());
            dist.set(graphCompareResult.getDist());
            context.write(testName,dist);
        }

    }
}
