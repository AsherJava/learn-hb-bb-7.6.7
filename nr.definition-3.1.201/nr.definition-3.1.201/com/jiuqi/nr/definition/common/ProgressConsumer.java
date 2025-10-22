/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

import java.util.List;

@FunctionalInterface
public interface ProgressConsumer {
    public void accept(Progress var1);

    public static interface ProgressUpdater
    extends Progress {
        public void nextStep();

        public void updateProgress(int var1, String var2);

        public void fail(String var1);

        public void finish(String var1);
    }

    public static interface Progress {
        public List<Step> getSteps();

        public int getCurrentStep();

        public int getProgress();

        public String getMessage();

        public boolean isFailed();

        public boolean isFinished();
    }

    public static class Step {
        private final String name;
        private final int percentage;

        public Step(String name, int percentage) {
            this.name = name;
            this.percentage = percentage;
        }

        public String getName() {
            return this.name;
        }

        public int getPercentage() {
            return this.percentage;
        }
    }
}

