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
    private static final int widthEnemies = 155, heightEnemies = 187;
    public static BufferedImage player;
    public static BufferedImage[] playerRunning;
    public static BufferedImage[] playerJump;
    public static BufferedImage[] playerStop;
    public static BufferedImage[] playerDown; //my add
    public static BufferedImage[] enemies1;
    public static BufferedImage[] enemies2;
    public static BufferedImage[] enemies2Dead;
    public static BufferedImage[] enemies3;
    public static BufferedImage[] enemies3Dead;
    public static BufferedImage[] enemies3Jump;
    public static BufferedImage[] fireballJutsu;
    public static BufferedImage[] kunaiThrowForward;
    public static BufferedImage[] kunaiThrowBackward;
    public static BufferedImage gameOver;
    public static BufferedImage gameOverLogo;
    public static void init()
    {   
        //si crea lo spritesheet da utilizzare
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/itachi.png"));
        SpriteSheet sheetEnemy= new SpriteSheet(ImageLoader.loadImage("/enemies.png"));
//        SpriteSheet sheetFireball = new SpriteSheet(ImageLoader.loadImage("/fireball.png"));
//        SpriteSheet sheetKunai = new SpriteSheet(ImageLoader.loadImage("/kunai1.png"));
        SpriteSheet sheetBullets= new SpriteSheet(ImageLoader.loadImage("/bullets.png"));
        SpriteSheet sheetgameOver=new SpriteSheet(ImageLoader.loadImage("/gameover.jpg"));
        SpriteSheet sheetgameOverLogo=new SpriteSheet(ImageLoader.loadImage("/gameoverlogo.png"));
        
        playerRunning = new BufferedImage[6];
        playerJump = new BufferedImage[8];
        playerStop = new BufferedImage[3];
        playerDown = new BufferedImage[4]; //my add
        enemies1 = new BufferedImage[7];
        enemies2 = new BufferedImage[8];
        enemies2Dead = new BufferedImage[5];
        enemies3 = new BufferedImage[7];
        enemies3Dead = new BufferedImage[8];
        enemies3Jump = new BufferedImage[8];
        fireballJutsu= new BufferedImage[7];
        kunaiThrowForward= new BufferedImage[3];
        kunaiThrowBackward= new BufferedImage[3];
        player = sheet.crop(0, 0 , width, height);
        gameOver=sheetgameOver.crop(0,0,1200,700);
        gameOverLogo=sheetgameOverLogo.crop(0,0,1200,700);
        
        for(int i=0;i<=5;i++){
            playerRunning[i] = sheet.crop(width*i, height, width, height);
        }
        
        for(int i=0;i<=7;i++){
            playerJump[i] = sheet.crop(width*i, height*3, width, height);
        }
        
        playerStop[0] = sheet.crop(0, height * 2, width, height);
        
        for(int i=1;i<=3;i++){
            playerDown[i] = sheet.crop(width*i, height*4, width, height);
        }
        
        for (int i=0;i<7;i++){
            //corvo
            enemies1[i]=sheetEnemy.crop(widthEnemies*i,heightEnemies*5,widthEnemies,heightEnemies);	
        }
        
        for (int i=0;i<5;i++){
            //gigante cade
            enemies2Dead[i]=sheetEnemy.crop(widthEnemies*(i+2),heightEnemies*4,widthEnemies,heightEnemies);	
        }
        
        for (int i=0;i<8;i++){
            //gigante corre
            enemies2[i]=sheetEnemy.crop(widthEnemies*i,heightEnemies*3,widthEnemies,heightEnemies);	
        }
        
        for (int i=0;i<7;i++){
            //Anbu corre
            enemies3[i]=sheetEnemy.crop(widthEnemies*i,heightEnemies*0,widthEnemies,heightEnemies);	
        
        }
        for (int i=0;i<8;i++){
            //Anbu muore
            enemies3Dead[i]=sheetEnemy.crop(widthEnemies*i,heightEnemies*2,widthEnemies,heightEnemies);	
        }
        for (int i=0;i<8;i++){
            //Anbu salta
            enemies3Jump[i]=sheetEnemy.crop(widthEnemies*i,heightEnemies*1,widthEnemies,heightEnemies);	
        }
        
        for (int i=0;i<7;i++){
            fireballJutsu[i]=sheetBullets.crop(width*i,0,width,height);
        }
        
        for (int i=0;i<3;i++){
            kunaiThrowForward[i]=sheetBullets.crop(width*i,height,width,height);
        }
        for (int i=0;i<3;i++){
            kunaiThrowBackward[i]=sheetBullets.crop(width*i,height*2,width,height);
        }
    }
}
