import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provides utility methods for image loading, font creation,
 * and word masking used in the Hangman game.
 */
public class CustomTools {

    // Loads an image from the given resource path
    public static JLabel loadImage(String resource){
        BufferedImage image;
        try{
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    // Updates the icon of an existing JLabel with a new image
    public static void updateImage(JLabel imageContainer, String resource){
        BufferedImage image;
        try{
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    // Creates and returns a custom font from the provided resource path
    public static Font createFont(String resource){
        // Get font file path
        String filePath = CustomTools.class.getClassLoader().getResource(resource).getPath();
        // Fix file path spaces
        if(filePath.contains("%20")){
            filePath = filePath.replaceAll("%20", " ");
        }
        try{
            File customFontFile = new File(filePath);
            return Font.createFont(Font.TRUETYPE_FONT, customFontFile);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    // Returns the word in a hidden form using asterisks (*), preserving spaces
    public static String hideWords(String word){
        String hiddenWord = "";
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) != ' '){
                hiddenWord += "*";
            }else{
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }
}
