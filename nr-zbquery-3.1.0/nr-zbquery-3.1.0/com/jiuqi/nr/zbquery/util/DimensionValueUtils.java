/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.manager.TimeKeyHelper
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.calibre2.common.CalibreDataOption$DataTreeType
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.bi.dataset.manager.TimeKeyHelper;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.model.EntityTreeType;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.PeriodUtil;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.util.CollectionUtils;

public class DimensionValueUtils {
    private static IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
    private static IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
    private static IEntityViewRunTimeController entityViewController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
    private static PeriodEngineService periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
    private static ICalibreDefineService calibreDefineService = (ICalibreDefineService)SpringBeanUtils.getBean(ICalibreDefineService.class);
    private static ICalibreDataService calibreDataService = (ICalibreDataService)SpringBeanUtils.getBean(ICalibreDataService.class);

    public static String getCurrentPeriod(QueryDimension dimension) throws Exception {
        return DimensionValueUtils.getCurrentPeriod(dimension, 0);
    }

    public static String getCurrentPeriod(QueryDimension dimension, int periodOffset) throws Exception {
        if (dimension.getDimensionType() != QueryDimensionType.PERIOD && !DimensionValueUtils.isPeriodChildDim(dimension)) {
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.dimNotPeriodDim", dimension.getName()));
        }
        if (dimension.getPeriodType() != PeriodType.CUSTOM) {
            if (dimension.getDimensionType() == QueryDimensionType.CHILD) {
                PeriodType periodType = PeriodUtil.getPeriodType(dimension.getParent(), dimension.getPeriodType());
                PeriodWrapper pw = com.jiuqi.np.period.PeriodUtil.currentPeriod((int)periodType.type(), (int)periodOffset);
                int vlaue = TimeKeyHelper.get((String)PeriodUtil.toBIPeriod(pw.toString(), periodType), (TimeGranularity)PeriodUtil.toTimeGranularity(dimension.getPeriodType()));
                return String.valueOf(vlaue);
            }
            PeriodWrapper pw = com.jiuqi.np.period.PeriodUtil.currentPeriod((int)dimension.getPeriodType().type(), (int)periodOffset);
            return PeriodUtil.toBIPeriod(pw.toString(), dimension.getPeriodType());
        }
        IPeriodEntity periodEntity = periodEngineService.getPeriodAdapter().getPeriodEntity(BqlTimeDimUtils.getPeriodEntityId((String)dimension.getName()));
        if (periodEntity != null) {
            IPeriodProvider periodProvider = periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey());
            String curPeriod = periodProvider.getCurPeriod().getCode();
            if (periodOffset == 0) {
                return curPeriod;
            }
            if (periodOffset == -1) {
                return periodProvider.priorPeriod(curPeriod);
            }
            throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.notSupportOffset", periodOffset));
        }
        throw new Exception(ZBQueryI18nUtils.getMessage("zbquery.exception.getPeriodDimFail", dimension.getName()));
    }

    public static String getFirstValue(QueryDimension dimension) throws Exception {
        return DimensionValueUtils.getFirstValue(dimension, null);
    }

    public static String[] getFirstAndChildValue(QueryDimension dimension) throws Exception {
        return DimensionValueUtils.getFirstAndChildValue(dimension, null);
    }

    public static String getFirstValue(QueryDimension dimension, String timekey) throws Exception {
        String[] values = DimensionValueUtils.getFirstAndChildValue(dimension, timekey, false);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return null;
    }

    public static String[] getFirstAndChildValue(QueryDimension dimension, String timekey) throws Exception {
        return DimensionValueUtils.getFirstAndChildValue(dimension, timekey, true);
    }

    private static String[] getFirstAndChildValue(QueryDimension dimension, String timekey, boolean needChild) throws Exception {
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            throw new Exception("\u65f6\u671f\u7ef4\u5ea6\u3010" + dimension.getName() + "\u3011\u65e0\u6cd5\u652f\u6301");
        }
        if (dimension.isCalibreDimension()) {
            return DimensionValueUtils.getFirstAndChildValue_calibre(dimension, needChild);
        }
        return DimensionValueUtils.getFirstAndChildValue_entity(dimension, timekey, needChild);
    }

    private static String[] getFirstAndChildValue_entity(QueryDimension dimension, String timekey, boolean needChild) throws Exception {
        EntityTreeType treeType = EntityTreeType.ROOT;
        if (needChild) {
            treeType = EntityTreeType.DIRECT_CHILDREN;
        }
        return DimensionValueUtils.getFirstAndChildValue_entity(dimension, timekey, treeType);
    }

    private static String[] getFirstAndChildValue_calibre(QueryDimension dimension, boolean needChild) throws Exception {
        ArrayList<String> values = new ArrayList<String>();
        CalibreDefineDTO calibreDefineDto = new CalibreDefineDTO();
        calibreDefineDto.setCode(dimension.getName());
        Result defineResult = calibreDefineService.get(calibreDefineDto);
        if (defineResult.getData() != null) {
            CalibreDataDTO dto = new CalibreDataDTO();
            dto.setDefineKey(((CalibreDefineDTO)defineResult.getData()).getKey());
            dto.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
            Result dataResult = calibreDataService.list(dto);
            if (!CollectionUtils.isEmpty((Collection)dataResult.getData())) {
                CalibreDataDTO calibre = (CalibreDataDTO)((List)dataResult.getData()).get(0);
                values.add(calibre.getCode());
                if (needChild && dimension.isTreeStructure()) {
                    dto.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
                    dto.setCode(calibre.getCode());
                    dataResult = calibreDataService.list(dto);
                    if (!CollectionUtils.isEmpty((Collection)dataResult.getData())) {
                        ((List)dataResult.getData()).forEach(v -> values.add(v.getCode()));
                    }
                }
            }
        }
        return values.toArray(new String[values.size()]);
    }

    public static boolean isPeriodChildDim(QueryDimension queryDim) {
        return queryDim.getDimensionType() == QueryDimensionType.CHILD && queryDim.getPeriodType() != null && queryDim.getPeriodType() != PeriodType.DEFAULT;
    }

    public static String[] getFirstAndAllChildValue(QueryDimension queryDim) throws Exception {
        return DimensionValueUtils.getFirstAndAllChildValue(queryDim, null);
    }

    public static String[] getFirstAndAllChildValue(QueryDimension dimension, String timeKey) throws Exception {
        if (dimension.getDimensionType() == QueryDimensionType.PERIOD) {
            throw new Exception("\u65f6\u671f\u7ef4\u5ea6\u3010" + dimension.getName() + "\u3011\u65e0\u6cd5\u652f\u6301");
        }
        if (dimension.isCalibreDimension()) {
            return DimensionValueUtils.getFirstAndChildValue_calibre(dimension, true);
        }
        return DimensionValueUtils.getFirstAndChildValue_entity(dimension, timeKey, EntityTreeType.ALL_CHILDREN);
    }

    private static String[] getFirstAndChildValue_entity(QueryDimension dimension, String timeKey, EntityTreeType treeType) throws Exception {
        IEntityTable entityTable;
        List rows;
        if (dimension.isEnableStandardCurrency()) {
            return new String[]{"PROVIDER_BASECURRENCY"};
        }
        ArrayList<String> values = new ArrayList<String>();
        IEntityDefine entityDefine = entityMetaService.queryEntity(FullNameWrapper.getEntityId(dimension));
        EntityViewDefine entityViewDefine = entityViewController.buildEntityView(entityDefine.getId());
        IEntityQuery iEntityQuery = entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(DimensionValueSet.EMPTY);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sorted(true);
        if (StringUtils.isNotEmpty((String)timeKey)) {
            DimensionValueSet dvs = new DimensionValueSet();
            dvs.setValue("DATATIME", (Object)timeKey);
            iEntityQuery.setMasterKeys(dvs);
        }
        if (!CollectionUtils.isEmpty(rows = (entityTable = iEntityQuery.executeReader(null)).getRootRows())) {
            IEntityRow parent = (IEntityRow)rows.get(0);
            values.add(parent.getEntityKeyData());
            if (dimension.isTreeStructure()) {
                List childs;
                if (treeType == EntityTreeType.DIRECT_CHILDREN) {
                    List childs2 = entityTable.getChildRows(parent.getEntityKeyData());
                    if (!CollectionUtils.isEmpty(childs2)) {
                        childs2.forEach(v -> values.add(v.getEntityKeyData()));
                    }
                } else if (treeType == EntityTreeType.ALL_CHILDREN && !CollectionUtils.isEmpty(childs = entityTable.getAllChildRows(parent.getEntityKeyData()))) {
                    childs.forEach(v -> values.add(v.getEntityKeyData()));
                }
            }
        }
        return values.toArray(new String[values.size()]);
    }
}

