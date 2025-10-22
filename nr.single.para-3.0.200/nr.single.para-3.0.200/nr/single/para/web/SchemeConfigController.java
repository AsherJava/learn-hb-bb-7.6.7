/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  nr.single.map.common.SingleResult
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.configurations.bean.UnitMapping
 *  nr.single.map.configurations.internal.bean.HandleSavePojo
 *  nr.single.map.configurations.internal.bean.QueryParam
 *  nr.single.map.configurations.service.FormulaSchemeService
 *  nr.single.map.configurations.service.MappingConfigService
 *  nr.single.map.configurations.vo.EntitySaveResult
 *  nr.single.map.configurations.vo.EnumItemMappingVO
 *  nr.single.map.configurations.vo.EnumMappingVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package nr.single.para.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import nr.single.map.common.SingleResult;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.internal.bean.HandleSavePojo;
import nr.single.map.configurations.internal.bean.QueryParam;
import nr.single.map.configurations.service.FormulaSchemeService;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.configurations.vo.EntitySaveResult;
import nr.single.map.configurations.vo.EnumItemMappingVO;
import nr.single.map.configurations.vo.EnumMappingVO;
import nr.single.para.upload.domain.CommonParamDTO;
import nr.single.para.upload.service.IParamQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/single/config"})
@Api(tags={"JIO\u6620\u5c04\u65b9\u6848\u914d\u7f6e"})
public class SchemeConfigController {
    private static final Logger log = LoggerFactory.getLogger(SchemeConfigController.class);
    @Autowired
    private MappingConfigService mappingConfigService;
    @Autowired
    private FormulaSchemeService formulaSchemeService;
    @Autowired
    private IParamQueryService paramQueryService;

