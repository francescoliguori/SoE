/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import gioco.prova.Handler;
import gioco.prova.score.HighScores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InputName {

    private JDialog f;
    private JButton b;
    private JTextField text;
    private JLabel label;
    private String name;
    //private int score;
    private Handler handler;

    public InputName(Handler handler) {
        this.handler = handler;
        f = new JDialog();
        //submit button
        b = new JButton("ok");
        b.setBounds(100, 100, 140, 40);
        //enter name label
        label = new JLabel();
        label.setText("Enter Name :");
        label.setBounds(10, 10, 100, 100);
        //empty label which will show event after button clicked
        //textfield to enter name
        text = new JTextField();
        text.setBounds(110, 50, 130, 30);
        //add to frame
        f.add(text);
        f.add(label);
        f.add(b);
        f.setSize(300, 200);
        f.setLayout(null);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //action listener
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = text.getText();
                HighScores h = new HighScores();
                h.write(name, handler.getGame().getGameState().getHudmngr().getScore().getCount());
                f.dispose();
                handler.getGame().getDisplay().getFrame().addKeyListener(handler.getGame().getKeyManager());
            }
        });
        text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    name = text.getText();
                    HighScores h = new HighScores();
                    h.write(name, handler.getGame().getGameState().getHudmngr().getScore().getCount());
                    f.dispose();
//                    handler.getGame().setKeyManager(new KeyManager());
                    handler.getGame().getDisplay().getFrame().addKeyListener(handler.getGame().getKeyManager());
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public String getName() {
        System.out.println(name);
        return name;
    }

}
