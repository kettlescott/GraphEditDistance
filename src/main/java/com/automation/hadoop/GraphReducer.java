package com.automation.hadoop;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class GraphReducer  extends Reducer<Text, DoubleWritable,Text, DoubleWritable> {

    PriorityQueue<GraphCompareResult> pq = new PriorityQueue<>(10, (a, b) -> Double.compare(b.getDist(), a.getDist()));

    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        List<GraphCompareResult> results = new ArrayList<>();
        int k = context.getConfiguration().getInt("topK", 1) ;
        GraphCompareResult graphCompareResult = new GraphCompareResult(key.toString(),values.iterator().next().get());
        pq.offer(graphCompareResult);
        while (pq.size() > k){
            pq.poll() ;
        }

        while (!pq.isEmpty()) {
            results.add(pq.poll());
        }
        Collections.reverse(results);
        for (GraphCompareResult result : results) {
            Text testName = new Text();
            testName.set(result.getTestName());
            DoubleWritable dist = new DoubleWritable();
            dist.set(result.getDist());
            context.write(testName,dist);
        }
    }


}
