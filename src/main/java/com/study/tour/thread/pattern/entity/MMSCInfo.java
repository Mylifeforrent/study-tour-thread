package com.study.tour.thread.pattern.entity;


/**
 * 彩信中心信息(不可变对象)
 * 模式角色: ImmutableObject.ImmutableObject
 */
public final class MMSCInfo {

    /**
     * 设备编号
     */
    private final String deviceID;

    /**
     * 彩信中心url
     */
    private final String url;

    /**
     * 该彩信中心允许的最大附件大小
     */
    private final int maxAttachmentSizeInBytes;


    public MMSCInfo(String deviceID, String url, int maxAttachmentSizeInBytes) {
        this.deviceID = deviceID;
        this.url = url;
        this.maxAttachmentSizeInBytes = maxAttachmentSizeInBytes;
    }

    public MMSCInfo(MMSCInfo prototype) {
        this.deviceID = prototype.deviceID;
        this.url = prototype.url;
        this.maxAttachmentSizeInBytes = prototype.maxAttachmentSizeInBytes;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmentSizeInBytes() {
        return maxAttachmentSizeInBytes;
    }
}
