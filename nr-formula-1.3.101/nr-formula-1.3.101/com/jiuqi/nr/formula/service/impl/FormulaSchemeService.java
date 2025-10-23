/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeExtFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.deploy.common.ParamDeployException
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.formula.FormulaConditionLink
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.formula.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeExtFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.formula.dto.FormulaSchemeDTO;
import com.jiuqi.nr.formula.dto.ItemOrderMoveDTO;
import com.jiuqi.nr.formula.exception.FormulaSchemeException;
import com.jiuqi.nr.formula.exception.NrTaskFormulaException;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.formula.service.IPublishCheckService;
import com.jiuqi.nr.formula.utils.convert.FormulaSchemeConvert;
import com.jiuqi.util.OrderGenerator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class FormulaSchemeService
implements IFormulaSchemeService {
    private static final Logger log = LoggerFactory.getLogger(FormulaSchemeService.class);
    @Autowired
    private IDesignTimeFormulaController formulaDesignTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IParamDeployController paramDeployController;
    @Autowired
    protected IPublishCheckService publishCheckService;
    @Autowired
    private IDesignTimeExtFormulaController extFormulaDesignTimeController;

    @Override
    public void insertFormulaScheme(FormulaSchemeDTO formulaSchemeDTO) {
        List defines;
        boolean existDefault;
        formulaSchemeDTO.setUpdateTime(new Date());
        formulaSchemeDTO.setKey(UUIDUtils.getKey());
        formulaSchemeDTO.setOrder(OrderGenerator.newOrder());
        DesignFormulaSchemeDefine designFormulaSchemeDefine = FormulaSchemeConvert.dtoToDefine(formulaSchemeDTO, this.formulaDesignTimeController);
        if (designFormulaSchemeDefine.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL && !(existDefault = (defines = this.formulaDesignTimeController.listFormulaSchemeByFormSchemeAndType(formulaSchemeDTO.getFormSchemeKey(), FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)).stream().anyMatch(FormulaSchemeDefine::isDefault))) {
            designFormulaSchemeDefine.setDefault(true);
        }
        this.formulaDesignTimeController.insertFormulaScheme(designFormulaSchemeDefine);
    }

    @Override
    public void insertFormulaSchemes(List<FormulaSchemeDTO> formulaSchemeDTOList) {
        DesignFormulaSchemeDefine[] schemeDefines = FormulaSchemeConvert.dtoToDefines(formulaSchemeDTOList, this.formulaDesignTimeController);
    }

    @Override
    public void updateFormulaScheme(FormulaSchemeDTO formulaSchemeDTO) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeDTO.getKey());
        formulaScheme.setTitle(formulaSchemeDTO.getTitle());
        formulaScheme.setDisplayMode(formulaSchemeDTO.getDisplayMode());
        formulaScheme.setShow(formulaSchemeDTO.getShowScheme().booleanValue());
        formulaScheme.setFormulaSchemeMenuApply(formulaSchemeDTO.getMenuApply());
        this.formulaDesignTimeController.updateFormulaScheme(formulaScheme);
    }

    @Override
    public void updateFormulaSchemes(List<FormulaSchemeDTO> formulaSchemeDTOList) {
    }

    @Override
    public void deleteFormulaScheme(String key) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(key);
        if (formulaScheme.isDefault()) {
            List defineList = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(formulaScheme.getFormSchemeKey());
            Optional<DesignFormulaSchemeDefine> findFirst = defineList.stream().filter(e -> !e.isDefault()).min(Comparator.comparing(IBaseMetaItem::getOrder));
            if (findFirst.isPresent()) {
                DesignFormulaSchemeDefine schemeDefine = findFirst.get();
                schemeDefine.setDefault(true);
                schemeDefine.setShow(true);
                this.formulaDesignTimeController.updateFormulaScheme(schemeDefine);
            } else {
                throw new RuntimeException("\u4e0d\u5141\u8bb8\u5220\u9664\u6240\u6709\u516c\u5f0f\u65b9\u6848");
            }
        }
        this.formulaDesignTimeController.deleteFormulaScheme(new String[]{key});
    }

    @Override
    public void deleteFormulaSchemes(List<String> keys) {
        this.formulaDesignTimeController.deleteFormulaScheme(keys.toArray(new String[0]));
    }

    @Override
    public FormulaSchemeDTO getFormulaScheme(String key) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(key);
        return FormulaSchemeConvert.defineToDto(formulaScheme);
    }

    @Override
    public List<FormulaSchemeDTO> listFormulaSchemeByFormScheme(String formSchemeKey) {
        List designFormulaSchemeDefines = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(formSchemeKey);
        return FormulaSchemeConvert.defineToDtos(designFormulaSchemeDefines);
    }

    @Override
    public List<FormulaSchemeDTO> listReportFormulaSchemeByFormScheme(String formSchemeKey) {
        return this.listFormulaSchemeByFormScheme(formSchemeKey).stream().filter(x -> x.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT)).collect(Collectors.toList());
    }

    @Override
    public List<FormulaSchemeDTO> allFormulaSchemes() {
        return FormulaSchemeConvert.defineToDtos(this.formulaDesignTimeController.listAllFormulaScheme());
    }

    @Override
    public List<FormulaSchemeDTO> listEFDCFormulaSchemeByFormScheme(String formSchemeKey) {
        return this.listFormulaSchemeByFormScheme(formSchemeKey).stream().filter(f -> f.getFormulaSchemeType().equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL)).collect(Collectors.toList());
    }

    @Override
    public FormulaSchemeDTO getEFDCFormulaScheme(String key) {
        return null;
    }

    @Override
    public void publishFormulaScheme(String formulaSchemeKey) {
        if (!this.publishCheckService.canFormulaSchemeEdit(formulaSchemeKey)) {
            throw new NrTaskFormulaException("\u4efb\u52a1\u6b63\u5728\u53d1\u5e03");
        }
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formulaScheme.getFormSchemeKey());
        if (formScheme == null) {
            throw new NrTaskFormulaException("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u672a\u53d1\u5e03");
        }
        try {
            this.paramDeployController.deploy(ParamResourceType.FORMULA, formulaSchemeKey, Collections.emptyList());
        }
        catch (ParamDeployException e) {
            throw e;
        }
        catch (Exception e) {
            throw new FormulaSchemeException(e.getMessage(), e);
        }
    }

    @Override
    public void publishFormulaScheme(String formulaSchemeKey, String formKey) {
    }

    @Override
    public void moveFormulaScheme(ItemOrderMoveDTO moveDTO) {
        List schemeDefines = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(moveDTO.getFormSchemeKey());
        String sourceKey = moveDTO.getSourceKey();
        int way = moveDTO.getWay();
        DesignFormulaSchemeDefine sourceDefine = null;
        DesignFormulaSchemeDefine targetDefine = null;
        for (int i = 0; i < schemeDefines.size(); ++i) {
            sourceDefine = (DesignFormulaSchemeDefine)schemeDefines.get(i);
            if (!sourceDefine.getKey().equals(sourceKey)) continue;
            if (way == 0 && i > 0) {
                targetDefine = (DesignFormulaSchemeDefine)schemeDefines.get(i - 1);
                break;
            }
            if (way == 1 && i < schemeDefines.size()) {
                targetDefine = (DesignFormulaSchemeDefine)schemeDefines.get(i + 1);
                break;
            }
            throw new FormulaSchemeException("\u79fb\u52a8\u65b9\u5f0f\u672a\u77e5\uff01");
        }
        if (targetDefine == null) {
            throw new FormulaSchemeException("\u79fb\u52a8\u65b9\u5f0f\u672a\u77e5\uff01");
        }
        String temp = targetDefine.getOrder();
        targetDefine.setOrder(sourceDefine.getOrder());
        sourceDefine.setOrder(temp);
        this.formulaDesignTimeController.updateFormulaScheme(targetDefine);
        this.formulaDesignTimeController.updateFormulaScheme(sourceDefine);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void copyFormulaScheme(FormulaSchemeDTO formulaSchemeDTO) {
        Assert.notNull((Object)formulaSchemeDTO.getKey(), "\u88ab\u590d\u5236\u7684\u516c\u5f0f\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)formulaSchemeDTO.getTitle(), "\u65b0\u516c\u5f0f\u65b9\u6848\u7684\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a");
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeDTO.getKey());
        if (formulaScheme == null) {
            throw new RuntimeException("\u88ab\u590d\u5236\u7684\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        DesignFormulaSchemeDefine newFormulaScheme = this.formulaDesignTimeController.initFormulaScheme();
        newFormulaScheme.setDefault(false);
        newFormulaScheme.setShow(formulaSchemeDTO.getShowScheme().booleanValue());
        newFormulaScheme.setTitle(formulaSchemeDTO.getTitle());
        newFormulaScheme.setDescription(formulaScheme.getDescription());
        newFormulaScheme.setDisplayMode(formulaSchemeDTO.getDisplayMode());
        newFormulaScheme.setVersion(formulaScheme.getVersion());
        newFormulaScheme.setFormSchemeKey(formulaScheme.getFormSchemeKey());
        newFormulaScheme.setOwnerLevelAndId(formulaScheme.getOwnerLevelAndId());
        newFormulaScheme.setFormulaSchemeType(formulaScheme.getFormulaSchemeType());
        this.formulaDesignTimeController.insertFormulaScheme(newFormulaScheme);
        List formulaDefines = this.formulaDesignTimeController.listFormulaByScheme(formulaScheme.getKey());
        List conditionLinks = this.formulaDesignTimeController.listFormulaConditionLinkByScheme(formulaScheme.getKey());
        Map<String, List<DesignFormulaConditionLink>> conditionMap = conditionLinks.stream().collect(Collectors.groupingBy(FormulaConditionLink::getFormulaKey));
        for (DesignFormulaDefine formulaDefine : formulaDefines) {
            String newKey = UUIDUtils.getKey();
            List<DesignFormulaConditionLink> condition = conditionMap.get(formulaDefine.getKey());
            if (!CollectionUtils.isEmpty(condition)) {
                for (DesignFormulaConditionLink conditionLink : condition) {
                    conditionLink.setFormulaKey(newKey);
                    conditionLink.setFormulaSchemeKey(newFormulaScheme.getKey());
                }
            }
            formulaDefine.setKey(newKey);
            formulaDefine.setFormulaSchemeKey(newFormulaScheme.getKey());
        }
        this.formulaDesignTimeController.insertFormula(formulaDefines.toArray(new DesignFormulaDefine[0]));
        List extFormula = this.extFormulaDesignTimeController.listFormulaByScheme(formulaScheme.getKey());
        for (DesignFormulaDefine formulaDefine : extFormula) {
            formulaDefine.setKey(UUIDUtils.getKey());
            formulaDefine.setFormulaSchemeKey(newFormulaScheme.getKey());
        }
        this.extFormulaDesignTimeController.insertExtFormula(extFormula.toArray(new DesignFormulaDefine[0]));
        this.formulaDesignTimeController.insertFormulaConditionLinks(conditionLinks);
    }

    @Override
    public void insertDefaultFormulaScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "\u62a5\u8868\u65b9\u6848KEY\u4e0d\u80fd\u4e3a\u7a7a");
        this.checkExist(formSchemeKey);
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaDesignTimeController.initFormulaScheme();
        designFormulaSchemeDefine.setTitle("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848");
        designFormulaSchemeDefine.setFormSchemeKey(formSchemeKey);
        designFormulaSchemeDefine.setDefault(true);
        designFormulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT);
        this.formulaDesignTimeController.insertFormulaScheme(designFormulaSchemeDefine);
    }

    private void checkExist(String formSchemeKey) {
        List schemeDefines = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(formSchemeKey);
        boolean b = schemeDefines.stream().anyMatch(FormulaSchemeDefine::isDefault);
        if (b) {
            throw new FormulaSchemeException("\u5df2\u5b58\u5728\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848");
        }
    }

    @Override
    public synchronized void defaultFormulaScheme(String formulaSchemeKey) {
        DesignFormulaSchemeDefine formulaScheme = this.formulaDesignTimeController.getFormulaScheme(formulaSchemeKey);
        formulaScheme.setDefault(true);
        List designFormulaSchemeDefines = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(formulaScheme.getFormSchemeKey());
        designFormulaSchemeDefines.forEach(x -> {
            if (formulaScheme.getFormulaSchemeType().equals((Object)x.getFormulaSchemeType()) && x.isDefault()) {
                x.setDefault(false);
                this.formulaDesignTimeController.updateFormulaScheme(x);
            }
        });
        this.formulaDesignTimeController.updateFormulaScheme(formulaScheme);
    }

    @Override
    public boolean checkFormulaScheme(FormulaSchemeDTO formulaSchemeDTO) {
        if (formulaSchemeDTO.getKey() == null) {
            formulaSchemeDTO.setKey(UUIDUtils.getKey());
        }
        try {
            List designFormulaSchemeDefines = this.formulaDesignTimeController.listFormulaSchemeByFormScheme(formulaSchemeDTO.getFormSchemeKey());
            return designFormulaSchemeDefines.stream().filter(e -> e.getFormulaSchemeType().equals((Object)formulaSchemeDTO.getFormulaSchemeType())).anyMatch(e -> e.getTitle().equalsIgnoreCase(formulaSchemeDTO.getTitle()) && !e.getKey().equalsIgnoreCase(formulaSchemeDTO.getKey()));
        }
        catch (Exception e2) {
            throw new FormulaSchemeException(e2.getMessage());
        }
    }
}

