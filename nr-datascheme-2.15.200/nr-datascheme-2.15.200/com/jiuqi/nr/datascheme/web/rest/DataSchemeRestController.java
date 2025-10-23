/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserManagerService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeNode
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeValidator
 *  com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.zb.scheme.core.ZbScheme
 *  com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion
 *  com.jiuqi.nr.zb.scheme.service.IZbSchemeService
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricSceneManager
 *  com.jiuqi.nvwa.encryption.crypto.domain.po.Scene
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserManagerService;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.adjustment.service.AdjustPeriodDesignService;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DataSchemeNode;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.DataSchemeValidator;
import com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDesignDTO;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;
import com.jiuqi.nr.datascheme.web.base.EntityUtil;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.DataSchemeCheckPropVO;
import com.jiuqi.nr.datascheme.web.facade.DataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.DesignContextVO;
import com.jiuqi.nr.datascheme.web.facade.SysOptionVO;
import com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO;
import com.jiuqi.nr.datascheme.web.param.AuthQueryPM;
import com.jiuqi.nr.datascheme.web.param.CopyNodeVO;
import com.jiuqi.nr.datascheme.web.param.MoveSchemeVO;
import com.jiuqi.nr.datascheme.web.param.ReportDimVO;
import com.jiuqi.nr.datascheme.web.param.ShowFieldVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.zb.scheme.core.ZbScheme;
import com.jiuqi.nr.zb.scheme.core.ZbSchemeVersion;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.encryption.crypto.SymmetricSceneManager;
import com.jiuqi.nvwa.encryption.crypto.domain.po.Scene;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u6570\u636e\u65b9\u6848\u670d\u52a1"})
public class DataSchemeRestController {
    private final Logger logger = LoggerFactory.getLogger(DataSchemeRestController.class);
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataSchemeValidator dataSchemeValidator;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private AdjustPeriodDesignService adjustmentPeriodInternalService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private IDataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    private IZbSchemeService zbSchemeController;
    @Autowired
    private SymmetricSceneManager symmetricSceneManager;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private SystemIdentityService systemIdentityService;

