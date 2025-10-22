/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.configuration.common.ConfigContentType
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.para.parser.SolutionParser
 *  com.jiuqi.nr.single.core.para.parser.table.FMDMParser
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportParser
 */
package nr.single.map.param.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.SolutionParser;
import com.jiuqi.nr.single.core.para.parser.table.FMDMParser;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportParser;
import java.util.ArrayList;
import nr.single.map.param.service.SingleParamFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleParamFileServiceImpl
implements SingleParamFileService {
    private static final Logger logger = LoggerFactory.getLogger(SingleParamFileServiceImpl.class);
    @Autowired
    private ConfigurationConfig configService;

    @Override
    public FMRepInfo getSingleFMDMInfo(String taskKey, String formSchemeKey) {
        FMRepInfo fmdmInfo = null;
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, "FMDM.JQT");
        if (config != null && StringUtils.isNotEmpty((String)config.getConfigurationContent())) {
            String code = config.getConfigurationContent();
            byte[] data = Convert.base64ToBytes((CharSequence)code);
            try {
                ArrayList fmZbs = new ArrayList();
                FMDMParser parser = new FMDMParser();
                fmdmInfo = parser.BuildFMDM(data, fmZbs);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return fmdmInfo;
    }

    @Override
    public RepInfo getSingleReportInfo(String taskKey, String formSchemeKey, String formCode) {
        RepInfo repInfo = null;
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, formCode + ".JQT");
        if (config != null && StringUtils.isNotEmpty((String)config.getConfigurationContent())) {
            String code = config.getConfigurationContent();
            byte[] data = Convert.base64ToBytes((CharSequence)code);
            try {
                ReportParser parser = new ReportParser();
                repInfo = parser.readReport(data);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return repInfo;
    }

    @Override
    public ParaInfo getSingleTaskInfo(String taskKey, String formSchemeKey) {
        ParaInfo paraInfo = null;
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, "TASKSIGN.TSK");
        BusinessConfigurationDefine config2 = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, "FloatOrderCtrl.dat");
        if (config != null && StringUtils.isNotEmpty((String)config.getConfigurationContent())) {
            String code = config.getConfigurationContent();
            byte[] datas = Convert.base64ToBytes((CharSequence)code);
            try {
                paraInfo = new ParaInfo(null);
                paraInfo.setParaType(0);
                paraInfo.setModify(false);
                Ini ini = new Ini();
                ini.loadIniFromBytes(datas, config.getFileName());
                SolutionParser parser = new SolutionParser();
                parser.parseTaskInfo(paraInfo, ini);
                if (config2 != null) {
                    paraInfo.setFloatOrderField("SYS_ORDER");
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return paraInfo;
    }

    @Override
    public byte[] getSingleParaFile(String taskKey, String formSchemeKey, String fileName) {
        byte[] datas = null;
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, fileName);
        if (config != null && StringUtils.isNotEmpty((String)config.getConfigurationContent())) {
            String code = config.getConfigurationContent();
            datas = Convert.base64ToBytes((CharSequence)code);
        }
        return datas;
    }

    @Override
    public Ini getSingleParaIniFile(String taskKey, String formSchemeKey, String fileName) {
        Ini ini = null;
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, fileName);
        if (config != null && StringUtils.isNotEmpty((String)config.getConfigurationContent())) {
            String code = config.getConfigurationContent();
            byte[] datas = Convert.base64ToBytes((CharSequence)code);
            ini = new Ini();
            try {
                ini.loadIniFromBytes(datas, config.getFileName());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return ini;
    }

    @Override
    public void updateSingleParaIniFile(String taskKey, String formSchemeKey, String fileName, Ini ini) {
        byte[] fileData = ini.saveIniToBytes(fileName);
        this.updateSingleParaIniFile(taskKey, formSchemeKey, fileName, fileData);
    }

    @Override
    public void updateSingleParaIniFile(String taskKey, String formSchemeKey, String fileName, byte[] fileData) {
        this.updateSingleParaIniFile(taskKey, formSchemeKey, fileName, fileData, "JIOTASK");
    }

    @Override
    public void updateSingleParaIniFile(String taskKey, String formSchemeKey, String fileName, byte[] fileData, String category) {
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, fileName);
        boolean isNew = false;
        if (config == null) {
            config = this.configService.getBusinessConfigurationController().createConfiguration();
            config.setTaskKey(taskKey);
            config.setFormSchemeKey(formSchemeKey);
            config.setCode(fileName);
            isNew = true;
        }
        config.setFileName(fileName);
        config.setTitle(fileName);
        config.setDescription(fileName + "-JIO\u6587\u4ef6");
        config.setTitle(fileName);
        config.setCategory(category);
        config.setContentType(ConfigContentType.CCT_BINARY);
        String value = Convert.bytesToBase64((byte[])fileData);
        config.setConfigurationContent(value);
        if (isNew) {
            this.configService.getBusinessConfigurationController().addConfiguration(config);
        } else {
            this.configService.getBusinessConfigurationController().updateConfiguration(config);
        }
    }
}

