package lee.com.echoclud;

import java.util.Arrays;


public class Main {

    public static void main(String[] args) {

//        int green = 0x00FFFF00;
//        int red = 0x00FF0000;

        System.out.println("----------- run ----------");
//        System.out.println(Integer.toString(green, 16));
//        System.out.println(Integer.toString(red, 16));
//        System.out.println(Integer.toString(red|green, 16));
//        System.out.println(Integer.toString(red^green, 16));
//        System.out.println(Integer.toString((red^green)&green, 16));

        String s = "a|b|c|d|e|f|g";

        System.out.println(Arrays.toString(s.split("|")));

    }

}
