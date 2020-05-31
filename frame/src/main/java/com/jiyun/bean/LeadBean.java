package com.jiyun.bean;

public class LeadBean {

    /**
     * errNo : 0
     * result : {"ad_id":"12572","end_time":"1590854400","height":"750","info_url":"http://newoss.zhulong.com/ad/202005/07/32/162532vf0thwgshmh9333a.jpg","jump_url":"https://edu.zhulong.com/cuxiao/bim_zt.html?f=app_qdt_bim_0","place_name":"APP_QD_01","prof":"bim","start_time":"1577376000","title":"全国BIM技能等级考试","type":"2","wb":"0","width":"750"}
     */

    private int errNo;
    private ResultBean result;

    public int getErrNo() {
        return errNo;
    }

    public void setErrNo(int errNo) {
        this.errNo = errNo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * ad_id : 12572
         * end_time : 1590854400
         * height : 750
         * info_url : http://newoss.zhulong.com/ad/202005/07/32/162532vf0thwgshmh9333a.jpg
         * jump_url : https://edu.zhulong.com/cuxiao/bim_zt.html?f=app_qdt_bim_0
         * place_name : APP_QD_01
         * prof : bim
         * start_time : 1577376000
         * title : 全国BIM技能等级考试
         * type : 2
         * wb : 0
         * width : 750
         */

        private String ad_id;
        private String end_time;
        private String height;
        private String info_url;
        private String jump_url;
        private String place_name;
        private String prof;
        private String start_time;
        private String title;
        private String type;
        private String wb;
        private String width;

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getInfo_url() {
            return info_url;
        }

        public void setInfo_url(String info_url) {
            this.info_url = info_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getProf() {
            return prof;
        }

        public void setProf(String prof) {
            this.prof = prof;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWb() {
            return wb;
        }

        public void setWb(String wb) {
            this.wb = wb;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }
}
