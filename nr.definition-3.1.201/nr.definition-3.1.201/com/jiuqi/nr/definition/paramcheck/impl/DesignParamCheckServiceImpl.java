/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.definition.paramcheck.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.exception.DesignCodeCheckException;
import com.jiuqi.nr.definition.exception.DesignTitleCheckException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormGroupDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService;
import com.jiuqi.nr.definition.internal.service.DesignPrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignParamCheckServiceImpl
implements IDesignParamCheckService {
    private final Logger logger = LoggerFactory.getLogger(DesignParamCheckServiceImpl.class);
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private DesignTaskDefineService designTaskDefineService;
    @Autowired
    private DesignFormSchemeDefineService designFormSchemeDefineService;
    @Autowired
    private DesignFormGroupDefineService designFormGroupDefineService;
    @Autowired
    private DesignFormulaSchemeDefineService designFormulaSchemeDefineService;
    @Autowired
    private DesignPrintTemplateSchemeDefineService designPrintTemplateSchemeDefineService;
    @Autowired
    private DesignTaskGroupDefineService designTaskGroupDefineService;
    @Autowired
    private DesignFormulaDefineService designFormulaDefineService;
    @Autowired
    private DesignFormulaVariableDefineService designFormulaVariableDefineService;
    @Value(value="${jiuqi.nr.task.validation.allow-duplicate-titles:false}")
    private boolean allowDuplicateTitles;

    @Override
    public void checkTaskTitle(DesignTaskDefine taskDefine) {
        if (this.allowDuplicateTitles) {
            return;
        }
        boolean flag = false;
        try {
            List<DesignTaskDefine> designTaskDefines = this.designTaskDefineService.queryAllTaskDefine();
            flag = designTaskDefines.stream().filter(t -> !t.getKey().equals(taskDefine.getKey())).anyMatch(t -> t.getTitle().equals(taskDefine.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(taskDefine.getTitle());
        }
    }

    @Override
    public void checkTaskCode(DesignTaskDefine taskDefine) {
        boolean flag = false;
        try {
            List<DesignTaskDefine> taskDefines = this.designTaskDefineService.queryAllTaskDefine();
            flag = taskDefines.stream().filter(t -> !t.getKey().equals(taskDefine.getKey())).anyMatch(t -> t.getTaskCode().equals(taskDefine.getTaskCode()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignCodeCheckException(taskDefine.getTaskCode());
        }
    }

    @Override
    public void checkFormTitle(DesignFormDefine formDefine) {
        if (!StringUtils.hasLength(formDefine.getTitle())) {
            throw new DesignTitleCheckException(formDefine.getTitle());
        }
        String reg = "^.*[\\\\\\/\\?\\\uff1f\\*\\[\\]]+.*$";
        if (Pattern.compile(reg).matcher(formDefine.getTitle()).matches()) {
            throw new DesignTitleCheckException(formDefine.getTitle());
        }
    }

    @Override
    public void checkFormCode(DesignFormDefine formDefine) {
        DesignFormDefine item = this.designFormDefineService.querySoftFormDefineBySchenme(formDefine.getFormScheme(), formDefine.getFormCode());
        if (null != item && !item.getKey().equals(formDefine.getKey())) {
            throw new DesignCodeCheckException(formDefine.getFormCode());
        }
    }

    @Override
    public void checkFormTitleAndCode(DesignFormDefine formDefine) {
        this.checkFormCode(formDefine);
        this.checkFormTitle(formDefine);
    }

    @Override
    public void checkFormScheme(DesignFormSchemeDefine formSchemeDefine) {
        boolean flag = false;
        try {
            List<DesignFormSchemeDefine> designFormSchemeDefines = this.designFormSchemeDefineService.queryFormSchemeDefineByTaskKey(formSchemeDefine.getTaskKey());
            flag = designFormSchemeDefines.stream().filter(s -> !s.getKey().equals(formSchemeDefine.getKey())).anyMatch(s -> s.getTitle().equals(formSchemeDefine.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(formSchemeDefine.getTitle());
        }
    }

    @Override
    public void checkFormSchemeCode(DesignFormSchemeDefine formSchemeDefine) {
        boolean flag = false;
        try {
            List<DesignFormSchemeDefine> formSchemeDefines = this.designFormSchemeDefineService.queryAllFormSchemeDefine();
            flag = formSchemeDefines.stream().filter(t -> !t.getKey().equals(formSchemeDefine.getKey())).anyMatch(t -> t.getFormSchemeCode().equals(formSchemeDefine.getFormSchemeCode()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignCodeCheckException(formSchemeDefine.getFormSchemeCode());
        }
    }

    @Override
    public void checkFormGroup(DesignFormGroupDefine formGroup) {
        boolean flag = false;
        try {
            List<DesignFormGroupDefine> designFormGroupDefines = this.designFormGroupDefineService.queryFormGroupDefinesByScheme(formGroup.getFormSchemeKey());
            flag = designFormGroupDefines.stream().filter(g -> !g.getKey().equals(formGroup.getKey())).anyMatch(g -> g.getTitle().equals(formGroup.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(formGroup.getTitle());
        }
    }

    @Override
    public void checkFormulaSchemeName(DesignFormulaSchemeDefine formulaSchemeDefine) {
        boolean flag = false;
        try {
            List<DesignFormulaSchemeDefine> designFormulaSchemeDefines = this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formulaSchemeDefine.getFormSchemeKey());
            flag = designFormulaSchemeDefines.stream().filter(f -> !f.getKey().equals(formulaSchemeDefine.getKey())).anyMatch(f -> f.getTitle().equals(formulaSchemeDefine.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(formulaSchemeDefine.getTitle());
        }
    }

    @Override
    public void checkPrintTemplateScheme(DesignPrintTemplateSchemeDefine printTemplateSchemeDefine) {
        boolean flag = false;
        try {
            List<DesignPrintTemplateSchemeDefine> allPrintSchemeByFormScheme = this.designPrintTemplateSchemeDefineService.getAllPrintSchemeByFormScheme(printTemplateSchemeDefine.getFormSchemeKey());
            flag = allPrintSchemeByFormScheme.stream().filter(p -> !p.getKey().equals(printTemplateSchemeDefine.getKey())).anyMatch(p -> p.getTitle().equals(printTemplateSchemeDefine.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(printTemplateSchemeDefine.getTitle());
        }
    }

    @Override
    public void checkTaskGroupTitle(DesignTaskGroupDefine taskGroup) {
        boolean flag = false;
        try {
            List<DesignTaskGroupDefine> designTaskGroupDefines = this.designTaskGroupDefineService.queryAllTaskGroupDefine();
            Stream<DesignTaskGroupDefine> stream = !StringUtils.hasLength(taskGroup.getParentKey()) ? designTaskGroupDefines.stream().filter(t -> !StringUtils.hasLength(t.getParentKey())) : designTaskGroupDefines.stream().filter(t -> t.getParentKey().equals(taskGroup.getParentKey()));
            flag = stream.filter(t -> !t.getKey().equals(taskGroup.getKey())).anyMatch(t -> t.getTitle().equals(taskGroup.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(taskGroup.getTitle());
        }
    }

    @Override
    public void checkFormulaSchemeNameByType(DesignFormulaSchemeDefine formulaSchemeDefine) {
        boolean flag = false;
        try {
            List<DesignFormulaSchemeDefine> designFormulaSchemeDefines = this.designFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formulaSchemeDefine.getFormSchemeKey());
            flag = designFormulaSchemeDefines.stream().filter(f -> !f.getKey().equals(formulaSchemeDefine.getKey()) && f.getFormulaSchemeType().equals((Object)formulaSchemeDefine.getFormulaSchemeType())).anyMatch(f -> f.getTitle().equals(formulaSchemeDefine.getTitle()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(formulaSchemeDefine.getTitle());
        }
    }

    @Override
    public void checkFormulaCode(DesignFormulaDefine[] formulaDefines) {
        try {
            HashSet<String> formulaSchemeKeySet = new HashSet<String>();
            HashMap checkFormulaMap = new HashMap();
            HashMap<String, List<DesignFormulaDefine>> dbFormulaMap = new HashMap<String, List<DesignFormulaDefine>>();
            for (DesignFormulaDefine formulaDefine : formulaDefines) {
                formulaSchemeKeySet.add(formulaDefine.getFormulaSchemeKey());
                if (checkFormulaMap.get(formulaDefine.getFormulaSchemeKey()) == null) {
                    checkFormulaMap.put(formulaDefine.getFormulaSchemeKey(), new ArrayList());
                    ((List)checkFormulaMap.get(formulaDefine.getFormulaSchemeKey())).add(formulaDefine);
                    continue;
                }
                ((List)checkFormulaMap.get(formulaDefine.getFormulaSchemeKey())).add(formulaDefine);
            }
            for (String formulaSchemeKey : formulaSchemeKeySet) {
                List<DesignFormulaDefine> formulaDefines1 = this.designFormulaDefineService.listFormulaDefineByScheme(formulaSchemeKey);
                dbFormulaMap.put(formulaSchemeKey, formulaDefines1);
            }
            for (String formulaSchemeKey : formulaSchemeKeySet) {
                Object s2;
                Map<String, String> checkFormulas = ((List)checkFormulaMap.get(formulaSchemeKey)).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, FormulaDefine::getCode));
                Map<String, String> dbFormulas = ((List)dbFormulaMap.get(formulaSchemeKey)).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, FormulaDefine::getCode));
                Map<String, DesignFormulaDefine> checkFormulaDefines = ((List)checkFormulaMap.get(formulaSchemeKey)).stream().collect(Collectors.toMap(IBaseMetaItem::getKey, e -> e));
                HashSet<String> isUpdate = new HashSet<String>();
                HashSet<String> isAdd = new HashSet<String>();
                for (String string : checkFormulas.keySet()) {
                    if (dbFormulas.get(string) == null) {
                        isAdd.add(string);
                        continue;
                    }
                    isUpdate.add(string);
                }
                if (!isAdd.isEmpty()) {
                    HashSet<String> strings = new HashSet<String>(dbFormulas.values());
                    for (Object s2 : isAdd) {
                        if (strings.add(checkFormulas.get(s2))) continue;
                        throw new DesignTitleCheckException(checkFormulas.get(s2));
                    }
                }
                if (isUpdate.isEmpty()) continue;
                HashSet<String> formKeys = new HashSet<String>();
                for (Object s2 : isUpdate) {
                    if (null == checkFormulaDefines.get(s2)) continue;
                    formKeys.add(checkFormulaDefines.get(s2).getFormKey());
                }
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                for (String s2 : dbFormulas.keySet()) {
                    hashMap.put(dbFormulas.get(s2), 1);
                }
                for (String s3 : isUpdate) {
                    if (checkFormulas.get(s3).equals(dbFormulas.get(s3))) continue;
                    if (hashMap.get(checkFormulas.get(s3)) == null) {
                        hashMap.put(dbFormulas.get(s3), (Integer)hashMap.get(dbFormulas.get(s3)) - 1);
                        hashMap.put(checkFormulas.get(s3), 1);
                        continue;
                    }
                    hashMap.put(dbFormulas.get(s3), (Integer)hashMap.get(dbFormulas.get(s3)) - 1);
                    hashMap.put(checkFormulas.get(s3), (Integer)hashMap.get(checkFormulas.get(s3)) + 1);
                }
                s2 = hashMap.keySet().iterator();
                while (s2.hasNext()) {
                    String s3;
                    s3 = (String)s2.next();
                    if ((Integer)hashMap.get(s3) <= 1 && (Integer)hashMap.get(s3) >= 0) continue;
                    throw new DesignTitleCheckException(s3);
                }
                List designFormulaDefines = (List)dbFormulaMap.get(formulaSchemeKey);
                HashMap<String, String> dbFormulasByForms = new HashMap<String, String>();
                for (DesignFormulaDefine designFormulaDefine : designFormulaDefines) {
                    if (!formKeys.contains(designFormulaDefine.getFormKey())) continue;
                    dbFormulasByForms.put(designFormulaDefine.getKey(), designFormulaDefine.getCode());
                }
                HashMap<Object, Integer> countMap = new HashMap<Object, Integer>();
                for (String s3 : dbFormulasByForms.keySet()) {
                    if (countMap.get(dbFormulasByForms.get(s3)) == null) {
                        countMap.put(dbFormulasByForms.get(s3), 1);
                        continue;
                    }
                    countMap.put(dbFormulasByForms.get(s3), (Integer)countMap.get(dbFormulasByForms.get(s3)) + 1);
                }
                for (String s4 : isUpdate) {
                    if (checkFormulas.get(s4).equals(dbFormulasByForms.get(s4))) continue;
                    if (countMap.get(checkFormulas.get(s4)) == null) {
                        countMap.put(dbFormulasByForms.get(s4), (Integer)countMap.get(dbFormulasByForms.get(s4)) - 1);
                        countMap.put(checkFormulas.get(s4), 1);
                        continue;
                    }
                    countMap.put(dbFormulasByForms.get(s4), (Integer)countMap.get(dbFormulasByForms.get(s4)) - 1);
                    countMap.put(checkFormulas.get(s4), (Integer)countMap.get(checkFormulas.get(s4)) + 1);
                }
                for (String s4 : countMap.keySet()) {
                    if ((Integer)countMap.get(s4) <= 1 && (Integer)countMap.get(s4) >= 0) continue;
                    throw new DesignTitleCheckException(s4);
                }
            }
        }
        catch (DesignTitleCheckException e2) {
            throw e2;
        }
        catch (Exception e3) {
            throw new DesignTitleCheckException("\u516c\u5f0f\u67e5\u8be2\u5931\u8d25", e3);
        }
    }

    @Override
    public void checkFormulaVariable(FormulaVariDefine formulaVariDefine) {
        boolean flag = false;
        try {
            List<FormulaVariDefine> formulaVariDefines = this.designFormulaVariableDefineService.queryAllFormulaVariable(formulaVariDefine.getFormSchemeKey());
            flag = formulaVariDefines.stream().filter(f -> !f.getKey().equals(formulaVariDefine.getKey())).anyMatch(f -> f.getCode().equals(formulaVariDefine.getCode()));
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        if (flag) {
            throw new DesignTitleCheckException(formulaVariDefine.getCode());
        }
    }
}

