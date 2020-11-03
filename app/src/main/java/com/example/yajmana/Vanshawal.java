package com.example.yajmana;

public class Vanshawal {
    private String vanshawal_no, email;
    private String late_fullname, first_name, middle_name, last_name;
    private String city, relation, yajman, mother, father;
    private String taluka, grandpa, panjoba, nipanjoba;
    private String district, real_uncle, cousin_uncle, real_uncle_son, cousin_uncle_son;
    private String caste, real_brother, real_brother_son, real_son;
    private String death_anniversary, tip;
    private String mobile;
    private String gender;
    private String signatureImagePath;


    public Vanshawal(String vanshawal_no, String late_fullname, String city, String taluka, String district, String caste, String death_anniversary, String mobile, String gender) {
        this.vanshawal_no = vanshawal_no;
        this.late_fullname = late_fullname;
        this.city = city;
        this.taluka = taluka;
        this.district = district;
        this.caste = caste;
        this.death_anniversary = death_anniversary;
        this.mobile = mobile;
        this.gender = gender;
    }

    public String getVanshawal_no() { return vanshawal_no; }

    public String getLate_fullname() {
        return late_fullname;
    }

    public String getFullName(){
        return first_name +' '+ middle_name +' '+ last_name;
    }

    public String getCity() {
        return city;
    }

    public String getTaluka() {
        return taluka;
    }

    public String getDistrict() {
        return district;
    }

    public String getCaste() {
        return caste;
    }

    public String getDeath_anniversary() {
        return death_anniversary;
    }

    public String getMobile() {
        return mobile;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() { return email; }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRelation() {
        return relation;
    }

    public String getYajman() {
        return yajman;
    }

    public String getMother() {
        return mother;
    }

    public String getFather() {
        return father;
    }

    public String getGrandpa() {
        return grandpa;
    }

    public String getPanjoba() {
        return panjoba;
    }

    public String getNipanjoba() {
        return nipanjoba;
    }

    public String getReal_uncle() {
        return real_uncle;
    }

    public String getCousin_uncle() {
        return cousin_uncle;
    }

    public String getReal_uncle_son() {
        return real_uncle_son;
    }

    public String getCousin_uncle_son() {
        return cousin_uncle_son;
    }

    public String getReal_brother() {
        return real_brother;
    }

    public String getReal_brother_son() {
        return real_brother_son;
    }

    public String getReal_son() {
        return real_son;
    }

    public String getTip() {
        return tip;
    }

    public String getSignatureImagePath() {
        return signatureImagePath;
    }

}
