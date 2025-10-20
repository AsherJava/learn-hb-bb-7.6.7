/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.logger.DefaultLogger;
import com.jiuqi.bi.dataset.logger.ILogger;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelException;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.ArrayList;
import java.util.List;

public class DSContext
implements IDSContext {
    private DSModel dsModel;
    private String userGuid;
    private String i18nLang;
    private com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv;
    private ILogger logger = new DefaultLogger();
    private boolean isResetEnv = true;
    private List<FilterItem> filters = new ArrayList<FilterItem>();
    private List<SortItem> sortItems = new ArrayList<SortItem>();
    private RangeValues timekeyRange;
    private boolean filtered = true;
    private boolean sorted = true;

    public DSContext() {
    }

    @Deprecated
    public DSContext(DSModel dsModel, String userGuid, IParameterEnv parameterEnv) {
        this.dsModel = dsModel;
        this.userGuid = userGuid;
        this.parameterEnv = new EnhancedParameterEnvAdapter(parameterEnv);
    }

    public DSContext(DSModel dsModel, String userGuid, com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv) {
        this.dsModel = dsModel;
        this.userGuid = userGuid;
        this.parameterEnv = parameterEnv;
    }

    @Override
    @Deprecated
    public IParameterEnv getParameterEnv() throws com.jiuqi.bi.parameter.ParameterException {
        if (this.isResetEnv) {
            if (this.dsModel != null) {
                if (this.parameterEnv == null) {
                    com.jiuqi.bi.parameter.engine.ParameterEnv env = new com.jiuqi.bi.parameter.engine.ParameterEnv(this.dsModel.getParams(), this.getUserGuid());
                    try {
                        this.dsModel.prepareParameterEnv(env);
                    }
                    catch (DSModelException e) {
                        throw new com.jiuqi.bi.parameter.ParameterException("\u5173\u8054\u53c2\u6570env\u5931\u8d25", e);
                    }
                    this.parameterEnv = new EnhancedParameterEnvAdapter(env);
                } else {
                    List<ParameterModel> params = this.dsModel.getParameterModels();
                    for (ParameterModel pm : params) {
                        if (this.parameterEnv.getParameterModelByName(pm.getName()) != null) continue;
                        pm.setHidden(true);
                        try {
                            this.parameterEnv.addParameterModel(pm);
                        }
                        catch (ParameterException e) {
                            throw new com.jiuqi.bi.parameter.ParameterException(e.getMessage(), e);
                        }
                    }
                }
            }
            this.isResetEnv = false;
        }
        if (this.parameterEnv instanceof EnhancedParameterEnvAdapter) {
            return ((EnhancedParameterEnvAdapter)this.parameterEnv).getOriginalParameterEnv();
        }
        throw new com.jiuqi.bi.parameter.ParameterException("\u53c2\u6570\u6267\u884c\u73af\u5883\u4f7f\u7528\u4e86\u65b0\u7684ParameterEnv\uff0c\u65e0\u6cd5\u83b7\u53d6\u5230\u65e7\u7684env\u73af\u5883");
    }

    public void setI18nLang(String i18nLang) {
        this.i18nLang = i18nLang;
    }

    @Override
    public String getI18nLang() {
        return this.i18nLang;
    }

    @Override
    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getEnhancedParameterEnv() throws ParameterException {
        if (this.isResetEnv) {
            if (this.dsModel != null) {
                if (this.parameterEnv == null) {
                    this.parameterEnv = new ParameterEnv(this.getUserGuid(), this.dsModel.getParameterModels());
                    try {
                        this.dsModel.prepareParameterEnv(this.parameterEnv);
                    }
                    catch (DSModelException e) {
                        throw new ParameterException("\u5173\u8054\u53c2\u6570env\u5931\u8d25", (Throwable)e);
                    }
                } else {
                    List<ParameterModel> params = this.dsModel.getParameterModels();
                    for (ParameterModel pm : params) {
                        AbstractParameterValue original;
                        ParameterModel p1 = this.parameterEnv.getParameterModelByName(pm.getName());
                        ParameterModel p2 = null;
                        if (StringUtils.isNotEmpty((String)pm.getMessageAlias())) {
                            p2 = this.parameterEnv.getParameterModelByName(pm.getMessageAlias());
                        }
                        if (p1 != null) continue;
                        pm.setHidden(true);
                        this.parameterEnv.addParameterModel(pm);
                        if (p2 == null || (original = this.parameterEnv.getOriginalValue(p2.getName())) == null) continue;
                        List val = this.parameterEnv.getValueAsList(p2.getName());
                        this.parameterEnv.setValue(pm.getName(), val);
                    }
                }
            }
            this.isResetEnv = false;
        }
        return this.parameterEnv;
    }

    public DSContext(DSModel dsModel) {
        this.dsModel = dsModel;
    }

    public void setDsModel(DSModel dsModel) {
        if (this.dsModel != null) {
            throw new RuntimeException("\u6570\u636e\u96c6\u6a21\u578b\u5df2\u8bbe\u7f6e\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u8bbe\u7f6e");
        }
        this.dsModel = dsModel;
        this.isResetEnv = true;
    }

    public void setEnhancedParameterEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv) {
        this.parameterEnv = parameterEnv;
        this.isResetEnv = true;
    }

    @Deprecated
    public void setParameterEnv(IParameterEnv parameterEnv) {
        this.setEnhancedParameterEnv(new EnhancedParameterEnvAdapter(parameterEnv));
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    @Override
    public String getDataSrcGuid() {
        if (this.dsModel instanceof SQLModel) {
            return ((SQLModel)this.dsModel).getDataSourceId();
        }
        return null;
    }

    public DSModel getDsModel() {
        return this.dsModel;
    }

    @Override
    public String getUserGuid() {
        return this.userGuid;
    }

    public ParameterModel getParameterModel(String parameterName) {
        if (this.parameterEnv == null) {
            return null;
        }
        return this.parameterEnv.getParameterModelByName(parameterName);
    }

    @Override
    public ILogger getLogger() {
        return this.logger;
    }

    public void addFilterItem(FilterItem filter) {
        this.filters.add(filter);
        this.filtered = false;
    }

    @Override
    public FilterItem[] getAllFilterItem() {
        return this.filters.toArray(new FilterItem[0]);
    }

    @Override
    public void markFiltered() {
        this.filtered = true;
    }

    @Override
    public boolean isFiltered() {
        return this.filtered;
    }

    @Override
    public SortItem[] getSortItems() {
        return this.sortItems.toArray(new SortItem[0]);
    }

    @Override
    public void markSorted() {
        this.sorted = true;
    }

    @Override
    public boolean isSorted() {
        return this.sorted;
    }

    public void addSortItem(SortItem item) {
        this.sortItems.add(item);
        this.sorted = false;
    }

    public void setTimekeyRange(String startTimekey, String endTimekey) {
        this.timekeyRange = new RangeValues((Object)startTimekey, (Object)endTimekey);
    }

    @Override
    public RangeValues getTimekeyFiterRange() {
        return this.timekeyRange;
    }
}

