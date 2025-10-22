/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.apache.commons.lang3.StringUtils
 */
package nr.midstore.core.internal.util.service;

import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.MidstoreLib;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreDimensionDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.util.IMidstoreDimensionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreDimensionServiceImpl
implements IMidstoreDimensionService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreDimensionServiceImpl.class);
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeServie;
    private SimpleDateFormat dateFormatterBase = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void createTempTable(MidstoreContext context) {
        TempAssistantTable tempTable = new TempAssistantTable(context.getExchangeEnityCodes(), 6);
        try {
            tempTable.createTempTable();
            tempTable.insertIntoTempTable();
            context.getIntfObjects().put("TempAssistantTable", tempTable);
            logger.info("\u4e2d\u95f4\u5e93\uff1a\u521b\u5efa\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void closeTempTable(MidstoreContext context) {
        if (context.getIntfObjects().containsKey("TempAssistantTable")) {
            TempAssistantTable tempTable = (TempAssistantTable)context.getIntfObjects().get("TempAssistantTable");
            try {
                tempTable.close();
                context.getIntfObjects().remove("TempAssistantTable");
                logger.info("\u4e2d\u95f4\u5e93\uff1a\u91ca\u653e\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Map<String, DimensionValue> getDimSetMap(MidstoreContext context) throws MidstoreException {
        String periodCode = this.getExcutePeriod(context);
        if (StringUtils.isNotEmpty((CharSequence)periodCode)) {
            context.setExcutePeriod(periodCode);
        }
        HashMap<String, DimensionValue> otherDimensionSet = null;
        if (StringUtils.isNotEmpty((CharSequence)context.getMidstoreScheme().getDimensions()) && StringUtils.isNotEmpty((CharSequence)context.getMidstoreScheme().getDimensions())) {
            try {
                MidstoreDimensionDTO mDim = MidstoreLib.toObject(context.getMidstoreScheme().getDimensions(), MidstoreDimensionDTO.class);
                otherDimensionSet = new HashMap<String, DimensionValue>();
                for (Map.Entry<String, DimensionValue> entry : mDim.getDimensionSet().entrySet()) {
                    DimensionValue dimValue = entry.getValue();
                    IEntityDefine define = this.entityMetaService.queryEntity(dimValue.getName());
                    if (define != null) {
                        dimValue.setName(define.getDimensionName());
                    }
                    otherDimensionSet.put(dimValue.getName(), dimValue);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException("\u60c5\u666f\u8bfb\u53d6\u6709\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
        String orgDatas = (String)context.getExcuteParams().get("OrgData");
        HashMap<String, DimensionValue> dimSetMap = new HashMap<String, DimensionValue>();
        if (StringUtils.isEmpty((CharSequence)orgDatas)) {
            if (context.getExchangeEnityCodes().size() == 0 && context.getMidstoreScheme().getExchangeMode() == ExchangeModeType.EXCHANGE_POST) {
                if (context.getSchemeInfo().isAllOrgData()) {
                    OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
                    String orgCode = EntityUtils.getId((String)context.getTaskDefine().getDw());
                    OrgDTO orgParam = new OrgDTO();
                    orgParam.setCategoryname(orgCode);
                    orgParam.setStopflag(Integer.valueOf(-1));
                    orgParam.setRecoveryflag(Integer.valueOf(-1));
                    orgParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
                    PageVO queryRes = orgDataClient.list(orgParam);
                    if (queryRes != null && queryRes.getRows() != null) {
                        for (OrgDO data : queryRes.getRows()) {
                            context.getExchangeEnityCodes().add(data.getCode());
                        }
                    }
                } else {
                    IMidstoreOrgDataService orgDataService = (IMidstoreOrgDataService)ApplicationContextRegister.getBean(IMidstoreOrgDataService.class);
                    MidstoreOrgDataDTO param = new MidstoreOrgDataDTO();
                    param.setSchemeKey(context.getSchemeKey());
                    List<MidstoreOrgDataDTO> list = orgDataService.list(param);
                    if (list.size() > 0) {
                        for (MidstoreOrgDataDTO item : list) {
                            context.getExchangeEnityCodes().add(item.getCode());
                        }
                    }
                }
            }
            StringBuilder units = new StringBuilder();
            for (String entityCode : context.getExchangeEnityCodes()) {
                if (units.length() == 0) {
                    units.append(entityCode);
                    continue;
                }
                units.append(",");
                units.append(entityCode);
            }
            orgDatas = units.toString();
        }
        DimensionValue corpDim = new DimensionValue();
        corpDim.setType(0);
        corpDim.setName(context.getEntityTypeName());
        corpDim.setValue(orgDatas);
        dimSetMap.put(context.getEntityTypeName(), corpDim);
        DimensionValue dateDim = new DimensionValue();
        dateDim.setType(0);
        dateDim.setName(context.getDateTypeName());
        dateDim.setValue(periodCode);
        dimSetMap.put(context.getDateTypeName(), dateDim);
        if (otherDimensionSet != null && !otherDimensionSet.isEmpty()) {
            dimSetMap.putAll(otherDimensionSet);
        }
        if (context.getTaskDefine() != null && this.dataSchemeServie.enableAdjustPeriod(context.getTaskDefine().getDataScheme()).booleanValue()) {
            DimensionValue adjustDim = new DimensionValue();
            adjustDim.setType(0);
            adjustDim.setName("ADJUST");
            adjustDim.setValue("0");
            dimSetMap.put("ADJUST", adjustDim);
        }
        return dimSetMap;
    }

    @Override
    public String getDePeriodFromNr(String timeEntityID, String nrPeriodCode) {
        if (StringUtils.isEmpty((CharSequence)nrPeriodCode)) {
            return nrPeriodCode;
        }
        if (nrPeriodCode.length() < 9) {
            return nrPeriodCode;
        }
        IPeriodEntity peroidEntity = this.periodAdapter.getPeriodEntity(timeEntityID);
        PeriodType nrPeriodType = peroidEntity.getType();
        String dePeriod = "";
        if (nrPeriodType == PeriodType.YEAR) {
            dePeriod = nrPeriodCode.substring(0, 4) + "0101";
        } else if (nrPeriodType == PeriodType.HALFYEAR) {
            String lastCode = nrPeriodCode.substring(nrPeriodCode.length() - 1, nrPeriodCode.length());
            if ("1".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "0101";
            } else if ("2".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "0701";
            }
        } else if (nrPeriodType == PeriodType.SEASON) {
            String lastCode = nrPeriodCode.substring(nrPeriodCode.length() - 1, nrPeriodCode.length());
            if ("1".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "0101";
            } else if ("2".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "0401";
            } else if ("3".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "0701";
            } else if ("4".equalsIgnoreCase(lastCode)) {
                dePeriod = nrPeriodCode.substring(0, 4) + "1001";
            }
        } else if (nrPeriodType == PeriodType.TENDAY) {
            String monthCode;
            String lastCode = nrPeriodCode.substring(nrPeriodCode.length() - 3, nrPeriodCode.length());
            int period = Integer.parseInt(lastCode);
            int month = period / 3;
            int num = period % 3;
            if (num == 0) {
                num = 3;
            }
            if ((monthCode = String.valueOf(month)).length() == 1) {
                monthCode = "0" + monthCode;
            }
            String dayCode = String.valueOf((num - 1) * 10 + 1);
            dePeriod = nrPeriodCode.substring(0, 4) + monthCode + dayCode;
        } else if (nrPeriodType == PeriodType.WEEK || nrPeriodType == PeriodType.DAY) {
            IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(timeEntityID);
            try {
                Date[] rangDates = periodProvider.getPeriodDateRegion(nrPeriodCode);
                if (rangDates != null && rangDates.length > 0) {
                    Date startDate = rangDates[0];
                    dePeriod = this.dateFormatterBase.format(startDate);
                }
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        } else if (nrPeriodType == PeriodType.MONTH) {
            dePeriod = nrPeriodCode.substring(0, 4) + nrPeriodCode.substring(7, 9) + "01";
        }
        return dePeriod;
    }

    @Override
    public String getNrPeriodFromDe(String timeEntityID, String dePeriodCode) {
        if (StringUtils.isEmpty((CharSequence)dePeriodCode)) {
            return dePeriodCode;
        }
        if (dePeriodCode.length() != 8) {
            return dePeriodCode;
        }
        IPeriodEntity peroidEntity = this.periodAdapter.getPeriodEntity(timeEntityID);
        PeriodType nrPeriodType = peroidEntity.getType();
        String nrPeriodTypeCode = String.valueOf((char)nrPeriodType.code());
        String nrPeriod = "";
        if (nrPeriodType == PeriodType.YEAR) {
            nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0001";
        } else if (nrPeriodType == PeriodType.HALFYEAR) {
            String lastCode = dePeriodCode.substring(4, 8);
            if ("0101".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0001";
            } else if ("0701".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0002";
            }
        } else if (nrPeriodType == PeriodType.SEASON) {
            String lastCode = dePeriodCode.substring(4, 8);
            if ("0101".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0001";
            } else if ("0401".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0002";
            } else if ("0701".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0003";
            } else if ("1001".equalsIgnoreCase(lastCode)) {
                nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "0004";
            }
        } else if (nrPeriodType == PeriodType.TENDAY) {
            String lastCode = dePeriodCode.substring(4, 8);
            String monthCode = lastCode.substring(0, 2);
            String dayCode = lastCode.substring(2, 4);
            int month = Integer.parseInt(monthCode);
            int day = Integer.parseInt(dayCode);
            int num = 1;
            if (day == 1) {
                num = 1;
            } else if (day == 11) {
                num = 2;
            } else if (day == 21) {
                num = 3;
            }
            int period = month * 3 + num;
            String periodCode = String.valueOf(period);
            if (periodCode.length() == 1) {
                periodCode = "000" + periodCode;
            } else if (periodCode.length() == 2) {
                periodCode = "00" + periodCode;
            } else if (periodCode.length() == 3) {
                periodCode = "0" + periodCode;
            }
            nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + periodCode;
        } else if (nrPeriodType == PeriodType.WEEK || nrPeriodType == PeriodType.DAY) {
            try {
                Date startDate = this.dateFormatterBase.parse(dePeriodCode);
                nrPeriod = PeriodUtils.getPeriodFromDate((int)nrPeriodType.type(), (Date)startDate);
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        } else if (nrPeriodType == PeriodType.MONTH) {
            nrPeriod = dePeriodCode.substring(0, 4) + nrPeriodTypeCode + "00" + dePeriodCode.substring(4, 6);
        }
        return nrPeriod;
    }

    @Override
    public String getCurPeriodByDate(String timeEntityID) {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(timeEntityID);
        String curPeriod = periodProvider.getCurPeriod().getCode();
        return curPeriod;
    }

    @Override
    public String getPriorPeriodByDate(String timeEntityID) {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(timeEntityID);
        String curPeriod = periodProvider.getCurPeriod().getCode();
        return periodProvider.priorPeriod(curPeriod);
    }

    @Override
    public String getPeriodTitle(String timeEntityID, String periodCode) {
        IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(timeEntityID);
        return periodProvider.getPeriodTitle(periodCode);
    }

    @Override
    public String getExcutePeriod(MidstoreContext context) {
        String periodCode = context.getExcutePeriod();
        String dataTime = (String)context.getExcuteParams().get("DataTime");
        String dataTime2 = (String)context.getExcuteParams().get("DATATIME");
        if (StringUtils.isNotEmpty((CharSequence)dataTime)) {
            if (dataTime.length() == 9) {
                periodCode = dataTime;
            } else if (dataTime.length() == 8) {
                // empty if block
            }
        } else if (StringUtils.isNotEmpty((CharSequence)dataTime2)) {
            periodCode = dataTime2;
        } else if (context.getSchemeInfo().getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_CURRENT) {
            IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(context.getTaskDefine().getDateTime());
            String curPeriod = periodProvider.getCurPeriod().getCode();
            if (StringUtils.isNotEmpty((CharSequence)curPeriod)) {
                periodCode = curPeriod;
            }
        } else if (context.getSchemeInfo().getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_LAST) {
            IPeriodProvider periodProvider = this.periodAdapter.getPeriodProvider(context.getTaskDefine().getDateTime());
            String curPeriod = this.getCurPeriodByDate(context.getTaskDefine().getDateTime());
            if (StringUtils.isNotEmpty((CharSequence)curPeriod)) {
                periodCode = periodProvider.priorPeriod(curPeriod);
            }
        } else if (context.getSchemeInfo().getExcutePeriodType() == ExcutePeriodType.EXCUTEPERIOD_APPOINT) {
            periodCode = context.getSchemeInfo().getExcutePeriod();
        }
        return periodCode;
    }
}

