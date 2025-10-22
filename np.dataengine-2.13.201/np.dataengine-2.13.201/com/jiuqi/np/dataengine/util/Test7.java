/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.util;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test7 {
    private static final Logger logger = LoggerFactory.getLogger(Test7.class);
    private static int[] visited = new int[]{0, 0, 0, 0, 0, 0, 0};
    private static int[][] e = new int[][]{{0, 1, 1, 0, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0}, {0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0}};
    static ArrayList<Integer> trace = new ArrayList();
    static boolean hasCycle = false;

    public static void main(String[] args) {
        Test7.findCycle(0);
        if (!hasCycle) {
            logger.info("No Cycle.");
        }
    }

    static void findCycle(int v) {
        if (visited[v] == 1) {
            int j = trace.indexOf(v);
            if (j != -1) {
                hasCycle = true;
                logger.info("Cycle:");
                while (j < trace.size()) {
                    logger.info(trace.get(j) + " ");
                    ++j;
                }
                logger.info("\n");
                return;
            }
            return;
        }
        Test7.visited[v] = 1;
        trace.add(v);
        for (int i = 0; i < 7; ++i) {
            if (e[v][i] != 1) continue;
            Test7.findCycle(i);
        }
        trace.remove(trace.size() - 1);
    }
}

