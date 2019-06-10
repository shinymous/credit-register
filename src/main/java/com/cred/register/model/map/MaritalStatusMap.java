package com.cred.register.model.map;

public enum MaritalStatusMap {

    SINGLE("Single"),
    MARRIED("Married"),
    SEPARATE("Separate"),
    DIVORCED("Divorced"),
    WIDOWER("WIDOWER");


    private String param;

    MaritalStatusMap(String param){this.param = param;}

    public String param() {
        return param;
    }

    public static String get(String key){
        for(MaritalStatusMap n : MaritalStatusMap.values()){
            if(key.equals(n.param()))
                return n.name();
        }
        return null;
    }
}
