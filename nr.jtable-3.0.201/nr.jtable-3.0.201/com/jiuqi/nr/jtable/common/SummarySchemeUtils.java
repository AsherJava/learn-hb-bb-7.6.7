/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeFieldInfo
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.summary.SummaryConfig
 *  com.jiuqi.nr.common.summary.SummaryField
 *  com.jiuqi.nr.common.summary.SummaryScheme
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.common;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeFieldInfo;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.summary.SummaryConfig;
import com.jiuqi.nr.common.summary.SummaryField;
import com.jiuqi.nr.common.summary.SummaryScheme;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummarySchemeUtils {
    private static final Logger logger = LoggerFactory.getLogger(SummarySchemeUtils.class);
    private IDataDefinitionRuntimeController tbRtCtl = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private IEntityViewRunTimeController viewRtCtl = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);

    public ReloadTreeInfo toReloadTreeInfo(SummaryScheme scheme) {
        try {
            return this._toReloadTreeInfo(scheme);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private ReloadTreeInfo _toReloadTreeInfo(SummaryScheme scheme) throws Exception {
        ReloadTreeInfo reloadInfo = null;
        if (scheme != null) {
            SummaryConfig config = scheme.getConfig();
            reloadInfo = new ReloadTreeInfo();
            reloadInfo.setFilterCondition(config.getSumFormula());
            reloadInfo.setNotShowPreEntitys(config.isSumOriUnitInvolved());
            reloadInfo.setShowDetailEntitys(config.isShowDetailUnit());
            List sumDimensions = config.getSumDimensions();
            ArrayList<ReloadTreeFieldInfo> sumFieldsInfo = new ArrayList<ReloadTreeFieldInfo>(0);
            for (SummaryField dimField : sumDimensions) {
                ReloadTreeFieldInfo fieldInfo = this.buildFieldInfo(dimField);
                if (fieldInfo == null) continue;
                sumFieldsInfo.add(fieldInfo);
            }
            reloadInfo.setReloadTreeFieldInfos(sumFieldsInfo);
        }
        return reloadInfo;
    }

    private ReloadTreeFieldInfo buildFieldInfo(SummaryField dimField) throws Exception {
        ReloadTreeFieldInfo fieldInfo = null;
        String dimType = dimField.getDimType();
        if (SummaryField.data_link.equals(dimType)) {
            fieldInfo = this.buildDataLinkFieldInfo(dimField);
        } else if (SummaryField.upload_state.equals(dimType)) {
            fieldInfo = this.buildUploadFieldInfo(dimField);
        }
        return fieldInfo;
    }

    private ReloadTreeFieldInfo buildDataLinkFieldInfo(SummaryField dimField) throws Exception {
        ReloadTreeFieldInfo fieldInfo = new ReloadTreeFieldInfo();
        FieldDefine fieldDefine = this.tbRtCtl.queryFieldDefine(dimField.getDimKey());
        fieldInfo.setFieldDefine(fieldDefine);
        fieldInfo.setShowLevels(this.toDimensionLevel(dimField));
        if (StringUtils.isNotEmpty((String)fieldDefine.getEntityKey())) {
            fieldInfo.setEntityView(this.viewRtCtl.buildEntityView(fieldDefine.getEntityKey()));
        }
        return fieldInfo;
    }

    private ReloadTreeFieldInfo buildUploadFieldInfo(SummaryField dimField) throws Exception {
        ReloadTreeFieldInfo fieldInfo = null;
        String[] structValues = dimField.getStructValues();
        if (structValues != null && structValues.length > 0) {
            fieldInfo = new ReloadTreeFieldInfo();
            FieldDefine fieldDefine = this.tbRtCtl.queryFieldDefine(dimField.getDimKey());
            fieldInfo.setFieldDefine(fieldDefine);
            fieldInfo.setTitleMap(new HashMap());
            Map structMaps = dimField.getStructMaps();
            for (String val : structValues) {
                fieldInfo.getTitleMap().put(val, structMaps.get(val));
            }
        }
        return fieldInfo;
    }

    private List<Integer> toDimensionLevel(SummaryField dimField) {
        String[] structValues;
        ArrayList<Integer> level = new ArrayList<Integer>();
        if (SummaryField.data_link.equals(dimField.getDimType()) && (structValues = dimField.getStructValues()) != null && structValues.length > 0) {
            level = new ArrayList();
            for (String value : structValues) {
                level.add(Integer.valueOf(value));
            }
        }
        return level;
    }
}

