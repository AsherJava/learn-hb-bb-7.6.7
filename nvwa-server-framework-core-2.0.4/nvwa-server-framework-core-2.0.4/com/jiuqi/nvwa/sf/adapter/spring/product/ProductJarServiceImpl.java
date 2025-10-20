/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.product;

import com.jiuqi.nvwa.sf.adapter.spring.product.IProductJarService;
import com.jiuqi.nvwa.sf.adapter.spring.product.ManifestResolver;
import com.jiuqi.nvwa.sf.adapter.spring.product.domain.ProductJarBean;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class ProductJarServiceImpl
implements IProductJarService {
    private volatile List<ProductJarBean> productJarBeans;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ProductJarBean> list() {
        if (this.productJarBeans == null) {
            ProductJarServiceImpl productJarServiceImpl = this;
            synchronized (productJarServiceImpl) {
                if (this.productJarBeans == null) {
                    this.productJarBeans = this.getJarBeans();
                }
            }
        }
        return this.productJarBeans;
    }

    private List<ProductJarBean> getJarBeans() {
        return ((Stream)ManifestResolver.uriManifestMap().entrySet().stream().parallel()).map(uriManifestEntry -> {
            Manifest mf = (Manifest)uriManifestEntry.getValue();
            Attributes mainAttributes = mf.getMainAttributes();
            ProductJarBean productJarBean = new ProductJarBean();
            String fileName = ((URI)uriManifestEntry.getKey()).toString();
            try {
                fileName = URLDecoder.decode(fileName, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            productJarBean.setPath(fileName);
            fileName = fileName.substring(fileName.lastIndexOf(47) + 1);
            productJarBean.setFileName(fileName);
            for (Map.Entry<Object, Object> entry : mainAttributes.entrySet()) {
                String key = entry.getKey().toString();
                if ("version".equals(key)) {
                    productJarBean.setVersion(entry.getValue().toString());
                }
                if ("buildTime".equals(key)) {
                    productJarBean.setBuildTime(entry.getValue().toString());
                }
                if ("name".equals(key)) {
                    productJarBean.setArtifactId(entry.getValue().toString());
                }
                if (!"group".equals(key)) continue;
                productJarBean.setGroupId(entry.getValue().toString());
            }
            return productJarBean;
        }).collect(Collectors.toList());
    }
}

