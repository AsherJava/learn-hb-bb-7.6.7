/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.compare.internal.service;

import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import java.util.ArrayList;
import java.util.List;
import nr.single.para.compare.bean.ParaCompareContext;
import nr.single.para.compare.bean.ParaCompareResult;
import nr.single.para.compare.definition.common.CompareParaType;
import nr.single.para.compare.service.EnumTableDefineCompareService;
import nr.single.para.compare.service.FMDMDefineCompareService;
import nr.single.para.compare.service.FormDefineCompareService;
import nr.single.para.compare.service.FormulaDefineCompareService;
import nr.single.para.compare.service.PrintDefineCompareService;
import nr.single.para.compare.service.TaskDefineCompareService;
import nr.single.para.compare.service.TaskLinkDefineCompareService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDefineCompareServiceImpl
implements TaskDefineCompareService {
    private static final Logger log = LoggerFactory.getLogger(TaskDefineCompareServiceImpl.class);
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private FMDMDefineCompareService fmdmService;
    @Autowired
    private EnumTableDefineCompareService enumService;
    @Autowired
    private FormDefineCompareService formService;
    @Autowired
    private FormulaDefineCompareService formulaService;
    @Autowired
    private PrintDefineCompareService printService;
    @Autowired
    private TaskLinkDefineCompareService taskLinkService;

    @Override
    public boolean compareTaskDefine(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        compareContext.setCurProgressLength(0.04);
        this.loadCache(compareContext);
        boolean isUploadBaseParam = true;
        if (compareContext.getOption() != null && !compareContext.getOption().isUploadBaseParam()) {
            isUploadBaseParam = false;
        }
        if (isUploadBaseParam) {
            compareContext.onProgress(0.1, "\u6bd4\u8f83\u5c01\u9762\u4ee3\u7801\u6307\u6807");
            compareContext.setCurProgressLength(0.1);
            log.info("\u6bd4\u8f83\u5c01\u9762\u4ee3\u7801");
            this.compareFMDMInfo(compareContext, comapreResult);
            compareContext.onProgress(0.2, "\u6bd4\u8f83\u679a\u4e3e\u5b57\u5178");
            compareContext.setCurProgressLength(0.1);
            log.info("\u6bd4\u8f83\u679a\u4e3e\u5b57\u5178");
            this.compareEnumInfo(compareContext, comapreResult);
            compareContext.onProgress(0.3, "\u6bd4\u8f83\u62a5\u8868");
            compareContext.setCurProgressLength(0.3);
            log.info("\u6bd4\u8f83\u62a5\u8868");
            this.compareFormInfo(compareContext, comapreResult);
        }
        if (compareContext.getOption().isUploadFormula()) {
            compareContext.onProgress(0.6, "\u6bd4\u8f83\u516c\u5f0f");
            compareContext.setCurProgressLength(0.1);
            this.compareFromulaInfo(compareContext, comapreResult);
        }
        if (compareContext.getOption().isUploadPrint()) {
            compareContext.onProgress(0.7, "\u6bd4\u8f83\u6253\u5370\u6a21\u677f");
            compareContext.setCurProgressLength(0.1);
            log.info("\u6bd4\u8f83\u6253\u5370\u6a21\u677f");
            this.comparePrintInfo(compareContext, comapreResult);
        }
        if (isUploadBaseParam) {
            compareContext.onProgress(0.8, "\u6bd4\u8f83\u5173\u8054\u4efb\u52a1");
            compareContext.setCurProgressLength(0.1);
            log.info("\u6bd4\u8f83\u5173\u8054\u4efb\u52a1");
            this.compareTaskLinkInfo(compareContext, comapreResult);
        }
        return true;
    }

    @Override
    public boolean compareFMDMDefine(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u5c01\u9762\u4ee3\u7801\u6307\u6807");
        compareContext.setCurProgressLength(0.8);
        return this.compareFMDMInfo(compareContext, comapreResult);
    }

    @Override
    public boolean compareEnumListDefine(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u679a\u4e3e");
        compareContext.setCurProgressLength(0.8);
        return this.compareEnumInfo(compareContext, comapreResult);
    }

    @Override
    public boolean compareEnumDefine(ParaCompareContext compareContext, String enumCompareKey, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u679a\u4e3e\u9879");
        compareContext.setCurProgressLength(0.8);
        if (StringUtils.isNotEmpty((CharSequence)compareContext.getOption().getEnumPrefix())) {
            ArrayList<String> enumCompareKeys = new ArrayList<String>();
            enumCompareKeys.add(enumCompareKey);
            return this.enumService.compareEnumTableDefinePrefix(compareContext, enumCompareKeys);
        }
        return this.enumService.compareEnumItemDefine(compareContext, enumCompareKey);
    }

    @Override
    public boolean compareEnumDefines(ParaCompareContext compareContext, List<String> enumCompareKeys, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u679a\u4e3e\u9879");
        compareContext.setCurProgressLength(0.8);
        boolean result = true;
        if (StringUtils.isNotEmpty((CharSequence)compareContext.getOption().getEnumPrefix())) {
            result = this.enumService.compareEnumTableDefinePrefix(compareContext, enumCompareKeys);
        } else {
            for (String enumCompareKey : enumCompareKeys) {
                boolean r2 = this.enumService.compareEnumItemDefine(compareContext, enumCompareKey);
                if (r2) continue;
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean compareFormListDefine(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u62a5\u8868");
        compareContext.setCurProgressLength(0.8);
        this.compareFormInfo(compareContext, comapreResult);
        return true;
    }

    @Override
    public boolean compareFormDefine(ParaCompareContext compareContext, String compareFormKey, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u6307\u6807");
        compareContext.setCurProgressLength(0.8);
        return this.formService.compareFormsDefine(compareContext, compareFormKey);
    }

    @Override
    public boolean compareFormDefines(ParaCompareContext compareContext, List<String> compareFormKeys, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u6307\u6807");
        compareContext.setCurProgressLength(0.8);
        return this.formService.compareFormsDefines(compareContext, compareFormKeys);
    }

    @Override
    public boolean compareFormRegionDefine(ParaCompareContext compareContext, String compareFormKey, int singleFloatingId, String newTableKey, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        compareContext.onProgress(0.06, "\u83b7\u53d6\u62a5\u8868\u65b9\u6848");
        this.loadCache(compareContext);
        compareContext.onProgress(0.1, "\u6bd4\u8f83\u6307\u6807");
        compareContext.setCurProgressLength(0.8);
        return this.formService.compareFormRegionDefine(compareContext, compareFormKey, singleFloatingId, newTableKey);
    }

    private boolean compareFMDMInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.fmdmService.compareFMDMDefine(compareContext);
    }

    private boolean compareEnumInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.enumService.compareEnumTableDefine(compareContext);
    }

    private boolean compareFormInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.formService.compareFormsDefine(compareContext);
    }

    private boolean compareFromulaInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.formulaService.compareFormulaDefine(compareContext);
    }

    private boolean comparePrintInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.printService.comparePrintDefine(compareContext);
    }

    private boolean compareTaskLinkInfo(ParaCompareContext compareContext, ParaCompareResult comapreResult) throws Exception {
        compareContext.setComapreResult(comapreResult);
        return this.taskLinkService.compareTaskLinkDefine(compareContext);
    }

    private void loadCache(ParaCompareContext compareContext) {
        DesignDataScheme dataScheme = null;
        DesignTaskDefine taskDefine = null;
        DesignFormSchemeDefine formScheme = null;
        if (compareContext.getOption().getCompareType() == CompareParaType.PARA_FORMSCHEME_HAS) {
            dataScheme = this.dataSchemeSevice.getDataScheme(compareContext.getDataSchemeKey());
            taskDefine = this.viewController.queryTaskDefine(compareContext.getTaskKey());
            formScheme = this.viewController.queryFormSchemeDefine(compareContext.getFormSchemeKey());
        } else if (compareContext.getOption().getCompareType() == CompareParaType.PARA_FORMSCHEME_NEW) {
            dataScheme = this.dataSchemeSevice.getDataScheme(compareContext.getDataSchemeKey());
            taskDefine = this.viewController.queryTaskDefine(compareContext.getTaskKey());
        } else if (compareContext.getOption().getCompareType() == CompareParaType.PARA_TASK_NEW) {
            dataScheme = this.dataSchemeSevice.getDataScheme(compareContext.getDataSchemeKey());
        } else if (compareContext.getOption().getCompareType() == CompareParaType.DATA_DATASCHEME_NEW || compareContext.getOption().getCompareType() == CompareParaType.DATA_ENTITY_NEW) {
            // empty if block
        }
        compareContext.getCache().setDataScheme(dataScheme);
        compareContext.getCache().setFormScheme(formScheme);
        compareContext.getCache().setTaskDefine(taskDefine);
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey) throws Exception {
        this.batchDelete(compareContext, compareKey, 0.0, 1.0);
    }

    @Override
    public void batchDelete(ParaCompareContext compareContext, String compareKey, double posStart, double posLen) throws Exception {
        compareContext.onProgress(posStart + 0.1 * posLen, "\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6307\u6807");
        compareContext.setCurProgressLength(0.1 * posLen);
        this.fmdmService.batchDelete(compareContext, compareKey);
        compareContext.onProgress(posStart + 0.2 * posLen, "\u5220\u9664\u679a\u4e3e\u5b57\u5178");
        compareContext.setCurProgressLength(0.1 * posLen);
        this.enumService.batchDelete(compareContext, compareKey);
        compareContext.onProgress(posStart + 0.3 * posLen, "\u5220\u9664\u62a5\u8868");
        compareContext.setCurProgressLength(0.3 * posLen);
        this.formService.batchDelete(compareContext, compareKey);
        compareContext.onProgress(posStart + 0.6 * posLen, "\u5220\u9664\u516c\u5f0f");
        compareContext.setCurProgressLength(0.1 * posLen);
        this.formulaService.batchDelete(compareContext, compareKey);
        compareContext.onProgress(posStart + 0.7 * posLen, "\u5220\u9664\u6253\u5370\u6a21\u677f");
        compareContext.setCurProgressLength(0.1 * posLen);
        this.printService.batchDelete(compareContext, compareKey);
        compareContext.onProgress(posStart + 0.8 * posLen, "\u5220\u9664\u5173\u8054\u4efb\u52a1");
        compareContext.setCurProgressLength(0.1 * posLen);
        this.taskLinkService.batchDelete(compareContext, compareKey);
    }
}

