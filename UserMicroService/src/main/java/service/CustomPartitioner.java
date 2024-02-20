package service;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomPartitioner implements Partitioner {

    private static AtomicInteger count_messages = new AtomicInteger(0);
    private static AtomicBoolean flag = new AtomicBoolean(false);

    @Override
    public void configure(Map<String, ?> map) {

    }

    @Override
    public void onNewBatch(String topic, Cluster cluster, int prevPartition){
        flag.set(true);
    }

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        int count_partition = cluster.partitionsForTopic("messages").size();
        int num_part =  (flag.get()?count_messages.get():count_messages.incrementAndGet()) % count_partition;
        flag.set(false);
        return num_part;
    }

    @Override
    public void close() {

    }

}
