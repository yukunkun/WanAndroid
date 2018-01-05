package com.yukunkun.wanandroid.enerty;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by yukun on 18-1-5.
 */

public class UesrInfo extends DataSupport{


    /**
     * errorCode : 0
     * errorMsg : null
     * data : {"id":1542,"username":"yukunkun","password":"123456789ykk","icon":null,"type":0,"collectIds":[1819,1824,1831,1837]}
     */
    /*
    * Set-Cookie:loginUserName=yukunkun;
        Set-Cookie:loginUserPassword=123456789ykk;
    * */

    private int errorCode;
    private Object errorMsg;
    /**
     * id : 1542
     * username : yukunkun
     * password : 123456789ykk
     * icon : null
     * type : 0
     * collectIds : [1819,1824,1831,1837]
     */

    private DataBean data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String username;
        private String password;
        private Object icon;
        private int type;
        private List<Integer> collectIds;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<Integer> getCollectIds() {
            return collectIds;
        }

        public void setCollectIds(List<Integer> collectIds) {
            this.collectIds = collectIds;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", icon=" + icon +
                    ", type=" + type +
                    ", collectIds=" + collectIds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UesrInfo{" +
                "errorCode=" + errorCode +
                ", errorMsg=" + errorMsg +
                ", data=" + data +
                '}';
    }
}
