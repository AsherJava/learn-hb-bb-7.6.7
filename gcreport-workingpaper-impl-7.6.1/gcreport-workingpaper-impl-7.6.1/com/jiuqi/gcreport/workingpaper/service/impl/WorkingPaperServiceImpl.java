/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl
 *  com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.workingpaper.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl;
import com.jiuqi.gcreport.workingpaper.dao.WorkingPaperQueryWayDao;
import com.jiuqi.gcreport.workingpaper.entity.WorkingPaperQueryWayItemEO;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQueryTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQueryWayEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperType;
import com.jiuqi.gcreport.workingpaper.querytask.WorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.impl.ArbitrarilyMergeQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.querytask.impl.WorkingPaperCubesSubjectQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.querytask.impl.WorkingPaperPrimaryQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.querytask.impl.WorkingPaperRuleQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.querytask.impl.WorkingPaperSubjectQueryTaskImpl;
import com.jiuqi.gcreport.workingpaper.service.WorkingPaperService;
import com.jiuqi.gcreport.workingpaper.vo.SubjectZbPentrationParamsVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperPentrationQueryCondtion;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkingPaperServiceImpl
implements WorkingPaperService {
    private static final Logger logger = LoggerFactory.getLogger(WorkingPaperServiceImpl.class);
    @Autowired
    private WorkingPaperQueryWayDao workPaperQueryWayDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public List<WorkingPaperQueryWayItemVO> getWorkingPaperQueryWays(WorkingPaperType workingPaperType) {
        List<WorkingPaperQueryWayItemEO> eos = this.workPaperQueryWayDao.getWorkingPaperQueryWays(workingPaperType);
        if (CollectionUtils.isEmpty(eos)) {
            List<WorkingPaperQueryWayItemVO> fixWorkPaperQueryWays = this.getFixWorkPaperQueryWays(workingPaperType);
            List fixWorkPaperQueryWayEOs = fixWorkPaperQueryWays.stream().map(vo -> {
                WorkingPaperQueryWayItemEO eo = new WorkingPaperQueryWayItemEO();
                BeanUtils.copyProperties(vo, (Object)eo);
                return eo;
            }).collect(Collectors.toList());
            this.workPaperQueryWayDao.addBatch(fixWorkPaperQueryWayEOs);
            return fixWorkPaperQueryWays;
        }
        ArrayList<WorkingPaperQueryWayItemVO> vos = new ArrayList<WorkingPaperQueryWayItemVO>();
        List workPaperQueryWays = eos.stream().map(eo -> {
            WorkingPaperQueryWayItemVO vo = new WorkingPaperQueryWayItemVO();
            BeanUtils.copyProperties(eo, vo);
            WorkingPaperQueryWayEnum workingPaperQueryWayEnum = WorkingPaperQueryWayEnum.getEnumByCode(vo.getId());
            if (workingPaperQueryWayEnum != null) {
                vo.setTitle(workingPaperQueryWayEnum.getTitle());
            }
            return vo;
        }).collect(Collectors.toList());
        vos.addAll(workPaperQueryWays);
        return vos;
    }

    public List<WorkingPaperQueryWayItemVO> getFixWorkPaperQueryWays(WorkingPaperType workingPaperType) {
        ArrayList<WorkingPaperQueryWayItemVO> fixVos = new ArrayList<WorkingPaperQueryWayItemVO>();
        if (WorkingPaperType.SIMPLE.equals((Object)workingPaperType)) {
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.JD.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.jd"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.JD.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(1.0), WorkingPaperType.SIMPLE.getType()));
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.GCYWXL.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.gcywlx"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.YWLX.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(2.0), WorkingPaperType.SIMPLE.getType()));
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.DX.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.dx"), WorkingPaperQmsTypeEnum.NO_SHOW.getCode(), WorkingPaperDxsTypeEnum.UNIT.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(3.0), WorkingPaperType.SIMPLE.getType()));
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.HBMX.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.hbmx"), WorkingPaperQmsTypeEnum.CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.NO_SHOW.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(4.0), WorkingPaperType.SIMPLE.getType()));
        } else if (WorkingPaperType.CUBES.equals((Object)workingPaperType)) {
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.CUBES_JD.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.cubes.jd"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.JD.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(1.0), WorkingPaperType.CUBES.getType()));
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.CUBES_GCYWXL.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.cubes.gcywlx"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.YWLX.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(2.0), WorkingPaperType.CUBES.getType()));
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.CUBES_UNIT.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.cubes.unit"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.UNIT.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(3.0), WorkingPaperType.CUBES.getType()));
        } else if (WorkingPaperType.MERGE.equals((Object)workingPaperType)) {
            fixVos.add(new WorkingPaperQueryWayItemVO(WorkingPaperQueryWayEnum.MERGE_JD.getCode(), Long.valueOf(0L), GcI18nUtil.getMessage((String)"gc.workingpaper.query.way.merge.jd"), WorkingPaperQmsTypeEnum.NOT_CONTAIN_DXS.getCode(), WorkingPaperDxsTypeEnum.JD.getCode(), "00000000-0000-0000-0000-000000000000", Double.valueOf(1.0), WorkingPaperType.MERGE.getType()));
        }
        return fixVos;
    }

    @Override
    public WorkingPaperQueryWayItemVO addOrUpdateWorkPaperQueryWay(WorkingPaperQueryWayItemVO vo) {
        if (StringUtils.isEmpty((String)vo.getTitle())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notnulltype"));
        }
        if (StringUtils.isEmpty((String)vo.getQmsType())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notnullclosing"));
        }
        if (StringUtils.isEmpty((String)vo.getDxsType())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notnulloffset"));
        }
        boolean isAdd = false;
        if (StringUtils.isEmpty((String)vo.getCreator())) {
            vo.setCreator(NpContextHolder.getContext().getUserId());
        }
        if (StringUtils.isEmpty((String)vo.getId())) {
            vo.setId(UUIDUtils.newUUIDStr());
            isAdd = true;
        }
        WorkingPaperQueryWayItemEO eo = new WorkingPaperQueryWayItemEO();
        if (isAdd) {
            vo.setFloatOrder(Double.valueOf(System.currentTimeMillis()));
            BeanUtils.copyProperties(vo, (Object)eo);
            this.workPaperQueryWayDao.add((BaseEntity)eo);
        } else {
            WorkingPaperQueryWayItemEO exsitsEO = (WorkingPaperQueryWayItemEO)this.workPaperQueryWayDao.get((Serializable)((Object)vo.getId()));
            exsitsEO.setDxsType(vo.getDxsType());
            exsitsEO.setQmsType(vo.getQmsType());
            exsitsEO.setTitle(vo.getTitle());
            this.workPaperQueryWayDao.update((BaseEntity)exsitsEO);
        }
        return vo;
    }

    @Override
    public Boolean deleteWorkPaperQueryWay(String currWayId) {
        WorkingPaperQueryWayItemEO eo = new WorkingPaperQueryWayItemEO();
        eo.setId(currWayId);
        this.workPaperQueryWayDao.delete((BaseEntity)eo);
        return Boolean.TRUE;
    }

    @Override
    public WorkingPaperQueryWayItemVO queryWorkPaperQueryWay(String queyWayId) {
        WorkingPaperQueryWayItemEO workingPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)this.workPaperQueryWayDao.get((Serializable)((Object)queyWayId));
        if (workingPaperQueryWayItemEO == null) {
            return null;
        }
        WorkingPaperQueryWayItemVO vo = new WorkingPaperQueryWayItemVO();
        BeanUtils.copyProperties((Object)workingPaperQueryWayItemEO, vo);
        return vo;
    }

    @Override
    public Boolean exchangeSortWorkPaperQueryWay(String currWayId, String exchangeWayId) {
        if (currWayId.equals(exchangeWayId)) {
            return Boolean.TRUE;
        }
        WorkingPaperQueryWayItemEO currWayItemEO = (WorkingPaperQueryWayItemEO)this.workPaperQueryWayDao.get((Serializable)((Object)currWayId));
        WorkingPaperQueryWayItemEO exchangeWayItemEO = (WorkingPaperQueryWayItemEO)this.workPaperQueryWayDao.get((Serializable)((Object)exchangeWayId));
        if (currWayItemEO == null || exchangeWayItemEO == null) {
            return Boolean.TRUE;
        }
        Double currWayItemOrder = currWayItemEO.getFloatOrder();
        Double exchangeWayItemOrder = exchangeWayItemEO.getFloatOrder();
        currWayItemEO.setFloatOrder(exchangeWayItemOrder);
        exchangeWayItemEO.setFloatOrder(currWayItemOrder);
        this.workPaperQueryWayDao.update((BaseEntity)currWayItemEO);
        this.workPaperQueryWayDao.update((BaseEntity)exchangeWayItemEO);
        return Boolean.TRUE;
    }

    @Override
    public List<WorkingPaperTableHeaderVO> createHeaderVO(WorkingPaperQueryCondition queryCondition) {
        WorkingPaperQueryTask queryTask;
        String queryType = queryCondition.getQueryType();
        String queryWayId = queryCondition.getQueryWayId();
        WorkingPaperQueryTypeEnum queryTypeEnum = WorkingPaperQueryTypeEnum.getEnumByCode(queryType);
        WorkingPaperQueryWayDao paperQueryWayDao = (WorkingPaperQueryWayDao)SpringContextUtils.getBean(WorkingPaperQueryWayDao.class);
        WorkingPaperQueryWayItemEO workPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)paperQueryWayDao.get((Serializable)((Object)queryWayId));
        if (workPaperQueryWayItemEO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposed") + queryWayId);
        }
        WorkingPaperDxsTypeEnum dxsTypeEnum = WorkingPaperDxsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getDxsType());
        if (dxsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + workPaperQueryWayItemEO.getDxsType());
        }
        WorkingPaperQmsTypeEnum qmsTypeEnum = WorkingPaperQmsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getQmsType());
        if (qmsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + workPaperQueryWayItemEO.getQmsType());
        }
        switch (queryTypeEnum) {
            case SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperSubjectQueryTaskImpl.class);
                break;
            }
            case PRIMARY: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperPrimaryQueryTaskImpl.class);
                break;
            }
            case RULE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperRuleQueryTaskImpl.class);
                break;
            }
            case MERGE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(ArbitrarilyMergeQueryTaskImpl.class);
                break;
            }
            case CUBES_SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperCubesSubjectQueryTaskImpl.class);
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedtype") + queryTypeEnum.getTitle());
            }
        }
        if (queryTask == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedquery") + queryWayId);
        }
        List<WorkingPaperTableHeaderVO> headerVO = queryTask.getHeaderVO(queryCondition, qmsTypeEnum, dxsTypeEnum);
        return headerVO;
    }

    @Override
    public List<WorkingPaperTableDataVO> getDataVO(WorkingPaperQueryCondition queryCondition) {
        List<WorkingPaperTableDataVO> dataVO;
        WorkingPaperQueryTask queryTask;
        String queryType = queryCondition.getQueryType();
        String queryWayId = queryCondition.getQueryWayId();
        WorkingPaperQueryTypeEnum queryTypeEnum = WorkingPaperQueryTypeEnum.getEnumByCode(queryType);
        WorkingPaperQueryWayDao paperQueryWayDao = (WorkingPaperQueryWayDao)SpringContextUtils.getBean(WorkingPaperQueryWayDao.class);
        WorkingPaperQueryWayItemEO workPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)paperQueryWayDao.get((Serializable)((Object)queryWayId));
        if (workPaperQueryWayItemEO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposed") + queryWayId);
        }
        WorkingPaperDxsTypeEnum dxsTypeEnum = WorkingPaperDxsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getDxsType());
        if (dxsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + workPaperQueryWayItemEO.getDxsType());
        }
        WorkingPaperQmsTypeEnum qmsTypeEnum = WorkingPaperQmsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getQmsType());
        if (qmsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + workPaperQueryWayItemEO.getQmsType());
        }
        switch (queryTypeEnum) {
            case SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperSubjectQueryTaskImpl.class);
                break;
            }
            case PRIMARY: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperPrimaryQueryTaskImpl.class);
                break;
            }
            case RULE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperRuleQueryTaskImpl.class);
                break;
            }
            case MERGE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(ArbitrarilyMergeQueryTaskImpl.class);
                break;
            }
            case CUBES_SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperCubesSubjectQueryTaskImpl.class);
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedtype") + queryTypeEnum.getTitle());
            }
        }
        if (queryTask == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedquery") + queryWayId);
        }
        LogHelper.info((String)"\u5408\u5e76-\u5de5\u4f5c\u5e95\u7a3f\u67e5\u8be2", (String)"\u67e5\u8be2-\u67e5\u770b\u7c7b\u578b".concat(queryTypeEnum.getTitle()).concat("\u67e5\u770b\u65b9\u5f0f").concat(workPaperQueryWayItemEO.getTitle()), (String)"\u4efb\u52a1-".concat(String.valueOf(queryCondition.getTaskName())).concat("\uff1b\u65f6\u671f-").concat(queryCondition.getPeriodStr()).concat("\uff1b\u67e5\u770b\u7c7b\u578b").concat(queryTypeEnum.getTitle()).concat("\uff1b\u67e5\u770b\u65b9\u5f0f\uff1b").concat(workPaperQueryWayItemEO.getTitle()).concat(Boolean.TRUE.equals(queryCondition.getIsAll()) ? "\u4e0d\u5206\u9875\u663e\u793a\uff1b" : "\u5206\u9875\u663e\u793a\uff1b").concat(Boolean.TRUE.equals(queryCondition.getIsFilterZero()) ? "\u4e0d\u663e\u793a\u7a7a\u884c\uff1b" : "\u663e\u793a\u7a7a\u884c\uff1b"));
        try {
            dataVO = queryTask.getDataVO(queryCondition, qmsTypeEnum, dxsTypeEnum);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return dataVO;
    }

    @Override
    public Object getWorkPaperPentrationDatas(HttpServletRequest request, HttpServletResponse response, WorkingPaperPentrationQueryCondtion pentrationQueryCondtion) {
        WorkingPaperQueryTask queryTask;
        String queryType = pentrationQueryCondtion.getQueryType();
        String queryWayId = pentrationQueryCondtion.getQueryWayId();
        WorkingPaperQueryTypeEnum queryTypeEnum = WorkingPaperQueryTypeEnum.getEnumByCode(queryType);
        WorkingPaperQueryWayDao paperQueryWayDao = (WorkingPaperQueryWayDao)SpringContextUtils.getBean(WorkingPaperQueryWayDao.class);
        WorkingPaperQueryWayItemEO workPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)paperQueryWayDao.get((Serializable)((Object)queryWayId));
        if (workPaperQueryWayItemEO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposed") + queryWayId);
        }
        WorkingPaperDxsTypeEnum dxsTypeEnum = WorkingPaperDxsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getDxsType());
        if (dxsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + workPaperQueryWayItemEO.getDxsType());
        }
        WorkingPaperQmsTypeEnum qmsTypeEnum = WorkingPaperQmsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getQmsType());
        if (qmsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + workPaperQueryWayItemEO.getQmsType());
        }
        switch (queryTypeEnum) {
            case SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperSubjectQueryTaskImpl.class);
                break;
            }
            case PRIMARY: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperPrimaryQueryTaskImpl.class);
                break;
            }
            case RULE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperRuleQueryTaskImpl.class);
                break;
            }
            case MERGE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(ArbitrarilyMergeQueryTaskImpl.class);
                break;
            }
            case CUBES_SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperCubesSubjectQueryTaskImpl.class);
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedtype") + queryTypeEnum.getTitle());
            }
        }
        if (queryTask == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedquery") + queryWayId);
        }
        Object paginationDataVO = queryTask.getWorkPaperPentrationDatas(request, response, pentrationQueryCondtion, qmsTypeEnum, dxsTypeEnum);
        return paginationDataVO;
    }

    @Override
    public ExportExcelSheet getExcelVO(ExportContext context, Workbook workbook, WorkingPaperQueryCondition queryCondition) {
        WorkingPaperQueryTask queryTask;
        String queryType = queryCondition.getQueryType();
        String queryWayId = queryCondition.getQueryWayId();
        WorkingPaperQueryTypeEnum queryTypeEnum = WorkingPaperQueryTypeEnum.getEnumByCode(queryType);
        WorkingPaperQueryWayDao paperQueryWayDao = (WorkingPaperQueryWayDao)SpringContextUtils.getBean(WorkingPaperQueryWayDao.class);
        WorkingPaperQueryWayItemEO workPaperQueryWayItemEO = (WorkingPaperQueryWayItemEO)paperQueryWayDao.get((Serializable)((Object)queryWayId));
        if (workPaperQueryWayItemEO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposed") + queryWayId);
        }
        WorkingPaperDxsTypeEnum dxsTypeEnum = WorkingPaperDxsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getDxsType());
        if (dxsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedoffset") + workPaperQueryWayItemEO.getDxsType());
        }
        WorkingPaperQmsTypeEnum qmsTypeEnum = WorkingPaperQmsTypeEnum.getEnumByCode(workPaperQueryWayItemEO.getQmsType());
        if (qmsTypeEnum == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedclosing") + workPaperQueryWayItemEO.getQmsType());
        }
        switch (queryTypeEnum) {
            case SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperSubjectQueryTaskImpl.class);
                break;
            }
            case PRIMARY: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperPrimaryQueryTaskImpl.class);
                break;
            }
            case RULE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperRuleQueryTaskImpl.class);
                break;
            }
            case MERGE: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(ArbitrarilyMergeQueryTaskImpl.class);
                break;
            }
            case CUBES_SUBJECT: {
                queryTask = (WorkingPaperQueryTask)SpringContextUtils.getBean(WorkingPaperCubesSubjectQueryTaskImpl.class);
                break;
            }
            default: {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedtype") + queryTypeEnum.getTitle());
            }
        }
        if (queryTask == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.workingpaper.msg.notsupposedquery") + queryWayId);
        }
        ExportExcelSheet excelSheet = queryTask.getExcelVO(context, workbook, queryCondition, qmsTypeEnum, dxsTypeEnum);
        return excelSheet;
    }

    @Override
    public SubjectZbPentrationParamsVO getSubjectZbPentrateParams(String zbBoundIndexPath, WorkingPaperQueryCondition condition) {
        SubjectZbPentrationParamsVO vo = new SubjectZbPentrationParamsVO();
        Map<String, DataLinkDefine> zbId2DataLinkDefineMap = this.getZbId2DataLinkDefineMap(condition);
        ConsolidatedSubjectEO eo = new ConsolidatedSubjectEO();
        if (StringUtils.isEmpty((String)zbBoundIndexPath)) {
            throw new BusinessRuntimeException("\u8bf7\u8bbe\u7f6e\u5bf9\u5e94\u6307\u6807!");
        }
        eo.setBoundIndexPath(zbBoundIndexPath);
        FieldDefine fieldDefine = this.subjectService.getFieldDefineBySubject(eo);
        if (fieldDefine != null && zbId2DataLinkDefineMap.containsKey(fieldDefine.getKey())) {
            DataLinkDefine dataLinkDefine = zbId2DataLinkDefineMap.get(fieldDefine.getKey());
            vo.setDataLinkKey(dataLinkDefine.getKey());
            vo.setRegionId(dataLinkDefine.getRegionKey());
            DataRegionDefine dataRegionDefine = this.runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
            vo.setFormId(dataRegionDefine.getFormKey());
        }
        return vo;
    }

    private Map<String, DataLinkDefine> getZbId2DataLinkDefineMap(WorkingPaperQueryCondition condition) {
        HashMap<String, DataLinkDefine> zbId2DataLinkDefineMap = new HashMap<String, DataLinkDefine>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(condition.getPeriodStr());
        ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
        dimensionSetMap.put("DATATIME", dimensionValue);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)condition.getPeriodStr());
        dimensionValueSet.setValue("MD_CURRENCY", (Object)condition.getCurrencyCode());
        dimensionValueSet.setValue("MD_GCORGTYPE", (Object)condition.getOrg_type());
        dimensionValueSet.setValue("MD_ORG", (Object)condition.getOrgid());
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(condition.getSchemeID());
        jtableContext.setDimensionSet(dimensionSetMap);
        String schemeID = condition.getSchemeID();
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeID);
        GCFormTabSelectServiceImpl formTabSelectService = (GCFormTabSelectServiceImpl)SpringContextUtils.getBean(GCFormTabSelectServiceImpl.class);
        List formDefineList = formDefines.stream().filter(formDefine1 -> formTabSelectService.isFormCondition(jtableContext, formDefine1.getFormCondition(), dimensionValueSet)).collect(Collectors.toList());
        for (FormDefine formDefine : formDefineList) {
            List dataLinkDefines = this.runTimeViewController.getAllLinksInForm(formDefine.getKey());
            dataLinkDefines.forEach(dataLinkDefine -> zbId2DataLinkDefineMap.put(dataLinkDefine.getLinkExpression(), (DataLinkDefine)dataLinkDefine));
        }
        return zbId2DataLinkDefineMap;
    }
}

