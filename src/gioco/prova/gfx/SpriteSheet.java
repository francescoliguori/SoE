/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.image.BufferedImage;

/*Questa classe rappresenta uno spritesheet. Il metodo pubblico crop viene utilizzato per 
prendere un oggetto di tipo BufferedImage dallo spritesheet sfruttando l'area dello 
spritesheet individuato dai 4 parametri in ingresso.*/
public class SpriteSheet 
{
    
    private BufferedImage sheet;
    
    public SpriteSheet(BufferedImage sheet)
    {
        this.sheet = sheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height)
    {
        return sheet.getSubimage(x, y, width, height);
    }
}
