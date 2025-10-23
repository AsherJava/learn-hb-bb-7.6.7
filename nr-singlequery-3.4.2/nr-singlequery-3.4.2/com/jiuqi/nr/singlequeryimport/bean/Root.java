/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlRootElement
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.singlequeryimport.bean.QueryData;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Root")
public class Root {
    private QueryData QueryData;

    public QueryData getQueryData() {
        return this.QueryData;
    }

    public void setQueryData(QueryData QueryData2) {
        this.QueryData = QueryData2;
    }
}

