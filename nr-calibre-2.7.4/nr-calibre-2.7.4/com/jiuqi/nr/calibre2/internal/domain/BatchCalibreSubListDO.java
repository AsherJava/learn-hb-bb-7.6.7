/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.internal.domain;

import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import java.util.ArrayList;
import java.util.List;

public class BatchCalibreSubListDO {
    private String calibreDefine;
    private String tableName;
    private List<CalibreSubListDO> calibreSubListDOList;

    public String getCalibreDefine() {
        return this.calibreDefine;
    }

    public void setCalibreDefine(String calibreDefine) {
        this.calibreDefine = calibreDefine;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CalibreSubListDO> getCalibreSubListDOList() {
        return this.calibreSubListDOList;
    }

    public void setCalibreSubListDOList(List<CalibreSubListDO> calibreSubListDOList) {
        this.calibreSubListDOList = calibreSubListDOList;
    }

    public void addCalibreSubListDO(CalibreSubListDO subListDO) {
        if (this.calibreSubListDOList == null) {
            this.calibreSubListDOList = new ArrayList<CalibreSubListDO>();
        }
        this.calibreSubListDOList.add(subListDO);
    }
}

