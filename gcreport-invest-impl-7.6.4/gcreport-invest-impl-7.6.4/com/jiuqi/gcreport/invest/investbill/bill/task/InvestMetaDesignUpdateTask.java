/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.SystemUserDTO
 *  com.jiuqi.np.user.feign.client.NvwaSystemUserClient
 *  com.jiuqi.va.bizmeta.common.utils.MetaUtils
 *  com.jiuqi.va.bizmeta.common.utils.VersionManage
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO
 *  com.jiuqi.va.bizmeta.service.IMetaVersionService
 *  com.jiuqi.va.bizmeta.service.impl.MetaDataService
 *  com.jiuqi.va.bizmeta.service.impl.MetaInfoService
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  javax.servlet.ServletContext
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.invest.investbill.bill.task.GcMetaInfoService;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestBillModuleInitiator;
import com.jiuqi.gcreport.invest.investbill.bill.task.InvestMetaDesignSpecialUpdateTask;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.SystemUserDTO;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.common.utils.VersionManage;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import com.jiuqi.va.bizmeta.domain.metaversion.MetaDataVersionDTO;
import com.jiuqi.va.bizmeta.service.IMetaVersionService;
import com.jiuqi.va.bizmeta.service.impl.MetaDataService;
import com.jiuqi.va.bizmeta.service.impl.MetaInfoService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class InvestMetaDesignUpdateTask
implements CustomClassExecutor,
InvestBillModuleInitiator {
    private Logger logger = LoggerFactory.getLogger(InvestMetaDesignUpdateTask.class);
    private static MetaDataDTO dataDTO;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) {
        boolean needBindUser = null == ShiroUtil.getUser();
        try {
            if (needBindUser) {
                NvwaSystemUserClient systemUserClient = (NvwaSystemUserClient)SpringContextUtils.getBean(NvwaSystemUserClient.class);
                SystemUserDTO systemUserDTO = (SystemUserDTO)systemUserClient.getUsers().get(0);
                UserLoginDTO user = new UserLoginDTO();
                user.setId(systemUserDTO.getId());
                user.setUsername(systemUserDTO.getName());
                ShiroUtil.bindUser((UserLoginDTO)user);
            }
            JsonNode designDataFromFile = this.loadFromFile("bill/bill_design_upgrade.json");
            String defineCode = this.asText(designDataFromFile.get("name"));
            JsonNode pluginsFromFile = designDataFromFile.get("plugins");
            MetaDataDTO dataDTO = this.loadFromDB(defineCode);
            JsonNode designDataFromDB = JsonUtils.readTree((String)dataDTO.getDesignData());
            JsonNode pluginsFromDB = designDataFromDB.get("plugins");
            for (int i = 0; i < pluginsFromFile.size(); ++i) {
                JsonNode pluginFromFile = pluginsFromFile.get(i);
                this.merge(pluginFromFile, pluginsFromDB);
            }
            this.setDefaultSchemeIfNotExists(pluginsFromDB);
            InvestMetaDesignSpecialUpdateTask task = new InvestMetaDesignSpecialUpdateTask();
            task.merge(pluginsFromDB);
            String designData = JsonUtils.writeValueAsString((Object)designDataFromDB);
            dataDTO.setDesignData(designData);
            InvestMetaDesignUpdateTask.dataDTO = dataDTO;
            this.publishBill(dataDTO, "INVESTBILL");
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        finally {
            if (needBindUser) {
                ShiroUtil.unbindUser();
            }
        }
    }

    public void init(ServletContext servletContext) throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void initWhenStarted(ServletContext servletContext) throws Exception {
        if (null != dataDTO) {
            boolean needBindUser = null == ShiroUtil.getUser();
            try {
                if (needBindUser) {
                    NvwaSystemUserClient systemUserClient = (NvwaSystemUserClient)SpringContextUtils.getBean(NvwaSystemUserClient.class);
                    SystemUserDTO systemUserDTO = (SystemUserDTO)systemUserClient.getUsers().get(0);
                    UserLoginDTO user = new UserLoginDTO();
                    user.setId(systemUserDTO.getId());
                    user.setUsername(systemUserDTO.getName());
                    ShiroUtil.bindUser((UserLoginDTO)user);
                }
                this.publishBill(dataDTO, "INVESTBILL");
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            finally {
                dataDTO = null;
                if (needBindUser) {
                    ShiroUtil.unbindUser();
                }
            }
        }
    }

    private void setDefaultSchemeIfNotExists(JsonNode pluginsFromDBResult) {
        String type = "view";
        for (int i = 0; i < pluginsFromDBResult.size(); ++i) {
            ObjectNode pluginFromDBResult = (ObjectNode)pluginsFromDBResult.get(i);
            if (!type.equals(this.asText(pluginFromDBResult.get("type")))) continue;
            this.logger.info("\u5339\u914d\u7c7b\u578b\uff1a" + type);
            JsonNode schemesNode = pluginFromDBResult.get("schemes");
            JsonNode templateNode = pluginFromDBResult.get("template");
            if (null != schemesNode || null == templateNode) break;
            ArrayNode newSchemesNode = (ArrayNode)JsonUtils.readTree((String)"[{\"title\": \"\u9ed8\u8ba4\u65b9\u6848\",\"code\": \"defaultScheme\",\"default\": true}]");
            ObjectNode newSchemeNode = (ObjectNode)newSchemesNode.get(0);
            newSchemeNode.set("template", templateNode);
            pluginFromDBResult.set("schemes", (JsonNode)newSchemesNode);
            break;
        }
    }

    private void publishBill(MetaDataDTO dataDTO, String name) {
        GcMetaInfoService metaInfoService = (GcMetaInfoService)SpringContextUtils.getBean(GcMetaInfoService.class);
        IMetaVersionService metaVersionService = (IMetaVersionService)SpringContextUtils.getBean(IMetaVersionService.class);
        long newVersion = VersionManage.getInstance().newVersion(metaVersionService);
        MetaInfoDTO infoDTO = metaInfoService.findMeta("GCBILL", "bill", name);
        if (null == infoDTO.getUniqueCode()) {
            String uniqueCode = MetaUtils.buildUniqueCode((String)infoDTO.getModuleName(), (String)infoDTO.getMetaType(), (String)infoDTO.getName());
            infoDTO.setUniqueCode(uniqueCode);
        }
        infoDTO.setRowVersion(Long.valueOf(newVersion));
        infoDTO = metaInfoService.updateMeta(ShiroUtil.getUser().getId(), infoDTO, dataDTO);
        MetaDataDeployDim deploy = new MetaDataDeployDim();
        deploy.setModuleName(infoDTO.getModuleName());
        deploy.setState(infoDTO.getState().intValue());
        deploy.setId(infoDTO.getId());
        metaInfoService.deployMetaById(ShiroUtil.getUser().getId(), deploy, newVersion);
        MetaDataVersionDTO dataVersionDO = new MetaDataVersionDTO();
        dataVersionDO.setCreateTime(new Date());
        dataVersionDO.setInfo("\u81ea\u52a8\u5347\u7ea7");
        dataVersionDO.setUserName(ShiroUtil.getUser().getId());
        dataVersionDO.setVersionNo(Long.valueOf(newVersion));
        metaVersionService.addMetaVersionInfo(dataVersionDO);
    }

    private void merge(JsonNode pluginFromFile, JsonNode pluginsFromDBResult) {
        String type = this.asText(pluginFromFile.get("type"));
        boolean match = false;
        for (int i = 0; i < pluginsFromDBResult.size(); ++i) {
            JsonNode tables;
            JsonNode defineInfo;
            JsonNode pluginFromDBResult = pluginsFromDBResult.get(i);
            if (!type.equals(this.asText(pluginFromDBResult.get("type")))) continue;
            match = true;
            this.logger.info("\u5339\u914d\u7c7b\u578b\uff1a" + type);
            JsonNode formulas = pluginFromFile.get("formulas");
            if (null != formulas) {
                this.mergeFormulas(formulas, pluginFromDBResult);
            }
            if (null != (defineInfo = pluginFromFile.get("defineInfo"))) {
                this.mergeDefineInfo(defineInfo, pluginFromDBResult);
            }
            if (null == (tables = pluginFromFile.get("tables"))) continue;
            this.mergeTables(tables, pluginFromDBResult);
        }
        if (!match) {
            this.logger.info("\u672a\u5339\u914d\u7c7b\u578b\uff1a" + type);
            ((ArrayNode)pluginsFromDBResult).add(pluginFromFile);
            this.logger.info(pluginFromFile.toString());
        }
    }

    private void mergeTables(JsonNode tablesFromFile, JsonNode pluginFromDBResult) {
        ArrayNode tablesFromDBResult = (ArrayNode)pluginFromDBResult.get("tables");
        for (int i = 0; i < tablesFromFile.size(); ++i) {
            JsonNode tableFromFile = tablesFromFile.get(i);
            String tableNameFromFile = this.asText(tableFromFile.get("tableName"));
            if (StringUtils.isEmpty((String)tableNameFromFile)) {
                this.logger.warn("\u8282\u70b9\u5f02\u5e38tableName\uff1a" + tableFromFile);
                continue;
            }
            for (int j = 0; j < tablesFromDBResult.size(); ++j) {
                JsonNode tableFromDBResult = tablesFromDBResult.get(j);
                String tableNameFromDB = this.asText(tableFromDBResult.get("tableName"));
                if (!tableNameFromFile.equals(tableNameFromDB)) continue;
                this.mergeTable(tableFromFile, tableFromDBResult);
            }
        }
    }

    private void mergeTable(JsonNode tableFromFile, JsonNode tableFromDBResult) {
        int i;
        ArrayNode fieldsFromFile = (ArrayNode)tableFromFile.get("fields");
        ArrayNode fieldsFromDB = (ArrayNode)tableFromDBResult.get("fields");
        HashSet<String> fieldNameSet = new HashSet<String>();
        for (i = 0; i < fieldsFromDB.size(); ++i) {
            fieldNameSet.add(fieldsFromDB.get(i).get("name").asText());
        }
        for (i = 0; i < fieldsFromFile.size(); ++i) {
            JsonNode fieldFromFile = fieldsFromFile.get(i);
            String fieldName = fieldFromFile.get("name").asText();
            if (!fieldNameSet.contains(fieldName)) {
                this.logger.info("\u6dfb\u52a0\u6210\u529f\uff1a" + fieldFromFile);
                fieldsFromDB.add(fieldFromFile);
                continue;
            }
            this.logger.warn("\u5b58\u5728\u91cd\u590d\u5185\u5bb9\uff1a" + fieldFromFile);
        }
    }

    private void mergeDefineInfo(JsonNode defineInfo, JsonNode pluginFromDBResult) {
    }

    private void mergeFormulas(JsonNode formulasFromFile, JsonNode pluginFromDBResult) {
        ArrayNode formulasFromDBResult = (ArrayNode)pluginFromDBResult.get("formulas");
        for (int i = 0; i < formulasFromFile.size(); ++i) {
            JsonNode formulaFromFile = formulasFromFile.get(i);
            String action = this.asText(formulaFromFile.get("action"));
            if (StringUtils.isEmpty((String)action)) {
                this.logger.warn("\u8282\u70b9\u5f02\u5e38action\uff1a" + formulaFromFile);
                continue;
            }
            String matchBy = this.asText(formulaFromFile.get("matchBy"));
            if (StringUtils.isEmpty((String)matchBy)) {
                this.logger.warn("\u8282\u70b9\u5f02\u5e38matchBy\uff1a" + formulaFromFile);
                continue;
            }
            String matchVal = this.asText(formulaFromFile.get(matchBy));
            if (StringUtils.isEmpty((String)matchVal)) {
                this.logger.warn("\u8282\u70b9\u5f02\u5e38matchVal\uff1a" + formulaFromFile);
                continue;
            }
            if ("del".equals(action)) {
                for (int j = 0; j < formulasFromDBResult.size(); ++j) {
                    JsonNode formulaFromDBResult = formulasFromDBResult.get(j);
                    if (!matchVal.equals(this.asText(formulaFromDBResult.get(matchBy)))) continue;
                    formulasFromDBResult.remove(j);
                    this.logger.warn("\u79fb\u9664\u6210\u529f\uff1a" + formulaFromDBResult);
                }
                continue;
            }
            if (!"add".equals(action)) continue;
            boolean match = false;
            for (int j = 0; j < formulasFromDBResult.size(); ++j) {
                JsonNode formulaFromDBResult = formulasFromDBResult.get(j);
                if (!matchVal.equals(this.asText(formulaFromDBResult.get(matchBy)))) continue;
                this.logger.warn("\u5b58\u5728\u91cd\u590d\u5185\u5bb9\uff1a" + formulaFromFile);
                match = true;
                break;
            }
            if (match) continue;
            this.logger.warn("\u6dfb\u52a0\u6210\u529f\uff1a" + formulaFromFile);
            formulasFromDBResult.add(formulaFromFile);
        }
    }

    private String asText(JsonNode jsonNode) {
        return null == jsonNode ? null : jsonNode.asText();
    }

    private MetaDataDTO loadFromDB(String defineCode) {
        MetaInfoService metaInfoService = (MetaInfoService)SpringContextUtils.getBean(MetaInfoService.class);
        MetaDataService metaDataService = (MetaDataService)SpringContextUtils.getBean(MetaDataService.class);
        MetaInfoDTO infoDTO = metaInfoService.getMetaInfoByUniqueCode(defineCode);
        MetaDataDTO dataDTO = metaDataService.getMetaDataById(infoDTO.getId());
        return dataDTO;
    }

    private JsonNode loadFromFile(String path) throws IOException {
        String billDesignStr = this.parseJson(path);
        return JsonUtils.readTree((String)billDesignStr);
    }

    private String parseJson(String path) throws IOException {
        ClassPathResource templateResource = new ClassPathResource(path);
        InputStream inputStream = templateResource.getInputStream();
        return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    }
}

