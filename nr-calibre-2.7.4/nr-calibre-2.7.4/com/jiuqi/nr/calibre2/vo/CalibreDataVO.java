/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.calibre2.vo;

import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.common.itree.INode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class CalibreDataVO
extends CalibreDataDTO
implements INode {
    private String locationKey;
    private String content;
    private List<String> parents;
    private boolean setContent;
    private String formSchemeKey;
    private String reason;
    private String parentName;
    private String refEntityName;
    private boolean virtualParent;

    public static List<CalibreDataVO> getListInstance(List<CalibreDataDTO> dataDOS) {
        ArrayList<CalibreDataVO> vos = new ArrayList<CalibreDataVO>(dataDOS.size());
        for (CalibreDataDO calibreDataDO : dataDOS) {
            CalibreDataVO vo = new CalibreDataVO();
            BeanUtils.copyProperties(calibreDataDO, vo);
            vos.add(vo);
        }
        return vos;
    }

    public static List<CalibreDataVO> toListInstance(List<CalibreDataDO> dataDOS) {
        ArrayList<CalibreDataVO> vos = new ArrayList<CalibreDataVO>(dataDOS.size());
        for (CalibreDataDO dataDO : dataDOS) {
            CalibreDataVO vo = new CalibreDataVO();
            BeanUtils.copyProperties(dataDO, vo);
            vos.add(vo);
        }
        return vos;
    }

    public static CalibreDataVO getInstance(CalibreDataDTO Dto) {
        CalibreDataVO vo = new CalibreDataVO();
        BeanUtils.copyProperties(Dto, vo);
        return vo;
    }

    public String getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public boolean isSetContent() {
        return this.setContent;
    }

    public void setSetContent(boolean setContent) {
        this.setContent = setContent;
    }

    public String getTitle() {
        return super.getName();
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getRefEntityName() {
        return this.refEntityName;
    }

    public void setRefEntityName(String refEntityName) {
        this.refEntityName = refEntityName;
    }

    public boolean isVirtualParent() {
        return this.virtualParent;
    }

    public void setVirtualParent(boolean virtualParent) {
        this.virtualParent = virtualParent;
    }
}

