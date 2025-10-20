/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnAttr
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.rate.impl.init;

import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.rate.impl.consts.RateTypeEnum;
import com.jiuqi.gcreport.rate.impl.init.ConversionInit;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonRateInitiator
implements ModuleInitiator {
    private static Logger LOGGER = LoggerFactory.getLogger(CommonRateInitiator.class);
    @Autowired
    CommonRateSchemeService rateSchemeService;
    @Autowired
    CommonRateService commonRateService;
    @Autowired(required=false)
    ConversionInit conversionInit;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    private List<String> copyIds = new ArrayList<String>();

    public void init(ServletContext context) throws Exception {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        List<CommonRateSchemeVO> vo = this.rateSchemeService.listAllRateScheme();
        if (!vo.isEmpty()) {
            return;
        }
        this.updateConversionData();
        String tenantName = ShiroUtil.getTenantName();
        BaseDataDTO param = new BaseDataDTO();
        param.setTenantName(tenantName);
        param.setTableName("MD_RATETYPE");
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(tenantName);
        bdsc.setBaseDataDTO(param);
        bdsc.setClean(true);
        this.baseDataCacheService.pushSyncMsg(bdsc);
    }

    public void updateConversionData() {
        this.clearVariables();
        this.updateRateScheme();
        this.updateRateValue();
        if (this.conversionInit != null) {
            this.conversionInit.excute();
        }
    }

    public void clearVariables() {
        this.copyIds = new ArrayList<String>();
    }

    public void updateRateScheme() {
        this.createDeafuleScheme();
        Map<String, Map> conversionSystemMap = EntNativeSqlDefaultDao.getInstance().selectMap(this.getQuerySql("GC_CONV_SYSTEM"), new Object[0]).stream().collect(Collectors.toMap(v -> v.get("ID").toString(), v -> v));
        Map<String, Map> conversionGroupMap = EntNativeSqlDefaultDao.getInstance().selectMap(this.getQuerySql("GC_CONV_RATE_G"), new Object[0]).stream().collect(Collectors.toMap(v -> v.get("ID").toString(), v -> v));
        for (String groupId : conversionGroupMap.keySet()) {
            Map groupData = conversionGroupMap.get(groupId);
            CommonRateSchemeVO vo = new CommonRateSchemeVO();
            vo.setId(groupData.get("ID").toString());
            String systemId = groupData.get("SYSTEMID").toString();
            Map conversionData = conversionSystemMap.get(systemId);
            if (conversionData == null) continue;
            int period = Integer.parseInt(groupData.get("PERIODID").toString());
            String periodSymbol = this.getPeriodSymbol(period);
            vo.setName(conversionData.get("SYSTEMNAME").toString() + "-" + CommonRateUtils.getPeriodTypeTitle(periodSymbol));
            vo.setCode(this.getRandomChar(7));
            vo.setCreatetime(new Date());
            vo.setUpdatetime(new Date());
            vo.setPeriodType(periodSymbol);
            if (vo.getPeriodType().equals("Y")) {
                this.copyIds.add(groupData.get("ID").toString());
            }
            this.rateSchemeService.saveRateScheme(vo);
        }
    }

    public void updateRateValue() {
        List<BaseDataDO> baseDataDO = CommonRateUtils.getAllRateTypes();
        ArrayList<DataModelColumn> addColumList = new ArrayList<DataModelColumn>();
        for (BaseDataDO baseDataDTO : baseDataDO) {
            if (RateTypeEnum.getEnumByCode(baseDataDTO.getCode()) != null) continue;
            String code = baseDataDTO.getCode();
            DataModelColumn column = new DataModelColumn();
            column.setColumnName("RATETYPE_" + code);
            column.setColumnTitle(baseDataDTO.getName());
            column.setColumnType(DataModelType.ColumnType.NUMERIC);
            Integer[] lengths = new Integer[]{19, 6};
            column.setLengths(lengths);
            column.setColumnAttr(DataModelType.ColumnAttr.EXTEND);
            addColumList.add(column);
        }
        CommonRateUtils.updateDefine("MD_ENT_RATE", addColumList);
        String copyId = this.copyIds.size() == 1 ? this.copyIds.get(0) : null;
        List<CommonRateSchemeVO> rateSchemeVOS = this.rateSchemeService.listAllRateScheme();
        Map<String, String> rateSchemeIdVCode = rateSchemeVOS.stream().collect(Collectors.toMap(CommonRateSchemeVO::getId, CommonRateSchemeVO::getCode));
        ArrayList<CommonRateInfoVO> voList = new ArrayList<CommonRateInfoVO>();
        Map<String, Map> nodeMap = EntNativeSqlDefaultDao.getInstance().selectMap(this.getQuerySql("GC_CONV_RATE_T"), new Object[0]).stream().collect(Collectors.toMap(v -> v.get("ID").toString(), v -> v));
        Map<String, List<Map>> rateValueMap = EntNativeSqlDefaultDao.getInstance().selectMap(this.getQuerySql("GC_CONV_RATE_V"), new Object[0]).stream().collect(Collectors.groupingBy(v -> v.get("ROWDATAID").toString()));
        for (String rowId : rateValueMap.keySet()) {
            try {
                String groupId;
                String rateSchemeCode;
                List<Map> rateDataList = rateValueMap.get(rowId);
                Map rateValue = rateDataList.get(0);
                String nodeId = rateValue.get("NODEID").toString();
                Map nodeData = nodeMap.get(nodeId);
                if (nodeData == null || (rateSchemeCode = rateSchemeIdVCode.get(groupId = nodeData.get("RATEGROUPID").toString())) == null) continue;
                String sourceCurrencyCode = nodeData.get("SOURCECURRENCYCODE").toString();
                String targetCurrencyCode = nodeData.get("TARGETCURRENCYCODE").toString();
                String dataTime = rateValue.get("DATATIME").toString();
                CommonRateInfoVO vo = new CommonRateInfoVO();
                vo.setRateSchemeCode(rateSchemeCode);
                vo.setSourceCurrencyCode(sourceCurrencyCode);
                vo.setTargetCurrencyCode(targetCurrencyCode);
                vo.setDataTime(dataTime);
                HashMap<String, BigDecimal> rateInfo = new HashMap<String, BigDecimal>();
                for (Map rateData : rateDataList) {
                    Double value = (Double)rateData.get("RATEVALUE");
                    rateInfo.put((String)rateData.get("RATETYPECODE"), new BigDecimal(value));
                }
                vo.setRateInfo(rateInfo);
                if (groupId.equals(copyId)) {
                    CommonRateInfoVO copyVO = new CommonRateInfoVO();
                    copyVO.setRateSchemeCode("DEFAULT");
                    copyVO.setSourceCurrencyCode(sourceCurrencyCode);
                    copyVO.setTargetCurrencyCode(targetCurrencyCode);
                    copyVO.setDataTime(dataTime);
                    copyVO.setRateInfo(rateInfo);
                    voList.add(copyVO);
                }
                voList.add(vo);
            }
            catch (Exception e) {
                LOGGER.error("\u884cId\u4e3a\uff1a" + rowId + "\u7684\u6c47\u7387\u503c\u6267\u884c\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a" + e.getMessage());
            }
        }
        this.commonRateService.saveRates(voList);
    }

    public void createDeafuleScheme() {
        CommonRateSchemeVO vo = new CommonRateSchemeVO();
        vo.setId("00000000-0000-0000-0000-000000000000");
        vo.setName("\u9ed8\u8ba4\u6c47\u7387\u65b9\u6848");
        vo.setCode("DEFAULT");
        vo.setCreatetime(new Date());
        vo.setUpdatetime(new Date());
        vo.setPeriodType("Y");
        vo.setDescription("\u9ed8\u8ba4\u6c47\u7387\u65b9\u6848\uff0c\u7981\u6b62\u5220\u9664");
        this.rateSchemeService.saveRateScheme(vo);
    }

    public String getQuerySql(String tableName) {
        return "select * from " + tableName;
    }

    public String getRandomChar(int length) {
        String str = "";
        for (int i = 0; i < length; ++i) {
            str = str + (char)(Math.random() * 26.0 + 65.0);
        }
        return str;
    }

    public String getPeriodSymbol(int periodType) {
        String periodSymbol = "";
        switch (periodType) {
            case 1: {
                periodSymbol = "N";
                break;
            }
            case 2: {
                periodSymbol = "H";
                break;
            }
            case 3: {
                periodSymbol = "J";
                break;
            }
            case 4: {
                periodSymbol = "Y";
                break;
            }
            case 5: {
                periodSymbol = "X";
                break;
            }
            case 6: {
                periodSymbol = "R";
                break;
            }
            case 7: {
                periodSymbol = "Z";
                break;
            }
            case 8: {
                periodSymbol = "B";
            }
        }
        return periodSymbol;
    }
}

