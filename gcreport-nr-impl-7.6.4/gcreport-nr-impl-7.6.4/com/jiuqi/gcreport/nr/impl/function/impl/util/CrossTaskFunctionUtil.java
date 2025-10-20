/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.gcreport.nr.impl.function.impl.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrossTaskFunctionUtil {
    private static final transient Logger logger = LoggerFactory.getLogger(CrossTaskFunctionUtil.class);

    public static String getOrgTypeBySchemeId(FormSchemeDefine destSchemeDefine) {
        TableModelDefine tableDefine = ((IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class)).getTableModel(destSchemeDefine.getDw());
        return tableDefine.getName();
    }

    public static FormSchemeDefine getDestFormSchemeDefine(ReportFmlExecEnvironment env, String linkTaskAlias) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        if (StringUtils.isEmpty((String)linkTaskAlias)) {
            return runTimeViewController.getFormScheme(env.getFormSchemeKey());
        }
        TaskLinkDefine linkDefine = runTimeViewController.queryTaskLinkByCurrentFormSchemeAndNumber(env.getFormSchemeKey(), linkTaskAlias);
        if (linkDefine == null) {
            throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u516c\u5f0f\u4e2d\u7684\u5173\u8054\u4efb\u52a1:" + linkTaskAlias);
        }
        FormSchemeDefine destSchemeDefine = null;
        if (linkDefine != null) {
            destSchemeDefine = runTimeViewController.getFormScheme(linkDefine.getRelatedFormSchemeKey());
        }
        if (destSchemeDefine == null) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u4e2d\u5173\u8054\u7684\u4efb\u52a1\u672a\u627e\u5230\u62a5\u8868\u65b9\u6848:" + linkTaskAlias);
        }
        return destSchemeDefine;
    }

    public static String convertPeriod(String sourcePeriodStr, PeriodType sourcePeriodType, PeriodType destPeriodType) {
        if (StringUtils.isEmpty((String)sourcePeriodStr) || sourcePeriodStr.length() != 9) {
            throw new BusinessRuntimeException("\u65f6\u671f\u5f02\u5e38\uff01");
        }
        char destType = (char)destPeriodType.code();
        char sourceType = (char)sourcePeriodType.code();
        int sourcePeriod = Integer.valueOf(sourcePeriodStr.substring(7));
        String destAcctPeriod = null;
        if (destType == 'N') {
            destAcctPeriod = "01";
        } else if (destType == 'H') {
            if (sourceType == 'N') {
                destAcctPeriod = "02";
            } else if (sourceType == 'H') {
                destAcctPeriod = sourcePeriodStr.substring(7);
            } else if (sourceType == 'J') {
                destAcctPeriod = sourcePeriod <= 2 ? "01" : "02";
            } else if (sourceType == 'Y') {
                destAcctPeriod = sourcePeriod <= 6 ? "01" : "02";
            }
        } else if (destType == 'J') {
            if (sourceType == 'N') {
                destAcctPeriod = "04";
            } else if (sourceType == 'H') {
                destAcctPeriod = "0" + sourcePeriod * 2;
            } else if (sourceType == 'J') {
                destAcctPeriod = sourcePeriodStr.substring(7);
            } else if (sourceType == 'Y') {
                destAcctPeriod = "0" + (sourcePeriod / 3 + (sourcePeriod % 3 != 0 ? 1 : 0));
            }
        } else if (destType == 'Y') {
            if (sourceType == 'N') {
                destAcctPeriod = "12";
            } else if (sourceType == 'H') {
                destAcctPeriod = sourcePeriod == 1 ? "0" + sourcePeriod * 6 : String.valueOf(sourcePeriod * 6);
            } else if (sourceType == 'J') {
                destAcctPeriod = sourcePeriod * 3 < 10 ? "0" + sourcePeriod * 3 : String.valueOf(sourcePeriod * 3);
            } else if (sourceType == 'Y') {
                destAcctPeriod = sourcePeriodStr.substring(7);
            }
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + destPeriodType);
        }
        if (destAcctPeriod == null) {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\uff1a" + sourcePeriodType.code());
        }
        return sourcePeriodStr.substring(0, 4) + (char)destPeriodType.code() + "00" + destAcctPeriod;
    }

    public static PeriodWrapper getSrcPeriod(String periodValue, int periodOffset, String periodType) {
        PeriodWrapper srcPeriod = new PeriodWrapper(periodValue);
        if (StringUtils.isNotEmpty((String)periodType)) {
            char periodChar = periodType.charAt(0);
            srcPeriod.setType(PeriodConsts.codeToType((int)periodChar));
        } else {
            periodType = String.valueOf((char)PeriodConsts.typeToCode((int)srcPeriod.getType()));
        }
        if (periodOffset != 0) {
            int absPeriodOffset = Math.abs(periodOffset);
            if (absPeriodOffset < 10000) {
                PeriodModifier modifier;
                String offsetStr = periodOffset + periodType;
                if (periodOffset > 0) {
                    offsetStr = "+" + offsetStr;
                }
                if ((modifier = PeriodModifier.parse((String)offsetStr)) != null) {
                    String destPeriodStr = new DefaultPeriodAdapter().modify(periodValue, modifier);
                    srcPeriod = new PeriodWrapper(destPeriodStr);
                }
            } else if (absPeriodOffset > 10000 && absPeriodOffset < 100000000) {
                int yearOffset = periodOffset / 10000;
                int period = absPeriodOffset % 10000;
                srcPeriod.setYear(srcPeriod.getYear() + yearOffset);
                srcPeriod.setPeriod(period);
            } else {
                int type = absPeriodOffset / 100000000;
                String offsetStr = String.valueOf(absPeriodOffset);
                String periodStr = offsetStr.substring(offsetStr.length() - 4, offsetStr.length());
                if (type == 1) {
                    int period = Integer.parseInt(periodStr);
                    srcPeriod.setPeriod(period);
                } else if (type == 2) {
                    String yearStr = offsetStr.substring(1, 5);
                    int year = Integer.valueOf(yearStr);
                    int period = Integer.parseInt(periodStr);
                    srcPeriod.setYear(year);
                    srcPeriod.setPeriod(period);
                } else if (type == 3) {
                    String yearStr = offsetStr.substring(1, 5);
                    int year = Integer.valueOf(yearStr);
                    if (periodOffset < 0) {
                        periodStr = "-" + periodStr;
                    }
                    int periodOff = Integer.parseInt(periodStr);
                    srcPeriod.setYear(year);
                    srcPeriod.setPeriod(srcPeriod.getPeriod() + periodOff);
                }
            }
        }
        return srcPeriod;
    }

    public static void valiatePeriodParamter(QueryContext context, IASTNode fetchZbParamter, IASTNode periodParamter) {
        try {
            PeriodType periodType;
            String linkTaskAlias;
            if (fetchZbParamter instanceof CellDataNode) {
                CellDataNode sourceZb = (CellDataNode)fetchZbParamter;
                linkTaskAlias = ((DynamicDataNode)sourceZb.getChild(0)).getRelateTaskItem();
            } else {
                String paramter = fetchZbParamter.toString().replaceAll("\"", "");
                int splitRegexIndex = paramter.indexOf(64);
                linkTaskAlias = splitRegexIndex < 0 ? "" : paramter.substring(splitRegexIndex + 1);
            }
            QueryContext queryContext = context;
            ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
            if (!StringUtils.isEmpty((String)linkTaskAlias)) {
                FormSchemeDefine formSchemeDefine = CrossTaskFunctionUtil.getDestFormSchemeDefine(env, linkTaskAlias);
                periodType = formSchemeDefine.getPeriodType();
            } else {
                FormSchemeDefine currSchemeDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(env.getFormSchemeKey());
                periodType = currSchemeDefine.getPeriodType();
            }
            int maxPeriod = CrossTaskFunctionUtil.getMaxPeriodByType(periodType.type());
            int period = Convert.toInt((Object)periodParamter.evaluate(null));
            if (period > Integer.valueOf(maxPeriod)) {
                throw new BusinessRuntimeException("\u6307\u5b9a\u65f6\u671f\u53c2\u6570\u4e0d\u7b26\u5408\u89c4\u8303\uff0c\u4e0d\u80fd\u5927\u4e8e\u5f53\u524d\u65f6\u671f\u7c7b\u578b\u7684\u6700\u5927\u503c:" + maxPeriod);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public static int getMaxPeriodByType(int perodType) {
        switch (perodType) {
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 12;
            }
        }
        throw new UnsupportedOperationException("\u4e0d\u652f\u6301\u7684\u65f6\u671f\u7c7b\u578b\u3002");
    }
}

