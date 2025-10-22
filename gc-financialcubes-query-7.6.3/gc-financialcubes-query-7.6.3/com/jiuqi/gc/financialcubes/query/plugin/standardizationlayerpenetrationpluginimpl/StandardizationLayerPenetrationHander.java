/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.gc.financialcubes.query.plugin.standardizationlayerpenetrationpluginimpl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.plugin.StandardizationLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.redisservice.PenetrationParamCache;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StandardizationLayerPenetrationHander
implements StandardizationLayerPenetrationPlugin {
    @Autowired
    protected PenetrationParamCache penetrationParamCache;
    @Autowired
    protected IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    protected List<PenetrationStandardization> penetrationStandardizations;

    @Override
    public PenetrationParamDTO convert(Map<String, String> msgMap, PenetrationContextInfo context) {
        PenetrationParamDTO penetrationParamDTO = new PenetrationParamDTO();
        List dataFieldByTableCode = this.iRuntimeDataSchemeService.getDataFieldByTableCode(context.getDataSchemeTableCode());
        if (CollectionUtils.isEmpty((Collection)dataFieldByTableCode)) {
            throw new BusinessRuntimeException("\u7a7f\u900f\u7c7b\u578b\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u7a7f\u900f");
        }
        String penetrateType = ((DataFieldDeployInfo)this.iRuntimeDataSchemeService.getDeployInfoByDataTableKey(((DataField)dataFieldByTableCode.get(0)).getDataTableKey()).get(0)).getTableName();
        penetrationParamDTO.setPenetrationType(penetrateType);
        penetrationParamDTO.setMdGcOrgType(PenetrationValueUtil.extractOrgType(this.iRuntimeDataSchemeService.getDataTableByCode(context.getDataSchemeTableCode()).getExpression()));
        this.processMsgMap(msgMap, penetrationParamDTO, context);
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)penetrationParamDTO.getMdGcOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, penetrationParamDTO.getDataTime()));
        penetrationParamDTO.setUnitType(orgService.getOrgByCode(penetrationParamDTO.getMdCode()).getBblx());
        this.setupPenetrationParamDTO(penetrationParamDTO);
        return penetrationParamDTO;
    }

    protected void processMsgMap(Map<String, String> msgMap, PenetrationParamDTO penetrationParamDTO, PenetrationContextInfo context) {
        for (Map.Entry<String, String> entry : msgMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            boolean matched = false;
            for (PenetrationStandardization parser : this.penetrationStandardizations) {
                if (!parser.match(key, value, context)) continue;
                parser.process(penetrationParamDTO, key, value, context);
                matched = true;
                break;
            }
            if (matched) continue;
            throw new BusinessRuntimeException("\u76ee\u524d\u6682\u4e0d\u652f\u6301\u6b64\u5b57\u6bb5\u7684\u7a7f\u900f" + key);
        }
    }

    protected void setupPenetrationParamDTO(PenetrationParamDTO dto) {
        Map<String, Object> data = dto.getData();
        if (data == null) {
            if (UnitType.SINGLE.getValue().equals(dto.getUnitType())) {
                dto.setUnitCode(dto.getMdCode());
            }
            return;
        }
        String oppUnitCode = ConverterUtils.getAsString((Object)data.get("OPPUNITCODE"));
        if (oppUnitCode != null) {
            dto.setOppoUnitCode(oppUnitCode);
            data.remove("OPPUNITCODE");
        }
        String subjectCode = ConverterUtils.getAsString((Object)data.get("SUBJECTCODE"));
        if (dto.getSubjectCode() == null && subjectCode != null) {
            dto.setSubjectCode(subjectCode);
            data.remove("SUBJECTCODE");
        }
        String unitCode = ConverterUtils.getAsString((Object)data.get("UNITCODE"));
        if (dto.getUnitCode() == null) {
            if (unitCode != null) {
                dto.setUnitCode(unitCode);
                data.remove("UNITCODE");
            } else if (UnitType.SINGLE.getValue().equals(dto.getUnitType())) {
                dto.setUnitCode(dto.getMdCode());
            }
        }
        if (data.containsKey("UNITCODE")) {
            data.remove("UNITCODE");
        }
    }
}

