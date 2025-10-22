/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 */
package com.jiuqi.nr.itreebase.collection;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.itreebase.collection.IFilterStringListSortParam;
import com.jiuqi.nr.itreebase.collection.MoveDirection;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class FilterStringList
implements IFilterStringList,
Cloneable,
Serializable {
    private static final long serialVersionUID = 2802414199442843970L;
    private List<String> owner = new LinkedList<String>();

    public FilterStringList() {
    }

    public FilterStringList(List<String> c) {
        if (null != c && !c.isEmpty()) {
            this.owner = new LinkedList<String>(c);
        }
    }

    @Override
    public int size() {
        return this.owner.size();
    }

    @Override
    public boolean isEmpty() {
        return this.owner.isEmpty();
    }

    @Override
    public boolean contains(String e) {
        return this.owner.contains(e);
    }

    @Override
    public List<String> toList() {
        return this.owner;
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        return this.owner.subList(fromIndex, toIndex);
    }

    @Override
    public IFilterStringList unionAll(List<String> c) {
        if (this.owner.isEmpty()) {
            this.owner = new LinkedList<String>(c);
        } else {
            LinkedHashSet<String> resultSet = new LinkedHashSet<String>(this.owner);
            resultSet.addAll(c);
            this.owner = new LinkedList<String>(resultSet);
        }
        return this;
    }

    @Override
    public IFilterStringList retainAll(List<String> c) {
        HashSet<String> enterSet = new HashSet<String>(c);
        this.owner.removeIf(s -> !enterSet.contains(s));
        return this;
    }

    @Override
    public IFilterStringList supplementaryAll(List<String> c) {
        HashSet<String> enterSet = new HashSet<String>(c);
        this.owner.removeIf(enterSet::contains);
        return this;
    }

    @Override
    public IFilterStringList removeAll(List<String> c) {
        this.owner.removeAll(c);
        return this;
    }

    @Override
    public IFilterStringList clear() {
        this.owner.clear();
        return this;
    }

    @Override
    public IFilterStringList sort(IFilterStringListSortParam sortPara) {
        if (!IFilterStringListSortParam.isValidParam(sortPara)) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u6392\u5e8f\u53c2\u6570\uff01\u8282\u70b9\u6392\u5e8f\u5931\u8d25\uff01");
        }
        String beforeNode = sortPara.getBeforeNode();
        String afterNode = sortPara.getAfterNode();
        String operate = sortPara.getOperate();
        if ("before".equals(operate)) {
            if (StringUtils.isEmpty((String)afterNode)) {
                afterNode = sortPara.getRange().get(1);
                this.move(MoveDirection.AFTER, afterNode, beforeNode);
            } else {
                this.move(MoveDirection.BEFORE, afterNode, beforeNode);
            }
        } else if ("after".equals(operate)) {
            if (StringUtils.isEmpty((String)beforeNode)) {
                beforeNode = sortPara.getRange().get(0);
                this.move(MoveDirection.BEFORE, beforeNode, afterNode);
            } else {
                this.move(MoveDirection.AFTER, beforeNode, afterNode);
            }
        }
        return this;
    }

    public FilterStringList clone() {
        FilterStringList clone;
        try {
            clone = (FilterStringList)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        LinkedList<String> ownerList = new LinkedList<String>();
        Collections.copy(ownerList, this.owner);
        clone.owner = ownerList;
        return clone;
    }

    public void move(MoveDirection direction, String posEl, String moveEl) {
        int meIdx = this.owner.indexOf(moveEl);
        int peIdx = this.owner.indexOf(posEl);
        if (meIdx != -1 && peIdx != -1) {
            String me = this.owner.remove(meIdx);
            peIdx = this.owner.indexOf(posEl);
            if (MoveDirection.BEFORE.compareTo(direction) == 0) {
                this.owner.add(peIdx, me);
            } else if (MoveDirection.AFTER.compareTo(direction) == 0) {
                this.owner.add(peIdx + 1, me);
            }
        }
    }
}

