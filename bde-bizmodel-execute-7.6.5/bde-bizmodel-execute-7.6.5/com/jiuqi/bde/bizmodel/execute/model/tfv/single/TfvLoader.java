/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.IBizDataModelLoader
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.ContextVariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.single;

import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.IBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.model.tfv.single.TfvDataCondi;
import com.jiuqi.bde.bizmodel.execute.model.tfv.single.TfvLoaderResultSetExtractor;
import com.jiuqi.bde.bizmodel.execute.model.tfv.utils.TfvExecuteUtil;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.ContextVariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class TfvLoader
implements IBizDataModelLoader<TfvDataCondi, Collection<FetchResult>> {
    @Autowired
    private DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return null;
    }

    public String getBizDataModelCode() {
        return BizDataModelEnum.TFVMODEL.getCode();
    }

    public String getComputationModelCode() {
        return "TFV";
    }

    public List<FetchResult> loadData(TfvDataCondi condi) {
        List<ExecuteSettingVO> fetchSettingList = condi.getFetchSettingList();
        ArrayList<FetchResult> resultList = new ArrayList<FetchResult>(fetchSettingList.size());
        FetchResult result = null;
        for (ExecuteSettingVO settingVo : fetchSettingList) {
            result = new FetchResult(settingVo);
            String sql = ContextVariableParseUtil.parse((String)settingVo.getFormula(), (FetchTaskContext)condi.getFetchTaskContext());
            BdeLogUtil.recordLog((String)condi.getFetchTaskContext().getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u81ea\u5b9a\u4e49\u53d6\u6570", (Object)new Object[]{condi}, (String)sql);
            List<Object> queryList = this.getResult(condi, settingVo, sql);
            if (CollectionUtils.isEmpty(queryList)) {
                if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)settingVo.getFieldDefineType())) {
                    result.setZbValue(new BigDecimal(TfvExecuteUtil.getValByFieldDefineType(settingVo).toString()));
                    result.setZbValueType(ColumnTypeEnum.NUMBER);
                } else {
                    result.setZbValue(TfvExecuteUtil.getValByFieldDefineType(settingVo).toString());
                    result.setZbValueType(ColumnTypeEnum.STRING);
                }
                resultList.add(result);
                continue;
            }
            if (settingVo.getFieldDefineType() == null) {
                if (queryList.get(0) == null) {
                    result.setZbValue(BigDecimal.ZERO.toString());
                    result.setZbValueType(ColumnTypeEnum.NUMBER);
                } else if (queryList.get(0) instanceof BigDecimal) {
                    result.setZbValue(new BigDecimal(queryList.get(0).toString()));
                    result.setZbValueType(ColumnTypeEnum.NUMBER);
                } else {
                    result.setZbValue(queryList.get(0).toString());
                    result.setZbValueType(ColumnTypeEnum.STRING);
                }
            } else if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)settingVo.getFieldDefineType())) {
                result.setZbValue(new BigDecimal(queryList.get(0).toString()));
                result.setZbValueType(ColumnTypeEnum.NUMBER);
            } else {
                result.setZbValue(queryList.get(0).toString());
                result.setZbValueType(ColumnTypeEnum.STRING);
            }
            resultList.add(result);
        }
        return resultList;
    }

    protected List<Object> getResult(TfvDataCondi condi, ExecuteSettingVO settingVo, String sql) {
        return (List)this.dataSourceService.pageQuery(condi.getFetchTaskContext().getOrgMapping().getDataSourceCode(), sql, 1, 1, new Object[0], (ResultSetExtractor)new TfvLoaderResultSetExtractor(settingVo));
    }

    protected String buildPeriod(String year, String period) {
        if ("0".equals(period)) {
            period = "1";
        }
        return String.format("%1$s-0%2$s", year, period);
    }
}

