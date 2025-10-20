/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.journalsingle.common.MDConst
 *  com.jiuqi.gcreport.journalsingle.common.PeriodTypeUtil
 *  com.jiuqi.gcreport.journalsingle.common.SqlUtil
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalPostRuleVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  org.apache.commons.lang3.ObjectUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.journalsingle.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.journalsingle.common.MDConst;
import com.jiuqi.gcreport.journalsingle.common.PeriodTypeUtil;
import com.jiuqi.gcreport.journalsingle.common.SqlUtil;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalPostRuleDao;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSingleDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalPostRuleEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.service.IJournalSinglePostService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import com.jiuqi.gcreport.journalsingle.vo.JournalEnvContextVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalPostRuleVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalSingleServiceImpl
implements IJournalSingleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    IJournalSingleDao singleDao;
    @Autowired
    IJournalPostRuleDao postRuledao;
    @Autowired
    private IJournalSingleSubjectService subjectService;
    @Autowired
    private IJournalSinglePostService singlePostService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IFormSchemeService formSchemeService;
    private ThreadPoolExecutor executorService;

    public JournalSingleServiceImpl() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("journal-post-%d").build();
        this.executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
    }

    @Override
    public Integer batchUpdatePostFlag(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId) {
        return this.singleDao.batchUpdatePostFlag(taskId, schemeId, periodStr, orgId, orgTypeId);
    }

    @Override
    public Integer batchUpdatePostFlag(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId, String afterZbTableName) {
        return this.singleDao.batchUpdatePostFlag(taskId, schemeId, periodStr, orgId, orgTypeId, afterZbTableName);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void addJournalDetail(List<List<JournalSingleVO>> batchlist, boolean isMerge) {
        ArrayList addList = new ArrayList();
        ArrayList<String> mrecidList = new ArrayList<String>();
        String taskId = batchlist.get(0).get(0).getTaskId();
        boolean existAdjust = DimensionUtils.isExistAdjust((String)taskId);
        batchlist.forEach(list -> {
            block14: {
                List<JournalSingleEO> eoList;
                block13: {
                    eoList = this.convertVoList2EoList((List<JournalSingleVO>)list);
                    String srcId = UUIDUtils.newUUIDStr();
                    for (Object eo : eoList) {
                        if (StringUtils.isEmpty((String)eo.getSrcID())) continue;
                        srcId = eo.getSrcID();
                        break;
                    }
                    if (!eoList.get(0).getEffectType().equals(MDConst.EFFECTTYPE_CURRMONTH)) break block13;
                    String mid = UUIDOrderUtils.newUUIDStr();
                    for (JournalSingleEO eo : eoList) {
                        if (eo.getSrcID() != null && !mrecidList.contains(eo.getSrcID())) {
                            mrecidList.add(eo.getSrcID());
                        }
                        eo.setmRecid(mid);
                        eo.setSrcID(srcId);
                        addList.add(eo);
                    }
                    break block14;
                }
                if (!eoList.get(0).getEffectType().equals(MDConst.EFFECTTYPE_CURRYEAR)) break block14;
                Integer[] laterPeriods = PeriodTypeUtil.getLaterPeriodByType((int)((JournalSingleVO)list.get(0)).getPeriodType(), (int)((JournalSingleVO)list.get(0)).getAcctPeriod(), (boolean)true);
                if (!existAdjust) {
                    Object eo;
                    eo = laterPeriods;
                    int n = ((Object)eo).length;
                    boolean i = false;
                    while (n < n) {
                        int period = (Integer)eo[n];
                        String mid2 = UUID.randomUUID().toString();
                        for (JournalSingleEO eo2 : eoList) {
                            if (eo2.getSrcID() != null && !mrecidList.contains(eo2.getSrcID())) {
                                mrecidList.add(eo2.getSrcID());
                            }
                            JournalSingleEO laterEo = new JournalSingleEO();
                            BeanUtils.copyProperties((Object)eo2, (Object)laterEo);
                            laterEo.setId(UUID.randomUUID().toString());
                            laterEo.setmRecid(mid2);
                            laterEo.setAcctPeriod(period);
                            String defaultPeriod = YearPeriodUtil.transform(null, (int)laterEo.getAcctYear(), (int)((JournalSingleVO)list.get(0)).getPeriodType(), (int)period).toString();
                            laterEo.setDefaultPeriod(defaultPeriod);
                            laterEo.setSrcID(eoList.get(0).getSrcID());
                            addList.add(laterEo);
                        }
                        ++n;
                    }
                } else {
                    int period;
                    HashMap<Integer, List> allAdjustPeriods = new HashMap<Integer, List>();
                    Integer[] integerArray = laterPeriods;
                    int n = integerArray.length;
                    for (period = 0; period < n; ++period) {
                        int period2 = integerArray[period];
                        String defaultPeriod = YearPeriodUtil.transform(null, (int)((JournalSingleVO)list.get(0)).getAcctYear(), (int)((JournalSingleVO)list.get(0)).getPeriodType(), (int)period2).toString();
                        List periodList = this.formSchemeService.queryAdjustPeriods(((JournalSingleVO)list.get(0)).getSchemeId(), defaultPeriod);
                        allAdjustPeriods.put(period2, periodList);
                    }
                    for (Map.Entry entry : allAdjustPeriods.entrySet()) {
                        period = (Integer)entry.getKey();
                        List periods = (List)entry.getValue();
                        for (AdjustPeriod adjustPeriod : periods) {
                            String mid2 = UUIDUtils.newUUIDStr();
                            for (JournalSingleEO eo : eoList) {
                                if (eo.getSrcID() != null && !mrecidList.contains(eo.getSrcID())) {
                                    mrecidList.add(eo.getSrcID());
                                }
                                JournalSingleEO laterEo = new JournalSingleEO();
                                BeanUtils.copyProperties((Object)eo, (Object)laterEo);
                                laterEo.setId(UUID.randomUUID().toString());
                                laterEo.setmRecid(mid2);
                                laterEo.setAcctPeriod(period);
                                laterEo.setDefaultPeriod(adjustPeriod.getPeriod());
                                laterEo.setSrcID(eoList.get(0).getSrcID());
                                laterEo.setAdjust(adjustPeriod.getCode());
                                addList.add(laterEo);
                            }
                        }
                    }
                }
            }
        });
        JournalSingleEO firstJournalSingleEO = null;
        if (mrecidList.size() > 0) {
            firstJournalSingleEO = (JournalSingleEO)((Object)addList.get(0));
            int acctYear = ((JournalSingleEO)((Object)addList.get(0))).getAcctYear();
            int acctPeriod = ((JournalSingleEO)((Object)addList.get(0))).getAcctPeriod();
            this.singleDao.batchDeleteBySrcid(mrecidList, acctYear, 1, acctPeriod);
        }
        this.singleDao.saveAll(addList);
        if (null != firstJournalSingleEO) {
            JournalSinglePostCondition condition = this.getJournalSinglePostCondition(firstJournalSingleEO);
            this.autoPostData(condition);
        }
    }

    private void autoPostData(JournalSinglePostCondition condition) {
        NpContext context = NpContextHolder.getContext();
        this.executorService.execute(() -> {
            NpContextHolder.setContext((NpContext)context);
            this.singlePostService.postData(condition);
        });
    }

    private JournalSinglePostCondition getJournalSinglePostCondition(JournalSingleEO singleEO) {
        JournalSinglePostCondition condition = new JournalSinglePostCondition();
        condition.setTaskId(singleEO.getTaskId());
        condition.setSchemeId(singleEO.getSchemeId());
        condition.setSelectAdjustCode(singleEO.getAdjust());
        condition.setAcctYear(singleEO.getAcctYear().intValue());
        condition.setAcctPeriod(singleEO.getAcctPeriod().intValue());
        condition.setPeriodStr(singleEO.getDefaultPeriod());
        condition.setInputUnitId(singleEO.getInputUnitId());
        String currencyId = "CNY";
        if (!StringUtils.isEmpty((String)singleEO.getCurrencyCode()) && !"CNY".equals(singleEO.getCurrencyCode())) {
            GcBaseData currencyData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", singleEO.getCurrencyCode());
            currencyId = currencyData.getCode();
        }
        condition.setCurrencyId(currencyId);
        condition.setOrgTypeId(singleEO.getOrgType());
        return condition;
    }

    @Override
    public int queryPageCountByCondition(JournalDetailCondition condi) {
        String sql = "select distinct t.mrecid c \nfrom gc_journal_single t where 1=1  \nand t.taskId = '" + condi.taskId + "'\nand t.schemeId = '" + condi.schemeId + "'\nand t.acctYear = " + condi.acctYear + "\nand t.adjtype = '" + condi.adjType + "'\n";
        if (!StringUtils.isEmpty((String)condi.currencyCode)) {
            sql = sql + "and t.currencyCode = '" + condi.currencyCode + "'\n";
        }
        sql = condi.showHistoryPeriod ? sql + " and t.acctperiod <= " + condi.acctPeriod + "\n" : sql + " and t.acctperiod = " + condi.acctPeriod + "\n";
        if (MDConst.EFFECTTYPE_CURRMONTH.equals(condi.effectType)) {
            sql = sql + "and t.effecttype='effectCurrMonth'\n";
        } else if (MDConst.EFFECTTYPE_CURRYEAR.equals(condi.effectType)) {
            sql = sql + "and t.effecttype='effectCurrYear'\n";
        }
        if (condi.inputUnitId != null) {
            sql = sql + "and t.inputUnitID = '" + condi.inputUnitId + "'\n";
        }
        if (!StringUtils.isEmpty((String)condi.adjustTypeCode)) {
            sql = sql + "and t.adjustTypeCode = '" + condi.adjustTypeCode + "'\n";
        }
        return this.singleDao.count(sql, new Object[0]);
    }

    @Override
    public List<JournalSingleEO> queryByPageCondition(JournalDetailCondition condi) {
        int maxResults;
        int firstResult;
        List mrecidList;
        List<JournalSingleEO> result = new ArrayList<JournalSingleEO>();
        String sql = "select distinct t.mrecid as ID,t.createtime as createtime \nfrom gc_journal_single t where 1=1 \nand t.taskId = '" + condi.taskId + "'\nand t.schemeId = '" + condi.schemeId + "'\nand t.acctYear = " + condi.acctYear + "\nand t.adjtype = '" + condi.adjType + "'\n";
        if (DimensionUtils.isExistAdjust((String)condi.getTaskId())) {
            sql = sql + "and t.ADJUST = '" + condi.getSelectAdjustCode() + "'\n";
        }
        if (!StringUtils.isEmpty((String)condi.currencyCode)) {
            sql = sql + "and t.currencyCode = '" + condi.currencyCode + "'\n";
        }
        sql = condi.showHistoryPeriod ? sql + " and t.acctperiod <= " + condi.acctPeriod + "\n" : sql + " and t.acctperiod = " + condi.acctPeriod + "\n";
        if (MDConst.EFFECTTYPE_CURRMONTH.equals(condi.effectType)) {
            sql = sql + "and t.effecttype='effectCurrMonth'\n";
        } else if (MDConst.EFFECTTYPE_CURRYEAR.equals(condi.effectType)) {
            sql = sql + "and t.effecttype='effectCurrYear'\n";
        }
        if (condi.inputUnitId != null) {
            sql = sql + "and t.inputUnitID = '" + condi.inputUnitId + "'\n";
        }
        if (!StringUtils.isEmpty((String)condi.adjustTypeCode)) {
            sql = sql + "and t.adjustTypeCode = '" + condi.adjustTypeCode + "'\n";
        }
        if (!StringUtils.isEmpty((String)condi.gcBusinessTypeCode)) {
            sql = sql + "and t.gcBusinessTypeCode = '" + condi.gcBusinessTypeCode + "'\n";
        }
        if ((mrecidList = this.singleDao.selectFirstListByPaging(String.class, sql = sql + "order by t.createtime desc \n", firstResult = (condi.pageNum - 1) * condi.pageSize, maxResults = firstResult + condi.pageSize, new Object[0])).size() > 0) {
            String sql2 = "select\n" + this.getTableSelColumns() + " and " + SqlUtil.buildInSql((String)"t.mrecid", (List)mrecidList) + "and t.taskId = '" + condi.taskId + "'\nand t.schemeId = '" + condi.schemeId + "'\nand t.acctYear = " + condi.acctYear + "\nand t.adjtype = '" + condi.adjType + "'\n order by t.createtime desc,t.sortorder  \n";
            result = this.singleDao.selectEntity(sql2, new Object[0]);
        }
        return result;
    }

    @Override
    public List<JournalSingleEO> queryByCondition(JournalDetailCondition condi) {
        List mrecidList;
        List<JournalSingleEO> result = new ArrayList<JournalSingleEO>();
        String sql = "select distinct t.mrecid as ID,t.createtime as createtime \nfrom gc_journal_single t where 1=1  \nand t.taskId = '" + condi.taskId + "'\nand t.schemeId = '" + condi.schemeId + "'\nand t.acctYear = " + condi.acctYear + "\nand t.adjtype = 'SINGLE'\nand  ((t.effecttype='effectCurrMonth' and t.default_period = '" + condi.getDefaultPeriod() + "')\n       or t.effecttype='effectCurrYear')\n";
        if (!StringUtils.isEmpty((String)condi.currencyCode)) {
            sql = sql + "and t.currencyCode = '" + condi.currencyCode + "'\n";
        }
        if (condi.inputUnitId != null) {
            sql = sql + "and t.inputUnitID = '" + condi.inputUnitId + "'\n";
        }
        if (!StringUtils.isEmpty((String)condi.adjustTypeCode)) {
            sql = sql + "and t.adjustTypeCode = '" + condi.adjustTypeCode + "'\n";
        }
        if ((mrecidList = this.singleDao.selectFirstList(String.class, sql = sql + "order by t.createtime desc \n", new Object[0])).size() > 0) {
            String sql2 = "select\n" + this.getTableSelColumns() + " and " + SqlUtil.buildInSql((String)"t.mrecid", (List)mrecidList) + "and t.taskId = '" + condi.taskId + "'\nand t.schemeId = '" + condi.schemeId + "'\nand t.acctYear = " + condi.acctYear + "\n order by t.createtime desc,t.sortorder  \n";
            result = this.singleDao.selectEntity(sql2, new Object[0]);
        }
        return result;
    }

    @Override
    public List<JournalSingleEO> queryDetailByID(JournalDetailCondition condi) {
        String sql = "select \n" + this.getTableSelColumns() + "and t.mrecid = ? \nand t.acctYear = ? \nand t.acctPeriod = ? \nand t.adjtype = ? \n";
        return this.singleDao.selectEntity(sql, new Object[]{condi.mRecid, condi.acctYear, condi.acctPeriod, condi.adjType});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void SingleDeleteByMrecid(List<String> mrecidList, int acctYear, int effectType, int acctPeriod) {
        List<JournalSingleEO> journalSingleBySrcid = this.singleDao.findJournalSingleBySrcid(mrecidList, acctYear, effectType, acctPeriod);
        if (CollectionUtils.isEmpty(journalSingleBySrcid)) {
            return;
        }
        this.singleDao.batchDeleteBySrcid(mrecidList, acctYear, effectType, acctPeriod);
        this.autoPostData(this.getJournalSinglePostCondition(journalSingleBySrcid.get(0)));
    }

    @Override
    public List<JournalSingleEO> queryDetailByMrecid(String mrecid) {
        String sql = "select \n" + this.getTableSelColumns() + "and t.mrecid = ? \n";
        return this.singleDao.selectEntity(sql, new Object[]{mrecid});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updatePostFlag(List<String> mrecidList) {
        String sql = "update gc_journal_single  t \nset postflag=1 \nwhere 1=1 \nand " + SqlUtil.buildInSql((String)"t.mrecid", mrecidList);
        this.singleDao.execute(sql);
    }

    private String getTableSelColumns() {
        return SqlUtils.getColumnsSqlByTableDefine((String)"GC_JOURNAL_SINGLE", (String)"t") + " from gc_journal_single t where 1=1 \n";
    }

    public List<JournalSingleEO> convertVoList2EoList(List<JournalSingleVO> list) {
        ArrayList<JournalSingleEO> eoList = new ArrayList<JournalSingleEO>();
        Date createDate = new Date();
        HashSet unitIdSet = new HashSet();
        for (JournalSingleVO vo : list) {
            vo.setId(UUID.randomUUID().toString());
            JournalSingleEO eo = new JournalSingleEO();
            vo.setCreateUser(NpContextHolder.getContext().getUser().getName());
            vo.setCreateDate(createDate);
            vo.setCreateTime(createDate);
            BeanUtils.copyProperties(vo, (Object)eo);
            eo.setPostFlag("\u5df2\u8fc7\u8d26".equals(vo.getPostFlagTitle()) ? 1 : 0);
            eo.setEnterPeriod(eo.getAcctPeriod());
            if (vo.getSubjectVo() != null) {
                eo.setSubjectCode(vo.getSubjectVo().getCode());
            }
            Map unSysFields = vo.getUnSysFields();
            for (Map.Entry unSysField : unSysFields.entrySet()) {
                eo.addFieldValue((String)unSysField.getKey(), unSysField.getValue());
            }
            if (!UUIDUtils.isEmpty((String)vo.getInputUnitId())) {
                eo.setOrgType(vo.getUnitVersion());
            }
            if (vo.getDebit() != null && vo.getDebit() != 0.0) {
                eo.setDc(1);
                eo.setDebitCNY(vo.getDebit());
            } else if (vo.getCredit() != null && vo.getCredit() != 0.0) {
                eo.setDc(-1);
                eo.setCreditCNY(vo.getCredit());
            }
            eoList.add(eo);
        }
        return eoList;
    }

    @Override
    public JournalSingleVO convertEO2VO(JournalSingleEO eo, String relateSchemeId, List<DimensionEO> dimensionVOs) {
        JournalSubjectEO subjectEO;
        JournalSingleVO vo = new JournalSingleVO();
        BeanUtils.copyProperties((Object)eo, vo);
        if (!StringUtils.isEmpty((String)eo.getSubjectCode()) && (subjectEO = this.subjectService.getSubjectEOByCode(relateSchemeId, eo.getSubjectCode())) != null) {
            BaseDataVO subjVo = new BaseDataVO();
            subjVo.setId(subjectEO.getId());
            subjVo.setCode(subjectEO.getCode());
            subjVo.setTitle(subjectEO.getTitle());
            vo.setSubjectVo(subjVo);
            vo.setSubjectName(subjVo.getCode() + "-" + subjVo.getTitle());
        }
        if (!CollectionUtils.isEmpty(dimensionVOs)) {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            dimensionVOs.forEach(dimensionVO -> {
                String dictTableName = dimensionVO.getTableName();
                String fieldCode = dimensionVO.getCode().toUpperCase();
                Object fieldDictValue = eo.getFieldValue(fieldCode);
                if (!StringUtils.isEmpty((String)dictTableName)) {
                    if (ObjectUtils.isEmpty((Object)fieldDictValue)) {
                        return;
                    }
                    GcBaseData baseData = tool.queryBaseDataSimpleItem(dictTableName, String.valueOf(fieldDictValue));
                    if (null != baseData) {
                        vo.addUnSysFieldValue(fieldCode, (Object)baseData);
                    }
                } else {
                    vo.addUnSysFieldValue(fieldCode, fieldDictValue);
                }
            });
        }
        if (eo.getDc() == 1) {
            vo.setDebit(eo.getDebitCNY());
        } else if (eo.getDc() == -1) {
            vo.setCredit(eo.getCreditCNY());
        }
        vo.setPostFlagTitle(eo.getPostFlag() == 1 ? "\u5df2\u8fc7\u8d26" : "\u672a\u8fc7\u8d26");
        return vo;
    }

    @Override
    public List<JournalEnvContextVO> queryJournalByDims(String orgId, String periodStr, String orgTypeId, String taskId, String schemeId, String adjust) {
        List<Object> journalEnvContextVOs = new ArrayList<JournalEnvContextVO>();
        List<JournalSingleEO> journalEOs = this.singleDao.listJournalSingleByDims(taskId, schemeId, periodStr, orgId, orgTypeId, adjust);
        if (!CollectionUtils.isEmpty(journalEOs)) {
            journalEnvContextVOs = journalEOs.stream().map(journalEO -> {
                JournalEnvContextVO journalEnvContextVO = new JournalEnvContextVO();
                BeanUtils.copyProperties(journalEO, journalEnvContextVO);
                journalEnvContextVO.setPostFlag(journalEO.getPostFlag() == 1);
                return journalEnvContextVO;
            }).collect(Collectors.toList());
        }
        return journalEnvContextVOs;
    }

    @Override
    public List<JournalSingleEO> listJournalSingleByDims(String taskId, String schemeId, String periodStr, String orgId, String orgTypeId, String adjust) {
        return this.singleDao.listJournalSingleByDims(taskId, schemeId, periodStr, orgId, orgTypeId, adjust);
    }

    private String getFormula(String fetchSchemeId, JournalSinglePostCondition condi, DataField fieldDefine) throws Exception {
        String fieldKey = fieldDefine.getKey();
        FormDefine formDefine = this.runTimeViewController.queryFormByCodeInScheme(condi.getSchemeId(), null);
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)SpringContextUtils.getBean(IFormulaRunTimeController.class);
        List allFormulasDefines = formulaRunTimeController.getAllFormulasInForm(fetchSchemeId, formDefine.getKey());
        if (CollectionUtils.isEmpty((Collection)allFormulasDefines)) {
            return null;
        }
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, dataDefinitionRuntimeController, entityViewRunTimeController, condi.getSchemeId());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
        executorContext.setDefaultGroupName(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
        ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext.isJQReportModel());
        QueryContext qContext = new QueryContext(executorContext, null);
        for (FormulaDefine formulaDefine : allFormulasDefines) {
            FieldDefine efdcFieldDefine;
            String expression = formulaDefine.getExpression();
            int equalIndex = expression.indexOf("=");
            String assginExp = expression.substring(0, equalIndex);
            String efdcExp = expression.substring(equalIndex + 1);
            if (assginExp.indexOf("*") >= 0) continue;
            IExpression exp = parser.parseEval(assginExp, (IContext)qContext);
            DynamicDataNode dataNode = null;
            for (IASTNode child : exp) {
                if (!(child instanceof DynamicDataNode)) continue;
                dataNode = (DynamicDataNode)child;
                break;
            }
            if ((efdcFieldDefine = dataNode.getDataLink().getField()) == null) {
                return null;
            }
            if (!fieldKey.equals(efdcFieldDefine.getKey())) continue;
            return efdcExp;
        }
        return null;
    }

    private double getResultValue(AbstractData result, JournalPostRuleVO postRuleVO) throws DataTypeException {
        int calcateRule = postRuleVO.getCalcateRule();
        return result.getAsFloat() * (double)calcateRule;
    }

    private String getFilterFormula(String formula) {
        return "GC_JOURNAL[DEBITCNY,'" + formula + "',SUM]";
    }

    private JournalPostRuleVO convertEo2Vo(JournalPostRuleEO eo) {
        JournalPostRuleVO vo = new JournalPostRuleVO();
        BeanUtils.copyProperties((Object)eo, vo);
        return vo;
    }
}

