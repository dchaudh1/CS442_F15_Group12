package com.cafetaria.cafe;

/**
 * Created by Smruti on 10/21/2015.
 */
public class DataProvider {

    private  String Name;
    private  Integer Price;
    private String Desc;
    private  Integer id;

    public DataProvider(Integer id,String Name, Integer Price, String Desc)
    {
        this.id=id;
        this.Name = Name;
        this.Price =Price;
        this.Desc = Desc;

    }

    public void setID(Integer id)
    {
        this.id = id;
    }

    public Integer getID()
    {
        return id;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public String getName()
    {
        return Name;
    }

    public void setPrice(Integer Price)
    {
        this.Price = Price;
    }

    public Integer getPrice()
    {
        return Price;
    }

    public void setDesc(String Desc)
    {
        this.Name = Name;
    }

    public String getDesc()
    {
        return Desc;
    }
}
