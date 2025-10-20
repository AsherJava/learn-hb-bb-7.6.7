/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.util.concurrent.TimeUnit;
import net.ttddyy.dsproxy.proxy.Stopwatch;
import net.ttddyy.dsproxy.proxy.StopwatchFactory;

public class SystemStopwatchFactory
implements StopwatchFactory {
    @Override
    public Stopwatch create() {
        return new SystemStopwatch();
    }

    public static class SystemStopwatch
    implements Stopwatch {
        private long startTime;

        @Override
        public Stopwatch start() {
            this.startTime = System.nanoTime();
            return this;
        }

        @Override
        public long getElapsedTime() {
            return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startTime);
        }
    }
}

