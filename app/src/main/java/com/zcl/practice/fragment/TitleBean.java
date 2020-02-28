package com.zcl.practice.fragment;


public class TitleBean {

    private int id;//主键
    private String title;//标题
    private int type;//类型

    
    public TitleBean(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
