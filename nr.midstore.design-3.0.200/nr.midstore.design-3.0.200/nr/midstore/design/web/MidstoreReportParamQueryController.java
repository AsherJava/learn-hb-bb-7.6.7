/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.mapping.bean.MappingScheme
 *  com.jiuqi.nr.mapping.service.MappingSchemeService
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricSceneManager
 *  com.jiuqi.nvwa.encryption.crypto.domain.po.Scene
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceBasicInfo
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  nr.midstore.core.definition.common.NrParamQueryError
 *  nr.midstore.core.util.IMidstoreFileServcie
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package nr.midstore.design.web;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.encryption.crypto.SymmetricSceneManager;
import com.jiuqi.nvwa.encryption.crypto.domain.po.Scene;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceBasicInfo;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import nr.midstore.core.definition.common.NrParamQueryError;
import nr.midstore.core.util.IMidstoreFileServcie;
import nr.midstore.design.common.MidstoreGrid2DataSeralizeToGeGe;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.service.IParamQueryService;
import nr.midstore.design.vo.DataSchemeVO;
import nr.midstore.design.vo.MidstoreFormObj;
import nr.midstore.design.vo.MidstoreFormTreeVO;
import nr.midstore.design.vo.MistoreDataLinkVO;
import nr.midstore.design.vo.MistoreFieldVO;
import nr.midstore.design.vo.MistoreFormVO;
import nr.midstore.design.vo.MistoreGroupVO;
import nr.midstore.design.vo.MistoreTableVO;
import nr.midstore.design.vo.MistoreTaskVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/mistore/report/query"})
@Api(tags={"\u4e2d\u95f4\u5e93\u62a5\u8868\u53c2\u6570\u67e5\u8be2"})
public class MidstoreReportParamQueryController {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreReportParamQueryController.class);
    @Autowired
    private IParamQueryService paramQueryService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MappingSchemeService mappingService;
    @Autowired
    private IMidstoreFileServcie fileServcie;

    @PostMapping(value={"/entity"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u5217\u8868")
    public List<CommonParamDTO> listEntity(@RequestBody List<String> entityKeys) throws JQException {
        List<CommonParamDTO> commonParamDTOS;
        try {
            commonParamDTOS = this.paramQueryService.listEntity(entityKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrParamQueryError.ENTITY_QUERY, e.getMessage());
        }
        return commonParamDTOS;
    }

    @PostMapping(value={"/getAllTask"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u7cfb\u7edf\u6240\u6709\u4efb\u52a1")
    public List<MistoreTaskVO> getTaskList() {
        return this.paramQueryService.getAllTask();
    }

    @PostMapping(value={"/getTaskList"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u7684\u4efb\u52a1\u5217\u8868")
    public List<MistoreTaskVO> getTaskList(@RequestBody List<String> taskKey) {
        return this.paramQueryService.getTaskList(taskKey);
    }

    @GetMapping(value={"/getTaskDefine/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u7684\u4efb\u52a1")
    public MistoreTaskVO getTaskDefine(@PathVariable(value="taskKey") String taskKey) {
        return this.paramQueryService.getTaskDefine(taskKey);
    }

    @GetMapping(value={"/getFormScheme/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848")
    public List<CommonParamDTO> getFormSchemeByTask(@PathVariable(value="taskKey") String taskKey) {
        return this.paramQueryService.listFormScheme(taskKey);
    }

    @GetMapping(value={"/getDataSchemePrefix/{dataSchemeKey}", "/getDataSchemePrefix/{dataSchemeKey}/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u5bf9\u5e94\u7684\u524d\u7f00")
    public DataSchemeVO getDataSchemePrefix(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @PathVariable(value="taskKey", required=false) String taskKey) {
        List dimensions;
        DataSchemeVO result = new DataSchemeVO();
        DesignDataScheme dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme != null) {
            if (dataScheme.getPrefix() != null) {
                result.setPrefix(dataScheme.getPrefix());
            } else {
                result.setPrefix("");
            }
        }
        if (!CollectionUtils.isEmpty(dimensions = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION)) && dimensions.size() == 1) {
            result.setMainDimensionKey(((DesignDataDimension)dimensions.get(0)).getDimKey());
        }
        if (StringUtils.isNotEmpty((String)taskKey)) {
            TaskDefine designTaskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            String dw = designTaskDefine.getDw();
            String DW = dw.substring(0, dw.indexOf("@"));
            result.setMainDimensionKey(DW);
        }
        return result;
    }

    @GetMapping(value={"/getDataTable/{dataSchemeKey}/{tableType}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6570\u636e\u8868")
    public List<CommonParamDTO> getDataTable(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @PathVariable(value="tableType") Boolean tableType) {
        return this.paramQueryService.listDataTable(dataSchemeKey, tableType);
    }

    @GetMapping(value={"/getEnumList/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e0b\u7684\u679a\u4e3e")
    public List<CommonParamDTO> getEnumList(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getEnumList(dataSchemeKey);
    }

    @GetMapping(value={"/getConfigSchemeByTask/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u5bf9\u5e94\u7684\u6620\u5c04\u65b9\u6848")
    public List<CommonParamDTO> getConfigSchemeByTask(@PathVariable(value="taskKey") String taskKey) {
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>();
        List mappings = this.mappingService.getMappingByTask(taskKey);
        for (MappingScheme mapping : mappings) {
            CommonParamDTO dto = new CommonParamDTO();
            dto.setKey(mapping.getKey());
            dto.setCode(mapping.getCode());
            dto.setTitle(mapping.getTitle());
            dto.getValues().put("formScheme", mapping.getFormScheme());
            dto.getValues().put("task", mapping.getTask());
            list.add(dto);
        }
        return list;
    }

    @GetMapping(value={"/getConfigSchemeByFormScheme/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u65b9\u6848\u5bf9\u5e94\u7684\u6620\u5c04\u65b9\u6848")
    public List<CommonParamDTO> getConfigSchemeByFormScheme(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List mappings = this.mappingService.getMappingByTaskForm(formScheme.getTaskKey(), formSchemeKey);
        for (MappingScheme mapping : mappings) {
            CommonParamDTO dto = new CommonParamDTO();
            dto.setKey(dto.getKey());
            dto.setCode(mapping.getCode());
            dto.setTitle(mapping.getTitle());
            dto.getValues().put("formScheme", mapping.getFormScheme());
            dto.getValues().put("task", mapping.getTask());
            list.add(dto);
        }
        return list;
    }

    @GetMapping(value={"/getDataBaselinks"})
    @ApiOperation(value="\u83b7\u53d6\u5973\u5a32\u914d\u7f6e\u7684\u6570\u636e\u6e90")
    public List<CommonParamDTO> getDataBaselinks() {
        DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringBeanUtils.getBean(DynamicDataSource.class);
        Set datasourceKeySet = dynamicDataSource.getDataSourceKeySet();
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>();
        for (String dataSoruce : datasourceKeySet) {
            NvwaDataSourceBasicInfo drBaseInfo = null;
            try {
                drBaseInfo = dynamicDataSource.getDataSourceBasicInfo(dataSoruce);
            }
            catch (DataSourceNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
            CommonParamDTO dto = new CommonParamDTO();
            dto.setCode(dataSoruce);
            if (drBaseInfo != null && StringUtils.isNotEmpty((String)drBaseInfo.getTitle())) {
                dto.setTitle(drBaseInfo.getTitle());
            } else {
                dto.setTitle(dataSoruce);
            }
            list.add(dto);
        }
        return list;
    }

    @GetMapping(value={"/getSceneCodes"})
    @ApiOperation(value="\u83b7\u53d6\u5973\u5a32\u52a0\u5bc6\u573a\u666f")
    public List<CommonParamDTO> getSceneCodes() {
        SymmetricSceneManager sceneManager = (SymmetricSceneManager)SpringBeanUtils.getBean(SymmetricSceneManager.class);
        List scenes = sceneManager.getEnableScenes();
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>();
        for (Scene scene : scenes) {
            CommonParamDTO dto = new CommonParamDTO();
            dto.setCode(scene.getSceId());
            dto.setTitle(scene.getName());
            dto.setDesc(scene.getDescription());
            list.add(dto);
        }
        return list;
    }

    @GetMapping(value={"/getEntityIdByTask/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u7684\u4e3b\u4f53ID")
    public String getEntityIdByTask(@PathVariable(value="taskKey") String taskKey) {
        return this.paramQueryService.getEntityIdByTask(taskKey);
    }

    @GetMapping(value={"/getFieldByBaseData/{baseDataKey}"})
    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u7684\u5b57\u6bb5")
    public List<MistoreFieldVO> getFieldByBaseData(@PathVariable(value="baseDataKey") String baseDataKey) {
        return this.paramQueryService.getFieldByBaseData(baseDataKey);
    }

    @GetMapping(value={"/getFieldByEntityID/{entityID}"})
    @ApiOperation(value="\u83b7\u53d6\u5b9e\u4f53\u7684\u5b57\u6bb5")
    public List<MistoreFieldVO> getFieldByEntityID(@PathVariable(value="entityID") String entityID) {
        return this.paramQueryService.getFieldByEntityID(entityID);
    }

    @GetMapping(value={"/getFieldByOrg/{orgDataName}", "/getFieldByOrg/{orgDataName}/{keyWord}"})
    @ApiOperation(value="\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u7684\u7684\u5b57\u6bb5")
    public List<MistoreFieldVO> getFieldByOrg(@PathVariable(value="orgDataName") String orgDataName, @PathVariable(value="keyWord", required=false) String keyWord) {
        if (StringUtils.isNotEmpty((String)keyWord)) {
            ArrayList<MistoreFieldVO> list = new ArrayList<MistoreFieldVO>();
            List<MistoreFieldVO> qList = this.paramQueryService.getFieldByOrg(orgDataName);
            for (MistoreFieldVO field : qList) {
                if (StringUtils.isNotEmpty((String)field.getCode()) && field.getCode().toUpperCase().contains(keyWord.toUpperCase())) {
                    list.add(field);
                    continue;
                }
                if (!StringUtils.isNotEmpty((String)field.getTitle()) || !field.getTitle().toUpperCase().contains(keyWord.toUpperCase())) continue;
                list.add(field);
            }
            return list;
        }
        return this.paramQueryService.getFieldByOrg(orgDataName);
    }

    @GetMapping(value={"/getFieldByTable/{datatableKey}", "/getFieldByTable/{datatableKey}/{keyWord}"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u8868\u7684\u5b57\u6bb5")
    public List<MistoreFieldVO> getFieldByTable(@PathVariable(value="datatableKey") String datatableKey, @PathVariable(value="keyWord", required=false) String keyWord) {
        if (StringUtils.isNotEmpty((String)keyWord)) {
            ArrayList<MistoreFieldVO> list = new ArrayList<MistoreFieldVO>();
            List<MistoreFieldVO> qList = this.paramQueryService.getFieldByTable(datatableKey);
            for (MistoreFieldVO field : qList) {
                if (StringUtils.isNotEmpty((String)field.getCode()) && field.getCode().toUpperCase().contains(keyWord.toUpperCase())) {
                    list.add(field);
                    continue;
                }
                if (!StringUtils.isNotEmpty((String)field.getTitle()) || !field.getTitle().toUpperCase().contains(keyWord.toUpperCase())) continue;
                list.add(field);
            }
            return list;
        }
        return this.paramQueryService.getFieldByTable(datatableKey);
    }

    @GetMapping(value={"/getAllBaseDataGroups"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u57fa\u7840\u6570\u636e\u5206\u7ec4")
    public List<MistoreGroupVO> getAllBaseDataGroups() {
        return this.paramQueryService.getAllBaseDataGroups();
    }

    @GetMapping(value={"/getRootBaseDataGroups"})
    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6839\u5206\u7ec4")
    public List<MistoreGroupVO> getRootBaseDataGroups() {
        return this.paramQueryService.getRootBaseDataGroups();
    }

    @GetMapping(value={"/getBaseDataGroupsByParent/{groupCode}"})
    @ApiOperation(value="\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6839\u5206\u7ec4")
    public List<MistoreGroupVO> getBaseDataGroupsByParent(@PathVariable(value="groupCode") String groupCode) {
        return this.paramQueryService.getBaseDataGroupsByParent(groupCode);
    }

    @GetMapping(value={"/getBaseDatasByGroup/{groupCode}"})
    @ApiOperation(value="\u6839\u636e\u4e0a\u7ea7\u5206\u7ec4\u83b7\u53d6\u57fa\u7840\u6570\u636e")
    public List<CommonParamDTO> getBaseDatasByGroup(@PathVariable(value="groupCode") String groupCode) {
        return this.paramQueryService.getBaseDatasByGroup(groupCode);
    }

    @GetMapping(value={"/getAllDataGroupsByScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u901a\u8fc7\u6570\u636e\u65b9\u6848\u83b7\u53d6\u6240\u6709\u5206\u7ec4")
    public List<MistoreGroupVO> getAllDataGroupsByScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getAllDataGroupsByScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getRootDataGroupsByScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u901a\u8fc7\u6570\u636e\u65b9\u6848\u83b7\u53d6\u6839\u5206\u7ec4")
    public List<MistoreGroupVO> getRootDataGroupsByScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getRootDataGroupsByScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getDataGroupsByParent/{groupKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u6839\u5206\u7ec4")
    public List<MistoreGroupVO> getDataGroupsByParent(@PathVariable(value="groupKey") String groupKey) {
        return this.paramQueryService.getDataGroupsByParent(groupKey);
    }

    @GetMapping(value={"/getRootDataTablesByScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u901a\u8fc7\u6570\u636e\u65b9\u6848\u83b7\u53d6\u65e0\u5206\u7ec4\u7684\u6307\u6807\u8868")
    public List<MistoreTableVO> getRootDataTablesByScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getRootDataTablesByScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getDataTablesByGroup/{groupKey}"})
    @ApiOperation(value="\u6839\u636e\u5206\u7ec4\u53d6\u6307\u6807\u8868")
    public List<MistoreTableVO> getDataTablesByGroup(@PathVariable(value="groupKey") String groupKey) {
        return this.paramQueryService.getDataTablesByGroup(groupKey);
    }

    @GetMapping(value={"/getAllDataTablesByScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6570\u636e\u8868")
    public List<MistoreTableVO> getAllDataTablesByScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getAllDataTablesByScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getAllFormGroupsByScheme/{formSchemeKey}"})
    @ApiOperation(value="\u901a\u8fc7\u6570\u636e\u65b9\u6848\u83b7\u53d6\u6240\u6709\u5206\u7ec4")
    public List<MistoreGroupVO> getAllFormGroupsByScheme(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        return this.paramQueryService.getAllFormGroupsByScheme(formSchemeKey);
    }

    @GetMapping(value={"/getRootFormGroupsByScheme/{formSchemeKey}"})
    @ApiOperation(value="\u901a\u8fc7\u6570\u636e\u65b9\u6848\u83b7\u53d6\u6839\u5206\u7ec4")
    public List<MistoreGroupVO> getRootFormGroupsByScheme(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        return this.paramQueryService.getRootFormGroupsByScheme(formSchemeKey);
    }

    @GetMapping(value={"/getFormGroupsByParent/{groupKey}"})
    @ApiOperation(value="\u83b7\u53d6\u8868\u8fbe\u90a3\u5206\u7ec4")
    public List<MistoreGroupVO> getFormGroupsByParent(@PathVariable(value="groupKey") String groupKey) {
        return this.paramQueryService.getFormGroupsByParent(groupKey);
    }

    @GetMapping(value={"/getFormsByGroup/{groupKey}/{isRecursion}"})
    @ApiOperation(value="\u6839\u636e\u5206\u7ec4\u53d6\u8868\u5355")
    public List<MistoreFormVO> getFormsByGroup(@PathVariable(value="groupKey") String groupKey, @PathVariable(value="isRecursion") boolean isRecursion) throws Exception {
        return this.paramQueryService.getFormsByGroup(groupKey, isRecursion);
    }

    @GetMapping(value={"/getAllFormsByScheme/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355")
    public List<MistoreFormVO> getAllFormsByScheme(@PathVariable(value="formSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getAllFormsByScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getReportTree/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u8868\u5355\u6811")
    public List<MidstoreFormTreeVO> getReportTree(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        return this.paramQueryService.getReportTree(formSchemeKey);
    }

    @GetMapping(value={"/getDataLinkByForm/{formKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6570\u636e\u94fe\u63a5")
    public List<MistoreDataLinkVO> getDataLinkByForm(@PathVariable(value="formKey") String formKey) {
        return this.paramQueryService.getDataLinkByForm(formKey);
    }

    @GetMapping(value={"/getGridDataByForm/{formKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u7684\u8868\u6837")
    public Grid2Data getGridDataByForm(@PathVariable(value="formKey") String formKey) {
        return this.paramQueryService.getGridDataByForm(formKey);
    }

    @GetMapping(value={"/getGridTextByForm/{formKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u7684\u8868\u6837\u6587\u672c")
    public String getGridTextByForm(@PathVariable(value="formKey") String formKey) throws Exception {
        Grid2Data grid = this.paramQueryService.getGridDataByForm(formKey);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new MidstoreGrid2DataSeralizeToGeGe());
        objectMapper.registerModule((Module)module);
        MidstoreFormObj formObj = new MidstoreFormObj();
        formObj.setFormStyle(grid);
        return objectMapper.writeValueAsString((Object)formObj);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/downloadDocumentByFileKey/{fileKey}"})
    @ApiOperation(value="\u6839\u636e\u6587\u4ef6KEY\u4e0b\u8f7d\u89c4\u8303\u6587\u6863")
    public void downloadDocumentByFileKey(HttpServletResponse response, @PathVariable(value="fileKey") String fileKey) {
        ServletOutputStream outputStream = null;
        try {
            String fileName = this.fileServcie.getFileInfo(fileKey).getName();
            outputStream = response.getOutputStream();
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            fileName = "attachment;filename=" + fileName;
            fileName = HtmlUtils.cleanHeaderValue((String)fileName);
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", fileName);
            this.fileServcie.downFile(fileKey, (OutputStream)outputStream);
            outputStream.flush();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }
}

