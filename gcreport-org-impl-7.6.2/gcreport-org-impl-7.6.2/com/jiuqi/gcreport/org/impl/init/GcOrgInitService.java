/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaBaseApplicationInitialization
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.org.impl.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.init.table.va.intf.FEntVaBaseApplicationInitialization;
import com.jiuqi.gcreport.org.impl.init.GcOrgAdjustTypeFieldProcessor;
import com.jiuqi.gcreport.org.impl.init.impl.OrgTypeTempObj;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class GcOrgInitService
implements FEntVaBaseApplicationInitialization {
    private static Logger logger = LoggerFactory.getLogger(GcOrgInitService.class);
    @Value(value="classpath*:/config/vaorg/field/*.fd.json")
    private Resource[] resources;
    @Value(value="classpath:/config/vaorg/type/gc_org.type.json")
    private Resource typeResource;
    private final OrgCategoryService orgCategoryService;
    private final GcOrgAdjustTypeFieldProcessor gcOrgAdjustTypeFieldProcessor;

    public GcOrgInitService(OrgCategoryService orgCategoryService, GcOrgAdjustTypeFieldProcessor gcOrgAdjustTypeFieldProcessor) {
        this.orgCategoryService = orgCategoryService;
        this.gcOrgAdjustTypeFieldProcessor = gcOrgAdjustTypeFieldProcessor;
    }

    public void init(boolean force, String tenant) {
        List<OrgTypeTempObj> types = this.getInitOrgTypes();
        Map<String, String> orgFields = this.getOrgTypeFields();
        if (types != null && types.size() > 0) {
            types.forEach(orgType -> this.initOrgType(tenant, (OrgTypeTempObj)orgType, (String)orgFields.get(orgType.getCode())));
        }
        this.gcOrgAdjustTypeFieldProcessor.addAdjTypeField(types, orgFields);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<OrgTypeTempObj> getInitOrgTypes() {
        try (InputStream in = this.typeResource.getInputStream();){
            ObjectMapper om = new ObjectMapper();
            OrgTypeTempObj[] types = (OrgTypeTempObj[])om.readValue(in, OrgTypeTempObj[].class);
            List<OrgTypeTempObj> list = Arrays.asList(types);
            return list;
        }
        catch (IOException e) {
            logger.error("Error reading file: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void initOrgType(String tenantName, OrgTypeTempObj vo, String zbs) {
        OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
        orgCategoryDO.setTenantName(tenantName);
        orgCategoryDO.setName(vo.getCode());
        orgCategoryDO.setVersionflag(Integer.valueOf(vo.isMutliversion() ? 1 : 0));
        OrgCategoryDO oldOrgCategoryDO = this.orgCategoryService.get(orgCategoryDO);
        if (oldOrgCategoryDO == null) {
            orgCategoryDO.setTitle(vo.getName());
            orgCategoryDO.setExtinfo(zbs);
            orgCategoryDO.addExtInfo("bizkeyfields", (Object)vo.getBizkeyfields());
            this.orgCategoryService.add(orgCategoryDO);
        } else {
            oldOrgCategoryDO.getZbs().stream().filter(zb -> zb.getId() == null).forEach(zb -> {
                if (zb.getId() == null) {
                    zb.setId(UUIDUtils.fromString36((String)UUIDUtils.emptyUUIDStr()));
                }
            });
            boolean changed = this.syncZb(oldOrgCategoryDO, zbs);
            if (changed) {
                this.orgCategoryService.update(oldOrgCategoryDO);
            }
        }
    }

    private Map<String, String> getOrgTypeFields() {
        HashMap<String, String> zbs = new HashMap<String, String>();
        for (Resource res : this.resources) {
            try (InputStream inputStream = res.getInputStream();){
                String filename = res.getFilename();
                assert (filename != null);
                filename = filename.substring(0, filename.indexOf("."));
                String value = IOUtils.toString((InputStream)inputStream, (Charset)StandardCharsets.UTF_8);
                zbs.put(filename, value);
            }
            catch (IOException e) {
                logger.error("Error reading file: " + e.getMessage(), e);
            }
        }
        return zbs;
    }

    private boolean syncZb(OrgCategoryDO org, String solidZbs) {
        boolean changed = false;
        List newZbs = (List)JsonUtils.readValue((String)solidZbs, (TypeReference)new TypeReference<List<ZB>>(){});
        for (ZB z : newZbs) {
            ZB oldZ = org.getZbByName(z.getName());
            if (oldZ == null) {
                org.syncZb(z);
                changed = true;
                continue;
            }
            if (oldZ.getPrecision() >= z.getPrecision()) continue;
            oldZ.setPrecision(z.getPrecision());
            changed = true;
        }
        return changed;
    }

    public int getSortOrder() {
        return 2;
    }
}

