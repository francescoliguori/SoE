/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author marcoruggiero
 */
/*Un asset è una qualsiasi immagine o canzone all'interno del gioco. Al momento 
si gestiscono solamente le immagini perché le canzoni non sono ancora presenti*/
public class Assets {
    
    //dimensioni di ogni elemento della "griglia" spritesheet
    private static final int width = 155, height = 187;
    public static BufferedImage player;
    public static BufferedImage[] playerRunning;
    public static BufferedImage[] playerJump;
    public static BufferedImage[] playerStop;
    public static BufferedImage[] playerDown; //my add
    public static BufferedImage[] enemies1;
    public static BufferedImage[] enemies2;
    public static BufferedImage[] fireballJutsu;
    public static void init()
    {   
        //si crea lo spritesheet da utilizzare
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/itachi_jump_piccolo.png"));
        SpriteSheet sheetEnemy= new SpriteSheet(ImageLoader.loadImage("/enemies5.png"));
        SpriteSheet sheetFireball = new SpriteSheet(ImageLoader.loadImage("/fireball.png"));
        
        playerRunning = new BufferedImage[6];
        playerJump = new BufferedImage[8];
        playerStop = new BufferedImage[3];
        playerDown = new BufferedImage[4]; //my add
        enemies1=new BufferedImage[6];
        enemies2=new BufferedImage[8];
        fireballJutsu= new BufferedImage[7];
        player = sheet.crop(0, 0 , width, height);
        
        for(int i=0;i<=5;i++){
            playerRunning[i] = sheet.crop(width*i, height, width, height);
        }
        /*playerRunning[0] = sheet.crop(0, height, width, height);
        playerRunning[1] = sheet.crop(width * 1, height, width, height);
	playerRunning[2] = sheet.crop(width * 2, height, width, height);
	playerRunning[3] = sheet.crop(width * 3, height, width, height);
	playerRunning[4] = sheet.crop(width * 4, height, width, height);
        playerRunning[5] = sheet.crop(width * 5, height, width, height);*/
        for(int i=0;i<=7;i++){
            playerJump[i] = sheet.crop(width*i, height*3, width, height);
        }
        /*playerJump[0] = sheet.crop(0, height, width, height);
        playerJump[1] = sheet.crop(width * 1, height*3, width, height);
	playerJump[2] = sheet.crop(width * 2, height*3, width, height);
	playerJump[3] = sheet.crop(width * 3, height*3, width, height);
	playerJump[4] = sheet.crop(width * 4, height*3, width, height);
        playerJump[5] = sheet.crop(width * 5, height*3, width, height);
        playerJump[6] = sheet.crop(width * 6, height*3, width, height);
        playerJump[7] = sheet.crop(width * 7, height*3, width, height);*/
        playerStop[0] = sheet.crop(0, height * 2, width, height);
        for(int i=1;i<=3;i++){
            playerDown[i] = sheet.crop(width*i, height*4, width, height);
        }
        /*playerDown[0] = sheet.crop(0, height*4, width, height);  //my add 
        playerDown[0] = sheet.crop(width * 1, height*4, width, height);
	playerDown[1] = sheet.crop(width * 2, height*4, width, height);
        playerDown[2] = sheet.crop(width * 3, height*4, width, height);
	playerDown[3] = sheet.crop(width * 4, height*4, width, height);
        playerDown[5] = sheet.crop(width * 5, height*4, width, height);*/
        for (int i=0;i<6;i++){
            enemies1[i]=sheetEnemy.crop(width*i,0,width,height);	
        }
        for (int i=0;i<8;i++){
            enemies2[i]=sheetEnemy.crop(width*i,height*2,width,height);	
        }
        for (int i=0;i<7;i++){
            fireballJutsu[i]=sheetFireball.crop(width*i,0,width,height);
        }
    }
}
