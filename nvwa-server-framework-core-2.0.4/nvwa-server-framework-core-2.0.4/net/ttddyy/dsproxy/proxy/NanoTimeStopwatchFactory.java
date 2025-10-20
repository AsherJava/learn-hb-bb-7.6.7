/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import net.ttddyy.dsproxy.proxy.Stopwatch;
import net.ttddyy.dsproxy.proxy.StopwatchFactory;

public class NanoTimeStopwatchFactory
implements StopwatchFactory {
    @Override
    public Stopwatch create() {
        return new NanoTimeStopwatch();
    }

    public static class NanoTimeStopwatch
    implements Stopwatch {
        private long startTime;

        @Override
        public Stopwatch start() {
            this.startTime = System.nanoTime();
            return this;
        }

        @Override
        public long getElapsedTime() {
            return System.nanoTime() - this.startTime;
        }
    }
}

