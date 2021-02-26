package hu.dungeonhunter.utils;

public class TextSeparator {

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

    private static int colorRemoveTextLength(String colorText){
        //TODO hogy tudok ebből listát csinálni?
        String emptyText = colorText.replace(Colors.ANSI_RED,"");
        emptyText = emptyText.replace(Colors.ANSI_RESET, "");
        return emptyText.length();
    }
}