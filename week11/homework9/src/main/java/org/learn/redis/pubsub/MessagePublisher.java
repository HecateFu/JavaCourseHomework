package org.learn.redis.pubsub;

public interface MessagePublisher {

    void publish(final String message);
}
