package videoGame;

import java.awt.image.BufferedImage;

/**
 * Class that contains an sprite-sheet image and can divide it in specific 
 * sprites
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class SpriteSheet {
    
    private BufferedImage sheet;//original sprite-sheet
    
    /**
     * Constructor that requires an  Image sheet to be assigned to the 
     * SpriteSheet object
     * @param sheet <b>Image</b> that contains the sprite sheet
     */
    public SpriteSheet(BufferedImage sheet){
        this.sheet = sheet;
    }
    
    /**
     * Method that can crop specific sprites form the sprite-sheet image, given
     * a position in the image, a width, and a length. (top left coordinates)
     * @param x Integer for the <b>x</b> position of the cropped image
     * @param y Integer for the <b>y</b> position of the cropped image
     * @param width Integer for the <b>width</b> of the cropped image
     * @param height Integer for the <b>height</b> of the cropped image
     * @return The cropped Image as <b>BufferedImage</b>
     */
    public BufferedImage crop (int x, int y, int width, int height){
        return sheet.getSubimage(x, y, width, height);
    }
}
