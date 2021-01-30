package com.codesoom.assignment.application;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class TaskIdGenerator implements IdGenerable {
    private static final int START_ID = 0;
    private final AtomicLong idCounter = new AtomicLong(START_ID);

    @Override
    public long generateNewId() {
        return idCounter.getAndIncrement();
    }

    @Override
    public void resetId() {
        idCounter.set(START_ID);
    }
}
