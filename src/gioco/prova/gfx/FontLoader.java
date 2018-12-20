/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    
    public Font load(String path, int size) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
