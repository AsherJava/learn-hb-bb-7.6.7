/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.graphics.ImageData
 *  com.jiuqi.nvwa.grid2.graphics.ImageDescriptor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.graphics.impl;

import com.jiuqi.nvwa.grid2.graphics.ImageData;
import com.jiuqi.nvwa.grid2.graphics.ImageDescriptor;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageDescriptorImpl
implements ImageDescriptor {
    private static final long serialVersionUID = 1L;
    private String imageId;
    private int width;
    private int height;
    private ImageData imageData;
    private String URI;

    public String getImageId() {
        return this.imageId;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ImageData getImageData() throws IOException {
        return this.imageData;
    }

    public void dispose() {
    }

    public String getURI() {
        return this.URI;
    }

    public JSONObject toClientObject() throws JSONException {
        return null;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public void setURI(String uRI) {
        this.URI = uRI;
    }
}

