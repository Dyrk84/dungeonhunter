package hu.dungeonhunter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TextSeparator {

    private static final String ANSI_REGEX = "\\x1b\\[[0-9;]+m";

    public static void format(String text) {
        if (text.length() != 0)
            text = " " + text + " ";
        int textLength = colorRemoveTextLength(text) + 2;
        int starsNumber;
        int lineLength = 100;
        int lineFirstHalf = ((lineLength - textLength) / 2) + (textLength % 2);
        int lineLastHalf = ((lineLength - textLength) / 2);

        // first stars line
        for (starsNumber = 0; starsNumber < lineFirstHalf; starsNumber++) {
            System.out.print("*");
        }
        // text on middle of the line
        System.out.print(text);
        // last stars line
        for (starsNumber = 0; starsNumber < lineLastHalf; starsNumber++) {
            System.out.print("*");
        }
        // the line ending star
        System.out.println("*");
    }

    private static int colorRemoveTextLength(String colorText) {

        List<String> colorList = new ArrayList<>();
        colorList.add(Colors.ANSI_RED);
        colorList.add(Colors.ANSI_BLACK);
        colorList.add(Colors.ANSI_BLUE);
        colorList.add(Colors.ANSI_PURPLE);
        colorList.add(Colors.ANSI_WHITE);
        colorList.add(Colors.ANSI_CYAN);
        colorList.add(Colors.ANSI_GREEN);
        colorList.add(Colors.ANSI_YELLOW);
        colorList.add(Colors.ANSI_RESET);
//
////        Iterator<String> colorsIterator = colorList.iterator();
////        //TODO meg lehet azt csinálni valahogy, hogy a változtatandó string helyére több stringet is be lehessen írni?
////        String emptyText = colorText.replace(colorsIterator.next(),"");
//        String emptyText1S = colorText.replace(colorList.get(0), "");
//        int emptyText1 = emptyText1S.length();
//        String emptyText2S = colorText.replace(Colors.ANSI_RESET, "");
//        int emptyText2 = emptyText2S.length();
//        String emptyText3S = colorText.replace(Colors.ANSI_RED, "");
//        int emptyText3 = emptyText3S.length();

        //TODO https://www.baeldung.com/java-enum-iteration Enumként végigiterálni rajta!

        return colorText.replaceAll(ANSI_REGEX, "").length(); //ansi kódos megoldás

        //listán iterálással végigmenni rajta
//        String emptyText = colorText;
//        for (int i = 0; i < colorList.size(); i++) {
//            emptyText = emptyText.replace(colorList.get(i), "");
//        }
//        return emptyText.length();
    }
}