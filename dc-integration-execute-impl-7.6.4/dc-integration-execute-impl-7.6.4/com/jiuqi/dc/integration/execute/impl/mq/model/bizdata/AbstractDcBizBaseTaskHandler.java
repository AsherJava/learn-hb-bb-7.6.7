/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.LogUtil
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.base.impl.utils.UnitRpConvertUtil
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskParamsEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.dc.integration.execute.impl.mq.model.bizdata;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.LogUtil;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.base.impl.utils.UnitRpConvertUtil;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.integration.execute.impl.data.UnitCodeConvertParam;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandlerGather;
import com.jiuqi.dc.integration.execute.impl.intf.IDirectVoucherConvertHandler;
import com.jiuqi.dc.integration.execute.impl.mq.BizDataExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.BizModelExecuteParam;
import com.jiuqi.dc.integration.execute.impl.utils.UnitRefConvertUtil;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskParamsEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractDcBizBaseTaskHandler
extends DcBaseTaskHandler {
    public static final String DEFAULT_ETL_POSITION = "dc";
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Autowired
    private IPluginTypeGather pluginGather;
    @Autowired
    private IBizDataConvertHandlerGather gather;
    @Autowired
    private BizDataRefDefineService defineService;
    @Autowired
    private TaskManageService taskMangeService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Value(value="${jiuqi.dc.etl.offsetMinute:3}")
    private int shiftMinute;
    @Value(value="${jiuqi.dc.etl.getUnitRangeNum:10}")
    private int unitRangeNum;
    @Value(value="${jiuqi.dc.etl.position:dc}")
    private String etlPosition;

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.LEVEL;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.FOLLOW;
    }

    public IDimType getDimType() {
        return DimType.UNITCODE;
    }

    public Map<String, String> getHandleParams(String preParam) {
        BizDataExecuteParam executeParam = (BizDataExecuteParam)JsonUtils.readValue((String)preParam, BizDataExecuteParam.class);
        DataMappingDefineDTO define = this.defineService.getByCode(executeParam.getDataSchemeCode(), executeParam.getDefineCode());
        DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(executeParam.getDataSchemeCode());
        IBizDataConvertHandler handler = this.gather.getHandler(this.pluginGather.getPluginType(define.getPluginType()), define.getCode(), dataScheme.getSourceDataType());
        HashMap paramMap = CollectionUtils.newHashMap();
        HashMap resultMap = CollectionUtils.newHashMap();
        if (handler == null) {
            return resultMap;
        }
        Date createDate = DateUtils.shifting((Date)DateUtils.now(), (int)12, (int)(this.shiftMinute * -1));
        String templateParam = executeParam.getTemplateParam();
        if (handler instanceof IDirectVoucherConvertHandler) {
            Pair<Boolean, String> resultPair;
            Set unitSeting;
            UnitCodeConvertParam unitCodeConvertParam = StringUtils.isEmpty((String)executeParam.getTemplateParam()) ? new UnitCodeConvertParam() : (UnitCodeConvertParam)JsonUtils.readValue((String)executeParam.getTemplateParam(), UnitCodeConvertParam.class);
            unitCodeConvertParam.setCreateDate(createDate);
            templateParam = JsonUtils.writeValueAsString((Object)unitCodeConvertParam);
            String unitCondi = "";
            if (!StringUtils.isEmpty((String)executeParam.getTemplateParam()) && (unitSeting = UnitRpConvertUtil.getUnitSeting((String)unitCodeConvertParam.getUnitType(), unitCodeConvertParam.getUnitCodes())).size() <= this.unitRangeNum) {
                Map<String, DataRefDTO> odsUnitCodeRefMap = UnitRefConvertUtil.getUnitCodeRefMap(executeParam.getDataSchemeCode());
                List unitListCondi = unitSeting.stream().map(odsUnitCodeRefMap::get).filter(Objects::nonNull).map(DataRefDTO::getOdsId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(unitListCondi)) {
                    unitCondi = SqlBuildUtil.getStrInCondi((String)"", unitListCondi);
                }
            }
            if (!((Boolean)(resultPair = ((IDirectVoucherConvertHandler)handler).handleProcessUnitRange(dataScheme.getCode(), createDate, unitCondi)).getFirst()).booleanValue()) {
                this.logger.error("\u8c03\u7528ETL\u5931\u8d25\uff0cETL\u65e5\u5fd7\u8be6\u60c5\u5982\u4e0b:\n" + (String)resultPair.getSecond());
                String info = DEFAULT_ETL_POSITION.equalsIgnoreCase(this.etlPosition) ? "\u83b7\u53d6\u5355\u4f4d\u5931\u8d25\uff0c\u8bf7\u67e5\u770b\u9884\u5904\u7406\u65e5\u5fd7\u3010\u6570\u636e\u6574\u5408\u3011\u83b7\u53d6\u5355\u4f4d\u8be6\u60c5\n" : "\u8c03\u7528ETL\u5931\u8d25\uff0c\u8bf7\u67e5\u770bETL\u65e5\u5fd7\u8be6\u60c5\n";
                throw new BusinessRuntimeException(info);
            }
        }
        Map<String, String> convertParam = handler.getConvertParam(executeParam.getDataSchemeCode(), templateParam);
        ArrayList<String> unitList = new ArrayList<String>(convertParam.values().size());
        Map<String, DataRefDTO> odsUnitIdRefMap = UnitRefConvertUtil.getOdsUnitIdRefMap(executeParam.getDataSchemeCode());
        for (Map.Entry<String, String> handleParamEntry : convertParam.entrySet()) {
            paramMap.put(JsonUtils.writeValueAsString((Object)new BizModelExecuteParam(preParam, handleParamEntry.getKey())), handleParamEntry.getValue());
            unitList.add(odsUnitIdRefMap.get(handleParamEntry.getValue()).getCode());
        }
        this.initTaskManage(unitList);
        resultMap.put(TaskParamsEnum.DIMTYPE.name(), handler.getDimType(executeParam.getDataSchemeCode()).getName());
        resultMap.put(TaskParamsEnum.PARAM.name(), JsonUtils.writeValueAsString((Object)paramMap));
        return resultMap;
    }

    protected void initTaskManage(List<String> unitCodes) {
        try {
            this.taskMangeService.initTaskManageByUnitCodes(unitCodes);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u521d\u59cb\u5316\u4efb\u52a1\u9879\u7ba1\u7406\u6570\u636e\u5f02\u5e38\n" + LogUtil.getExceptionStackStr((Throwable)e));
        }
    }
}

