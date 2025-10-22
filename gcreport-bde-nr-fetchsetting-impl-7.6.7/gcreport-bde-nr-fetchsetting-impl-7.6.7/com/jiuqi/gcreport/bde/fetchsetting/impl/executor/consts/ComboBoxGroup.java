/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.common.constant.AgingPeriodTypeEnum
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.MatchRuleEnum
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.common.constant.AgingPeriodTypeEnum;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.MatchRuleEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ComboBoxGroup {
    private String[] fetchSourceDropDownData;
    private String[] fetchTypeDropDownData;
    private String[] signDropDownData;
    private String[] fieldType;
    private String[] floatConfigType;
    private String[] matchRuleDropDownData;
    private String[] sumTypeDropDownData;
    private String[] dimTypeDropDownData;
    private String[] agingRangeTypeDropDownData;
    private String[] agingGroupDropDownData;
    private ExportContext context;

    public ComboBoxGroup(ExportContext context) {
        this.context = context;
    }

    public String[] getFetchSourceDropDownData() {
        if (this.fetchSourceDropDownData == null) {
            List fetchSources = (List)this.context.getVarMap().get("FETCH_SOURCE");
            this.fetchSourceDropDownData = fetchSources.stream().map(BizModelDTO::getName).collect(Collectors.toList()).toArray(new String[0]);
        }
        return this.fetchSourceDropDownData;
    }

    public String[] getFetchTypeDropDownData() {
        if (this.fetchTypeDropDownData == null) {
            List fetchSources = (List)this.context.getVarMap().get("FETCH_SOURCE");
            LinkedHashSet fetchTypeCodes = new LinkedHashSet();
            fetchSources.stream().filter(item -> !ComputationModelEnum.CUSTOMFETCH.getCode().equals(item.getComputationModelCode()) && !ComputationModelEnum.BASEDATA.getCode().equals(item.getComputationModelCode())).filter(item -> !CollectionUtils.isEmpty(FetchSettingNrUtil.getBizModelFields(item))).forEach(item -> fetchTypeCodes.addAll(FetchSettingNrUtil.getBizModelFields(item)));
            this.fetchTypeDropDownData = fetchTypeCodes.toArray(new String[0]);
        }
        return this.fetchTypeDropDownData;
    }

    public String[] getSignDropDownData() {
        if (this.signDropDownData == null) {
            this.signDropDownData = new String[]{"+", "-"};
        }
        return this.signDropDownData;
    }

    public String[] getFieldType() {
        if (this.fieldType == null) {
            this.fieldType = new String[]{"\u81ea\u5b9a\u4e49\u6587\u672c", "\u7ed3\u679c\u5217", "\u81ea\u5b9a\u4e49\u89c4\u5219"};
        }
        return this.fieldType;
    }

    public String[] getFloatConfigType() {
        if (this.floatConfigType == null) {
            this.floatConfigType = new String[]{"\u6807\u51c6\u4e1a\u52a1\u6a21\u578b", "\u9ad8\u7ea7\u4e1a\u52a1\u6a21\u578b", "\u81ea\u5b9a\u4e49\u67e5\u8be2", "\u81ea\u5b9a\u4e49SQL"};
        }
        return this.floatConfigType;
    }

    public String[] getMatchRuleDropDownData() {
        if (this.matchRuleDropDownData == null) {
            this.matchRuleDropDownData = new String[]{MatchRuleEnum.EQ.getName(), MatchRuleEnum.LIKE.getName()};
        }
        return this.matchRuleDropDownData;
    }

    public String[] getSumTypeDropDownData() {
        if (this.sumTypeDropDownData == null) {
            this.sumTypeDropDownData = new String[]{SumTypeEnum.MX.getName(), SumTypeEnum.FMX.getName()};
        }
        return this.sumTypeDropDownData;
    }

    public String[] getDimTypeDropDownData() {
        if (this.dimTypeDropDownData == null) {
            List fetchSources = (List)this.context.getVarMap().get("DIMENSION");
            this.dimTypeDropDownData = fetchSources.stream().map(DimensionVO::getTitle).collect(Collectors.toList()).toArray(new String[0]);
        }
        return this.dimTypeDropDownData;
    }

    public String[] getCflOrDjDropDownData() {
        ArrayList<String> dimTypeName = new ArrayList<String>(Arrays.asList(this.getDimTypeDropDownData()));
        dimTypeName.add(FetchFixedFieldEnum.SUBJECTCODE.getName());
        return dimTypeName.toArray(new String[0]);
    }

    public String[] getAgingRangeTypeDropDownData() {
        if (this.agingRangeTypeDropDownData == null) {
            this.agingRangeTypeDropDownData = new String[]{AgingPeriodTypeEnum.Y.getName(), AgingPeriodTypeEnum.M.getName()};
        }
        return this.agingRangeTypeDropDownData;
    }

    public String[] getAgingGroupDropDownData() {
        if (this.agingGroupDropDownData == null) {
            List gcBaseDataList = (List)this.context.getVarMap().get("MD_AGING");
            this.agingGroupDropDownData = gcBaseDataList.stream().map(GcBaseData::getTitle).collect(Collectors.toList()).toArray(new String[0]);
        }
        return this.agingGroupDropDownData;
    }
}