    @ApiOperation(value="\u6309\u5206\u7ec4\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"scheme/query/group/{key}"})
    public List<BaseDataSchemeVO> queryBaseDataSchemesByGroup(@PathVariable String key) {
        List allDataScheme = this.designDataSchemeService.getDataSchemeByParent(key);
        ArrayList<BaseDataSchemeVO> result = new ArrayList<BaseDataSchemeVO>();
        if (null != allDataScheme && !allDataScheme.isEmpty()) {
            List keys = allDataScheme.stream().map(Basic::getKey).collect(Collectors.toList());
            Map deployStatusMap = this.runtimeDataSchemeService.getDeployStatus(keys);
            HashMap<String, String> zbSchemeTitleMap = new HashMap<String, String>();
            for (DesignDataScheme dataScheme : allDataScheme) {
                if (!this.dataSchemeAuthService.canReadScheme(dataScheme.getKey())) continue;
                BaseDataSchemeVO vo = EntityUtil.schemeEntity2VO(new BaseDataSchemeVO(), dataScheme);
                DataSchemeDeployStatus status = (DataSchemeDeployStatus)deployStatusMap.get(dataScheme.getKey());
                if (null != status) {
                    vo.setDeployState(status.getDeployStatus().getValue());
                } else {
                    vo.setDeployState(DeployStatusEnum.NEVER_DEPLOY.getValue());
                }
                User user = this.userManagerService.getByUsername(dataScheme.getCreator());
                vo.setCreatorTitle(null != user ? user.getNickname() : "Admin");
                this.setZbSchemeParam(vo, zbSchemeTitleMap);
                result.add(vo);
            }
        }
        return result;
    }

    private void setZbSchemeParam(DataSchemeVO vo) {
        if (vo.getZbSchemeKey() != null) {
            ZbSchemeVersion zbSchemeVersion;
            ZbScheme zbScheme = this.zbSchemeController.getZbScheme(vo.getZbSchemeKey());
            if (zbScheme != null) {
                vo.setZbSchemeTitle(zbScheme.getTitle());
            }
            if ((zbSchemeVersion = this.zbSchemeController.getZbSchemeVersion(vo.getZbSchemeVersion())) != null) {
                vo.setZbSchemePeriod(zbSchemeVersion.getStartPeriod());
                vo.setZbSchemePeriodTitle(zbSchemeVersion.getTitle());
                vo.setZbSchemeVersionStatus(zbSchemeVersion.getStatus().getValue());
            }
        }
    }

    private void setZbSchemeParam(BaseDataSchemeVO vo, Map<String, String> zbSchemeTitleMap) {
        if (vo.getZbSchemeKey() != null) {
            if (zbSchemeTitleMap.containsKey(vo.getZbSchemeKey())) {
                vo.setZbSchemeTitle(zbSchemeTitleMap.get(vo.getZbSchemeKey()));
            } else {
                ZbScheme zbScheme = this.zbSchemeController.getZbScheme(vo.getZbSchemeKey());
                if (zbScheme != null) {
                    vo.setZbSchemeTitle(zbScheme.getTitle());
                    zbSchemeTitleMap.put(zbScheme.getKey(), zbScheme.getTitle());
                }
            }
            ZbSchemeVersion zbSchemeVersion = this.zbSchemeController.getZbSchemeVersion(vo.getZbSchemeVersion());
            if (zbSchemeVersion != null) {
                vo.setZbSchemePeriod(zbSchemeVersion.getStartPeriod());
                vo.setZbSchemePeriodTitle(zbSchemeVersion.getTitle());
                vo.setZbSchemeVersionStatus(zbSchemeVersion.getStatus().getValue());
            }
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u65b9\u6848\u5217\u8868")
    @GetMapping(value={"scheme/query-all"})
    public List<BaseDataSchemeVO> queryBaseDataSchemes() {
        List allDataScheme = this.designDataSchemeService.getAllDataScheme();
        return allDataScheme.stream().filter(s -> this.dataSchemeAuthService.canReadScheme(s.getKey())).map(v -> EntityUtil.schemeEntity2VO(new BaseDataSchemeVO(), v)).collect(Collectors.toList());
    }

    @ApiOperation(value="\u6309\u4e3b\u952e\u67e5\u8be2\u6570\u636e\u65b9\u6848")
    @GetMapping(value={"scheme/query/{key}"})
    @Transactional(rollbackFor={Exception.class})
    public DataSchemeVO queryDataScheme(@PathVariable String key) throws JQException {
        if (!this.dataSchemeAuthService.canReadScheme(key)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(key);
        if (dataScheme == null) {
            return null;
        }
        List dims = this.designDataSchemeService.getDataSchemeDimension(key);
        DataSchemeVO vo = EntityUtil.schemeEntity2VO(new DataSchemeVO(), dataScheme);
        if (dims != null) {
            dims.stream().filter(x -> !"ADJUST".equals(x.getDimKey())).forEach(v -> {
                if (v.getDimensionType() == DimensionType.UNIT) {
                    vo.setOrgDimKey(v.getDimKey());
                } else if (v.getDimensionType() == DimensionType.PERIOD) {
                    vo.setPeriodDimKey(v.getDimKey());
                    vo.setPeriodType(null != v.getPeriodType() ? v.getPeriodType().type() : 0);
                } else if (v.getDimensionType() == DimensionType.DIMENSION) {
                    List<String> dimKeys = vo.getDimKeys();
                    if (dimKeys == null) {
                        dimKeys = new ArrayList<String>();
                        vo.setDimKeys(dimKeys);
                    }
                    dimKeys.add(v.getDimKey());
                } else if (v.getDimensionType() == DimensionType.UNIT_SCOPE) {
                    List<String> orgDimScope = vo.getOrgDimScope();
                    if (orgDimScope == null) {
                        orgDimScope = new ArrayList<String>();
                        vo.setOrgDimScope(orgDimScope);
                    }
                    orgDimScope.add(v.getDimKey());
                }
            });
            this.createReportDimVO(vo, dims);
        }
        vo.setEnableAdjustmentPeriod(this.enableAdjustmentPeriod(vo.getKey()));
        vo.setAdjustPeriodVOList(this.getAdjustmentPeriods(vo.getKey()));
        DataScheme runtimeDataScheme = this.runtimeDataSchemeService.getDataScheme(vo.getKey());
        vo.setDeployed(null != runtimeDataScheme);
        this.setZbSchemeParam(vo);
        return vo;
    }

    private void createReportDimVO(DataSchemeVO vo, List<DesignDataDimension> dims) {
        Optional<DesignDataDimension> first = dims.stream().filter(x -> x.getDimensionType() == DimensionType.UNIT).findFirst();
        if (first.isPresent()) {
            Map<String, ReportDimVO> reportDimMap = vo.getReportDimMap();
            DesignDataDimension unit = first.get();
            IEntityModel entityModel = this.entityMetaService.getEntityModel(unit.getDimKey());
            List entityRefers = this.entityMetaService.getEntityRefer(unit.getDimKey());
            if (Objects.isNull(entityModel) || Objects.isNull(entityRefers)) {
                return;
            }
            List entityAttributeList = entityModel.getShowFields();
            HashSet exclude = new HashSet();
            Collections.addAll(exclude, entityModel.getCodeField().getCode(), entityModel.getNameField().getCode(), entityModel.getParentField().getCode(), "SHORTNAME");
            List showFields = entityAttributeList.stream().filter(x -> !exclude.contains(x.getCode())).map(ShowFieldVO::convert).collect(Collectors.toList());
            dims.stream().filter(x -> AdjustUtils.isAdjust(x.getDimKey()) == false && x.getDimensionType() == DimensionType.DIMENSION).forEach(x -> {
                IEntityRefer entityRefer;
                ReportDimVO reportDimVO = ReportDimVO.convertToVO(x);
                reportDimVO.setShowFields(showFields);
                if (!CollectionUtils.isEmpty(entityRefers) && Objects.nonNull(entityRefer = (IEntityRefer)entityRefers.stream().filter(y -> y.getReferEntityId().equals(x.getDimKey())).findFirst().orElse(null))) {
                    reportDimVO.setDefaultDimAttribute(entityRefer.getOwnField());
                }
                reportDimMap.put(x.getDimKey(), reportDimVO);
            });
        }
    }

    private List<AdjustPeriodVO> getAdjustmentPeriods(String key) {
        List<DesignAdjustPeriodDTO> query = this.adjustmentPeriodInternalService.query(key);
        return query.stream().filter(x -> AdjustUtils.isNotAdjustData(x) == false).sorted(Comparator.comparing(Ordered::getOrder, String::compareTo)).filter(adjustPeriod -> StringUtils.hasLength(adjustPeriod.getPeriod())).map(AdjustPeriodVO::convertToVO).collect(Collectors.toList());
    }

    private boolean enableAdjustmentPeriod(String key) {
        if (StringUtils.hasLength(key)) {
            return this.designDataSchemeService.enableAdjustPeriod(key);
        }
        return false;
    }

    @ApiOperation(value="\u65b0\u589e\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"scheme/add"})
    @Transactional(rollbackFor={Exception.class})
    public String addDataScheme(@RequestBody DataSchemeVO schemeObj) throws JQException {
        if (!this.dataSchemeAuthService.canWriteGroup(schemeObj.getDataGroupKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u65b0\u589e\u6570\u636e\u65b9\u6848\uff1a{}[{}]\u3002", (Object)schemeObj.getTitle(), (Object)schemeObj.getCode());
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity(this.designDataSchemeService, schemeObj, null);
        if (!StringUtils.hasLength(schemeObj.getOrgDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u5355\u4f4d\u7ef4\u5ea6.");
        }
        if (!StringUtils.hasLength(schemeObj.getPeriodDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u65f6\u671f\u7ef4\u5ea6.");
        }
        List<DesignDataDimension> dimensions = this.buildDimsByVO(schemeObj);
        this.solutionAdjustPeriod(schemeObj, dimensions);
        this.saveAdjustData(schemeObj.getKey(), schemeObj.getAdjustPeriodVOList());
        this.solutionCalibre(schemeObj, dimensions);
        try {
            this.designDataSchemeService.insertDataScheme(scheme, dimensions);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
        return scheme.getKey();
    }

    private void saveAdjustData(String dataSchemeKey, List<AdjustPeriodVO> adjustPeriodVOList) throws JQException {
        if (CollectionUtils.isEmpty(adjustPeriodVOList)) {
            this.adjustmentPeriodInternalService.deleteByDataScheme(dataSchemeKey);
        } else {
            ArrayList<DesignAdjustPeriodDTO> dtos = new ArrayList<DesignAdjustPeriodDTO>();
            for (AdjustPeriodVO periodVO : adjustPeriodVOList) {
                DesignAdjustPeriodDTO dto = new DesignAdjustPeriodDTO();
                dto.setDataSchemeKey(dataSchemeKey);
                dto.setPeriod(periodVO.getPeriod());
                dto.setCode(periodVO.getCode());
                dto.setOrder(periodVO.getOrder());
                dto.setTitle(periodVO.getTitle());
                dtos.add(dto);
            }
            try {
                this.adjustmentPeriodInternalService.updateAdjust(dtos);
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.ADJUSTMENT_PERIOD_1, e.getMessage());
            }
        }
    }

    @ApiOperation(value="\u5f00\u542f\u8c03\u6574\u671f")
    @GetMapping(value={"data/enable/adjust"})
    public void enableAdjust(@RequestParam String dataSchemeKey) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(dataSchemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        if (StringUtils.hasLength(dataSchemeKey)) {
            DesignDataDimension dim = this.designDataSchemeService.initDataSchemeDimension();
            dim.setDimKey("ADJUST");
            dim.setDimensionType(DimensionType.DIMENSION);
            dim.setReportDim(Boolean.valueOf(true));
            dim.setDataSchemeKey(dataSchemeKey);
            dim.setOrder(OrderGenerator.newOrder());
            try {
                this.designDataSchemeService.addPublicDimToDataScheme(dataSchemeKey, Collections.singleton(dim), Collections.singletonMap(dim.getDimKey(), "0"));
            }
            catch (Exception e) {
                throw new JQException((ErrorEnum)DataSchemeEnum.ADJUSTMENT_PERIOD_2, e.getMessage());
            }
        }
    }

    private void solutionCalibre(DataSchemeVO schemeObj, List<DesignDataDimension> dimensions) throws JQException {
        this.logger.debug("\u662f\u5426\u5f00\u542f\u53e3\u5f84\uff1a{}", (Object)schemeObj.isEnableCalibre());
        if (schemeObj.isEnableCalibre().booleanValue()) {
            DesignDataDimension dim = this.designDataSchemeService.initDataSchemeDimension();
            String calibre = schemeObj.getCalibre();
            List entityRefer = this.entityMetaService.getEntityRefer("MD_ORG@ORG");
            for (IEntityRefer refer : entityRefer) {
                if (!refer.getOwnField().equals(calibre)) continue;
                for (DesignDataDimension dimension : dimensions) {
                    if (!dimension.getDimKey().equals(refer.getReferEntityId())) continue;
                    this.logger.debug("\u5b9e\u4f53\u5173\u8054\u5b9e\u4f53\u4e0e\u53e3\u5f84\u91cd\u590d");
                    throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, "\u53e3\u5f84\u5c5e\u6027\u5173\u8054\u5b9e\u4f53\u4e0e\u60c5\u666f\u91cd\u590d");
                }
                dim.setDimAttribute(schemeObj.getCalibre());
                dim.setDimKey(refer.getReferEntityId());
                dim.setDimensionType(DimensionType.CALIBRE);
                dimensions.add(dim);
                this.logger.debug("\u6dfb\u52a0\u53e3\u5f84\u7ef4\u5ea6\uff1a{}", (Object)dim);
                break;
            }
        }
    }

    private void solutionAdjustPeriod(DataSchemeVO schemeObj, List<DesignDataDimension> dimensions) {
        if (Boolean.TRUE.equals(schemeObj.getEnableAdjustmentPeriod())) {
            DesignDataDimension dim = this.designDataSchemeService.initDataSchemeDimension();
            dim.setDimKey("ADJUST");
            dim.setDimensionType(DimensionType.DIMENSION);
            dim.setReportDim(Boolean.valueOf(true));
            dimensions.add(dim);
        }
    }

    private List<DesignDataDimension> buildDimsByVO(DataSchemeVO schemeObj) {
        DesignDataDimension dim;
        ArrayList<DesignDataDimension> dimensions = new ArrayList<DesignDataDimension>(5);
        DesignDataDimension dimension = this.designDataSchemeService.initDataSchemeDimension();
        dimension.setDimKey(schemeObj.getOrgDimKey());
        dimension.setDimensionType(DimensionType.UNIT);
        dimensions.add(dimension);
        DesignDataDimension period = this.designDataSchemeService.initDataSchemeDimension();
        period.setDimKey(schemeObj.getPeriodDimKey());
        period.setDimensionType(DimensionType.PERIOD);
        period.setPeriodType(PeriodType.fromType((int)schemeObj.getPeriodType()));
        dimensions.add(period);
        if (schemeObj.getDimKeys() != null) {
            for (String v : schemeObj.getDimKeys()) {
                dim = this.designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.DIMENSION);
                this.setReportDimensionData(dim, v, schemeObj);
                dimensions.add(dim);
            }
        }
        if (schemeObj.getOrgDimScope() != null) {
            for (String v : schemeObj.getOrgDimScope()) {
                dim = this.designDataSchemeService.initDataSchemeDimension();
                dim.setDimKey(v);
                dim.setDimensionType(DimensionType.UNIT_SCOPE);
                dimensions.add(dim);
            }
        }
        return dimensions;
    }

    private void setReportDimensionData(DesignDataDimension dim, String v, DataSchemeVO schemeObj) {
        ReportDimVO reportDimVO;
        Map<String, ReportDimVO> reportDimMap = schemeObj.getReportDimMap();
        if (reportDimMap.containsKey(v) && (reportDimVO = reportDimMap.get(v)) != null) {
            dim.setReportDim(reportDimVO.getReportDim());
            dim.setDimAttribute(reportDimVO.getDimAttribute());
        }
    }

    @ApiOperation(value="\u590d\u5236\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"scheme/copy"})
    public void copyDataScheme(@RequestBody DataSchemeVO schemeObj) throws JQException {
        String key = schemeObj.getKey();
        if (!this.dataSchemeAuthService.canReadScheme(key)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u590d\u5236\u6570\u636e\u65b9\u6848\uff1a{}[{}]\u3002", (Object)schemeObj.getTitle(), (Object)schemeObj.getCode());
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity(this.designDataSchemeService, schemeObj, null);
        String newSchemeKey = UUIDUtils.getKey();
        scheme.setKey(newSchemeKey);
        ArrayList<DesignDataDimension> designDataDimensions = null;
        if (schemeObj.getOrgDimKey() != null) {
            DataDimDesignDTO dim;
            designDataDimensions = new ArrayList<DesignDataDimension>();
            DataDimDesignDTO unit = new DataDimDesignDTO();
            unit.setDataSchemeKey(newSchemeKey);
            unit.setDimensionType(DimensionType.UNIT);
            unit.setDimKey(schemeObj.getOrgDimKey());
            designDataDimensions.add(unit);
            DataDimDesignDTO period = new DataDimDesignDTO();
            period.setDataSchemeKey(newSchemeKey);
            period.setDimensionType(DimensionType.PERIOD);
            period.setPeriodType(PeriodType.fromType((int)schemeObj.getPeriodType()));
            period.setDimKey(schemeObj.getPeriodDimKey());
            designDataDimensions.add(period);
            if (null != schemeObj.getDimKeys() && !schemeObj.getDimKeys().isEmpty()) {
                for (String v : schemeObj.getDimKeys()) {
                    dim = new DataDimDesignDTO();
                    dim.setDataSchemeKey(newSchemeKey);
                    dim.setDimKey(v);
                    dim.setDimensionType(DimensionType.DIMENSION);
                    designDataDimensions.add(dim);
                }
            }
            if (null != schemeObj.getOrgDimScope() && !schemeObj.getOrgDimScope().isEmpty()) {
                for (String v : schemeObj.getOrgDimScope()) {
                    dim = new DataDimDesignDTO();
                    dim.setDataSchemeKey(newSchemeKey);
                    dim.setDimKey(v);
                    dim.setDimensionType(DimensionType.UNIT_SCOPE);
                    designDataDimensions.add(dim);
                }
            }
            if (!CollectionUtils.isEmpty(schemeObj.getReportDimMap())) {
                Map<String, ReportDimVO> reportDimMap = schemeObj.getReportDimMap();
                for (DesignDataDimension dimension : designDataDimensions) {
                    ReportDimVO reportDimVO = reportDimMap.get(dimension.getDimKey());
                    if (reportDimVO == null) continue;
                    dimension.setDimAttribute(reportDimVO.getDimAttribute());
                }
            }
            this.solutionAdjustPeriod(schemeObj, designDataDimensions);
            this.saveAdjustData(newSchemeKey, schemeObj.getAdjustPeriodVOList());
        }
        try {
            this.designDataSchemeService.copyDataScheme(key, scheme, designDataDimensions);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u590d\u5236\u6570\u636e\u5206\u7ec4/\u8868")
    @PostMapping(value={"node/copy"})
    public void copyNode(@RequestBody CopyNodeVO copyNodeVO) throws JQException {
        DataSchemeNodeDTO target = copyNodeVO.getTarget();
        List<DataSchemeNodeDTO> copyData = copyNodeVO.getCopyData();
        this.logger.info("\u62f7\u8d1d\u6570\u636e {} \u5230 {}", (Object)copyData, (Object)target);
        if (copyData == null || target == null) {
            return;
        }
        if (copyData.isEmpty()) {
            return;
        }
        HashSet<String> keySet = new HashSet<String>(copyData.size());
        for (DataSchemeNodeDTO dataSchemeNodeDTO : copyData) {
            keySet.add(dataSchemeNodeDTO.getKey());
        }
        ArrayList<DataSchemeNodeDTO> copy = new ArrayList<DataSchemeNodeDTO>(copyData.size());
        for (DataSchemeNodeDTO copyDatum : copyData) {
            String parentKey = copyDatum.getParentKey();
            boolean contains = keySet.contains(parentKey);
            if (contains) continue;
            copy.add(copyDatum);
        }
        try {
            this.designDataSchemeService.copyDataScheme((DataSchemeNode)target, copy);
        }
        catch (SchemeDataException schemeDataException) {
            this.logger.error(schemeDataException.getMessage(), schemeDataException);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, schemeDataException.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6570\u636e\u65b9\u6848")
    @PostMapping(value={"scheme/update"})
    @Transactional(rollbackFor={Exception.class})
    public void updateDataScheme(@RequestBody DataSchemeVO schemeObj) throws JQException {
        if (!this.dataSchemeAuthService.canWriteScheme(schemeObj.getKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        this.logger.debug("\u66f4\u65b0\u6570\u636e\u65b9\u6848\uff1a{}[{}]\u3002", (Object)schemeObj.getTitle(), (Object)schemeObj.getCode());
        if (!StringUtils.hasLength(schemeObj.getPeriodDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u65f6\u671f\u7ef4\u5ea6.");
        }
        if (!StringUtils.hasLength(schemeObj.getOrgDimKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DS_1, "\u6ca1\u6709\u8bbe\u7f6e\u5355\u4f4d\u7ef4\u5ea6.");
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(schemeObj.getKey());
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity(this.designDataSchemeService, schemeObj, dataScheme);
        List<DesignDataDimension> designDataDimensions = this.buildDimsByVO(schemeObj);
        this.solutionAdjustPeriod(schemeObj, designDataDimensions);
        this.solutionCalibre(schemeObj, designDataDimensions);
        try {
            if (this.dataSchemeValidator.isSubLevel(scheme)) {
                this.dataSchemeValidator.checkSubLevelModify(scheme, designDataDimensions);
                this.designDataSchemeService.updateSubLevelDataScheme(scheme);
            } else {
                this.designDataSchemeService.updateDataScheme(scheme, designDataDimensions);
            }
            this.saveAdjustData(schemeObj.getKey(), schemeObj.getAdjustPeriodVOList());
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u6570\u636e\u65b9\u6848(\u6821\u9a8c\u662f\u5426\u5df2\u7ecf\u5f55\u5165\u6570\u636e\uff0c\u6709\u6570\u636e\u4e0d\u5141\u8bb8\u5220\u9664\uff01)")
    @GetMapping(value={"scheme/delete/{key}"})
    public void deleteDataScheme(@PathVariable String key) throws JQException {
        if (this.dataBaseLimitModeProvider.databaseLimitMode()) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ERROR02);
        }
        if (!this.dataSchemeAuthService.canWriteScheme(key)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            this.dataSchemeValidator.levelCheckDataScheme(key);
            this.designDataSchemeService.deleteDataScheme(key);
            this.adjustmentPeriodInternalService.deleteByDataScheme(key);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6570\u636e\u65b9\u6848\u6743\u9650")
    @PostMapping(value={"scheme/query/auth"})
    public boolean queryAuth(@RequestBody AuthQueryPM params) {
        if (AuthQueryPM.ResourceType.SCHEME_GROUP == params.getResourceType()) {
            if (AuthQueryPM.PrivilegeType.READ == params.getPrivilegeType()) {
                return this.dataSchemeAuthService.canReadGroup(params.getResourceId());
            }
            return this.dataSchemeAuthService.canWriteGroup(params.getResourceId());
        }
        if (AuthQueryPM.PrivilegeType.READ == params.getPrivilegeType()) {
            return this.dataSchemeAuthService.canReadScheme(params.getResourceId());
        }
        return this.dataSchemeAuthService.canWriteScheme(params.getResourceId());
    }

    @ApiOperation(value="\u67e5\u8be2\u81ea\u5b9a\u4e49\u65f6\u671f\u4fe1\u606f")
    @GetMapping(value={"cus-period/year/{periodKey}/{code}"})
    public String getYearStr(@PathVariable String periodKey, @PathVariable String code) {
        Date startDate = this.periodEngineService.getPeriodAdapter().getStartDateByPeriodCode(code, periodKey);
        return new SimpleDateFormat("yyyy\u5e74").format(startDate);
    }

    @PostMapping(value={"no-table/group/clear"})
    public void clearSchemeGroup(@RequestBody Map<String, String> map) throws JQException {
        String schemeKey = map.get("schemeKey");
        if (!this.dataSchemeAuthService.canWriteScheme(schemeKey)) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        try {
            this.designDataSchemeService.clearSchemeGroup(schemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_ERROR01, (Throwable)e);
        }
    }

    @GetMapping(value={"ddl/has/opened"})
    public Boolean hasOpenedDDL() {
        return this.dataBaseLimitModeProvider != null && this.dataBaseLimitModeProvider.databaseLimitMode();
    }

    @GetMapping(value={"query/encrypt/scenes"})
    public Map<String, String> queryEncryptScenes() {
        List enableScenes = this.symmetricSceneManager.getEnableScenes();
        LinkedHashMap<String, String> encryptScenes = new LinkedHashMap<String, String>();
        for (Scene scene : enableScenes) {
            encryptScenes.put(scene.getSceId(), scene.getName() + "[" + scene.getSceId() + "]");
        }
        return encryptScenes;
    }

    @GetMapping(value={"get/design/context/{dataSchemeKey}"})
    public DesignContextVO getDesignContext(@PathVariable String dataSchemeKey) {
        DesignContextVO context = new DesignContextVO();
        context.setKey(dataSchemeKey);
        if (this.dataSchemeAuthService.canWriteScheme(dataSchemeKey)) {
            context.setAuth(AuthQueryPM.PrivilegeType.WRITE);
        } else if (this.dataSchemeAuthService.canReadScheme(dataSchemeKey)) {
            context.setAuth(AuthQueryPM.PrivilegeType.READ);
        }
        context.setDeployed(null != this.runtimeDataSchemeService.getDataScheme(dataSchemeKey));
        context.setEntered(this.runtimeDataSchemeService.dataSchemeCheckData(dataSchemeKey));
        context.setEnableNoddl(this.hasOpenedDDL());
        context.setEnableI18n(LanguageType.enableMultiLanguage());
        context.setEnableAccount(this.designDataSchemeService.enableAccountTable());
        return context;
    }

    @GetMapping(value={"query_calibre_param"})
    public List<ShowFieldVO> queryCalibreParam() {
        IEntityModel entityModel = this.entityMetaService.getEntityModel("MD_ORG@ORG");
        List showFields = entityModel.getShowFields();
        List entityRefers = this.entityMetaService.getEntityRefer("MD_ORG@ORG");
        HashMap<String, String> referMap = new HashMap<String, String>(entityRefers.size());
        HashMap<String, String> referTitleMap = new HashMap<String, String>(entityRefers.size());
        for (IEntityRefer entityRefer : entityRefers) {
            referMap.put(entityRefer.getOwnField(), entityRefer.getReferEntityId());
            if (referTitleMap.containsKey(entityRefer.getReferEntityId())) continue;
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityRefer.getReferEntityId());
            referTitleMap.put(entityRefer.getReferEntityId(), tableModel.getTitle());
        }
        ArrayList<ShowFieldVO> res = new ArrayList<ShowFieldVO>();
        for (IEntityAttribute showField : showFields) {
            if (showField.isMultival() || !referMap.containsKey(showField.getCode())) continue;
            ShowFieldVO showFieldVO = ShowFieldVO.convert(showField);
            showFieldVO.setReferEntityId((String)referMap.get(showField.getCode()));
            showFieldVO.setReferEntityTitle((String)referTitleMap.get(showFieldVO.getReferEntityId()));
            res.add(showFieldVO);
        }
        return res;
    }

    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u7cfb\u7edf\u9009\u9879\u914d\u7f6e")
    @GetMapping(value={"query/sys-opt"})
    public SysOptionVO getSysOption() {
        SysOptionVO res = new SysOptionVO();
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user == null || this.systemIdentityService.isSystemIdentity(user.getId()) || this.systemIdentityService.isBusinessManager(user.getId())) {
            return res;
        }
        String value = this.nvwaSystemOptionService.findValueById("userDeletionDisabled");
        res.setUserDeletionDisabled("1".equals(value));
        return res;
    }

    @ApiOperation(value="\u6570\u636e\u65b9\u6848\u6570\u636e\u6821\u9a8c")
    @PostMapping(value={"scheme/data/check"})
    public String schemeDataCheck(@RequestBody DataSchemeCheckPropVO dataSchemeVO) {
        if (dataSchemeVO.getKey() == null) {
            dataSchemeVO.setKey(UUIDUtils.getKey());
        }
        DesignDataScheme scheme = EntityUtil.schemeVO2Entity(this.designDataSchemeService, dataSchemeVO, null);
        try {
            this.dataSchemeValidator.checkDataScheme(scheme, dataSchemeVO.getProperties().toArray(new String[0]));
        }
        catch (SchemeDataException e) {
            return e.getMessage();
        }
        return "";
    }

    @PostMapping(value={"move_scheme"})
    @ApiOperation(value="\u79fb\u52a8\u6570\u636e\u65b9\u6848")
    @Transactional(rollbackFor={Exception.class})
    public List<BaseDataSchemeVO> moveScheme(@RequestBody MoveSchemeVO vo) throws JQException {
        this.logger.debug("\u79fb\u52a8\u6570\u636e\u65b9\u6848\uff1akey: {}, parent: {}, move: {} \u3002", vo.getKey(), vo.getParentKey(), vo.getMove());
        List schemes = this.designDataSchemeService.getDataSchemeByParent(vo.getParentKey());
        schemes = schemes.stream().filter(t -> this.dataSchemeAuthService.canReadScheme(t.getKey())).collect(Collectors.toList());
        for (int i = 0; i < schemes.size(); ++i) {
            int nextIndex = i + vo.getMove();
            if (!((DesignDataScheme)schemes.get(i)).getKey().equals(vo.getKey())) continue;
            if (nextIndex < 0 || nextIndex >= schemes.size()) {
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, "\u79fb\u52a8\u4f4d\u7f6e\u8d85\u51fa\u8303\u56f4");
            }
            if (!this.dataSchemeAuthService.canWriteScheme(((DesignDataScheme)schemes.get(i)).getKey())) {
                throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
            }
            String order = ((DesignDataScheme)schemes.get(i)).getOrder();
            ((DesignDataScheme)schemes.get(i)).setOrder(((DesignDataScheme)schemes.get(nextIndex)).getOrder());
            ((DesignDataScheme)schemes.get(nextIndex)).setOrder(order);
            this.designDataSchemeService.updateDataSchemes(Arrays.asList((DesignDataScheme)schemes.get(i), (DesignDataScheme)schemes.get(nextIndex)));
            return this.queryBaseDataSchemesByGroup(vo.getParentKey());
        }
        return Collections.emptyList();
    }
}

