/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.single.core.common.InOutDataType
 *  com.jiuqi.nr.single.core.para.JQTFileMap
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.para.parser.JIOParamParser
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.service.SingleFileParserService
 *  com.jiuqi.nr.single.lib.reg.internal.alaes.AlaesConsts
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  nr.single.data.bean.CheckSoftNode
 *  nr.single.data.bean.CheckSoftParam
 *  nr.single.data.reg.service.SingleParaAuthService
 *  nr.single.data.reg.service.SingleSoftAuthService
 *  nr.single.map.data.PathUtil
 *  nr.single.para.compare.definition.CompareInfoDTO
 *  nr.single.para.compare.definition.ISingleCompareInfoService
 *  nr.single.para.compare.internal.util.CompareUtil
 *  nr.single.para.parain.util.IParaImportFileServcie
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package nr.single.client.web;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.single.core.common.InOutDataType;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.JIOParamParser;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.service.SingleFileParserService;
import com.jiuqi.nr.single.lib.reg.internal.alaes.AlaesConsts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.validation.Valid;
import nr.single.data.bean.CheckSoftNode;
import nr.single.data.bean.CheckSoftParam;
import nr.single.data.reg.service.SingleParaAuthService;
import nr.single.data.reg.service.SingleSoftAuthService;
import nr.single.map.data.PathUtil;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.internal.util.CompareUtil;
import nr.single.para.parain.util.IParaImportFileServcie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/single/auth"})
@Api(tags={"\u5355\u673a\u7248\u6743\u9650\u5904\u7406"})
public class SingleAuthController {
    private static final Logger logger = LoggerFactory.getLogger(SingleAuthController.class);
    @Autowired
    private SingleParaAuthService paraAuth;
    @Autowired
    private SingleSoftAuthService softAuth;
    @Autowired
    private ISingleCompareInfoService infoService;
    @Autowired
    private IParaImportFileServcie fileService;
    @Autowired
    private SingleFileParserService singleParserService;
    @Autowired
    private SingleSoftAuthService softAuthService;

    @ApiOperation(value="\u83b7\u53d6\u673a\u5668\u7801")
    @RequestMapping(value={"/getmachcode"}, method={RequestMethod.POST})
    public CheckSoftNode getMachCode() throws Exception {
        return this.softAuth.getMachCode(null);
    }

    @ApiOperation(value="\u83b7\u53d6\u673a\u5668\u7801")
    @RequestMapping(value={"/getmachinecode"}, method={RequestMethod.POST})
    public CheckSoftNode getMachineCode(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.getMachCode(checkParam);
    }

    @ApiOperation(value="\u83b7\u53d6\u6821\u9a8c\u53c2\u6570")
    @RequestMapping(value={"/getcheckparam"}, method={RequestMethod.POST})
    public CheckSoftNode getCheckParam(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.getCheckParam(checkParam);
    }

