/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.util;

import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.internal.EmptyGraph;
import com.jiuqi.nr.graph.label.ILabel;
import com.jiuqi.nr.graph.label.ILabelabled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GraphUtils {
    private static boolean isProd;
    public static final IGraph EmptyGraph;

    public GraphUtils(boolean isProd) {
        GraphUtils.isProd = isProd;
    }

    public static boolean equalsLabel(ILabel label, ILabelabled<? extends ILabel> item) {
        return isProd ? isProd : label.equals(item.getLabel());
    }

    public static <E> List<E> deepGet(Function<E, Collection<E>> dataGetter) {
        Collection<E> datas = dataGetter.apply(null);
        if (null == datas || datas.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<E> list = new ArrayList<E>();
        list.addAll(datas);
        list.addAll(GraphUtils.deepGet(datas, dataGetter));
        return list;
    }

    private static <E> List<E> deepGet(Collection<E> datas, Function<E, Collection<E>> dataGetter) {
        ArrayList<E> list = new ArrayList<E>();
        for (E data : datas) {
            Collection<E> nextDatas = dataGetter.apply(data);
            if (null == nextDatas || nextDatas.isEmpty()) continue;
            list.addAll(nextDatas);
            List<E> nextDeepDatas = GraphUtils.deepGet(nextDatas, dataGetter);
            if (null == nextDeepDatas || nextDeepDatas.isEmpty()) continue;
            list.addAll(nextDeepDatas);
        }
        return list;
    }

    public static String concatIndex(Object ... keys) {
        return Arrays.toString(keys);
    }

    public static IGraph emptyGraph() {
        return EmptyGraph;
    }

    static {
        EmptyGraph = new EmptyGraph();
    }
}

