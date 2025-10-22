/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.exception.PeriodFormatException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.JtableData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.params.output.SecretLevelInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.ISecretLevelService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotContext
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotInfo
 *  com.jiuqi.nr.snapshot.output.SnapshotInfo
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 */
package com.jiuqi.nr.dataentry.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DataVersionParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.service.ExportExcelNameService;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.util.BatchExportConsts;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.exception.PeriodFormatException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.SecretLevelInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.ISecretLevelService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.snapshot.input.BatchCreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.input.CreateSnapshotInfo;
import com.jiuqi.nr.snapshot.output.SnapshotInfo;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class DataEntryUtil {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryUtil.class);

    public static String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (StringUtils.isEmpty((String)language) || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    public static boolean isChinese() {
        return DataEntryUtil.getLanguage().equals("zh");
    }

    public static List<FormData> getFirstFormOfFormGroup(List<FormGroupData> runtimeFormList, String groupKey) {
        ArrayList<FormData> list = new ArrayList<FormData>();
        for (int m = 0; m < runtimeFormList.size(); ++m) {
            List<FormData> forms;
            FormGroupData group = runtimeFormList.get(m);
            if (!group.getKey().toString().equals(groupKey) || (forms = DataEntryUtil.getAllForms(group)).isEmpty()) continue;
            list.addAll(forms);
        }
        return list;
    }

    public static List<FormData> getAllForms(FormGroupData group) {
        ArrayList<FormData> reports = new ArrayList<FormData>();
        reports.addAll(group.getReports());
        List<FormGroupData> groups = group.getGroups();
        for (FormGroupData groupData : groups) {
            reports.addAll(DataEntryUtil.getAllForms(groupData));
        }
        return reports;
    }

    public static void genereateActionItem(JsonGenerator gen, ActionItem item) {
        try {
            gen.writeStringField("code", item.getCode());
            gen.writeStringField("title", item.getTitle());
            gen.writeStringField("desc", item.getDesc());
            gen.writeObjectField("actionType", (Object)item.getActionType());
            gen.writeStringField("parentCode", item.getParentCode());
            gen.writeStringField("params", item.getParams());
            gen.writeStringField("paramsDesc", item.getParamsDesc());
            gen.writeBooleanField("enablePermission", item.isEnablePermission());
            gen.writeStringField("icon", item.getIcon());
            gen.writeStringField("bgColor", item.getBgColor());
            gen.writeObjectField("accelerator", (Object)item.getAccelerator());
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public static String getFormTitle(JtableData jtableData) {
        String[] info = new String[4];
        String formName = "";
        info[0] = jtableData.getForm().getCode();
        info[1] = jtableData.getForm().getTitle();
        info[3] = jtableData.getForm().getSerialNumber();
        block6: for (int i = 0; i < info.length; ++i) {
            if (info[i] == null) continue;
            switch (i) {
                case 0: {
                    formName = formName + info[i] + " ";
                    continue block6;
                }
                case 1: {
                    formName = formName + info[i];
                    continue block6;
                }
                case 2: {
                    formName = formName + "(" + info[i] + ")";
                    continue block6;
                }
                case 3: {
                    formName = formName + " " + info[i];
                    continue block6;
                }
            }
        }
        return formName;
    }

    public static String getFormTitle(FormData formDefine) {
        return formDefine.getCode() + " " + formDefine.getTitle();
    }

    public static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        PeriodWrapper fromPeriodWrapper = null;
        PeriodWrapper toPeriodWrapper = null;
        try {
            fromPeriodWrapper = new PeriodWrapper(fromPeriod);
            toPeriodWrapper = new PeriodWrapper(toPeriod);
        }
        catch (Exception e) {
            throw new PeriodFormatException(new String[]{e.getMessage()});
        }
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        GregorianCalendar currentCalendar = PeriodUtil.period2Calendar((PeriodWrapper)currentPeriod);
        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
        GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
        if (currentCalendar.compareTo(fromCalendar) >= 0 && currentCalendar.compareTo(toCalendar) <= 0) {
            return currentPeriod;
        }
        GregorianCalendar nowGregorianCalendar = new GregorianCalendar();
        if (nowGregorianCalendar.after(toCalendar)) {
            return toPeriodWrapper;
        }
        return fromPeriodWrapper;
    }

    private static PeriodWrapper adjustWeekPeriod(PeriodWrapper currentPeriod, int periodType, int periodOffset) {
        if (periodType != 7) {
            return currentPeriod;
        }
        PeriodWrapper currPeriod = new PeriodWrapper();
        GregorianCalendar calendar = new GregorianCalendar();
        int weekOfYear = DataEntryUtil.getWeekOfYear(calendar);
        int year = calendar.get(1);
        int month = calendar.get(2);
        int period = 1;
        period = weekOfYear;
        year += periodOffset / 53;
        if ((period += periodOffset % 53) <= 0) {
            --year;
            period = 53 + period;
        } else if (period > 53) {
            ++year;
            period -= 53;
        }
        currPeriod.setAll(year, periodType, period);
        return currPeriod;
    }

    private static int getWeekOfYear(GregorianCalendar calendar) {
        int year = calendar.get(1);
        GregorianCalendar januaryCalendar = new GregorianCalendar(year, 0, 1);
        int week = januaryCalendar.get(7);
        calendar.setFirstDayOfWeek(2);
        if (week == 3 || week == 4 || week == 5) {
            calendar.setMinimalDaysInFirstWeek(7 - week + 2);
        } else {
            calendar.setMinimalDaysInFirstWeek(7);
        }
        int weekOfYear = calendar.get(3);
        return weekOfYear;
    }

    public static boolean getCustomCurrPeriod(String fromPeriod, String toPeriod) {
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)6, (int)0);
        GregorianCalendar currentCalendar = PeriodUtil.period2Calendar((PeriodWrapper)currentPeriod);
        GregorianCalendar fromCalendar = PeriodUtil.period2Calendar((String)fromPeriod);
        GregorianCalendar toCalendar = PeriodUtil.period2Calendar((String)toPeriod);
        return currentCalendar.compareTo(fromCalendar) >= 0 && currentCalendar.compareTo(toCalendar) <= 0;
    }

    public static Date[] parseFromPeriod(String periodString) {
        if (StringUtils.isEmpty((String)periodString)) {
            return null;
        }
        String[] periodList = PeriodUtil.getTimesArr((String)periodString);
        if (periodList == null) {
            return null;
        }
        Date[] dates = new Date[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dates[0] = simpleDateFormat.parse(periodList[0]);
            dates[1] = simpleDateFormat.parse(periodList[1]);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return dates;
    }

    public static List<FormData> getAllForms(IDataEntryParamService dataEntryParamService, ExportParam param, String formKeys) {
        String[] keys;
        ArrayList<FormData> forms = new ArrayList<FormData>();
        JtableContext jtableContext = param.getContext();
        List<FormGroupData> formGroup = dataEntryParamService.getRuntimeFormList(jtableContext);
        ArrayList<String> alreadyHave = new ArrayList<String>();
        for (FormGroupData formGroupData : formGroup) {
            List<FormData> tempList = DataEntryUtil.getAllForms(formGroupData);
            for (FormData tempFormData : tempList) {
                if (alreadyHave.contains(tempFormData.getKey())) continue;
                forms.add(tempFormData);
                alreadyHave.add(tempFormData.getKey());
            }
        }
        if (null != formKeys && !StringUtils.isEmpty((String)formKeys) && null != (keys = formKeys.split(";")) && keys.length > 0) {
            ArrayList<String> fontLists = new ArrayList<String>(Arrays.asList(keys));
            Iterator its = forms.iterator();
            while (its.hasNext()) {
                FormData formData = (FormData)its.next();
                if (fontLists.contains(formData.getKey().toString())) continue;
                its.remove();
            }
        }
        return forms;
    }

    public static List<String> getRepeatCompanyName(String companyKey, JtableContext jtableContext) {
        IJtableEntityService jtableEntityService = (IJtableEntityService)BeanUtil.getBean(IJtableEntityService.class);
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        ISecretLevelService iSecretLevelService = (ISecretLevelService)BeanUtil.getBean(ISecretLevelService.class);
        ExportExcelNameService exportExcelNameService = (ExportExcelNameService)BeanUtil.getBean(ExportExcelNameService.class);
        ArrayList<String> companyNameList = new ArrayList<String>();
        ArrayList<String> repeatCompanyName = new ArrayList<String>();
        EntityViewData dwEntity = jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        DimensionValue dimensionValue = (DimensionValue)jtableContext.getDimensionSet().get(dwEntity.getDimensionName());
        List<String> list = null;
        if (StringUtils.isEmpty((String)dimensionValue.getValue())) {
            list = jtableEntityService.getAllEntityKey(dwEntity.getKey(), jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
        } else {
            String value = dimensionValue.getValue();
            String[] splits = value.split(";");
            list = Arrays.asList(splits);
        }
        for (String key : list) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setEntityViewKey(companyKey);
            entityQueryByKeyInfo.setEntityKey(key);
            entityQueryByKeyInfo.setContext(jtableContext);
            EntityData entrunInfo = jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo).getEntity();
            if (null == entrunInfo) continue;
            String comPanyName = entrunInfo.getTitle();
            if (StringUtils.isEmpty((String)comPanyName)) {
                comPanyName = entrunInfo.getRowCaption();
            }
            if (iSecretLevelService.secretLevelEnable(jtableContext.getTaskKey())) {
                SecretLevelInfo secretLevelInfo = iSecretLevelService.getSecretLevel(jtableContext);
                comPanyName = comPanyName + exportExcelNameService.getSysSeparator() + secretLevelInfo.getSecretLevelItem().getTitle();
            }
            if (companyNameList.contains(comPanyName)) {
                repeatCompanyName.add(comPanyName);
                continue;
            }
            companyNameList.add(comPanyName);
        }
        return repeatCompanyName;
    }

    public static String sheetNameVolidate(String sheetName) {
        if (sheetName.length() > BatchExportConsts.SHEET_LENGTH) {
            sheetName = sheetName.substring(0, BatchExportConsts.SHEET_LENGTH - 3);
        }
        if (sheetName.startsWith("'")) {
            sheetName = "_" + sheetName.substring(1);
        }
        if (sheetName.endsWith("'")) {
            sheetName = sheetName.substring(0, sheetName.length() - 1) + "_";
        }
        if (sheetName.contains(":")) {
            sheetName = sheetName.replace(":", "_");
        }
        if (sheetName.contains("\\")) {
            sheetName = sheetName.replace("\\", "_");
        }
        if (sheetName.contains("/")) {
            sheetName = sheetName.replace("/", "_");
        }
        if (sheetName.contains("?")) {
            sheetName = sheetName.replace("?", "_");
        }
        if (sheetName.contains("*")) {
            sheetName = sheetName.replace("*", "_");
        }
        if (sheetName.contains("[")) {
            sheetName = sheetName.replace("[", "_");
        }
        if (sheetName.contains("]")) {
            sheetName = sheetName.replace("]", "_");
        }
        return sheetName;
    }

    public static Map<String, String> sheetNameToCompanyName(Sheet sheet) {
        HashMap<String, String> resMap = new HashMap<String, String>();
        for (Row row : sheet) {
            int x = row.getRowNum();
            if (x == 0) continue;
            Cell cellCode = row.getCell(1);
            String code = cellCode.getStringCellValue();
            Cell cellName = row.getCell(2);
            String name = cellName.getStringCellValue();
            resMap.put(code, name);
        }
        return resMap;
    }

    public static DataVersionParam getDataVersionParam(ExecuteTaskParam executeTaskParam) {
        DataVersionParam param = new DataVersionParam();
        param.setContext(executeTaskParam.getContext());
        param.setAutoCreated(true);
        param.setDescribe("\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + executeTaskParam.getActionTitle() + "\u7248\u672c ");
        param.setDataVersionId(UUID.randomUUID().toString());
        param.setEdit(false);
        param.setTitle(executeTaskParam.getActionTitle());
        return param;
    }

    public static DataVersionParam getDataVersionParam(IDataEntryParamService dataEntryParamService, Map<String, DimensionValue> dimensionset, String formSchemeKey, UUID dataVersionId) {
        JtableContext context = new JtableContext();
        context.setDimensionSet(dimensionset);
        context.setFormSchemeKey(formSchemeKey);
        DataVersionParam param = new DataVersionParam();
        param.setContext(context);
        param.setAutoCreated(true);
        param.setDescribe("\u4e0a\u62a5\u9000\u56de\u7cfb\u7edf\u81ea\u52a8\u751f\u6210\u9000\u56de\u7248\u672c");
        param.setDataVersionId(dataVersionId.toString());
        param.setEdit(false);
        param.setTitle("\u9000\u56de");
        return param;
    }

    public static CreateSnapshotContext getDataSnapshotParam(ExecuteTaskParam executeTaskParam, SnapshotService dataSnapshotService, DimCollectionBuildUtil dimCollectionBuildUtil, DimensionCollectionUtil dimensionCollectionUtil, IRunTimeViewController iRunTimeViewController) {
        CreateSnapshotContext param = new CreateSnapshotContext();
        Map dimensionSet = executeTaskParam.getContext().getDimensionSet();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (String key : dimensionSet.keySet()) {
            String[] values = ((DimensionValue)dimensionSet.get(key)).getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                builder.setEntityValue(key, null, new Object[]{((DimensionValue)dimensionSet.get(key)).getValue()});
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            builder.setEntityValue(key, null, new Object[]{valueList});
        }
        DimensionCollection dimensionCollection = builder.getCollection();
        param.setDimensionCollection(dimensionCollection);
        param.setFormSchemeKey(executeTaskParam.getContext().getFormSchemeKey());
        ArrayList<String> formkeys = new ArrayList<String>();
        List forms = new ArrayList<FormDefine>();
        FormSchemeDefine formScheme = iRunTimeViewController.getFormScheme(executeTaskParam.getContext().getFormSchemeKey());
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        if (wordFlowType.equals((Object)WorkFlowType.FORM)) {
            forms.add(iRunTimeViewController.queryFormById(executeTaskParam.getContext().getFormKey()));
        } else if (wordFlowType.equals((Object)WorkFlowType.GROUP)) {
            try {
                List thisForms = iRunTimeViewController.getAllFormsInGroup(executeTaskParam.getContext().getFormGroupKey());
                forms.addAll(thisForms);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            forms = iRunTimeViewController.queryAllFormDefinesByFormScheme(executeTaskParam.getContext().getFormSchemeKey());
        }
        for (FormDefine formDefine : forms) {
            if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)) continue;
            formkeys.add(formDefine.getKey());
        }
        if (formkeys.size() == 0) {
            return null;
        }
        param.setFormKeys(formkeys);
        param.setDescribe("\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + executeTaskParam.getActionTitle() + "\u5feb\u7167 ");
        String titleHalf = executeTaskParam.getActionTitle() + "_V";
        StringBuffer title = new StringBuffer().append(titleHalf);
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)executeTaskParam.getContext().getDimensionSet());
        DimensionCombination dimensionCombination = dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, executeTaskParam.getContext().getFormSchemeKey());
        List snapshotInfoList = dataSnapshotService.querySnapshot(dimensionCombination, executeTaskParam.getContext().getTaskKey());
        HashMap<Integer, Integer> TitleMap = new HashMap<Integer, Integer>();
        for (SnapshotInfo dataSnapshotInfo : snapshotInfoList) {
            if (!dataSnapshotInfo.getSnapshot().getTitle().startsWith(titleHalf)) continue;
            int nums = Integer.parseInt(dataSnapshotInfo.getSnapshot().getTitle().replace(titleHalf, ""));
            TitleMap.put(nums, 1);
        }
        for (int i = 1; i < Integer.MAX_VALUE; ++i) {
            if (TitleMap.containsKey(i)) continue;
            title.append(i);
            break;
        }
        if (title.toString().equals(titleHalf)) {
            logger.error("\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + executeTaskParam.getActionTitle() + "\u5feb\u7167\u7684\u6570\u91cf\u8d85\u8fc7\u9650\u5236\uff01");
            return null;
        }
        param.setTitle(title.toString());
        param.setIsAutoCreate(true);
        return param;
    }

    public static List<CreateSnapshotContext> getDataSnapshotParam(SnapshotService snapshotService, Map<String, DimensionValue> dimensionset, BatchExecuteTaskParam batchExecuteTaskParam, IJtableParamService jtableParamService, IJtableEntityService jtableEntityService, IRunTimeViewController iRunTimeViewController, DimensionCollectionUtil dimensionCollectionUtil, DimCollectionBuildUtil dimCollectionBuildUtil) {
        JtableContext context = batchExecuteTaskParam.getContext();
        String actionTitle = batchExecuteTaskParam.getActionTitle().replace("\u6279\u91cf", "");
        ArrayList<CreateSnapshotContext> createSnapshotContexts = new ArrayList<CreateSnapshotContext>();
        EntityViewData dwEntityView = jtableParamService.getDwEntity(context.getFormSchemeKey());
        if (dimensionset.get(dwEntityView.getDimensionName()).getValue().equals("")) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionset);
            EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
            String periodStr = dimensionset.get(periodEntityView.getDimensionName()).getValue();
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            if (periodEntityView != null) {
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
            }
            entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
            JtableContext jtableContext = new JtableContext(context);
            jtableContext.setDimensionSet(dimensionValueMap);
            entityQueryInfo.setContext(jtableContext);
            EntityReturnInfo entityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
            List valueList = DimensionValueSetUtil.getAllEntityKey((EntityReturnInfo)entityReturnInfo, (boolean)false);
            if (CollectionUtils.isEmpty(valueList)) {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), (Object)"");
            } else if (valueList.size() == 1) {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList.get(0));
            } else {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), (Object)valueList);
            }
            dimensionset = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        }
        List mapList = DimensionValueSetUtil.getDimensionSetList(dimensionset);
        for (Map map : mapList) {
            CreateSnapshotContext param = new CreateSnapshotContext();
            DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(map, context.getFormSchemeKey());
            param.setDimensionCollection(dimensionCollection);
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)map);
            DimensionCombination dimensionCombination = dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, context.getFormSchemeKey());
            List forms = iRunTimeViewController.queryAllFormDefinesByFormScheme(context.getFormSchemeKey());
            ArrayList<String> formkeys = new ArrayList<String>();
            for (FormDefine formDefine : forms) {
                if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)) continue;
                formkeys.add(formDefine.getKey());
            }
            param.setFormKeys(formkeys);
            param.setDescribe("\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + actionTitle + "\u5feb\u7167 ");
            param.setFormSchemeKey(context.getFormSchemeKey());
            String titleHalf = actionTitle + "_V";
            StringBuffer title = new StringBuffer().append(titleHalf);
            List snapshotInfoList = snapshotService.querySnapshot(dimensionCombination, context.getTaskKey());
            HashMap<Integer, Integer> TitleMap = new HashMap<Integer, Integer>();
            for (SnapshotInfo dataSnapshotInfo : snapshotInfoList) {
                if (!dataSnapshotInfo.getSnapshot().getTitle().startsWith(titleHalf)) continue;
                int nums = Integer.parseInt(dataSnapshotInfo.getSnapshot().getTitle().replace(titleHalf, ""));
                TitleMap.put(nums, 1);
            }
            for (int i = 1; i < Integer.MAX_VALUE; ++i) {
                if (TitleMap.containsKey(i)) continue;
                title.append(i);
                break;
            }
            if (title.toString().equals(titleHalf)) {
                logger.error("\u5355\u4f4d" + ((DimensionValue)map.get(dwEntityView.getDimensionName())).getValue() + ":\u4e0a\u62a5\u9000\u56de\u7cfb\u7edf\u81ea\u52a8\u751f\u6210" + actionTitle + "\u5feb\u7167\u7684\u6570\u91cf\u8d85\u8fc7\u9650\u5236\uff01");
                continue;
            }
            param.setTitle(title.toString());
            param.setIsAutoCreate(true);
            createSnapshotContexts.add(param);
        }
        return createSnapshotContexts;
    }

    public static BatchCreateSnapshotContext getDataSnapshotBatchParam(SnapshotService snapshotService, Map<String, DimensionValue> dimensionset, BatchExecuteTaskParam batchExecuteTaskParam, Map<String, List<String>> unit2Forms, IJtableParamService jtableParamService, IJtableEntityService jtableEntityService, IRunTimeViewController iRunTimeViewController, DimCollectionBuildUtil dimCollectionBuildUtil) {
        JtableContext context = batchExecuteTaskParam.getContext();
        String actionTitle = batchExecuteTaskParam.getActionTitle().replace("\u6279\u91cf", "");
        ArrayList<CreateSnapshotInfo> createSnapshotContexts = new ArrayList<CreateSnapshotInfo>();
        BatchCreateSnapshotContext batchCreateSnapshotContext = new BatchCreateSnapshotContext();
        EntityViewData dwEntityView = jtableParamService.getDwEntity(context.getFormSchemeKey());
        if (dimensionset.get(dwEntityView.getDimensionName()).getValue().equals("")) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionset);
            EntityViewData periodEntityView = jtableParamService.getDataTimeEntity(context.getFormSchemeKey());
            String periodStr = dimensionset.get(periodEntityView.getDimensionName()).getValue();
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
            if (periodEntityView != null) {
                DimensionValue periodValue = new DimensionValue();
                periodValue.setName(periodEntityView.getDimensionName());
                periodValue.setValue(periodStr);
                dimensionValueMap.put(periodEntityView.getDimensionName(), periodValue);
            }
            entityQueryInfo.setEntityViewKey(dwEntityView.getKey());
            JtableContext jtableContext = new JtableContext(context);
            jtableContext.setDimensionSet(dimensionValueMap);
            entityQueryInfo.setContext(jtableContext);
            EntityReturnInfo entityReturnInfo = jtableEntityService.queryEntityData(entityQueryInfo);
            List valueList = DimensionValueSetUtil.getAllEntityKey((EntityReturnInfo)entityReturnInfo, (boolean)false);
            if (CollectionUtils.isEmpty(valueList)) {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), (Object)"");
            } else if (valueList.size() == 1) {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), valueList.get(0));
            } else {
                dimensionValueSet.setValue(dwEntityView.getDimensionName(), (Object)valueList);
            }
            dimensionset = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        }
        IWorkFlowDimensionBuilder workFlowDimensionBuilder = (IWorkFlowDimensionBuilder)BeanUtil.getBean(IWorkFlowDimensionBuilder.class);
        DimensionCollection buildDimensionCollection = workFlowDimensionBuilder.buildDimensionCollection(context.getTaskKey(), dimensionset);
        List dimensionCombinations = buildDimensionCollection.getDimensionCombinations();
        FormSchemeDefine formScheme = iRunTimeViewController.getFormScheme(context.getFormSchemeKey());
        WorkFlowType wordFlowType = formScheme.getFlowsSetting().getWordFlowType();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            String unit = (String)dimensionValueSet.getValue(dwEntityView.getDimensionName());
            if (!unit2Forms.containsKey(unit)) continue;
            List<String> formOrGroupKey = unit2Forms.get(unit);
            CreateSnapshotInfo param = new CreateSnapshotInfo();
            param.setDimensionCombination(dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, context.getFormSchemeKey()));
            ArrayList<String> formkeys = new ArrayList<String>();
            List forms = new ArrayList();
            if (wordFlowType.equals((Object)WorkFlowType.FORM) && formOrGroupKey.size() != 0) {
                forms = iRunTimeViewController.queryFormsById(formOrGroupKey);
            } else if (wordFlowType.equals((Object)WorkFlowType.GROUP) && formOrGroupKey.size() != 0) {
                for (String key : formOrGroupKey) {
                    try {
                        List thisForms = iRunTimeViewController.getAllFormsInGroup(key);
                        forms.addAll(thisForms);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                forms = iRunTimeViewController.queryAllFormDefinesByFormScheme(context.getFormSchemeKey());
            }
            for (FormDefine formDefine : forms) {
                if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_INSERTANALYSIS) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ANALYSISREPORT) || formDefine.getFormType().equals((Object)FormType.FORM_TYPE_ACCOUNT)) continue;
                formkeys.add(formDefine.getKey());
            }
            if (formkeys.size() == 0) continue;
            param.setFormKeys(formkeys);
            param.setDescribe("\u6d41\u7a0b\u81ea\u52a8\u751f\u6210" + actionTitle + "\u5feb\u7167 ");
            param.setFormSchemeKey(context.getFormSchemeKey());
            String titleHalf = actionTitle + "_V";
            StringBuffer title = new StringBuffer().append(titleHalf);
            List snapshotInfoList = snapshotService.querySnapshot(dimensionCombination, context.getTaskKey());
            HashMap<Integer, Integer> TitleMap = new HashMap<Integer, Integer>();
            for (SnapshotInfo dataSnapshotInfo : snapshotInfoList) {
                if (!dataSnapshotInfo.getSnapshot().getTitle().startsWith(titleHalf)) continue;
                int nums = Integer.parseInt(dataSnapshotInfo.getSnapshot().getTitle().replace(titleHalf, ""));
                TitleMap.put(nums, 1);
            }
            for (int i = 1; i < Integer.MAX_VALUE; ++i) {
                if (TitleMap.containsKey(i)) continue;
                title.append(i);
                break;
            }
            if (title.toString().equals(titleHalf)) {
                logger.error("\u5355\u4f4d" + dimensionValueSet.getValue(dwEntityView.getDimensionName()) + ":\u4e0a\u62a5\u9000\u56de\u7cfb\u7edf\u81ea\u52a8\u751f\u6210" + actionTitle + "\u5feb\u7167\u7684\u6570\u91cf\u8d85\u8fc7\u9650\u5236\uff01");
                continue;
            }
            param.setTitle(title.toString());
            param.setAutoCreate(true);
            createSnapshotContexts.add(param);
        }
        batchCreateSnapshotContext.setCreateSnapshotInfos(createSnapshotContexts);
        batchCreateSnapshotContext.setTaskKey(context.getTaskKey());
        return batchCreateSnapshotContext;
    }
}

