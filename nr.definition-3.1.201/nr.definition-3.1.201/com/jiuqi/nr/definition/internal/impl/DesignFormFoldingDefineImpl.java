/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@DBAnno.DBTable(dbTable="NR_PARAM_FORM_FOLDING_DES")
public class DesignFormFoldingDefineImpl
implements DesignFormFoldingDefine {
    @DBAnno.DBField(dbField="FF_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="FF_FORM_KEY")
    private String formKey;
    @DBAnno.DBField(dbField="FF_START")
    private Integer startIdx;
    @DBAnno.DBField(dbField="FF_END")
    private Integer endIdx;
    @DBAnno.DBField(dbField="FF_HIDDEN_REGION")
    private String hiddenRegion;
    @DBAnno.DBField(dbField="FF_DIRECTION")
    private Integer direction;
    @DBAnno.DBField(dbField="FF_FOLDING", tranWith="transBoolean", dbType=Integer.class, appType=boolean.class)
    private boolean folding;
    @DBAnno.DBField(dbField="FF_UPDATE_TIME", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date updateTime;

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    @Override
    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    @Override
    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    @Override
    public void setHiddenRegion(List<FormFoldingSpecialRegion> hiddenRegion) {
        StringBuffer result = new StringBuffer();
        if (!CollectionUtils.isEmpty(hiddenRegion)) {
            for (FormFoldingSpecialRegion region : hiddenRegion) {
                if (StringUtils.hasText(result)) {
                    result.append(";");
                }
                result.append(region.toString());
            }
            this.hiddenRegion = result.toString();
        }
    }

    @Override
    public void setDirection(FormFoldingDirEnum direction) {
        this.direction = direction.getValue();
    }

    @Override
    public void setFolding(boolean isFolding) {
        this.folding = isFolding;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getFormKey() {
        return this.formKey;
    }

    @Override
    public Integer getStartIdx() {
        return this.startIdx;
    }

    @Override
    public Integer getEndIdx() {
        return this.endIdx;
    }

    @Override
    public List<FormFoldingSpecialRegion> getHiddenRegion() {
        ArrayList<FormFoldingSpecialRegion> result = new ArrayList<FormFoldingSpecialRegion>();
        if (this.hiddenRegion == null) {
            return result;
        }
        String[] split = this.hiddenRegion.split(";");
        if (split != null || split.length != 0) {
            for (int i = 0; i < split.length; ++i) {
                String[] regionPos = split[i].split(",");
                result.add(new FormFoldingSpecialRegion(Integer.valueOf(regionPos[0]), Integer.valueOf(regionPos[1])));
            }
        }
        return result;
    }

    @Override
    public FormFoldingDirEnum getDirection() {
        return FormFoldingDirEnum.valueOf(this.direction);
    }

    @Override
    public boolean isFolding() {
        return this.folding;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