    @GetMapping(value={"/query-all-mapping/{scheme}"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6620\u5c04\u65b9\u6848")
    public SingleResult queryMappingByReport(@PathVariable(value="scheme") String schemekey) {
        SingleResult result = SingleResult.ok();
        List configs = this.mappingConfigService.getAllMappingInReport(schemekey);
        result.setData((Object)configs);
        return result;
    }

    @GetMapping(value={"/query-mapping-task-code/{taskFlag}"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u673a\u7248\u4efb\u52a1\u6807\u8bc6\u4e0b\u7684\u6620\u5c04\u65b9\u6848")
    public SingleResult queryMappingBySingleTaskFlag(@PathVariable(value="taskFlag") String taskFlag) {
        SingleResult result = SingleResult.ok();
        List configs = this.mappingConfigService.getConfigInSingleTask(taskFlag);
        result.setData((Object)configs);
        return result;
    }

    @GetMapping(value={"/query-mapping-task/{taskKey}"})
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u6620\u5c04\u65b9\u6848")
    public SingleResult queryMappingByTaskKey(@PathVariable(value="taskKey") String taskKey) {
        SingleResult result = SingleResult.ok();
        List configs = this.mappingConfigService.getAllMappingInTask(taskKey);
        result.setData((Object)configs);
        return result;
    }

    @GetMapping(value={"/query-rule/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u5bfc\u5165\u5bfc\u51fa\u89c4\u5219")
    public SingleResult queryImportAndExportRule(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        List ruleMaps = this.mappingConfigService.getRuleMapByConfig(mappingKey);
        result.setData((Object)ruleMaps);
        return result;
    }

    @GetMapping(value={"/query-config/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u6620\u5c04\u65b9\u6848\u914d\u7f6e")
    public SingleResult queryConfigBykey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        Map config = this.mappingConfigService.getMappingConfig(mappingKey);
        result.setData((Object)config);
        return result;
    }

    @GetMapping(value={"/query-entity/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryEntityBykey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        UnitMapping entityConfig = this.mappingConfigService.getEntityConfig(mappingKey);
        result.setData((Object)entityConfig);
        return result;
    }

    @GetMapping(value={"/query-formula/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryformulaBykey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        List formulaConfig = this.mappingConfigService.getFormulaConfig(mappingKey);
        result.setData((Object)formulaConfig);
        return result;
    }

    @GetMapping(value={"/query-schemeformula"})
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryformulaBySchemekey(@RequestParam(value="mappingKey", name="mappingKey") String mappingKey, @RequestParam(value="schemeKey", name="schemeKey") String schemeKey) {
        SingleResult result = SingleResult.ok();
        List config = this.mappingConfigService.getFormulaConfig(mappingKey, schemeKey);
        result.setData((Object)config);
        return result;
    }

    @PostMapping(value={"/query-reportformula"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4e0b\u7684\u516c\u5f0f\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryformulaByReportkey(@Valid @RequestBody QueryParam param) {
        SingleResult result = SingleResult.ok();
        List formulaConfig = this.mappingConfigService.getFormulaConfig(param.getMappingKey(), param.getSchemeKey(), param.getRepotKey());
        result.setData((Object)formulaConfig);
        return result;
    }

    @GetMapping(value={"/query-zb/{mappingKey}"})
    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryZbBykey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        List config = this.mappingConfigService.getZbConfig(mappingKey);
        result.setData((Object)config);
        return result;
    }

    @GetMapping(value={"/query-formzb/{mappingKey}/{formKey}"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4e0b\u7684\u6307\u6807\u6620\u5c04\u4fe1\u606f")
    public SingleResult queryZbByForm(@PathVariable(value="mappingKey") String mappingKey, @PathVariable(value="formKey") String formKey) {
        SingleResult result = SingleResult.ok();
        List zbInfos = this.mappingConfigService.getZbConfigByForm(mappingKey, formKey);
        result.setData((Object)zbInfos);
        return result;
    }

    @PostMapping(value={"/save-zb"})
    @ApiOperation(value="\u4fdd\u5b58\u6307\u6807\u6620\u5c04\u914d\u7f6e")
    public SingleResult saveZbConfig(@Valid @RequestBody HandleSavePojo save) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.saveZbConfig(save.getMappingConfigKey().toString(), save.getFormCode(), save.getZbFields());
        return result;
    }

    @PostMapping(value={"/save-entity"})
    @ApiOperation(value="\u4fdd\u5b58\u5355\u4f4d\u6620\u5c04\u914d\u7f6e")
    public SingleResult saveEntityConfig(@Valid @RequestBody HandleSavePojo save) {
        SingleResult result = SingleResult.ok();
        try {
            EntitySaveResult entitySaveResult = this.mappingConfigService.saveEntityConfig(save.getMappingConfigKey(), save.getMapping(), save.getMapRule());
            result.setData((Object)entitySaveResult);
        }
        catch (Exception e) {
            result = SingleResult.build((Integer)201, (String)e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/save-formula"})
    @ApiOperation(value="\u4fdd\u5b58\u516c\u5f0f\u6620\u5c04\u914d\u7f6e")
    public SingleResult saveFormulaConfig(@Valid @RequestBody HandleSavePojo save) {
        SingleResult result = SingleResult.ok();
        try {
            this.mappingConfigService.saveFormulaConfig(save.getMappingConfigKey(), save.getFormulaSchemeKey(), save.getFormulaInfos());
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/save-config"})
    @ApiOperation(value="\u4fdd\u5b58\u65b9\u6848\u914d\u7f6e")
    public SingleResult saveMapping(@Valid @RequestBody HandleSavePojo save) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.saveMappingConfig(save.getMappingConfigKey(), save.getConfig());
        return result;
    }

    @GetMapping(value={"/query-entity-view"})
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u9ed8\u8ba4\u4e3b\u4f53\u7684\u9ed8\u8ba4\u89c6\u56fe")
    public SingleResult queryEntityViewInTask(@RequestParam(value="taskKey", name="taskKey") String taskKey, @RequestParam(value="schemeKey", name="schemeKey") String schemeKey) {
        SingleResult result = SingleResult.ok();
        String entityId = this.mappingConfigService.getEntityIdByTask(taskKey, schemeKey);
        result.setData((Object)entityId);
        return result;
    }

    @GetMapping(value={"/query-scheme/{reportKey}"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u65b9\u6848")
    public SingleResult querySchemeByReport(@PathVariable(value="reportKey") String reportKey) {
        SingleResult result = SingleResult.ok();
        List schemes = this.formulaSchemeService.getFormulaSchemesByReport(reportKey);
        result.setData((Object)schemes);
        return result;
    }

    @GetMapping(value={"/query-form-tree/{schemeKey}"})
    @ApiOperation(value="\u6784\u9020\u62a5\u8868\u6811\u5f62")
    public SingleResult buildFormTree(@PathVariable(value="schemeKey") String schemeKey) {
        SingleResult result = SingleResult.ok();
        List trees = this.formulaSchemeService.buildReportTree(schemeKey);
        result.setData((Object)trees);
        return result;
    }

    @GetMapping(value={"/query-all-formula/{schemeKey}"})
    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u4e0b\u6240\u6709\u7684\u516c\u5f0f")
    public SingleResult queryAllFormulaInScheme(@PathVariable(value="schemeKey") String schemeKey) {
        SingleResult result = SingleResult.ok();
        List formulas = this.formulaSchemeService.getAllFormulas(schemeKey);
        result.setData((Object)formulas);
        return result;
    }

    @GetMapping(value={"/query-report-formula"})
    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u4e0b\u516c\u5f0f")
    public SingleResult queryFormulaInForm(@RequestParam(value="formKey", name="formKey") String formKey, @RequestParam(value="formulaSchemeKey", name="formulaSchemeKey") String formulaSchemeKey) {
        SingleResult result = SingleResult.ok();
        List formulas = this.formulaSchemeService.getFormulasByForm(formulaSchemeKey, formKey);
        result.setData((Object)formulas);
        return result;
    }

    @GetMapping(value={"/copy-config/{mappingKey}"})
    @ApiOperation(value="\u590d\u5236\u6620\u5c04\u914d\u7f6e\u65b9\u6848")
    public SingleResult copyConfigByKey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        SingleConfigInfo info = this.mappingConfigService.copyMappingConfig(mappingKey);
        result.setData((Object)info);
        return result;
    }

    @GetMapping(value={"/delete-config/{mappingKey}"})
    @ApiOperation(value="\u5220\u9664\u6620\u5c04\u914d\u7f6e\u65b9\u6848")
    public SingleResult deleteConfigByKey(@PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.deleteMappingConfig(mappingKey);
        return result;
    }

    @GetMapping(value={"/update-mappingname"})
    @ApiOperation(value="\u4fee\u6539\u6620\u5c04\u914d\u7f6e\u65b9\u6848\u540d\u79f0")
    public SingleResult upadteMappingName(@RequestParam(value="mappingKey", name="mappingKey") String mappingKey, @RequestParam(value="name", name="name") String name) {
        SingleResult result = SingleResult.ok();
        if (name != null) {
            try {
                name = URLDecoder.decode(name, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
        }
        this.mappingConfigService.updateMappingName(name, mappingKey);
        return result;
    }

    @PostMapping(value={"/search-formula-define"})
    @ApiOperation(value="\u641c\u7d22\u516c\u5f0f")
    public SingleResult searchFormulaDefine(@Valid @RequestBody QueryParam param) {
        SingleResult result = SingleResult.ok();
        List formulaDefines = this.formulaSchemeService.searchFormulaDefine(param);
        result.setData((Object)formulaDefines);
        return result;
    }

    @PostMapping(value={"/clean-entity"})
    @ApiOperation(value="\u6e05\u7a7a\u5355\u4f4d\u6620\u5c04")
    public SingleResult cleanEntity(@Valid @RequestBody QueryParam parm) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.cleanEntityConfig(parm.getMappingKey());
        return result;
    }

    @PostMapping(value={"/clean-rule"})
    @ApiOperation(value="\u6e05\u7a7a\u5bfc\u5165\u5bfc\u51fa\u89c4\u5219")
    public SingleResult cleanRule(@Valid @RequestBody QueryParam parm) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.cleanRuleConfig(parm.getMappingKey());
        return result;
    }

    @PostMapping(value={"/clean-period"})
    @ApiOperation(value="\u6e05\u7a7a\u65f6\u671f\u6620\u5c04")
    public SingleResult cleanPeriod(@Valid @RequestBody QueryParam parm) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.cleanPeriodConfig(parm.getMappingKey());
        return result;
    }

    @PostMapping(value={"/clean-zb"})
    @ApiOperation(value="\u6e05\u7a7a\u6307\u6807\u6620\u5c04")
    public SingleResult cleanZb(@Valid @RequestBody QueryParam parm) {
        SingleResult result = SingleResult.ok();
        try {
            this.mappingConfigService.cleanZbConfig(parm.getMappingKey(), parm.getRepotKey());
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/clean-formula"})
    @ApiOperation(value="\u6e05\u7a7a\u516c\u5f0f\u6620\u5c04")
    public SingleResult cleanFormula(@Valid @RequestBody QueryParam parm) {
        SingleResult result = SingleResult.ok();
        try {
            this.mappingConfigService.cleanFormula(parm.getMappingKey(), parm.getSchemeKey(), parm.getRepotKey());
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/change-order/{way}/{mappingKey}"})
    @ApiOperation(value="\u8c03\u6574\u6620\u5c04\u65b9\u6848\u987a\u5e8f")
    public SingleResult changeMappingOrder(@PathVariable(value="way") String way, @PathVariable(value="mappingKey") String mappingKey) {
        SingleResult result = SingleResult.ok();
        try {
            this.mappingConfigService.changeOrder(mappingKey, "up".equals(way));
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/query-region-enum/{form}"})
    @ApiOperation(value="\u67e5\u8be2\u6d6e\u52a8\u533a\u57df\u679a\u4e3e\u6570\u636e")
    public SingleResult queryRegionEnum(@PathVariable(value="form") String form) {
        SingleResult result = SingleResult.ok();
        try {
            Map enumDataInFloatRegion = this.formulaSchemeService.queryEnumDataInFloatRegion(form);
            result.setData((Object)enumDataInFloatRegion);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/query-entity-seleted/{unitKey}"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u9009\u62e9\u5668\u9009\u4e2d\u7684\u5355\u4f4d")
    public SingleResult querySeletedEntities(@PathVariable String unitKey) {
        SingleResult result = SingleResult.ok();
        try {
            List seletedEntities = this.mappingConfigService.getSeletedEntities(unitKey);
            result.setData((Object)seletedEntities);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/query-enum-mapping/{mappingConfig}"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u6620\u5c04")
    public SingleResult queryEnumMapping(@PathVariable String mappingConfig) {
        SingleResult result = SingleResult.ok();
        EnumMappingVO enumMapping = this.mappingConfigService.getEnumMapping(mappingConfig);
        result.setData((Object)enumMapping);
        return result;
    }

    @GetMapping(value={"/query-enumItem-mapping/{mappingConfig}/{enumCode}"})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u9879\u76ee\u6620\u5c04")
    public SingleResult queryEnumItemMapping(@PathVariable String mappingConfig, @PathVariable String enumCode) {
        SingleResult result = SingleResult.ok();
        EnumItemMappingVO enumItemMapping = this.mappingConfigService.getEnumItemMapping(mappingConfig, enumCode);
        result.setData((Object)enumItemMapping);
        return result;
    }

    @PostMapping(value={"/save-enum-mapping"})
    @ApiOperation(value="\u4fdd\u5b58\u679a\u4e3e\u6620\u5c04")
    public SingleResult saveEnumMapping(@Valid @RequestBody EnumMappingVO enumMappingVO) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.saveEnumMapping(enumMappingVO);
        return result;
    }

    @PostMapping(value={"/save-enumItem-mapping"})
    @ApiOperation(value="\u4fdd\u5b58\u679a\u4e3e\u9879\u76ee\u6620\u5c04")
    public SingleResult saveEnumItemMapping(@Valid @RequestBody EnumItemMappingVO enumItemMapping) {
        SingleResult result = SingleResult.ok();
        this.mappingConfigService.saveEnumItemMapping(enumItemMapping);
        return result;
    }

    @PostMapping(value={"/check-enum"})
    @ApiOperation(value="\u68c0\u67e5\u679a\u4e3e\u5b9a\u4e49\u662f\u5426\u5b58\u5728")
    public SingleResult checkEnum(@RequestBody List<String> codes) {
        SingleResult result = SingleResult.ok();
        List<CommonParamDTO> commonParamDTOS = this.paramQueryService.checkEnums(codes);
        result.setData(commonParamDTOS);
        return result;
    }

    @GetMapping(value={"/quick-mapping/{mappingConfig}/{enumCode}/{type}"})
    @ApiOperation(value="\u5feb\u901f\u751f\u6210\u6620\u5c04")
    public SingleResult quickMatch(@PathVariable String mappingConfig, @PathVariable String enumCode, @PathVariable String type) {
        SingleResult result = SingleResult.ok();
        try {
            EnumItemMappingVO enumItemMapping = this.mappingConfigService.quickMatch(mappingConfig, enumCode, type);
            result.setData((Object)enumItemMapping);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/query-period/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5bf9\u5e94\u7684\u5355\u4f4d\u65f6\u671f")
    public SingleResult queryPeriod(@PathVariable String formSchemekey) {
        SingleResult result = SingleResult.ok();
        try {
            String period = this.mappingConfigService.queryPeriod(formSchemekey);
            result.setData((Object)period);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @GetMapping(value={"/query-attribute/{formSchemekey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e3b\u7ef4\u5ea6\u7684\u5c5e\u6027")
    public SingleResult queryEntityAttribute(@PathVariable String formSchemekey) {
        SingleResult result = SingleResult.ok();
        try {
            List attribute = this.mappingConfigService.queryEntityAttribute(formSchemekey);
            result.setData((Object)attribute);
        }
        catch (Exception e) {
            result.setStatus(Integer.valueOf(201));
            result.setMsg(e.getMessage());
        }
        return result;
    }
}

