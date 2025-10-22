/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.service.PeriodDataService
 *  com.jiuqi.nr.period.service.PeriodService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.service.PeriodDataService;
import com.jiuqi.nr.period.service.PeriodService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.EntityDefineImportServiceManager;
import nr.single.para.parain.service.IDataSchemeImportService;
import nr.single.para.parain.service.IEntityDefineImportService;
import nr.single.para.parain.service.IPeriodDefineImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DataSchemeImportServiceImpl
implements IDataSchemeImportService {
    private static final Logger log = LoggerFactory.getLogger(DataSchemeImportServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IPeriodDefineImportService peroidDefineServcie;
    @Autowired
    private PeriodService periodService;
    @Autowired
    private PeriodDataService periodDataService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public String importDataSchemeDefine(TaskImportContext importContext) throws Exception {
        IEntityDefineImportService entityDefineServcie;
        ParaInfo paraInfo = importContext.getParaInfo();
        String dataSchemeKey = importContext.getDataSchemeKey();
        boolean daaSchemeIsNew = true;
        DesignDataScheme dataScheme = null;
        if (StringUtils.isNotEmpty((String)dataSchemeKey) && (dataScheme = this.dataSchemeSevice.getDataScheme(dataSchemeKey)) != null) {
            daaSchemeIsNew = false;
        }
        if (dataScheme == null) {
            dataScheme = this.dataSchemeSevice.initDataScheme();
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getFilePrefix())) {
                dataScheme.setPrefix(importContext.getImportOption().getFilePrefix());
            } else if (importContext.getCompareInfo() == null) {
                dataScheme.setPrefix(this.GetTaskFileFlag2(importContext));
            }
            if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
                dataScheme.setKey(dataSchemeKey);
            }
            dataScheme.setCode(dataScheme.getPrefix() + "_" + paraInfo.getPrefix());
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getDataSchemeCode())) {
                dataScheme.setCode(importContext.getImportOption().getDataSchemeCode());
            }
            dataScheme.setTitle(this.GetTaskTitle(importContext));
            if (StringUtils.isNotEmpty((String)importContext.getImportOption().getDataSchemeTitle())) {
                dataScheme.setTitle(importContext.getImportOption().getDataSchemeTitle());
            }
        }
        dataScheme.setAuto(Boolean.valueOf(false));
        dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        importContext.setDataScheme(dataScheme);
        boolean isSelectEntity = importContext.getImportOption().isSelectEntity();
        if (daaSchemeIsNew) {
            DesignDataGroup schemeGroup = this.createNewDataGroup("JIO\u53c2\u6570\u5bfc\u5165", "NB_DATASCHEME", "5a1833c9-8166-4763-b7bf-1435e5a2934b", "", OrderGenerator.newOrder());
            dataScheme.setDataGroupKey(schemeGroup.getKey());
        }
        if (isSelectEntity && StringUtils.isNotEmpty((String)importContext.getImportOption().getCorpEntityId())) {
            List<DesignDataDimension> dimensions = this.GetDimensions(importContext, dataScheme.getKey());
            if (daaSchemeIsNew) {
                this.dataSchemeSevice.insertDataScheme(dataScheme, dimensions);
            }
            if (importContext.getImportOption().isFMDMFieldInEntity()) {
                IEntityDefineImportService entityDefineServcie2 = this.getEntityDefineServcieByEnityId(importContext.getImportOption().getCorpEntityId());
                entityDefineServcie2.updateCorpEntity(importContext);
            }
        } else if (isSelectEntity && StringUtils.isEmpty((String)importContext.getImportOption().getCorpEntityId())) {
            entityDefineServcie = this.getEntityDefineServcieByCategory("ORG");
            entityDefineServcie.importCorpEntity(importContext);
            List<DesignDataDimension> dimensions = this.GetDimensions(importContext, dataScheme.getKey());
            if (daaSchemeIsNew) {
                this.dataSchemeSevice.insertDataScheme(dataScheme, dimensions);
            }
            if (importContext.getImportOption().isFMDMFieldInEntity()) {
                entityDefineServcie.updateCorpEntity(importContext);
            }
        } else {
            entityDefineServcie = this.getEntityDefineServcieByCategory("BASE");
            entityDefineServcie.importCorpEntity(importContext);
            this.peroidDefineServcie.importDateEntity(importContext);
            List<DesignDataDimension> dimensions = this.GetDimensions(importContext, dataScheme.getKey());
            if (daaSchemeIsNew) {
                this.dataSchemeSevice.insertDataScheme(dataScheme, dimensions);
            }
        }
        if (StringUtils.isEmpty((String)importContext.getTaskKey())) {
            importContext.setTaskKey(UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty((String)importContext.getFormSchemeKey())) {
            importContext.setFormSchemeKey(UUID.randomUUID().toString());
        }
        importContext.setDataSchemeKey(dataScheme.getKey());
        importContext.setDataScheme(dataScheme);
        return dataScheme.getKey();
    }

    @Override
    public String updateDataSchemeDefine(TaskImportContext importContext) throws Exception {
        List<DesignDataDimension> dimensions;
        String dataSchemeKey = importContext.getDataSchemeKey();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        if (dataScheme == null && StringUtils.isNotEmpty((String)dataSchemeKey)) {
            dataScheme = this.dataSchemeSevice.getDataScheme(dataSchemeKey);
        }
        if (dataScheme == null || (dimensions = this.GetDimensions(importContext, dataScheme.getKey())).size() > 0) {
            // empty if block
        }
        return dataSchemeKey;
    }

    @Override
    public String importDataSchemeDefineFromOther(TaskImportContext importContext, String oldDataScheme) throws Exception {
        ParaInfo paraInfo = importContext.getParaInfo();
        String dataSchemeKey = importContext.getDataSchemeKey();
        DesignDataScheme dataScheme = null;
        DesignDataScheme olddataScheme = null;
        boolean daaSchemeIsNew = true;
        if (StringUtils.isNotEmpty((String)oldDataScheme)) {
            olddataScheme = this.dataSchemeSevice.getDataScheme(oldDataScheme);
        }
        if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
            dataScheme = this.dataSchemeSevice.getDataScheme(dataSchemeKey);
            if (dataScheme != null) {
                daaSchemeIsNew = false;
            }
        } else {
            dataSchemeKey = UUID.randomUUID().toString();
        }
        if (dataScheme == null) {
            dataScheme = this.dataSchemeSevice.initDataScheme();
            dataScheme.setPrefix(this.GetTaskFileFlag2(importContext));
            if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
                dataScheme.setKey(dataSchemeKey);
            }
            dataScheme.setCode(paraInfo.getPrefix() + "_" + dataScheme.getPrefix());
            dataScheme.setTitle(this.GetTaskTitle(importContext));
        }
        dataScheme.setAuto(Boolean.valueOf(false));
        dataScheme.setDataGroupKey("00000000-0000-0000-0000-000000000000");
        importContext.setDataScheme(dataScheme);
        boolean isSelectEntity = false;
        if (!daaSchemeIsNew) {
            DesignDataGroup schemeGroup = this.createNewDataGroup("JIO\u53c2\u6570\u5bfc\u5165", "NB_DATASCHEME", "5a1833c9-8166-4763-b7bf-1435e5a2934b", "", OrderGenerator.newOrder());
            dataScheme.setDataGroupKey(schemeGroup.getKey());
        }
        if (!isSelectEntity) {
            if (olddataScheme == null) {
                IEntityDefineImportService entityDefineServcie = this.getEntityDefineServcieByCategory("BASE");
                entityDefineServcie.importCorpEntity(importContext);
                this.peroidDefineServcie.importDateEntity(importContext);
            } else {
                List oldDims = this.dataSchemeSevice.getDataSchemeDimension(olddataScheme.getKey());
                String dimEntityIds = "";
                for (DesignDataDimension dim : oldDims) {
                    if (dim.getDimensionType() == DimensionType.UNIT) {
                        importContext.getImportOption().setCorpEntityId(dim.getDimKey());
                        continue;
                    }
                    if (dim.getDimensionType() == DimensionType.PERIOD) {
                        importContext.getImportOption().setDateEntityId(dim.getDimKey());
                        continue;
                    }
                    if (StringUtils.isNotEmpty((String)dimEntityIds)) {
                        dimEntityIds = dimEntityIds + ";" + dimEntityIds;
                    }
                    dimEntityIds = dimEntityIds + dim.getDimKey();
                }
                importContext.getImportOption().setDimEntityIds(dimEntityIds);
            }
            List<DesignDataDimension> dimensions = this.GetDimensions(importContext, dataScheme.getKey());
            if (daaSchemeIsNew) {
                this.dataSchemeSevice.insertDataScheme(dataScheme, dimensions);
            }
        }
        if (StringUtils.isEmpty((String)importContext.getTaskKey())) {
            importContext.setTaskKey(UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty((String)importContext.getFormSchemeKey())) {
            importContext.setFormSchemeKey(UUID.randomUUID().toString());
        }
        importContext.setDataSchemeKey(dataScheme.getKey());
        importContext.setDataScheme(dataScheme);
        return dataScheme.getKey();
    }

    private List<DesignDataDimension> GetDimensions(TaskImportContext importContext, String dataSchemeKey) {
        String corpEntityId = importContext.getImportOption().getCorpEntityId();
        String dateEntityId = importContext.getImportOption().getDateEntityId();
        String dimEntityIds = importContext.getImportOption().getDimEntityIds();
        ArrayList<DesignDataDimension> dimensions = new ArrayList<DesignDataDimension>();
        if (StringUtils.isNotEmpty((String)corpEntityId)) {
            DesignDataDimension corpDim = this.dataSchemeSevice.initDataSchemeDimension();
            corpDim.setDimKey(corpEntityId);
            corpDim.setDataSchemeKey(dataSchemeKey);
            corpDim.setDimensionType(DimensionType.UNIT);
            corpDim.setLevel(importContext.getCurServerCode());
            corpDim.setOrder(OrderGenerator.newOrder());
            dimensions.add(corpDim);
        }
        if (StringUtils.isNotEmpty((String)dateEntityId)) {
            IPeriodEntity periodEnitty = null;
            try {
                periodEnitty = this.periodService.queryPeriodByKey(dateEntityId);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            DesignDataDimension dateDim = this.dataSchemeSevice.initDataSchemeDimension();
            dateDim.setDimKey(dateEntityId);
            dateDim.setDataSchemeKey(dataSchemeKey);
            dateDim.setDimensionType(DimensionType.PERIOD);
            dateDim.setPeriodType(PeriodType.CUSTOM);
            if (periodEnitty != null) {
                dateDim.setPeriodType(periodEnitty.getPeriodType());
            }
            dateDim.setLevel(importContext.getCurServerCode());
            dateDim.setOrder(OrderGenerator.newOrder());
            dimensions.add(dateDim);
        }
        if (StringUtils.isNotEmpty((String)dimEntityIds)) {
            String[] dimEntityCodes;
            for (String dimCode : dimEntityCodes = dimEntityIds.split(";")) {
                DesignDataDimension dataDim = this.dataSchemeSevice.initDataSchemeDimension();
                dataDim.setDimKey(dimCode);
                dataDim.setDataSchemeKey(dataSchemeKey);
                dataDim.setDimensionType(DimensionType.DIMENSION);
                dataDim.setLevel(importContext.getCurServerCode());
                dataDim.setOrder(OrderGenerator.newOrder());
                dimensions.add(dataDim);
            }
        }
        return dimensions;
    }

    private String GetTaskFileFlag2(TaskImportContext importContext) {
        String aCode = "";
        if (importContext.getImportOption() != null && StringUtils.isNotEmpty((String)importContext.getImportOption().getFilePrefix()) && importContext.getImportOption().getFilePrefix().length() == 4) {
            aCode = importContext.getImportOption().getFilePrefix().toUpperCase();
        }
        ParaInfo para = importContext.getParaInfo();
        if (StringUtils.isEmpty((String)aCode)) {
            aCode = StringUtils.isNotEmpty((String)para.getFileFlag()) ? para.getFileFlag().substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(5, 8);
            aCode = aCode.substring(0, 4);
            DesignDataScheme dataScheme = this.dataSchemeSevice.getDataSchemeByPrefix(aCode);
            while (null != dataScheme) {
                aCode = StringUtils.isNotEmpty((String)para.getFileFlag()) ? para.getFileFlag().substring(0, 1) + OrderGenerator.newOrder().substring(5, 8) : "N" + OrderGenerator.newOrder().substring(4, 8);
                dataScheme = this.dataSchemeSevice.getDataSchemeByPrefix(aCode);
            }
        }
        para.setNetFileFlag(aCode);
        return aCode;
    }

    private String GetTaskTitle(TaskImportContext importContext) {
        String aCode = "";
        ParaInfo para = importContext.getParaInfo();
        List dataSchemes = this.dataSchemeSevice.getAllDataScheme();
        HashMap dataSchemeDic = new HashMap();
        dataSchemes.forEach(scheme -> dataSchemeDic.put(scheme.getTitle(), scheme));
        if (StringUtils.isEmpty((String)aCode)) {
            aCode = para.getTaskName();
            DesignDataScheme dataScheme = (DesignDataScheme)dataSchemeDic.get(aCode);
            int aIndex = 1;
            while (null != dataScheme) {
                aCode = para.getTaskName() + String.valueOf(aIndex);
                dataScheme = (DesignDataScheme)dataSchemeDic.get(aCode);
                ++aIndex;
            }
        }
        return aCode;
    }

    private DesignDataGroup createNewDataGroup(String title, String code, String groupKey, String descript, String order) throws Exception {
        List groups = this.dataSchemeSevice.getRootSchemeDataGroup();
        DesignDataGroup group = null;
        DesignDataGroup keyGroup = null;
        DesignDataGroup titleGroup = null;
        for (DesignDataGroup g : groups) {
            if (title.equalsIgnoreCase(g.getTitle())) {
                titleGroup = g;
                continue;
            }
            if (!groupKey.equalsIgnoreCase(g.getKey())) continue;
            keyGroup = g;
        }
        if (titleGroup != null) {
            group = titleGroup;
        } else if (keyGroup != null) {
            group = keyGroup;
        }
        if (null == group) {
            group = this.dataSchemeSevice.initDataGroup();
            if (StringUtils.isNotEmpty((String)code) && code.length() > 20) {
                code = code.substring(0, 20);
            }
            if (StringUtils.isNotEmpty((String)title) && title.length() > 40) {
                title = title.substring(0, 40);
            }
            if (StringUtils.isNotEmpty((String)descript) && descript.length() > 40) {
                descript = descript.substring(0, 40);
            }
            group.setKey(groupKey);
            group.setCode(code);
            group.setTitle(title);
            group.setOrder(order);
            group.setDesc(descript);
            group.setDataGroupKind(DataGroupKind.SCHEME_GROUP);
            group.setParentKey("00000000-0000-0000-0000-000000000000");
            this.dataSchemeSevice.insertDataGroup(group);
        }
        return group;
    }

    @Override
    public IEntityDefineImportService getEntityDefineServcieByEnityId(String dwEntityId) {
        String category;
        IEntityDefineImportService service = null;
        if (StringUtils.isNotEmpty((String)dwEntityId) && StringUtils.isNotEmpty((String)(category = EntityUtils.getCategory((String)dwEntityId)))) {
            service = EntityDefineImportServiceManager.getInstance().findService(category);
        }
        if (service == null) {
            EntityDefineImportServiceManager.getInstance().findService("BASE");
        }
        return service;
    }

    @Override
    public IEntityDefineImportService getEntityDefineServcieByCategory(String category) {
        IEntityDefineImportService service = null;
        if (StringUtils.isNotEmpty((String)category)) {
            service = EntityDefineImportServiceManager.getInstance().findService(category);
        }
        if (service == null) {
            EntityDefineImportServiceManager.getInstance().findService("BASE");
        }
        return service;
    }

    @Override
    public String updateDimEntitys(TaskImportContext importContext) throws Exception {
        String dimViewKeys = null;
        DesignDataScheme dataScheme = importContext.getDataScheme();
        List dimesions = this.dataSchemeSevice.getDataSchemeDimension(dataScheme.getKey());
        for (DesignDataDimension dim : dimesions) {
            IEntityDefine entityDefine;
            String dimEntityKey;
            if (dim.getDimensionType() != DimensionType.DIMENSION || !StringUtils.isNotEmpty((String)(dimEntityKey = dim.getDimKey())) || (entityDefine = this.entityMetaService.queryEntity(dimEntityKey)) == null) continue;
            String dimViewKey = dimEntityKey;
            if (StringUtils.isEmpty(dimViewKeys)) {
                dimViewKeys = dimViewKey;
                continue;
            }
            dimViewKeys = dimViewKeys + ";" + dimViewKey;
        }
        return dimViewKeys;
    }
}

