/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.image.BufferedImage;

/*Un asset è una qualsiasi immagine o canzone all'interno del gioco. Al momento 
si gestiscono solamente le immagini perché le canzoni non sono ancora presenti*/
public class Assets {

    //dimensioni di ogni elemento della "griglia" spritesheet
    private static final int width = 155, height = 187;
    private static final int widthEnemies = 155, heightEnemies = 187;
    private static final int widthBoss = 100, heightBoss = 160;
    private static final int widthBossLA = 451;
	
    public static BufferedImage player;
    public static BufferedImage[] playerRunning;
    public static BufferedImage[] playerJump;
    public static BufferedImage[] playerDown; //my add
    public static BufferedImage[] enemies1;
    public static BufferedImage[] enemies1Dead;
    public static BufferedImage[] enemies2;
    public static BufferedImage[] enemies2Dead;
    public static BufferedImage[] enemies3;
    public static BufferedImage[] enemies3Dead;
    public static BufferedImage[] enemies3Jump;
    public static BufferedImage[] fireballJutsu;
    public static BufferedImage[] kunaiThrowForward;
    public static BufferedImage[] kunaiThrowBackward;
    public static BufferedImage[] arrowThrowBackward;
    public static BufferedImage[] oroJump;
    public static BufferedImage[] oroDead;
    public static BufferedImage[] oroAttack;
    public static BufferedImage[] oroLongAttack;
    public static BufferedImage gameOver;
    public static BufferedImage gameOverLogo;
    public static BufferedImage ramen;
    public static BufferedImage menu;
    public static BufferedImage story;
    public static BufferedImage credits;
    public static BufferedImage score;
    public static BufferedImage commands;
    public static BufferedImage[] demoUp;
    public static BufferedImage[] demoDown;
    public static BufferedImage[] demoLeft;
    public static BufferedImage[] demoRight;
    
