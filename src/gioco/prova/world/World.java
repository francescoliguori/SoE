/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.world;
import gioco.prova.Handler;
import gioco.prova.gfx.ImageLoader;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 *
 * @author Utente
 */
public class World {
    private int width, height;
    private int[][]tiles;
    private Handler handler;
    private BufferedImage background;
 
    public World(Handler handler, String path)
    {
       this.handler = handler;
       //background=ImageLoader.loadImage(path);
        
    }
    
    public void tick()
    {
        
    }
    public void render(Graphics g)
    {
        g.drawImage(ImageLoader.loadImage("/background1.png"),0,0,1000,700,null);
    }
    
  
    private void loadWorld(String path)
    {
     //metodo utilizzato per caricare il mondo dal file  
        
        
            
       
    }

}
