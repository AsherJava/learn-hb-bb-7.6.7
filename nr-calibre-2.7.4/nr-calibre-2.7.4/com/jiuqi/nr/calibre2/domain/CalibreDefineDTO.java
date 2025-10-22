/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.domain;

import com.jiuqi.nr.calibre2.common.CalibreDefineOption;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import java.util.List;

public class CalibreDefineDTO
extends CalibreDefineDO {
    private Integer shareStatus;
    private String keyWords;
    private CalibreDefineOption.ShareWay shareWay;
    private CalibreDefineOption.PageCondition pageCondition;
    private List<String> moveKeys;
    private Integer direction;

    public Integer getShareStatus() {
        return this.shareStatus;
    }

    public void setShareStatus(Integer shareStatus) {
        this.shareStatus = shareStatus;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public CalibreDefineOption.PageCondition getPageCondition() {
        return this.pageCondition;
    }

    public void setPageCondition(CalibreDefineOption.PageCondition pageCondition) {
        this.pageCondition = pageCondition;
    }

    public CalibreDefineOption.ShareWay getShareWay() {
        return this.shareWay;
    }

    public void setShareWay(CalibreDefineOption.ShareWay shareWay) {
        this.shareWay = shareWay;
    }

    public List<String> getMoveKeys() {
        return this.moveKeys;
    }

    public void setMoveKeys(List<String> moveKeys) {
        this.moveKeys = moveKeys;
    }

    public Integer getDirection() {
        return this.direction;
    }
}

