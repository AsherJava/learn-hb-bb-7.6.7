/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.LinkTypeUtil;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;

public abstract class LinkData {
    protected String key;
    protected String regionKey;
    protected int regionType;
    protected String title;
    protected int col;
    protected int row;
    protected int dataCol;
    protected int dataRow;
    protected int type;
    protected List<String> expression;
    protected String defaultValue;
    protected String style;
    protected String cstyle;
    protected String zbid;
    protected String zbcode;
    protected String zbtitle;
    protected String zbdesc;
    protected int zbvaluetype;
    protected int zbgather;
    protected boolean nullable;
    protected String uniqueCode;
    protected String bindingExpression;
    private boolean inited = false;
    protected boolean hasPermission = true;
    protected DataLinkType dataLinkType;
    protected boolean dataMask;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public int getRegionType() {
        return this.regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getDataCol() {
        return this.dataCol;
    }

    public void setDataCol(int dataCol) {
        this.dataCol = dataCol;
    }

    public int getDataRow() {
        return this.dataRow;
    }

    public void setDataRow(int dataRow) {
        this.dataRow = dataRow;
    }

    public List<String> getExpression() {
        return this.expression;
    }

    public void setExpression(List<String> expression) {
        this.expression = expression;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCstyle() {
        return this.cstyle;
    }

    public void setCstyle(String cstyle) {
        this.cstyle = cstyle;
    }

    public String getZbid() {
        return this.zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getZbcode() {
        return this.zbcode;
    }

    public void setZbcode(String zbcode) {
        this.zbcode = zbcode;
    }

    public String getZbtitle() {
        return this.zbtitle;
    }

    public void setZbtitle(String zbtitle) {
        this.zbtitle = zbtitle;
    }

    public String getZbdesc() {
        return this.zbdesc;
    }

    public void setZbdesc(String zbdesc) {
        this.zbdesc = zbdesc;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getZbvaluetype() {
        return this.zbvaluetype;
    }

    public void setZbvaluetype(int zbvaluetype) {
        this.zbvaluetype = zbvaluetype;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getZbgather() {
        return this.zbgather;
    }

    public void setZbgather(int zbgather) {
        this.zbgather = zbgather;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getBindingExpression() {
        return this.bindingExpression;
    }

    public void setBindingExpression(String bindingExpression) {
        this.bindingExpression = bindingExpression;
    }

    public DataLinkType getDataLinkType() {
        return this.dataLinkType;
    }

    public void setDataLinkType(DataLinkType dataLinkType) {
        this.dataLinkType = dataLinkType;
    }

    public boolean isDataMask() {
        return this.dataMask;
    }

    public void setDataMask(boolean dataMask) {
        this.dataMask = dataMask;
    }

    protected LinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        this.initDataLinkDefine(dataLinkDefine);
        if (fieldDefine != null) {
            DataField dataField = (DataField)fieldDefine;
            this.zbid = fieldDefine.getKey();
            this.zbcode = fieldDefine.getCode();
            this.zbtitle = fieldDefine.getTitle();
            this.zbdesc = fieldDefine.getDescription();
            this.zbvaluetype = fieldDefine.getValueType().getValue();
            this.zbgather = fieldDefine.getGatherType().getValue();
            this.defaultValue = fieldDefine.getDefaultValue();
            this.type = LinkTypeUtil.getType(fieldDefine.getType()).getValue();
            this.dataMask = StringUtils.isNotEmpty((String)dataField.getDataMaskCode());
        } else {
            this.zbid = "";
            this.zbcode = "";
            this.zbtitle = "";
            this.zbdesc = "";
        }
    }

    protected LinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        this.initDataLinkDefine(dataLinkDefine);
        if (columnModelDefine != null) {
            this.zbid = columnModelDefine.getID();
            this.zbcode = columnModelDefine.getCode();
            this.zbtitle = columnModelDefine.getTitle();
            this.zbdesc = columnModelDefine.getDesc();
            this.zbvaluetype = columnModelDefine.getApplyType() == null ? ApplyType.NONE.getValue() : columnModelDefine.getApplyType().getValue();
            this.zbgather = columnModelDefine.getAggrType() == null ? AggrType.NONE.getValue() : columnModelDefine.getAggrType().getValue();
            this.defaultValue = columnModelDefine.getDefaultValue();
            this.type = LinkTypeUtil.getType(columnModelDefine.getColumnType()).getValue();
        } else {
            this.zbid = "";
            this.zbcode = "";
            this.zbtitle = "";
            this.zbdesc = "";
        }
    }

    private void initDataLinkDefine(DataLinkDefine dataLinkDefine) {
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        DataRegionDefine regionDefine = runtimeView.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        if (regionDefine != null) {
            this.regionKey = regionDefine.getKey();
            this.regionType = regionDefine.getRegionKind().getValue();
        }
        this.key = dataLinkDefine.getKey();
        this.title = dataLinkDefine.getTitle();
        this.col = dataLinkDefine.getPosX();
        this.row = dataLinkDefine.getPosY();
        this.dataCol = dataLinkDefine.getColNum();
        this.dataRow = dataLinkDefine.getRowNum();
        if (dataLinkDefine.getDataValidation() != null && dataLinkDefine.getDataValidation().size() > 0) {
            if (this.expression == null) {
                this.expression = new ArrayList<String>();
            }
            for (String formula : dataLinkDefine.getDataValidation()) {
                if (StringUtils.isEmpty((String)formula)) continue;
                this.expression.add(formula);
            }
        }
        this.nullable = dataLinkDefine.getAllowNullAble();
        this.uniqueCode = dataLinkDefine.getUniqueCode();
        this.bindingExpression = dataLinkDefine.getBindingExpression();
        this.dataLinkType = dataLinkDefine.getType();
    }

    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        this.init();
        if (!this.hasPermission) {
            return "";
        }
        if (data == null || data.isNull) {
            return "";
        }
        String asString = data.getAsString();
        if ("\u2014\u2014".equals(asString)) {
            return asString;
        }
        return data;
    }

    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache, JtableContext jtableContext) {
        return this.getFormatData(data, dataFormaterCache);
    }

    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        if ((value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) && !this.nullable) {
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + "\u4e0d\u80fd\u4e3a\u7a7a");
        }
        return null;
    }

    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo, boolean matchRound) {
        return this.getData(value, dataFormaterCache, saveErrorDataInfo);
    }

    protected void init() {
        if (this.inited) {
            return;
        }
        this.inited = true;
    }

    public boolean checkFuzzyValue(AbstractData value, Object formatValue, DataFormaterCache dataFormaterCache, String fuzzyValue) {
        return formatValue.toString().contains(fuzzyValue);
    }
}