    public static void init() {
        //si crea lo spritesheet da utilizzare
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/itachi.png"));
        SpriteSheet sheetEnemy = new SpriteSheet(ImageLoader.loadImage("/enemies.png"));
        SpriteSheet sheetBoss = new SpriteSheet(ImageLoader.loadImage("/oro.png"));
        SpriteSheet sheetBossLA = new SpriteSheet(ImageLoader.loadImage("/oroLA.png"));
        SpriteSheet sheetBullets = new SpriteSheet(ImageLoader.loadImage("/bullets.png"));
        SpriteSheet sheetgameOver = new SpriteSheet(ImageLoader.loadImage("/gameover.jpg"));
        SpriteSheet sheetgameOverLogo = new SpriteSheet(ImageLoader.loadImage("/gameoverlogo.png"));
        SpriteSheet sheetgameramen = new SpriteSheet(ImageLoader.loadImage("/ramenbowl.png"));
        SpriteSheet sheetStory=new SpriteSheet(ImageLoader.loadImage("/story1.png"));
        SpriteSheet sheetCredits=new SpriteSheet(ImageLoader.loadImage("/credits.jpg"));
        SpriteSheet sheetMenu=new SpriteSheet(ImageLoader.loadImage("/start2.png"));
        SpriteSheet sheetScore= new SpriteSheet(ImageLoader.loadImage("/score.jpg"));
        SpriteSheet sheetCommands= new SpriteSheet(ImageLoader.loadImage("/keys.png"));
        SpriteSheet sheetDemoUp = new SpriteSheet(ImageLoader.loadImage("/demo/up.png"));
        SpriteSheet sheetDemoDown = new SpriteSheet(ImageLoader.loadImage("/demo/down.png"));
        SpriteSheet sheetDemoLeft = new SpriteSheet(ImageLoader.loadImage("/demo/left.png"));
        SpriteSheet sheetDemoRight = new SpriteSheet(ImageLoader.loadImage("/demo/right.png"));
        
        playerRunning = new BufferedImage[6];
        playerJump = new BufferedImage[8];
        playerDown = new BufferedImage[4]; //my add
        enemies1 = new BufferedImage[5];
        enemies1Dead = new BufferedImage[8];
        enemies2 = new BufferedImage[8];
        enemies2Dead = new BufferedImage[5];
        enemies3 = new BufferedImage[7];
        enemies3Dead = new BufferedImage[8];
        enemies3Jump = new BufferedImage[8];
        fireballJutsu = new BufferedImage[7];
        kunaiThrowForward = new BufferedImage[1];
        kunaiThrowBackward = new BufferedImage[1];
        arrowThrowBackward = new BufferedImage[1];
        demoUp = new BufferedImage[6];
        demoDown = new BufferedImage[6];
        demoLeft = new BufferedImage[6];
        demoRight = new BufferedImage[6];
        oroJump = new BufferedImage[3];
        oroDead = new BufferedImage[9];
        oroAttack = new BufferedImage[19];
        oroLongAttack = new BufferedImage[16];
        player = sheet.crop(0, 0, width, height);
        ramen = sheetgameramen.crop(0, 0, 55, 42);
        gameOver = sheetgameOver.crop(0, 0, 1200, 700);
        gameOverLogo = sheetgameOverLogo.crop(0, 0, 1200, 700);
        story=sheetStory.crop(0,0,1200,700);
        menu=sheetMenu.crop(0,0,1200,700);
        credits=sheetCredits.crop(0,0,1200,700);
        score=sheetScore.crop(0,0,1200,700);
        commands=sheetCommands.crop(0,0,1200,700);
        for (int i = 0; i <= 5; i++) {
            playerRunning[i] = sheet.crop(width * i, height, width, height);
        }

        for (int i = 0; i <= 7; i++) {
            playerJump[i] = sheet.crop(width * i, height * 3, width, height);
        }

        for (int i = 1; i <= 3; i++) {
            playerDown[i] = sheet.crop(width * i, height * 4, width, height);
        }
		
		// boss jump
        for (int i = 7; i > 4; i--) {
            oroJump[7 - i] = sheetBoss.crop(widthBoss * i, 0,widthBoss, heightBoss);
        }
        
        for (int i = 0; i < 5; i++) {
            //boss attack
            for (int j = 1; j >= 0; j--) {
                oroAttack[(i * 2) + (1 - j)] = sheetBoss.crop((int) (widthBoss * (1 + (3.5 * j))), heightBoss * (3 + i), (int) (widthBoss * 3.5), heightBoss);
            }
        }
        //adding reverse boss attack
        for(int i=10; i<19; i++){
            oroAttack[i]= oroAttack[18-i];
        }
        
        int k = 0;
        while(k<16){
            for (int j = 1; j>=0; j--){
                oroLongAttack[k] = sheetBossLA.crop(widthBossLA * j, heightBoss*(k/2), widthBossLA, heightBoss);
                k++;
            }
        }
        
        for (int i = 3; i >= 0; i--) {
            // boss hit
            oroDead[3-i] = sheetBoss.crop((int) (widthBoss * (2 + (i * 1.5))), heightBoss, (int) (widthBoss * 1.5), heightBoss);
        }

        oroDead[4] = sheetBoss.crop((int) (widthBoss * 0.5), heightBoss, (int) (widthBoss * 1.5), heightBoss);
        for (int i = 8; i >= 5; i--) {
            // adding boss dead
            oroDead[13-i] = sheetBoss.crop((int) (widthBoss * (2 + ((i-5) * 1.5))), heightBoss*2, (int) (widthBoss * 1.5), heightBoss);
        }

        for (int i = 0; i < 5; i++) {
            // arciere
            enemies1[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 7, widthEnemies, heightEnemies);
        }

        for (int i = 0; i < 8; i++) {
            //arciere cade
            enemies1Dead[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 6, widthEnemies, heightEnemies);
        }

        for (int i = 0; i < 5; i++) {
            //gigante cade
            enemies2Dead[i] = sheetEnemy.crop(widthEnemies * (i + 2), heightEnemies * 4, widthEnemies, heightEnemies);
        }

        for (int i = 0; i < 8; i++) {
            //gigante corre
            enemies2[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 3, widthEnemies, heightEnemies);
        }

        for (int i = 0; i < 7; i++) {
            //Anbu corre
            enemies3[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 0, widthEnemies, heightEnemies);

        }
        for (int i = 0; i < 8; i++) {
            //Anbu muore
            enemies3Dead[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 2, widthEnemies, heightEnemies);
        }
        for (int i = 0; i < 8; i++) {
            //Anbu salta
            enemies3Jump[i] = sheetEnemy.crop(widthEnemies * i, heightEnemies * 1, widthEnemies, heightEnemies);
        }

        for (int i = 0; i < 7; i++) {
            fireballJutsu[i] = sheetBullets.crop(width * i, 0, width, height);
        }

         for (int i = 0; i < 6; i++) {
            demoUp[i] = sheetDemoUp.crop(240 * i, 0, 240, 153);
        }

        for (int i = 0; i < 6; i++) {
            demoDown[i] = sheetDemoDown.crop(240 * i, 0, 240, 153);
        }

        for (int i = 0; i < 6; i++) {
            demoLeft[i] = sheetDemoLeft.crop(240 * i, 0, 240, 153);
        }

        for (int i = 0; i < 6; i++) {
            demoRight[i] = sheetDemoRight.crop(240 * i, 0, 240, 153);
        }
        
        kunaiThrowForward[0] = sheetBullets.crop(0, height, width, height);
        kunaiThrowBackward[0] = sheetBullets.crop(0, height * 2, width, height);
        arrowThrowBackward[0] = sheetBullets.crop(0, height * 3, width, height);
    }
}
