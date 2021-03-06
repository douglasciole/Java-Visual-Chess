package Chess.Interface;

import Chess.Basics.DTextPanel;
import Chess.Basics.Square;
import Chess.Structure.Config;
import Chess.Structure.Game;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Display {
    private JFrame appFrame = new JFrame(Config.gameTitle);
    Screen screen = new Screen();
    public DTextPanel currentField = new DTextPanel("From Position", 2, 5);
    public DTextPanel nextField = new DTextPanel("To Position", 2, 5);
    private int backLock = 0;

    public boolean isLock() {
        return backLock > 0;
    }

    public void setLock() {
        this.backLock = 2;
    }

    public void unlock() {
        if (this.backLock > 0)
            this.backLock--;
    }

    public void show() {
        appFrame.setVisible(true);
        currentField.focus();
    }
    public void hide() {
        appFrame.setVisible(false);
    }

    private void setupFrame() {
        appFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        appFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                int dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to quit the game?\n(All progress will be lost!)","Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, Config.icon);
                if(dialogResult == JOptionPane.YES_OPTION){
                    hide();
                    Game.getGameInstance().reset();
                    Game.getGameInstance().getTitleScreen().disconnect();
                    Game.getGameInstance().getTitleScreen().show();
                }
            }
        });
        appFrame.setResizable(false);
        appFrame.setSize(Config.boardDimentions[0], Config.boardDimentions[1]);
        Config.centerWindows(appFrame);
        appFrame.setVisible(false);
    }

    public String fromText() {
        return currentField.getText();
    }

    public Display() {
        //Bottom panel
        JPanel southPanel = new JPanel();

        //From position
        currentField.addListener(new KeyUpValidadte());
        southPanel.add(currentField);
        //To position
        nextField.addListener(new KeyUpValidadte());
        southPanel.add(nextField);

        JButton button = new JButton("Move");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String current = Game.getGameInstance().getDisplay().currentField.getText();
                String next = Game.getGameInstance().getDisplay().nextField.getText();

                if(current.length() > 1 && Game.validateInput(current) &&
                        next.length() > 1 && Game.validateInput(next)){

                    Game.getGameInstance().requestMove(current, next);
                }
            }
        });
        southPanel.add(button);

        Container content = appFrame.getContentPane();
        Container topContainer = Box.createHorizontalBox();
        topContainer.add(screen);

        content.add(topContainer, BorderLayout.NORTH);
        content.add(southPanel, BorderLayout.PAGE_END);

        setupFrame();
    }

    public void draw(String t) {
        screen.draw(t);
    }
}

class KeyUpValidadte extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        String current = Game.getGameInstance().getDisplay().currentField.getText();
        String next = Game.getGameInstance().getDisplay().nextField.getText();

        if(e.getKeyCode() == KeyEvent.VK_ENTER &&
                current.length() > 1 && Game.validateInput(current) &&
                next.length() > 1 && Game.validateInput(next)){

            Game.getGameInstance().requestMove(current, next);
        }


        if (e.getComponent().equals(Game.getGameInstance().getDisplay().nextField.getTXTField())) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && Game.getGameInstance().getDisplay().nextField.getText().trim().equals("")) {
                Game.getGameInstance().getDisplay().setLock();
                Game.getGameInstance().getDisplay().currentField.focus();
            }

            if (e.isShiftDown()) {
                Game.getGameInstance().getDisplay().setLock();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String current = Game.getGameInstance().getDisplay().currentField.getText();
        String next = Game.getGameInstance().getDisplay().nextField.getText();

        if (current.length() > 1) {
            if (Game.validateInput(current)) {
                Square selectedSquare = Game.getGameInstance().getBoard().getGrid().get(current);
                if (!selectedSquare.isEmpty() && (selectedSquare.getPiece().getColor() == Game.getGameInstance().getCurrentPlayer() || Config.adminMode)) {
                    Game.getGameInstance().hightlight(new String[]{current}, Config.htmlFromColor, false);

                    if (!Game.getGameInstance().getDisplay().isLock()) {
                        Game.getGameInstance().getDisplay().nextField.focus();
                    }

                    ArrayList<String> tmpList = selectedSquare.getPiece().getPossibleMoves(selectedSquare);
                    String[] possibleMoves = (tmpList != null)?new String[tmpList.size()] : new String[0];
                    if (tmpList != null)
                        possibleMoves = tmpList.toArray(possibleMoves);

                    //Hightlight and Show suggestions
                    Game.getGameInstance().setSuggestions(tmpList);
                    Game.getGameInstance().hightlight(possibleMoves, Config.htmlMovimentColor, false);

                    //Hightlight if TO option is a valid option
                    if (next.length() > 1 && !current.equals(next)) {
                        if (Game.validateInput(next) && tmpList != null && tmpList.contains(next)) {
                            Game.getGameInstance().hightlight(new String[]{next}, Config.htmlToColor, false);
                        }
                    }

                }
            }
        }

        Game.getGameInstance().getDisplay().unlock();
        Game.getGameInstance().update();
    }
}