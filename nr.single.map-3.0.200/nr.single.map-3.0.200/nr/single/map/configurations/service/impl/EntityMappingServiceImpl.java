/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.ParseMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityMappingServiceImpl
extends ParseMapping {
    private static final Logger logger = LoggerFactory.getLogger(EntityMappingServiceImpl.class);
    private static final String NET_CODE = "\u7f51\u62a5\u5355\u4f4d\u6807\u8bc6";
    private static final String SINGLE_CODE = "\u5e73\u53f0\u5355\u4f4d\u6807\u8bc6";
    private static final String NET_BBLX = "\u7f51\u62a5\u5355\u4f4d\u62a5\u8868\u7c7b\u578b";
    private static final String REG_SINGLE = "^[0-9A-Z#]*$";
    private IllegalData errorData;

    @Override
    public int parseFileMapping(byte[] files) {
        int count = 0;
        UnitMapping mapping = new UnitMapping();
        this.errorData = new IllegalData();
        ArrayList<UnitCustomMapping> unitInfos = new ArrayList<UnitCustomMapping>();
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(files));
        BufferedReader br = new BufferedReader(reader);
        String lineTxt = null;
        try {
            int idx = 0;
            while ((lineTxt = br.readLine()) != null) {
                if ("".equals(lineTxt.trim()) || ++idx < 2) continue;
                String[] unitCode = lineTxt.trim().split("\\s+");
                UnitCustomMapping unitCustom = this.getUnitCustom(unitCode);
                unitCustom.setImportIndex(String.valueOf(idx));
                if (unitCustom.getSingleUnitCode() != null) {
                    if (unitCustom.getSingleUnitCode().matches(REG_SINGLE)) {
                        unitInfos.add(unitCustom);
                    } else {
                        this.errorData.addErrorEntity("\u9519\u8bef\u683c\u5f0f\u7684\u5e73\u53f0\u5355\u4f4d\u4ee3\u7801\uff01", unitCustom.getImportIndex(), unitCustom.getSingleUnitCode());
                    }
                } else {
                    this.errorData.addErrorEntity("\u4e0d\u5b58\u5728\u7684\u5e73\u53f0\u5355\u4f4d\uff01", unitCustom.getImportIndex(), unitCustom.getSingleUnitCode());
                }
                ++count;
            }
            mapping.setUnitInfos(unitInfos);
            this.setUnitInfo(mapping);
            br.close();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public void convertFromConfig(ISingleMappingConfig config) {
        this.setUnitInfo(config.getMapping());
        this.setMapRule(config.getMapRule());
    }

    @Override
    public String buildTextFile() {
        UnitMapping unitMapping = this.getUnitInfo();
        List<UnitCustomMapping> infos = unitMapping.getUnitInfos();
        StringBuffer sbs = new StringBuffer();
        sbs.append(NET_CODE).append(" ").append(SINGLE_CODE).append(" ").append(NET_BBLX).append("\r\n");
        AtomicReference<String> netCode = new AtomicReference<String>(NET_CODE);
        AtomicReference<String> singleCode = new AtomicReference<String>(SINGLE_CODE);
        AtomicReference<String> netBblx = new AtomicReference<String>("");
        infos.forEach(e -> {
            if (e.getNetUnitCode() != null) {
                netCode.set(e.getNetUnitCode());
            }
            sbs.append(netCode);
            sbs.append(" ");
            if (e.getSingleUnitCode() != null) {
                singleCode.set(e.getSingleUnitCode());
            }
            sbs.append(singleCode);
            sbs.append(" ");
            if (e.getBblx() != null) {
                netBblx.set(e.getBblx());
            }
            sbs.append(netBblx);
            sbs.append("\r\n");
        });
        return sbs.toString();
    }

    @Override
    public IllegalData getErrorData() {
        return this.errorData;
    }

    private UnitCustomMapping getUnitCustom(String[] line) {
        UnitCustomMapping unit = new UnitCustomMapping();
        if (line.length > 0) {
            for (int i = 0; i < line.length; ++i) {
                if (i == 0) {
                    unit.setNetUnitCode(line[i]);
                    continue;
                }
                if (i == 1) {
                    unit.setSingleUnitCode(line[i]);
                    continue;
                }
                if (i != 2) continue;
                unit.setBblx(line[i]);
            }
        }
        return unit;
    }
}

