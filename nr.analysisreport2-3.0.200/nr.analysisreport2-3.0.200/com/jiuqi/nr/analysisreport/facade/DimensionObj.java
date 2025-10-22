/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.params.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.analysisreport.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.params.DimensionType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class DimensionObj
implements INode,
Serializable {
    private static final long serialVersionUID = 360694693872841992L;
    private String key;
    private String code;
    private String name;
    private String title;
    private String viewKey;
    private DimensionType type;
    private DimConfig config;

    public DimensionType getType() {
        return this.type;
    }

    public void setType(DimensionType type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DimConfig getConfig() {
        if (null == this.config) {
            this.config = new DimConfig();
        }
        return this.config;
    }

    public void setConfig(DimConfig config) {
        this.config = config;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public static DimensionObj toTreeNodeObj(IPeriodEntity item) {
        DimensionObj obj = new DimensionObj();
        obj.setKey(item.getKey());
        obj.setViewKey(item.getKey());
        obj.setCode(item.getCode());
        obj.setTitle(item.getTitle());
        obj.setName(item.getTitle());
        obj.setType(DimensionType.DIMENSION_PERIOD);
        return obj;
    }

    public static DimensionObj toTreeNodeObj(IEntityDefine item) {
        DimensionObj obj = new DimensionObj();
        obj.setKey(item.getId());
        obj.setViewKey(item.getId());
        obj.setCode(item.getCode());
        obj.setTitle(item.getTitle());
        obj.setName(item.getTitle());
        obj.setType(DimensionType.DIMENSION_UNIT);
        return obj;
    }

    public int hashCode() {
        return (this.key + this.viewKey).hashCode();
    }

    public boolean equals(Object obj) {
        DimensionObj dimensionObj = (DimensionObj)obj;
        return this.key == dimensionObj.key && this.viewKey == dimensionObj.viewKey;
    }

    public class DimConfig
    implements Serializable {
        private static final long serialVersionUID = 7153112434755047273L;
        private String periodRange;
        private int periodType = 1;
        private boolean showLinkEntity;
        private boolean showOnTree = true;
        private String linkEntityKey;
        private UnitRange unitRange = UnitRange.ALL_CHILDREN;
        private List<String> unitKeys = new ArrayList<String>();
        private transient List customPeriods = new ArrayList();
        private String unitTitles;
        private String condition;
        private boolean hidden;
        private String defaultEntityConf;
        private String defaultEntityTitle;
        private String defaultEntityVal;

        public String getLinkEntityKey() {
            return this.linkEntityKey;
        }

        public void setLinkEntityKey(String linkEntityKey) {
            this.linkEntityKey = linkEntityKey;
        }

        public String getPeriodRange() {
            if (StringUtils.isEmpty((CharSequence)this.periodRange)) {
                this.periodRange = PeriodUtil.currentPeriod((int)this.periodType).toString();
                this.periodRange = this.periodRange + "-" + this.periodRange;
            }
            return this.periodRange;
        }

        public void setPeriodRange(String periodRange) {
            this.periodRange = periodRange;
        }

        public int getPeriodType() {
            return this.periodType;
        }

        public void setPeriodType(int periodType) {
            this.periodType = periodType;
        }

        public boolean isShowLinkEntity() {
            return this.showLinkEntity;
        }

        public void setShowLinkEntity(boolean showLinkEntity) {
            this.showLinkEntity = showLinkEntity;
        }

        public boolean isShowOnTree() {
            return this.showOnTree;
        }

        public void setShowOnTree(boolean showOnTree) {
            this.showOnTree = showOnTree;
        }

        public UnitRange getUnitRange() {
            return this.unitRange;
        }

        public void setUnitRange(UnitRange unitRange) {
            this.unitRange = unitRange;
        }

        @JsonIgnore
        public String getFromPeriod() {
            String[] split;
            if (!StringUtils.isEmpty((CharSequence)this.periodRange) && (split = this.periodRange.split("-")).length == 2) {
                return split[0];
            }
            return null;
        }

        @JsonIgnore
        public String getToPeriod() {
            String[] split;
            if (!StringUtils.isEmpty((CharSequence)this.periodRange) && (split = this.periodRange.split("-")).length == 2) {
                return split[1];
            }
            return null;
        }

        public List<String> getUnitKeys() {
            return this.unitKeys;
        }

        public void setUnitKeys(List<String> unitKeys) {
            this.unitKeys = unitKeys;
        }

        public List getCustomPeriods() {
            return this.customPeriods;
        }

        public void setCustomPeriods(List customPeriods) {
            this.customPeriods = customPeriods;
        }

        public String getCondition() {
            return this.condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getUnitTitles() {
            return this.unitTitles;
        }

        public void setUnitTitles(String unitTitles) {
            this.unitTitles = unitTitles;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public void setHidden(boolean hidden) {
            this.hidden = hidden;
        }

        public String getDefaultEntityConf() {
            return this.defaultEntityConf;
        }

        public void setDefaultEntityConf(String defaultEntityConf) {
            this.defaultEntityConf = defaultEntityConf;
        }

        public String getDefaultEntityTitle() {
            return this.defaultEntityTitle;
        }

        public void setDefaultEntityTitle(String defaultEntityTitle) {
            this.defaultEntityTitle = defaultEntityTitle;
        }

        public String getDefaultEntityVal() {
            return this.defaultEntityVal;
        }

        public void setDefaultEntityVal(String defaultEntityVal) {
            this.defaultEntityVal = defaultEntityVal;
        }
    }

    public static enum UnitRange {
        ITSELF,
        CHILDREN,
        ALL_CHILDREN,
        SELECTION,
        CONDITION,
        NONE,
        BROTHERS;

    }
}

