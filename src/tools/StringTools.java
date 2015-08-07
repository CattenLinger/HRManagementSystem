package tools;

import java.util.Date;
import java.util.Random;

/**
 * Created by TEACH on 15-7-2.
 */
public class StringTools {
    private static Random random = new Random(new Date().getTime());
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            //System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String RandomNumber(){
        return String.format("%d",Math.round(random.nextDouble() * 322678));
    }
}
