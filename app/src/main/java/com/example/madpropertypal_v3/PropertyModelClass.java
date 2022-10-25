package com.example.madpropertypal_v3;

public class PropertyModelClass {

    Integer id;
    String name;
    String protype;
    String leasetype;
    String bednum;
    String bathnum;
    String size;
    String price;
    String garden;
    String drive;
    String local;
    String des;


    public PropertyModelClass(String name, String protype, String leasetype, String bednum,
                              String bathnum, String size, String price, String garden,
                              String drive, String local, String des) {
        this.name = name;
        this.protype = protype;
        this.leasetype = leasetype;
        this.bednum = bednum;
        this.bathnum = bathnum;
        this.size = size;
        this.price = price;
        this.garden = garden;
        this.drive = drive;
        this.local = local;
        this.des = des;
    }



    public PropertyModelClass(Integer id, String name, String protype, String leasetype, String bednum,
                              String bathnum, String size, String price, String garden,
                              String drive, String local, String des) {
        this.id = id;
        this.name = name;
        this.protype = protype;
        this.leasetype = leasetype;
        this.bednum = bednum;
        this.bathnum = bathnum;
        this.size = size;
        this.price = price;
        this.garden = garden;
        this.drive = drive;
        this.local = local;
        this.des = des;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtype() {
        return protype;
    }

    public void setProtype(String protype) {
        this.protype = protype;
    }

    public String getLeasetype() {
        return leasetype;
    }

    public void setLeasetype(String leasetype) {
        this.leasetype = leasetype;
    }

    public String getBednum() {
        return bednum;
    }

    public void setBednum(String bednum) {
        this.bednum = bednum;
    }

    public String getBathnum() {
        return bathnum;
    }

    public void setBathnum(String bathnum) {
        this.bathnum = bathnum;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


}
