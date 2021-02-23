package hu.dungeonhunter.tools;
import lombok.Getter;
import lombok.Setter;

public class TextSeparator {

    @Setter
    public String text;

    public TextSeparator() {
        this.text = "";
        codeOfTheTextSeparator();
    }


    public TextSeparator(String text) {
        this.text = " " + text + " ";
        codeOfTheTextSeparator();
    }

    private void codeOfTheTextSeparator() {
        int textLength = text.length() + 2;
        int starsNumber;
        int lineLength = 80;
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
}
