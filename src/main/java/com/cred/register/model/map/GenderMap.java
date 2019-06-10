package com.cred.register.model.map;

public enum GenderMap {

    M("Male"),
    F("Female");

    private String param;

    GenderMap(String param){this.param = param;}

    public String param() {
        return param;
    }

    public static String get(String key){
        for(GenderMap n : GenderMap.values()){
            if(key.equals(n.param()))
                return n.name();
        }
        return null;
    }


}
