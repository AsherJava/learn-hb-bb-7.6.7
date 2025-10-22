/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;

@ApiModel(value="BaseDataView", description="\u57fa\u7840\u6570\u636e\u89c6\u56fe")
public class BaseDataView {
    @ApiModelProperty(value="\u5b9e\u4f53ID", name="key")
    private String key;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u7ef4\u5ea6\u540d", name="dimensionName")
    private String dimensionName;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u7c7b\u578b\uff0c\u53c2\u7167\uff08com.jiuqi.np.definition.common.TableKind\uff09", name="kind")
    private String kind;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u77ed\u6807\u9898", name="titleAbbreviation")
    private String titleAbbreviation;
    @ApiModelProperty(value="\u7b2c\u4e00\u4e3b\u4f53\u89c6\u56fe\uff08\u5355\u4f4d\u4e3b\u4f53\u89c6\u56fe\uff09", name="masterEntity")
    private boolean masterEntity = false;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u8fc7\u6ee4\u6761\u4ef6", name="rowFilter")
    private String rowFilter;
    @ApiModelProperty(value="\u5b9e\u4f53\u67e5\u8be2\u6743\u9650", name="entityAuth")
    private boolean entityAuth = true;
    @ApiModelProperty(value=" \u5f53\u53ea\u6709\u4e00\u6761\u8bb0\u5f55\u65f6\u8981\u9690\u85cf\u7684\u4e3b\u4f53\u89c6\u56fe", name="hiddenWhenSingleRecord")
    private boolean hiddenWhenSingleRecord = true;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u7684\u6811\u5f62\u7ed3\u6784\u5b57\u7b26\u4e32", name="treeStruct")
    private String treeStruct;
    @ApiModelProperty(value="\u4e3b\u4f53\u89c6\u56fe\u7684\u6811\u5f62\u6700\u5927\u6df1\u5ea6", name="maxDepth")
    private int maxDepth;
    @ApiModelProperty(value="\u662f\u5426\u9700\u8981\u8fdb\u884c\u5dee\u989d\u6c47\u603b", name="minusSum")
    private boolean minusSum = false;
    @ApiModelProperty(value="tableName", name="tableName")
    private String tableName;

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

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitleAbbreviation() {
        return this.titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }

    public boolean isMasterEntity() {
        return this.masterEntity;
    }

    public void setMasterEntity(boolean masterEntity) {
        this.masterEntity = masterEntity;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public boolean isEntityAuth() {
        return this.entityAuth;
    }

    public void setEntityAuth(boolean entityAuth) {
        this.entityAuth = entityAuth;
    }

    public boolean isHiddenWhenSingleRecord() {
        return this.hiddenWhenSingleRecord;
    }

    public void setHiddenWhenSingleRecord(boolean hiddenWhenSingleRecord) {
        this.hiddenWhenSingleRecord = hiddenWhenSingleRecord;
    }

    public String getTreeStruct() {
        return this.treeStruct;
    }

    public void setTreeStruct(String treeStruct) {
        this.treeStruct = treeStruct;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public boolean isMinusSum() {
        return this.minusSum;
    }

    public void setMinusSum(boolean minusSum) {
        this.minusSum = minusSum;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void initialize(IEntityDefine entityDefine) {
        this.key = entityDefine.getId();
        this.dimensionName = entityDefine.getDimensionName();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        if (entityDefine.getTreeStruct() != null && StringUtils.isNotEmpty((String)entityDefine.getTreeStruct().getLevelCode())) {
            String treeStructStr = entityDefine.getTreeStruct().getLevelCode();
            char[] charArray = treeStructStr.toCharArray();
            ArrayList<String> treeStructArrat = new ArrayList<String>();
            for (char treeStructchar : charArray) {
                treeStructArrat.add(String.valueOf(treeStructchar));
            }
            this.treeStruct = StringUtils.join((Object[])treeStructArrat.toArray(), (String)";");
        }
        TableModelDefine tableModel = entityMetaService.getTableModel(entityDefine.getId());
        this.tableName = tableModel.getName();
        this.title = entityDefine.getTitle();
        this.titleAbbreviation = entityDefine.getTitle();
    }

    public void initialize(IPeriodEntity periodEntity) {
        this.key = periodEntity.getKey();
        this.dimensionName = periodEntity.getDimensionName();
        this.kind = "TABLE_KIND_ENTITY_PERIOD";
        this.title = periodEntity.getTitle();
        this.titleAbbreviation = periodEntity.getTitle();
    }
}

