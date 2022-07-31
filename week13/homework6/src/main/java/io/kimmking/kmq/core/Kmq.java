package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class Kmq {

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new ArrayList<>(capacity);
        this.offset = 0;
    }

    private String topic;

    private int capacity;

    private int offset;

    private List<KmqMessage> queue;

    public boolean send(KmqMessage message) {
        if(queue.size()<capacity){
            return queue.add(message);
        } else {
            return false;
        }
    }

    public KmqMessage poll() {
        if(offset>queue.size()){
            return null;
        }
        KmqMessage msg = queue.get(offset);
        offset += 1;
        return msg;
    }
}
