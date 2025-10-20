/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectFieldDefineHolder {
    private final List<ISubjectExpImpFieldDefine> innerDefineList;
    private final List<ISubjectExpImpFieldDefine> externalDefineList;
    private final List<ISubjectExpImpFieldDefine> defineList;

    public SubjectFieldDefineHolder(List<ISubjectExpImpFieldDefine> innerDefineList, List<ISubjectExpImpFieldDefine> externalDefineList) {
        this.innerDefineList = innerDefineList;
        this.defineList = new ArrayList<ISubjectExpImpFieldDefine>(innerDefineList.size() + externalDefineList.size());
        this.defineList.addAll(innerDefineList);
        Set innerCodeSet = this.defineList.stream().map(ISubjectExpImpFieldDefine::getCode).collect(Collectors.toSet());
        this.externalDefineList = new ArrayList<ISubjectExpImpFieldDefine>(externalDefineList.size());
        for (ISubjectExpImpFieldDefine externalFieldDefine : externalDefineList) {
            if (innerCodeSet.contains(externalFieldDefine.getCode())) continue;
            this.externalDefineList.add(externalFieldDefine);
        }
        this.defineList.addAll(this.externalDefineList);
    }

    public List<ISubjectExpImpFieldDefine> getInnerDefineList() {
        return this.innerDefineList;
    }

    public List<ISubjectExpImpFieldDefine> getExternalDefineList() {
        return this.externalDefineList;
    }

    public List<ISubjectExpImpFieldDefine> getDefineList() {
        return this.defineList;
    }
}

