/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ParameterDataSourceManager {
    private static ParameterDataSourceManager instance = new ParameterDataSourceManager();
    private List<AbstractParameterDataSourceFactory> factories = new ArrayList<AbstractParameterDataSourceFactory>();

    private ParameterDataSourceManager() {
        this.registerDataSourceFactory(new NonDataSourceFactory());
        this.registerDataSourceFactory(new CustomListDataSourceFactory());
        this.registerDataSourceFactory(new SQLDataSourceFactory());
    }

    public static ParameterDataSourceManager getInstance() {
        return instance;
    }

    public AbstractParameterDataSourceFactory getFactory(String type) {
        for (AbstractParameterDataSourceFactory factory : this.factories) {
            if (!factory.type().equals(type)) continue;
            return factory;
        }
        return null;
    }

    public void registerDataSourceFactory(AbstractParameterDataSourceFactory factory) {
        for (AbstractParameterDataSourceFactory f : this.factories) {
            if (!f.type().equals(factory.type())) continue;
            return;
        }
        this.factories.add(factory);
    }

    public void removeDataSourceFactory(String type) {
        for (AbstractParameterDataSourceFactory f : this.factories) {
            if (!f.type().equals(type)) continue;
            this.factories.remove(f);
            return;
        }
    }

    public List<AbstractParameterDataSourceFactory> factories() {
        ArrayList<AbstractParameterDataSourceFactory> list = new ArrayList<AbstractParameterDataSourceFactory>(this.factories);
        Collections.sort(list, new Comparator<AbstractParameterDataSourceFactory>(){

            @Override
            public int compare(AbstractParameterDataSourceFactory o1, AbstractParameterDataSourceFactory o2) {
                return o2.getOrder() - o1.getOrder();
            }
        });
        return list;
    }
}

