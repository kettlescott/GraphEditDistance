package com.automation.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;


public class GraphSimilarity {

    public static void  main (String [] args) throws Exception {
        BasicConfigurator.configure();
        String  target = "{\"edges\":[{\"cost\":2.597145033,\"from\":\"TEST_testNewCreateSendLetterTaskNGCM2067_STARTS\",\"to\":\"PAGE_HomePage\",\"operation\":\"login\"},{\"cost\":0.584723745,\"from\":\"PAGE_HomePage\",\"to\":\"PAGE_ClaimSearch\",\"operation\":\"LINK_Link icon-cases DataLabel icon\"},{\"cost\":0.251310867,\"from\":\"PAGE_ClaimSearch\",\"to\":\"PAGE_Claim\",\"operation\":\"LINK_Link icon-cases DataLabel icon\"},{\"cost\":2.734497366,\"from\":\"PAGE_Claim\",\"to\":\"PAGE_ClaimDetailPage\",\"operation\":\"BUTTON_Button\"},{\"cost\":2.385490008,\"from\":\"PAGE_ClaimDetailPage\",\"to\":\"PAGE_RecoverySupport\",\"operation\":\"BUTTON_Button\"},{\"cost\":0.739951149,\"from\":\"PAGE_RecoverySupport\",\"to\":\"PAGE_WorkManager\",\"operation\":\"BUTTON_Button\"},{\"cost\":1.401457161,\"from\":\"PAGE_RecoverySupport\",\"to\":\"PAGE_DocumentsForClaim\",\"operation\":\"BUTTON_Button\"},{\"cost\":0.191048531,\"from\":\"PAGE_WorkManager\",\"to\":\"PAGE_NameSearch\",\"operation\":\"BUTTON_Button\"},{\"cost\":1.232039501,\"from\":\"PAGE_NameSearch\",\"to\":\"PAGE_SendLetter\",\"operation\":\"BUTTON_Button\"},{\"cost\":1.481300481,\"from\":\"PAGE_SendLetter\",\"to\":\"PAGE_ActivityPage\",\"operation\":\"BUTTON_Button\"},{\"cost\":0.737375668,\"from\":\"PAGE_SendLetter\",\"to\":\"PAGE_RecoverySupport\",\"operation\":\"BUTTON_Button\"},{\"cost\":0.232696869,\"from\":\"PAGE_ActivityPage\",\"to\":\"PAGE_ActivityDetails\",\"operation\":\"BUTTON_Button\"},{\"cost\":0.944540497,\"from\":\"PAGE_ActivityDetails\",\"to\":\"PAGE_SendLetter\",\"operation\":\"BUTTON_Button\"},{\"cost\":1.246919221,\"from\":\"PAGE_DocumentsForClaim\",\"to\":\"TEST_testNewCreateSendLetterTaskNGCM2067_ENDS\",\"operation\":\"BUTTON_Button\"}],\"testName\":\"testNewCreateSendLetterTaskNGCM2067\"}";
        Configuration conf = new Configuration();
        conf.set("TargetGraph", target);
        conf.setInt("topK", 10);
        /*
        this is for local test
        Path pt = new Path("file:///C:/input");
        Path out = new Path("file:///C:/input/out");
         */
        Job job = Job.getInstance(conf, "Graph Similarity");
        job.setJarByClass(GraphSimilarity.class);
        job.setMapperClass(GraphMapper.class);
        job.setCombinerClass(GraphReducer.class);
        job.setReducerClass(GraphReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);
        FileInputFormat.addInputPath(job, new Path("hdfs://localhost:9000/topology"));

        FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:9000/output"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
