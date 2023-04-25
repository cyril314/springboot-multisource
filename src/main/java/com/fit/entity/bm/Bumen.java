package com.fit.entity.bm;

import java.util.List;

/**
 * 部门管理实体类
 */
public class Bumen {

    private String BUMEN_ID;    //主键
    private String NAME;                    //名称
    private String PARENT_ID;                //父类ID
    private String target;
    private Bumen bumen;
    private List<Bumen> subBumen;
    private boolean hasBumen = false;
    private String treeurl;

    private String BNAME;            //部门名称

    public String getFBNAME() {
        return BNAME;
    }

    public void setFBNAME(String BNAME) {
        this.BNAME = BNAME;
    }

    public String getBUMEN_ID() {
        return BUMEN_ID;
    }

    public void setBUMEN_ID(String BUMEN_ID) {
        this.BUMEN_ID = BUMEN_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Bumen getBumen() {
        return bumen;
    }

    public void setBumen(Bumen bumen) {
        this.bumen = bumen;
    }

    public List<Bumen> getSubBumen() {
        return subBumen;
    }

    public void setSubBumen(List<Bumen> subBumen) {
        this.subBumen = subBumen;
    }

    public boolean isHasBumen() {
        return hasBumen;
    }

    public void setHasBumen(boolean hasBumen) {
        this.hasBumen = hasBumen;
    }

    public String getTreeurl() {
        return treeurl;
    }

    public void setTreeurl(String treeurl) {
        this.treeurl = treeurl;
    }

}
