package com.xyliwp.zhihuixiyou.utils;

/**
 * 课表的实例
 * Created by lwp940118 on 2016/10/31.
 */
public class KeBiao {

    private String kechengming;
    private String teacher;
    private String didian;
    private String jieci;
    private int zhouji;

    public String getKechengming() {
        return kechengming;
    }

    public void setKechengming(String kechengming) {
        this.kechengming = kechengming;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDidian() {
        return didian;
    }

    public void setDidian(String didian) {
        this.didian = didian;
    }

    public String getJieci() {
        return jieci;
    }

    public void setJieci(String jieci) {
        this.jieci = jieci;
    }

    public int getZhouji() {
        return zhouji;
    }

    public void setZhouji(int zhouji) {
        this.zhouji = zhouji;
    }

    public String toString(){
        return kechengming+"\n"+teacher+"@"+didian;
    }
}
