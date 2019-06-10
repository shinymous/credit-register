package com.cred.register.util;

import static java.util.Objects.isNull;

public class CpfUtil {

    public static String removeSpecialCharacters(String cpf){
        if(cpf != null && cpf.length() > 0 ){
            return cpf.replace(".", "").replace("/","").replace("-", "");
        }else {
            return cpf;
        }
    }


    public static boolean isCpfValid(String fullCpf){
        String cpf = removeSpecialCharacters(fullCpf);
        if(isNull(cpf)){
            return false;
        }

        if (cpf.length() == 11 && !cpf.equals("00000000000") )
        {
            int     d1, d2;
            int     digit1, digit2, remainder;
            int     cpfDigit;
            String  nDigResult;
            d1 = d2 = 0;
            digit1 = digit2 = remainder = 0;
            for (int n_Count = 1; n_Count < cpf.length() -1; n_Count++)
            {
                cpfDigit = Integer.valueOf (cpf.substring(n_Count -1, n_Count)).intValue();
                d1 = d1 + ( 11 - n_Count ) * cpfDigit;
                d2 = d2 + ( 12 - n_Count ) * cpfDigit;
            };
            remainder = (d1 % 11);
            if (remainder < 2)
                digit1 = 0;
            else
                digit1 = 11 - remainder;
            d2 += 2 * digit1;
            remainder = (d2 % 11);
            if (remainder < 2)
                digit2 = 0;
            else
                digit2 = 11 - remainder;
            String nDigVerific = cpf.substring (cpf.length()-2, cpf.length());
            nDigResult = String.valueOf(digit1) + String.valueOf(digit2);
            return nDigVerific.equals(nDigResult);
        }

        return false;
    }

}