    @ApiOperation(value="\u68c0\u67e5\u5141\u8bb8\u4f7f\u7528\u62a5\u8868\u65b9\u6848")
    @RequestMapping(value={"/checktaskparam"}, method={RequestMethod.POST})
    public CheckSoftNode checktaskparam(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        String taskKey = null;
        String formSchemeKey = null;
        if (checkParam.getVariableMap() != null) {
            if (checkParam.getVariableMap().containsKey("TaskKey")) {
                taskKey = (String)checkParam.getVariableMap().get("TaskKey");
            }
            if (checkParam.getVariableMap().containsKey("FormSchemeKey")) {
                formSchemeKey = (String)checkParam.getVariableMap().get("FormSchemeKey");
            }
        }
        if (StringUtils.isNotEmpty(taskKey) && StringUtils.isNotEmpty(formSchemeKey)) {
            return this.paraAuth.checktaskparam(taskKey, formSchemeKey, checkParam);
        }
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        node.setMessage("\u4efb\u52a1KEY\u548c\u62a5\u8868\u65b9\u6848KEY\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        return node;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ApiOperation(value="\u68c0\u67e5\u5141\u8bb8\u88dd\u5165JIO\u53c3\u6578")
    @RequestMapping(value={"/checkjioparam"}, method={RequestMethod.POST})
    public CheckSoftNode checkjioparam(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        String compareKey = null;
        if (checkParam.getVariableMap() != null && checkParam.getVariableMap().containsKey("compareKey")) {
            compareKey = (String)checkParam.getVariableMap().get("compareKey");
        }
        if (StringUtils.isNotEmpty(compareKey)) {
            CompareInfoDTO info = this.infoService.getByKey(compareKey);
            String filePath = CompareUtil.getCompareFilePath();
            byte[] oldFileData = null;
            if (StringUtils.isNotEmpty((CharSequence)info.getJioData())) {
                oldFileData = info.getJioData().getBytes(StandardCharsets.UTF_8);
            }
            String jioFile = this.fileService.downFile(filePath, info.getJioFile(), oldFileData, info.getJioFileKey());
            JIOParamParser jioParaser = this.singleParserService.getParaParaser(jioFile);
            try {
                if (jioParaser.getInOutData().indexOf(InOutDataType.BBCS) < 0 && jioParaser.getInOutData().indexOf(InOutDataType.CSCS) < 0) {
                    throw new Exception("\u5bfc\u5165\u7684\u6587\u4ef6\u4e2d\u65e0\u53c2\u6570");
                }
                String taskDir = jioParaser.getFilePath();
                String paraDir = PathUtil.getNewPath((String)taskDir, (String)"PARA");
                Ini ini = new Ini();
                try {
                    ini.loadIniFile(taskDir + "TASKSIGN.TSK");
                    byte[] iniData = ini.saveIniToBytes(taskDir + "TASKSIGN.TSK");
                    checkParam.getAuthConfig().setParaData(iniData);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                CheckSoftNode checkResult = this.paraAuth.checktaskparam(null, null, checkParam);
                if (checkResult.getCheckState() == 1) {
                    Map configMap = this.softAuthService.readAuthFromConfig(checkParam.getAuthConfig());
                    String authInvidKeyOff = (String)configMap.get("InvidKeyOff");
                    String AppName = (String)configMap.get("AppName");
                    String taskFlag = jioParaser.getParaInfo().getPrefix();
                    if (StringUtils.isNotEmpty((CharSequence)authInvidKeyOff) && StringUtils.isNotEmpty((CharSequence)taskFlag)) {
                        if (PathUtil.getFileExists((String)(paraDir + "MJTKFG.JQX"))) {
                            String destFile = paraDir + OrderGenerator.newOrder() + "_check.Ini";
                            String encodeCode = taskFlag + String.valueOf(Integer.parseInt(authInvidKeyOff, 16));
                            AlaesConsts.decryptAESFile((String)(paraDir + "MJTKFG.JQX"), (String)encodeCode, (String)destFile);
                            Ini ini2 = new Ini();
                            try {
                                ini2.loadIniFile(destFile);
                                String readTaskFalg = ini2.ReadString("config", "TaskFlag", "");
                                if (!taskFlag.equalsIgnoreCase(readTaskFalg)) {
                                    checkResult.setCheckState(2);
                                }
                            }
                            catch (Exception e1) {
                                checkResult.setCheckState(2);
                                logger.info(e1.getMessage(), e1);
                            }
                        } else {
                            checkResult.setCheckState(2);
                        }
                        if (checkResult.getCheckState() == 2) {
                            checkResult.setMessage("\u672c\u7cfb\u7edf\u53ea\u80fd\u7528\u4e8e" + AppName + "\u7279\u5b9a\u53c2\u6570\uff0c\u5982\u6709\u95ee\u9898\u8bf7\u4e0e\u4e45\u5176\u516c\u53f8\u8054\u7cfb");
                        }
                    } else if (StringUtils.isEmpty((CharSequence)taskFlag)) {
                        checkResult.setCheckState(2);
                        checkResult.setMessage("\u672c\u7cfb\u7edf\u53ea\u80fd\u7528\u4e8e" + AppName + "\u7279\u5b9a\u53c2\u6570\uff0c\u5982\u6709\u95ee\u9898\u8bf7\u4e0e\u4e45\u5176\u516c\u53f8\u8054\u7cfb");
                    }
                    if (checkResult.getCheckState() == 1) {
                        String authLimitYear = (String)configMap.get("LimitYear");
                        String authLimitMonth = (String)configMap.get("LimitMonth");
                        if (StringUtils.isNotEmpty((CharSequence)authLimitYear) && StringUtils.isNotEmpty((CharSequence)authLimitMonth)) {
                            int authLimitYearNum = 0;
                            int authLimitMonthNum = 0;
                            try {
                                authLimitYearNum = Integer.parseInt(authLimitYear);
                                authLimitMonthNum = Integer.parseInt(authLimitMonth);
                            }
                            catch (Exception e) {
                                logger.error(e.getMessage(), e);
                                logger.info("\u9519\u8bef\u7684\u53c2\u6570\u9650\u5236\u5e74\u6708\uff1a" + authLimitYear + "," + authLimitMonth);
                            }
                            for (RepInfo rep : jioParaser.getParaInfo().getRepInfos()) {
                                JQTFileMap jqFile = jioParaser.getParaInfo().getJqtFileMap(rep.getCode());
                                if (jqFile == null || jqFile.getHeader() == null || jqFile.getHeader().getCreationYear() <= 0 || jqFile.getHeader().getCreationMonth() <= 0) continue;
                                int fileYear = jqFile.getHeader().getCreationYear();
                                int fileMonth = jqFile.getHeader().getCreationMonth();
                                if (authLimitYearNum >= fileYear && (authLimitYearNum != fileYear || authLimitMonthNum >= fileMonth)) continue;
                                checkResult.setCheckState(2);
                                checkResult.setMessage("\u4efb\u52a1\u53c2\u6570\u7248\u672c\u5df2\u66f4\u65b0\uff0c\u60a8\u53ef\u80fd\u9700\u8981\u66f4\u9ad8\u7248\u672c\u7684\u8f6f\u4ef6\u624d\u80fd\u4f7f\u7528\u8be5\u53c2\u6570\uff01");
                                logger.info("\u4efb\u52a1\u53c2\u6570\u7248\u672c\u5df2\u66f4\u65b0" + rep.getCode() + ",\u521b\u5efa\u5e74\u6708\uff1a" + fileYear + "," + fileMonth);
                                break;
                            }
                        }
                    }
                }
                CheckSoftNode checkSoftNode = checkResult;
                return checkSoftNode;
            }
            finally {
                PathUtil.deleteDir((String)jioParaser.getFilePath());
                PathUtil.deleteFile((String)jioFile);
            }
        }
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        node.setMessage("\u5bfc\u5165\u6bd4\u8f83\u7ed3\u679ccompareKey\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        return node;
    }

    @ApiOperation(value="\u68c0\u67e5\u8f6f\u4ef6\u662f\u5426\u6ce8\u518c")
    @RequestMapping(value={"/checksoftreg"}, method={RequestMethod.POST})
    public CheckSoftNode checkSoftReg(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.checkSoftReg(checkParam);
    }

    @ApiOperation(value="\u83b7\u53d6\u8f6f\u4ef6\u6ce8\u518c\u4fe1\u606f")
    @RequestMapping(value={"/getsoftreginfo"}, method={RequestMethod.POST})
    public CheckSoftNode getSoftRegInfo(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.getSoftRegInfo(checkParam);
    }

    @ApiOperation(value="\u8f6f\u4ef6\u6ce8\u518c\u5e76\u6821\u9a8c")
    @RequestMapping(value={"/dosoftreg"}, method={RequestMethod.POST})
    public CheckSoftNode dosoftreg(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.doSoftRegister(checkParam);
    }

    @ApiOperation(value="\u83b7\u53d6\u8f6f\u4ef6\u6ce8\u518c\u6388\u6743\u65e5\u671f")
    @RequestMapping(value={"/getRegEndDate"}, method={RequestMethod.POST})
    public CheckSoftNode getRegEndDate(@Valid @RequestBody CheckSoftParam checkParam) throws Exception {
        return this.softAuth.getRegEndDate(checkParam);
    }
}

