/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NameProvider {
    private Map<String, Integer> idMap = new HashMap<String, Integer>();
    private Set<String> nameSet = new TreeSet<String>(new Comparator<String>(){

        @Override
        public int compare(String o1, String o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            if (o2 == null) {
                return 1;
            }
            return o1.compareToIgnoreCase(o2);
        }
    });

    public String nameOf(String prefix) {
        Integer curID = this.idMap.get(prefix);
        int id = curID == null ? 1 : curID + 1;
        String newName = prefix + id;
        while (this.nameSet.contains(newName)) {
            newName = prefix + ++id;
        }
        this.idMap.put(prefix, id);
        this.nameSet.add(newName);
        return newName;
    }

    public String uniqueOf(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        String newName = name;
        int i = 0;
        while (this.nameSet.contains(newName)) {
            newName = name + "_" + Integer.toString(++i);
        }
        this.nameSet.add(newName);
        return newName;
    }

    public void useName(String name) {
        this.nameSet.add(name);
    }

    public void useNames(Collection<String> names) {
        this.nameSet.addAll(names);
    }

    public void reset() {
        this.idMap.clear();
        this.nameSet.clear();
    }
}

