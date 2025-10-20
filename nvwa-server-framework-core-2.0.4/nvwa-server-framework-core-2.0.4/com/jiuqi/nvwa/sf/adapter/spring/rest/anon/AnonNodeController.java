/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiuqi.nvwa.sf.adapter.spring.Response;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.IServiceNodeManager;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.ServiceNode;
import com.jiuqi.nvwa.sf.adapter.spring.cloud.impl.SFRemoteResourceManage;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductLineBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/anon/sf/api"})
public class AnonNodeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SFRemoteResourceManage remoteResourceManage;
    public static final ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @GetMapping(value={"/product/list"})
    public Response productLineList() {
        HashMap<String, ProductLineBean> productLineBeanMap = new HashMap<String, ProductLineBean>();
        ArrayList<ProductLineBean> list = new ArrayList<ProductLineBean>();
        for (IServiceManager serviceManager : this.remoteResourceManage.getServiceManagerResources()) {
            try {
                String serviceName = serviceManager.getServiceName();
                List<ProductLineBean> productLineList = serviceManager.getProductLineList();
                for (ProductLineBean productLineBean : productLineList) {
                    if (productLineBeanMap.containsKey(productLineBean.getId())) {
                        ProductLineBean productLineBean1 = (ProductLineBean)productLineBeanMap.get(productLineBean.getId());
                        if (productLineBean1.getVersion().equalsIgnoreCase(productLineBean.getVersion())) continue;
                        productLineBean.setTitle(productLineBean.getTitle() + "(" + serviceName + ")");
                        list.add(productLineBean);
                        continue;
                    }
                    productLineBeanMap.put(productLineBean.getId(), productLineBean);
                    list.add(productLineBean);
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        try {
            list.sort(Comparator.comparing(ProductLineBean::getId));
            return Response.ok(MAPPER.writeValueAsString(list));
        }
        catch (JsonProcessingException e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping(value={"/nodes"})
    public Response services() {
        ArrayList<ServiceNode> list = new ArrayList<ServiceNode>();
        for (IServiceNodeManager serviceNodeManagerResource : this.remoteResourceManage.getServiceNodeManagerResources()) {
            try {
                list.addAll(serviceNodeManagerResource.getNode());
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return Response.okWithObj(list);
    }

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}

