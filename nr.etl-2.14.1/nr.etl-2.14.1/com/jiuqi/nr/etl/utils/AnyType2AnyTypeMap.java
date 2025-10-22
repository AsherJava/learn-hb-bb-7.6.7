/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAccessType
 *  javax.xml.bind.annotation.XmlAccessorType
 *  javax.xml.bind.annotation.XmlType
 */
package com.jiuqi.nr.etl.utils;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="anyType2anyTypeMap", propOrder={"entry"})
public class AnyType2AnyTypeMap {
    protected List<Entry> entry;

    public List<Entry> getEntry() {
        if (this.entry == null) {
            this.entry = new ArrayList<Entry>();
        }
        return this.entry;
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"key", "value"})
    public static class Entry {
        protected Object key;
        protected Object value;

        public Object getKey() {
            return this.key;
        }

        public void setKey(Object value) {
            this.key = value;
        }

        public Object getValue() {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}

