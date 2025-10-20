/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeException
 *  com.jiuqi.bi.util.tree.TreeNode
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeException;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ParameterResultset
implements Iterable<ParameterResultItem>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 3742205083970154704L;
    private List<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
    public static final ParameterResultset EMPTY_RESULTSET = new ParameterResultset();

    public ParameterResultset() {
    }

    public ParameterResultset(ParameterResultItem item) {
        this.items.add(item);
    }

    public ParameterResultset(ParameterResultItem min, ParameterResultItem max) {
        this.items.add(min);
        this.items.add(max);
    }

    public ParameterResultset(Collection<ParameterResultItem> rows) {
        if (rows != null) {
            this.items.addAll(rows);
        }
    }

    public final List<Object> getValueAsList() {
        ArrayList<Object> list = new ArrayList<Object>(this.items.size());
        for (ParameterResultItem item : this.items) {
            list.add(item.getValue());
        }
        return list;
    }

    public final List<String> getValueAsString(IParameterValueFormat format) throws ParameterException {
        ArrayList<String> list = new ArrayList<String>(this.items.size());
        for (ParameterResultItem item : this.items) {
            list.add(format.format(item.getValue()));
        }
        return list;
    }

    public final List<String> getNameValueAsList() {
        ArrayList<String> list = new ArrayList<String>();
        for (ParameterResultItem item : this.items) {
            list.add(item.getTitle());
        }
        return list;
    }

    public final boolean isEmpty() {
        return this.items.isEmpty();
    }

    public final int size() {
        return this.items.size();
    }

    public final ParameterResultItem get(int index) {
        return this.items.get(index);
    }

    public final ParameterResultItem findByKey(Object key) {
        if (key == null) {
            return null;
        }
        for (ParameterResultItem item : this.items) {
            if (!key.equals(item.getValue())) continue;
            return item;
        }
        return null;
    }

    public final List<ParameterResultItem> findByTitle(String title) {
        if (title == null) {
            return null;
        }
        return this.items.stream().filter(item -> title.equals(item.getTitle())).collect(Collectors.toList());
    }

    public final void reverse() {
        Collections.reverse(this.items);
    }

    @Override
    public Iterator<ParameterResultItem> iterator() {
        return this.items.iterator();
    }

    public static final ParameterResultset valueOf(Object value) {
        return new ParameterResultset(new ParameterResultItem(value));
    }

    public void sort(Comparator<ParameterResultItem> comparator) {
        ParameterResultItem[] a = this.items.toArray(new ParameterResultItem[this.items.size()]);
        Arrays.sort(a, comparator);
        ListIterator<ParameterResultItem> i = this.items.listIterator();
        for (ParameterResultItem e : a) {
            i.next();
            i.set(e);
        }
    }

    public void sortByKeysOrder(List<?> keyList) {
        if (keyList == null || keyList.isEmpty()) {
            return;
        }
        final HashMap map = new HashMap();
        for (int i = 0; i < keyList.size(); ++i) {
            map.put(keyList.get(i), i);
        }
        this.sort(new Comparator<ParameterResultItem>(){
            Integer def = 1000000;

            @Override
            public int compare(ParameterResultItem o1, ParameterResultItem o2) {
                int p1 = map.getOrDefault(o1.getValue(), this.def);
                int p2 = map.getOrDefault(o2.getValue(), this.def);
                return p1 - p2;
            }
        });
    }

    public TreeNode toTree() throws ParameterException {
        try {
            TreeBuilder builder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ObjectVistor(){

                public String getParentCode(Object arg0) {
                    ParameterResultItem item = (ParameterResultItem)arg0;
                    return item.getParent();
                }

                public String getCode(Object arg0) {
                    ParameterResultItem item = (ParameterResultItem)arg0;
                    Object v = item.getValue();
                    if (v instanceof String) {
                        return (String)v;
                    }
                    return v == null ? null : v.toString();
                }
            });
            return builder.build(this.iterator());
        }
        catch (TreeException e) {
            throw new ParameterException("\u6784\u9020\u6811\u5f62\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("code,title,parent,level,leaf,path").append(System.lineSeparator());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.items.forEach(item -> {
            Object val = item.getValue();
            String valText = null;
            valText = val instanceof Calendar ? sdf.format(((Calendar)val).getTime()) : (val instanceof Date ? sdf.format((Date)val) : (val == null ? null : val.toString()));
            buf.append(valText).append(",").append(item.getTitle()).append(",").append(item.getParent());
            buf.append(",").append(item.getLevel()).append(",").append(item.isLeaf());
            buf.append(",").append(item.getPath()).append(System.lineSeparator());
        });
        return buf.toString();
    }
}

