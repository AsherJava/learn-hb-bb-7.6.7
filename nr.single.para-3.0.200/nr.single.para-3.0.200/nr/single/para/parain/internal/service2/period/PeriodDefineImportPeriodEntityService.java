/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl
 *  com.jiuqi.nr.period.modal.impl.PeriodDefineImpl
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.nr.period.service.PeriodService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 */
package nr.single.para.parain.internal.service2.period;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IParaImportCommonService;
import nr.single.para.parain.service.IPeriodDefineImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PeriodDefineImportPeriodEntityService
implements IPeriodDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(PeriodDefineImportPeriodEntityService.class);
    @Autowired
    private IParaImportCommonService paraCommonService;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDataService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;

    @Override
    public String importDateEntity(TaskImportContext importContext) throws Exception {
        ParaInfo para = importContext.getParaInfo();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        PeriodType periodType = this.paraCommonService.getTaskPeriod(importContext);
        int periodCount = this.paraCommonService.getTaskPeriodCount(importContext);
        PeriodType netPeriodType = periodType;
        EnumsItemModel newEnum = null;
        EnumsItemModel oldEnum = para.getSQEnum();
        Object periodViewKey = null;
        String peroidTableKey = null;
        if (periodType == PeriodType.CUSTOM || oldEnum != null && periodCount != oldEnum.getItemDataList().size()) {
            boolean isPeriodNew;
            List peroidEntitys = this.periodService.queryCustomPeriodList();
            HashMap<String, IPeriodEntity> peroidEntityDic = new HashMap<String, IPeriodEntity>();
            HashMap<String, IPeriodEntity> peroidEntitytitleDic = new HashMap<String, IPeriodEntity>();
            for (IPeriodEntity entity : peroidEntitys) {
                peroidEntityDic.put(entity.getCode(), entity);
                peroidEntitytitleDic.put(entity.getTitle(), entity);
            }
            String fileFlag = "";
            if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
                fileFlag = dataScheme.getPrefix().toUpperCase();
            } else if (StringUtils.isNotEmpty((String)importContext.getSchemeInfoCache().getFormScheme().getFilePrefix())) {
                fileFlag = importContext.getSchemeInfoCache().getFormScheme().getFilePrefix().toLowerCase();
            }
            String periodTableCode = fileFlag.toUpperCase();
            IPeriodEntity periodTable = (IPeriodEntity)peroidEntityDic.get(periodTableCode);
            boolean bl = isPeriodNew = null == periodTable;
            if (isPeriodNew) {
                PeriodDefineImpl periodDefine = new PeriodDefineImpl();
                periodDefine.setKey(periodTableCode);
                periodDefine.setCode(periodTableCode);
                String title = dataScheme.getTitle() + "\u65f6\u671f";
                if (peroidEntitytitleDic.containsKey(title)) {
                    int n = 0;
                    while (peroidEntitytitleDic.containsKey(title)) {
                        title = dataScheme.getTitle() + String.valueOf(++n) + "\u65f6\u671f";
                    }
                }
                periodDefine.setTitle(title);
                periodDefine.setType(PeriodType.CUSTOM);
                periodTable = periodDefine;
                this.periodService.insertCustomPeriod(periodTable);
                peroidTableKey = periodTable.getKey();
            } else {
                peroidTableKey = periodTable.getKey();
                netPeriodType = periodTable.getPeriodType();
            }
            importContext.getSchemeInfoCache().getTableCache().put(periodTable.getCode(), new TableInfoDefine(periodTable));
            ArrayList<DesignFieldDefine> periodFields = new ArrayList<DesignFieldDefine>();
            newEnum = this.paraCommonService.getPeriodEnumByPeriodType(importContext, oldEnum, netPeriodType, true);
            try {
                this.ImportPeriodDatas(peroidTableKey, periodFields, newEnum, periodTableCode);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            this.paraCommonService.UpdateEnumMap(importContext, newEnum, new TableInfoDefine(periodTable));
            if (newEnum.getItemDataList().size() > 0 && formScheme != null) {
                formScheme.setFromPeriod(((DataInfo)newEnum.getItemDataList().get(0)).getCode());
                formScheme.setToPeriod(((DataInfo)newEnum.getItemDataList().get(newEnum.getItemDataList().size() - 1)).getCode());
            }
        } else {
            List peroidEntitys = this.periodService.queryDefaultPeriodList();
            for (IPeriodEntity entity : peroidEntitys) {
                if (entity.getType() != periodType) continue;
                peroidTableKey = entity.getKey();
            }
            newEnum = this.paraCommonService.getPeriodEnumByPeriodType(importContext, oldEnum, netPeriodType, true);
            this.paraCommonService.UpdateEnumMap(importContext, newEnum, null);
            if (formScheme != null) {
                formScheme.setFromPeriod(para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodType) + "0001");
                if (null != newEnum && newEnum.getItemDataList().size() > 0) {
                    String toPeriodCode = String.valueOf(periodCount);
                    if (periodCount <= 0) {
                        DataInfo info = (DataInfo)newEnum.getItemDataList().get(newEnum.getItemDataList().size() - 1);
                        toPeriodCode = info.getCode();
                    }
                    if (toPeriodCode.length() < 4) {
                        toPeriodCode = "000000".substring(0, 4 - toPeriodCode.length()) + toPeriodCode;
                    } else if (toPeriodCode.length() > 4) {
                        toPeriodCode = toPeriodCode.substring(toPeriodCode.length() - 4, toPeriodCode.length() - 1);
                    }
                    formScheme.setToPeriod(para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodType) + toPeriodCode);
                } else {
                    formScheme.setToPeriod(formScheme.getFromPeriod());
                }
            }
        }
        importContext.getImportOption().setDateEntityId(peroidTableKey);
        return peroidTableKey;
    }

    private void ImportPeriodDatas(String periodEntityId, List<DesignFieldDefine> fieldDefines, EnumsItemModel singleEnum, String tableCode) throws Exception {
        List periodRows = this.periodDataService.queryPeriodDataByPeriodCode(tableCode);
        HashMap<String, IPeriodRow> periodDic = new HashMap<String, IPeriodRow>();
        for (IPeriodRow row : periodRows) {
            periodDic.put(row.getCode(), row);
        }
        Map enums = singleEnum.getEnumItemList();
        for (DataInfo item : enums.values()) {
            PeriodDataDefineImpl row1 = new PeriodDataDefineImpl();
            row1.setCode(item.getCode());
            row1.setTitle(item.getName());
            row1.setStartDate(null);
            row1.setEndDate(null);
            row1.setAlias(item.getMapCode());
            this.periodDataService.insertCustomPeriod((IPeriodRow)row1, tableCode);
        }
    }

    @Override
    public String updateDateEntity(TaskImportContext importContext) throws Exception {
        String peroidEntiyKey = null;
        ParaInfo para = importContext.getParaInfo();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        List dimesions = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey());
        for (DesignDataDimension dim : dimesions) {
            if (dim.getDimensionType() != DimensionType.PERIOD) continue;
            peroidEntiyKey = dim.getDimKey();
        }
        if (StringUtils.isNotEmpty(peroidEntiyKey)) {
            IPeriodEntity peroidEntity = this.periodAdapter.getPeriodEntity(peroidEntiyKey);
            PeriodType periodType = this.paraCommonService.getTaskPeriod(importContext);
            int periodCount = this.paraCommonService.getTaskPeriodCount(importContext);
            PeriodType netPeriodType = periodType;
            if (peroidEntity != null) {
                netPeriodType = peroidEntity.getPeriodType();
            }
            EnumsItemModel newEnum = null;
            EnumsItemModel oldEnum = para.getSQEnum();
            if (periodType == PeriodType.CUSTOM || oldEnum != null && periodCount != oldEnum.getItemDataList().size()) {
                newEnum = this.paraCommonService.getPeriodEnumByPeriodType(importContext, oldEnum, netPeriodType, true);
                this.paraCommonService.UpdateEnumMap(importContext, newEnum, null);
                if (newEnum.getItemDataList().size() > 0 && peroidEntity != null && peroidEntity.getPeriodType() == periodType) {
                    formScheme.setFromPeriod(((DataInfo)newEnum.getItemDataList().get(0)).getCode());
                    formScheme.setToPeriod(((DataInfo)newEnum.getItemDataList().get(newEnum.getItemDataList().size() - 1)).getCode());
                }
            } else {
                List peroidEntitys = this.periodService.queryDefaultPeriodList();
                for (IPeriodEntity entity : peroidEntitys) {
                    if (entity.getType() != periodType) continue;
                    peroidEntiyKey = entity.getKey();
                }
                newEnum = this.paraCommonService.getPeriodEnumByPeriodType(importContext, oldEnum, netPeriodType, true);
                this.paraCommonService.UpdateEnumMap(importContext, newEnum, null);
                if (peroidEntity != null && peroidEntity.getPeriodType() == periodType) {
                    formScheme.setFromPeriod(para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodType) + "0001");
                    if (null != newEnum && newEnum.getItemDataList().size() > 0) {
                        String toPeriodCode = String.valueOf(periodCount);
                        if (periodCount <= 0) {
                            DataInfo info = (DataInfo)newEnum.getItemDataList().get(newEnum.getItemDataList().size() - 1);
                            toPeriodCode = info.getCode();
                        }
                        if (toPeriodCode.length() < 4) {
                            toPeriodCode = "000000".substring(0, 4 - toPeriodCode.length()) + toPeriodCode;
                        } else if (toPeriodCode.length() > 4) {
                            toPeriodCode = toPeriodCode.substring(toPeriodCode.length() - 4, toPeriodCode.length() - 1);
                        }
                        formScheme.setToPeriod(para.getTaskYear() + this.paraCommonService.getPeriodTypeCode(periodType) + toPeriodCode);
                    } else {
                        formScheme.setToPeriod(formScheme.getFromPeriod());
                    }
                }
            }
        }
        return peroidEntiyKey;
    }
}

