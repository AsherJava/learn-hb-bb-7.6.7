/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.filterTemplate.facade;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_PARAM_FILTERTEMPLATE")
public class FilterTemplateDO
implements IBaseMetaItem {
    @DBAnno.DBField(dbField="FT_KEY", isPk=true)
    private String filterTemplateID;
    @DBAnno.DBField(dbField="FT_ENTITY_KEY")
    private String entityID;
    @DBAnno.DBField(dbField="FT_TITLE")
    private String filterTemplateTitle;
    @DBAnno.DBField(dbField="FT_FILTER_EXPRESSION")
    private String filterContent;
    @DBAnno.DBField(dbField="FT_ORDINAL", isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="FT_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    private Instant updateTime;

    public FilterTemplateDO() {
    }

    public FilterTemplateDO(FilterTemplateDTO filterTemplateDTO) {
        if (filterTemplateDTO == null) {
            return;
        }
        this.filterTemplateID = filterTemplateDTO.getFilterTemplateID();
        this.entityID = filterTemplateDTO.getEntityID();
        this.filterTemplateTitle = filterTemplateDTO.getFilterTemplateTitle();
        this.filterContent = filterTemplateDTO.getFilterContent();
        this.order = filterTemplateDTO.getOrder();
        this.updateTime = filterTemplateDTO.getUpdateTime();
    }

    public String getFilterTemplateID() {
        return this.filterTemplateID;
    }

    public void setFilterTemplateID(String filterTemplateID) {
        this.filterTemplateID = filterTemplateID;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getFilterTemplateTitle() {
        return this.filterTemplateTitle;
    }

    public void setFilterTemplateTitle(String filterTemplateTitle) {
        this.filterTemplateTitle = filterTemplateTitle;
    }

    public String getFilterContent() {
        return this.filterContent;
    }

    public void setFilterContent(String filterContent) {
        this.filterContent = filterContent;
    }

    public String getKey() {
        return this.filterTemplateID;
    }

    public String getTitle() {
        return this.filterTemplateTitle;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}

