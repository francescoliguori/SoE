/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*La classe ImageLoader viene utilizzata per caricare le immeagini del gioco.
In Java le immagini sono salvat in un oggetto di tipo BufferedImage. Il metodo
loadImage ritorna quindi una BufferedImage a partire da un certo path.
When you write .class after a class name, it references the class literal - java.lang.Class object
that represents information about given class. For example, if your class is Print,
then Print.class is an object that represents the class Print on runtime. It is the same object 
that is returned by the getClass() method of any (direct) instance of Print.*/
public class ImageLoader 
{
   
    public static BufferedImage loadImage(String path)
    {
        try 
        {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException ex) 
        {
            Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        return null;
        }
}
