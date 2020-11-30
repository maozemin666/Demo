package com.example.myapplication.haikang.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeviceListResponse {
    /**
     * code : 0
     * msg : success
     * data : {"total":13,"pageNo":1,"pageSize":1,"list":[{"altitude":null,"cameraIndexCode":"eddf8458f74d42e9bf4ecfc752dba146","cameraName":"3层吉米后厨入口","cameraType":0,"cameraTypeName":"枪机","capabilitySet":"io,event_io,event_ias,event_rule,event_heat,record,net,event_face,vss,ptz,status,maintenance,event_device","capabilitySetName":null,"intelligentSet":null,"intelligentSetName":null,"channelNo":"33","channelType":"digital","channelTypeName":"数字通道","createTime":"2018-09-15T11:14:27.812+08:00","encodeDevIndexCode":"1d3d5c26e6174cf1aa452f57cac91879","encodeDevResourceType":null,"encodeDevResourceTypeName":null,"gbIndexCode":null,"installLocation":null,"keyBoardCode":null,"latitude":null,"longitude":null,"pixel":null,"ptz":null,"ptzName":null,"ptzController":null,"ptzControllerName":null,"recordLocation":null,"recordLocationName":null,"regionIndexCode":"2feadc43-ffef-464b-a2e2-b146a02de5ba","status":null,"statusName":null,"transType":1,"transTypeName":"TCP","treatyType":null,"treatyTypeName":null,"viewshed":null,"updateTime":"2018-09-15T11:19:48.973+08:00"}]}
     */

    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * total : 13
         * pageNo : 1
         * pageSize : 1
         * list : [{"altitude":null,"cameraIndexCode":"eddf8458f74d42e9bf4ecfc752dba146","cameraName":"3层吉米后厨入口","cameraType":0,"cameraTypeName":"枪机","capabilitySet":"io,event_io,event_ias,event_rule,event_heat,record,net,event_face,vss,ptz,status,maintenance,event_device","capabilitySetName":null,"intelligentSet":null,"intelligentSetName":null,"channelNo":"33","channelType":"digital","channelTypeName":"数字通道","createTime":"2018-09-15T11:14:27.812+08:00","encodeDevIndexCode":"1d3d5c26e6174cf1aa452f57cac91879","encodeDevResourceType":null,"encodeDevResourceTypeName":null,"gbIndexCode":null,"installLocation":null,"keyBoardCode":null,"latitude":null,"longitude":null,"pixel":null,"ptz":null,"ptzName":null,"ptzController":null,"ptzControllerName":null,"recordLocation":null,"recordLocationName":null,"regionIndexCode":"2feadc43-ffef-464b-a2e2-b146a02de5ba","status":null,"statusName":null,"transType":1,"transTypeName":"TCP","treatyType":null,"treatyTypeName":null,"viewshed":null,"updateTime":"2018-09-15T11:19:48.973+08:00"}]
         */

        @SerializedName("total")
        private Integer total;
        @SerializedName("pageNo")
        private Integer pageNo;
        @SerializedName("pageSize")
        private Integer pageSize;
        @SerializedName("list")
        private List<ListDTO> list;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            /**
             * altitude : null
             * cameraIndexCode : eddf8458f74d42e9bf4ecfc752dba146
             * cameraName : 3层吉米后厨入口
             * cameraType : 0
             * cameraTypeName : 枪机
             * capabilitySet : io,event_io,event_ias,event_rule,event_heat,record,net,event_face,vss,ptz,status,maintenance,event_device
             * capabilitySetName : null
             * intelligentSet : null
             * intelligentSetName : null
             * channelNo : 33
             * channelType : digital
             * channelTypeName : 数字通道
             * createTime : 2018-09-15T11:14:27.812+08:00
             * encodeDevIndexCode : 1d3d5c26e6174cf1aa452f57cac91879
             * encodeDevResourceType : null
             * encodeDevResourceTypeName : null
             * gbIndexCode : null
             * installLocation : null
             * keyBoardCode : null
             * latitude : null
             * longitude : null
             * pixel : null
             * ptz : null
             * ptzName : null
             * ptzController : null
             * ptzControllerName : null
             * recordLocation : null
             * recordLocationName : null
             * regionIndexCode : 2feadc43-ffef-464b-a2e2-b146a02de5ba
             * status : null
             * statusName : null
             * transType : 1
             * transTypeName : TCP
             * treatyType : null
             * treatyTypeName : null
             * viewshed : null
             * updateTime : 2018-09-15T11:19:48.973+08:00
             */

            @SerializedName("altitude")
            private Object altitude;
            @SerializedName("cameraIndexCode")
            private String cameraIndexCode;
            @SerializedName("cameraName")
            private String cameraName;
            @SerializedName("cameraType")
            private Integer cameraType;
            @SerializedName("cameraTypeName")
            private String cameraTypeName;
            @SerializedName("capabilitySet")
            private String capabilitySet;
            @SerializedName("capabilitySetName")
            private Object capabilitySetName;
            @SerializedName("intelligentSet")
            private Object intelligentSet;
            @SerializedName("intelligentSetName")
            private Object intelligentSetName;
            @SerializedName("channelNo")
            private String channelNo;
            @SerializedName("channelType")
            private String channelType;
            @SerializedName("channelTypeName")
            private String channelTypeName;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("encodeDevIndexCode")
            private String encodeDevIndexCode;
            @SerializedName("encodeDevResourceType")
            private Object encodeDevResourceType;
            @SerializedName("encodeDevResourceTypeName")
            private Object encodeDevResourceTypeName;
            @SerializedName("gbIndexCode")
            private Object gbIndexCode;
            @SerializedName("installLocation")
            private Object installLocation;
            @SerializedName("keyBoardCode")
            private Object keyBoardCode;
            @SerializedName("latitude")
            private Object latitude;
            @SerializedName("longitude")
            private Object longitude;
            @SerializedName("pixel")
            private Object pixel;
            @SerializedName("ptz")
            private Object ptz;
            @SerializedName("ptzName")
            private Object ptzName;
            @SerializedName("ptzController")
            private Object ptzController;
            @SerializedName("ptzControllerName")
            private Object ptzControllerName;
            @SerializedName("recordLocation")
            private Object recordLocation;
            @SerializedName("recordLocationName")
            private Object recordLocationName;
            @SerializedName("regionIndexCode")
            private String regionIndexCode;
            @SerializedName("status")
            private Object status;
            @SerializedName("statusName")
            private Object statusName;
            @SerializedName("transType")
            private Integer transType;
            @SerializedName("transTypeName")
            private String transTypeName;
            @SerializedName("treatyType")
            private Object treatyType;
            @SerializedName("treatyTypeName")
            private Object treatyTypeName;
            @SerializedName("viewshed")
            private Object viewshed;
            @SerializedName("updateTime")
            private String updateTime;

            public Object getAltitude() {
                return altitude;
            }

            public void setAltitude(Object altitude) {
                this.altitude = altitude;
            }

            public String getCameraIndexCode() {
                return cameraIndexCode;
            }

            public void setCameraIndexCode(String cameraIndexCode) {
                this.cameraIndexCode = cameraIndexCode;
            }

            public String getCameraName() {
                return cameraName;
            }

            public void setCameraName(String cameraName) {
                this.cameraName = cameraName;
            }

            public Integer getCameraType() {
                return cameraType;
            }

            public void setCameraType(Integer cameraType) {
                this.cameraType = cameraType;
            }

            public String getCameraTypeName() {
                return cameraTypeName;
            }

            public void setCameraTypeName(String cameraTypeName) {
                this.cameraTypeName = cameraTypeName;
            }

            public String getCapabilitySet() {
                return capabilitySet;
            }

            public void setCapabilitySet(String capabilitySet) {
                this.capabilitySet = capabilitySet;
            }

            public Object getCapabilitySetName() {
                return capabilitySetName;
            }

            public void setCapabilitySetName(Object capabilitySetName) {
                this.capabilitySetName = capabilitySetName;
            }

            public Object getIntelligentSet() {
                return intelligentSet;
            }

            public void setIntelligentSet(Object intelligentSet) {
                this.intelligentSet = intelligentSet;
            }

            public Object getIntelligentSetName() {
                return intelligentSetName;
            }

            public void setIntelligentSetName(Object intelligentSetName) {
                this.intelligentSetName = intelligentSetName;
            }

            public String getChannelNo() {
                return channelNo;
            }

            public void setChannelNo(String channelNo) {
                this.channelNo = channelNo;
            }

            public String getChannelType() {
                return channelType;
            }

            public void setChannelType(String channelType) {
                this.channelType = channelType;
            }

            public String getChannelTypeName() {
                return channelTypeName;
            }

            public void setChannelTypeName(String channelTypeName) {
                this.channelTypeName = channelTypeName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getEncodeDevIndexCode() {
                return encodeDevIndexCode;
            }

            public void setEncodeDevIndexCode(String encodeDevIndexCode) {
                this.encodeDevIndexCode = encodeDevIndexCode;
            }

            public Object getEncodeDevResourceType() {
                return encodeDevResourceType;
            }

            public void setEncodeDevResourceType(Object encodeDevResourceType) {
                this.encodeDevResourceType = encodeDevResourceType;
            }

            public Object getEncodeDevResourceTypeName() {
                return encodeDevResourceTypeName;
            }

            public void setEncodeDevResourceTypeName(Object encodeDevResourceTypeName) {
                this.encodeDevResourceTypeName = encodeDevResourceTypeName;
            }

            public Object getGbIndexCode() {
                return gbIndexCode;
            }

            public void setGbIndexCode(Object gbIndexCode) {
                this.gbIndexCode = gbIndexCode;
            }

            public Object getInstallLocation() {
                return installLocation;
            }

            public void setInstallLocation(Object installLocation) {
                this.installLocation = installLocation;
            }

            public Object getKeyBoardCode() {
                return keyBoardCode;
            }

            public void setKeyBoardCode(Object keyBoardCode) {
                this.keyBoardCode = keyBoardCode;
            }

            public Object getLatitude() {
                return latitude;
            }

            public void setLatitude(Object latitude) {
                this.latitude = latitude;
            }

            public Object getLongitude() {
                return longitude;
            }

            public void setLongitude(Object longitude) {
                this.longitude = longitude;
            }

            public Object getPixel() {
                return pixel;
            }

            public void setPixel(Object pixel) {
                this.pixel = pixel;
            }

            public Object getPtz() {
                return ptz;
            }

            public void setPtz(Object ptz) {
                this.ptz = ptz;
            }

            public Object getPtzName() {
                return ptzName;
            }

            public void setPtzName(Object ptzName) {
                this.ptzName = ptzName;
            }

            public Object getPtzController() {
                return ptzController;
            }

            public void setPtzController(Object ptzController) {
                this.ptzController = ptzController;
            }

            public Object getPtzControllerName() {
                return ptzControllerName;
            }

            public void setPtzControllerName(Object ptzControllerName) {
                this.ptzControllerName = ptzControllerName;
            }

            public Object getRecordLocation() {
                return recordLocation;
            }

            public void setRecordLocation(Object recordLocation) {
                this.recordLocation = recordLocation;
            }

            public Object getRecordLocationName() {
                return recordLocationName;
            }

            public void setRecordLocationName(Object recordLocationName) {
                this.recordLocationName = recordLocationName;
            }

            public String getRegionIndexCode() {
                return regionIndexCode;
            }

            public void setRegionIndexCode(String regionIndexCode) {
                this.regionIndexCode = regionIndexCode;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getStatusName() {
                return statusName;
            }

            public void setStatusName(Object statusName) {
                this.statusName = statusName;
            }

            public Integer getTransType() {
                return transType;
            }

            public void setTransType(Integer transType) {
                this.transType = transType;
            }

            public String getTransTypeName() {
                return transTypeName;
            }

            public void setTransTypeName(String transTypeName) {
                this.transTypeName = transTypeName;
            }

            public Object getTreatyType() {
                return treatyType;
            }

            public void setTreatyType(Object treatyType) {
                this.treatyType = treatyType;
            }

            public Object getTreatyTypeName() {
                return treatyTypeName;
            }

            public void setTreatyTypeName(Object treatyTypeName) {
                this.treatyTypeName = treatyTypeName;
            }

            public Object getViewshed() {
                return viewshed;
            }

            public void setViewshed(Object viewshed) {
                this.viewshed = viewshed;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
/*    {
        "code": "0",
            "msg": "success",
            "data": {
        "total": 13,
                "pageNo": 1,
                "pageSize": 1,
                "list": [
        {
            "altitude": null,
                "cameraIndexCode": "eddf8458f74d42e9bf4ecfc752dba146",
                "cameraName": "3层吉米后厨入口",
                "cameraType": 0,
                "cameraTypeName": "枪机",
                "capabilitySet": "io,event_io,event_ias,event_rule,event_heat,record,net,event_face,vss,ptz,status,maintenance,event_device",
                "capabilitySetName": null,
                "intelligentSet": null,
                "intelligentSetName": null,
                "channelNo": "33",
                "channelType": "digital",
                "channelTypeName": "数字通道",
                "createTime": "2018-09-15T11:14:27.812+08:00",
                "encodeDevIndexCode": "1d3d5c26e6174cf1aa452f57cac91879",
                "encodeDevResourceType": null,
                "encodeDevResourceTypeName": null,
                "gbIndexCode": null,
                "installLocation": null,
                "keyBoardCode": null,
                "latitude": null,
                "longitude": null,
                "pixel": null,
                "ptz": null,
                "ptzName": null,
                "ptzController": null,
                "ptzControllerName": null,
                "recordLocation": null,
                "recordLocationName": null,
                "regionIndexCode": "2feadc43-ffef-464b-a2e2-b146a02de5ba",
                "status": null,
                "statusName": null,
                "transType": 1,
                "transTypeName": "TCP",
                "treatyType": null,
                "treatyTypeName": null,
                "viewshed": null,
                "updateTime": "2018-09-15T11:19:48.973+08:00"
        }
        ]
    }
    }*/

}
