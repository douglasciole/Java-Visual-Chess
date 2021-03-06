package Chess.Structure;
import Chess.Basics.*;
import Chess.Interface.PromotionScreen;

import javax.swing.*;
import java.util.Hashtable;
import java.util.Map;

/**
 * characters source: https://jrgraphix.net/r/Unicode/25A0-25FF
 */
public class Board {
    private Map<String, Square> grid = new Hashtable<>();

    private String drawLetters() {
        return String.format(Config.letters.charAt(0)+"%s"+Config.letters.charAt(1)+
                "%s"+Config.letters.charAt(2)+"%s"+Config.letters.charAt(3)+
                "%s"+Config.letters.charAt(4)+"%s"+Config.letters.charAt(5)+
                "%s"+Config.letters.charAt(6)+"%s"+Config.letters.charAt(7)+
                "%s"+Config.lineBreaker, Config.spacing, Config.spacing, Config.spacing,
                Config.spacing, Config.spacing, Config.spacing, Config.spacing, Config.spacing);
    }

    private String drawNumbers(int i) {
        return 1 + i + Config.spacing;
    }

    private void setUpWhite() {
        grid.put("a2", new Square(1, 0, new Pawn(PieceColor.WHITE)));
        grid.put("b2", new Square(1, 1, new Pawn(PieceColor.WHITE)));
        grid.put("c2", new Square(1, 2, new Pawn(PieceColor.WHITE)));
        grid.put("d2", new Square(1, 3, new Pawn(PieceColor.WHITE)));
        grid.put("e2", new Square(1, 4, new Pawn(PieceColor.WHITE)));
        grid.put("f2", new Square(1, 5, new Pawn(PieceColor.WHITE)));
        grid.put("g2", new Square(1, 6, new Pawn(PieceColor.WHITE)));
        grid.put("h2", new Square(1, 7, new Pawn(PieceColor.WHITE)));

        grid.put("a1", new Square(0, 0, new Rook(PieceColor.WHITE)));
        grid.put("b1", new Square(0, 1, new Knight(PieceColor.WHITE)));
        grid.put("c1", new Square(0, 2, new Bishop(PieceColor.WHITE)));
        grid.put("d1", new Square(0, 3, new Queen(PieceColor.WHITE)));
        grid.put("e1", new Square(0, 4, new King(PieceColor.WHITE)));
        grid.put("f1", new Square(0, 5, new Bishop(PieceColor.WHITE)));
        grid.put("g1", new Square(0, 6, new Knight(PieceColor.WHITE)));
        grid.put("h1", new Square(0, 7, new Rook(PieceColor.WHITE)));
    }

    private void setUpBlack() {
        grid.put("a7", new Square(6, 0, new Pawn(PieceColor.BLACK)));
        grid.put("b7", new Square(6, 1, new Pawn(PieceColor.BLACK)));
        grid.put("c7", new Square(6, 2, new Pawn(PieceColor.BLACK)));
        grid.put("d7", new Square(6, 3, new Pawn(PieceColor.BLACK)));
        grid.put("e7", new Square(6, 4, new Pawn(PieceColor.BLACK)));
        grid.put("f7", new Square(6, 5, new Pawn(PieceColor.BLACK)));
        grid.put("g7", new Square(6, 6, new Pawn(PieceColor.BLACK)));
        grid.put("h7", new Square(6, 7, new Pawn(PieceColor.BLACK)));

        grid.put("a8", new Square(7, 0, new Rook(PieceColor.BLACK)));
        grid.put("b8", new Square(7, 1, new Knight(PieceColor.BLACK)));
        grid.put("c8", new Square(7, 2, new Bishop(PieceColor.BLACK)));
        grid.put("d8", new Square(7, 3, new Queen(PieceColor.BLACK)));
        grid.put("e8", new Square(7, 4, new King(PieceColor.BLACK)));
        grid.put("f8", new Square(7, 5, new Bishop(PieceColor.BLACK)));
        grid.put("g8", new Square(7, 6, new Knight(PieceColor.BLACK)));
        grid.put("h8", new Square(7, 7, new Rook(PieceColor.BLACK)));
    }

    public boolean movePiece(String from, String to) {
        return movePiece(from, to, false);
    }

    public boolean movePiece(String from, String to, boolean ignorePath) {
//        System.out.println(to);
//        String enPos = "";
//        String enCap = "";
//        if (grid.get(from).getPiece().getClass().getName().equals(Pawn.getClassName()) &&
//                !((Pawn) grid.get(from).getPiece()).getEnPassant().equals(""))
//        {
//            enPos = ((Pawn) grid.get(from).getPiece()).getEnPassant().substring(0, 2);
//            enCap = ((Pawn) grid.get(from).getPiece()).getEnPassant().substring(3, 5);
//
//            System.out.println(enPos);
//            System.out.println(enCap);
//            System.out.println(to);
//        }

        if (ignorePath || grid.get(from).getPiece().isValidMove(grid.get(from), grid.get(to))) {
            if (!grid.get(to).isEmpty()) {
                Game.getGameInstance().getPlayer(Game.getGameInstance().getCurrentPlayer()).capture(grid.get(to).getPiece());
                if (grid.get(to).getPiece().getClass() == King.class) {
                    JOptionPane.showMessageDialog(null, Game.getGameInstance().getCurrentPlayer() + " WIN!!", "", JOptionPane.INFORMATION_MESSAGE, Config.icon);
                    Game.getGameInstance().getDisplay().hide();
                    Game.getGameInstance().getTitleScreen().show();
                    Game.getGameInstance().reset();
                    return true;
                }
            }

//            if (grid.get(to).isEmpty() && !enPos.equals("")) {
//                Game.getGameInstance().getPlayer(Game.getGameInstance().getCurrentPlayer()).capture(grid.get(enPos).getPiece());
//                grid.get(enPos).changePiece(null);
//            }

            grid.get(from).getPiece().setMoved();
            grid.get(to).changePiece(grid.get(from).getPiece());
            grid.get(from).changePiece(null);

            if (grid.get(to).getPiece().getClass().getName().equals(King.getClassName())) {
                if (Math.abs(grid.get(to).getCol() - grid.get(from).getCol()) > 1) {
                    if (to.equals("c1")) {
                        movePiece("a1", "d1", true);

                    }else if (to.equals("g1")) {
                        movePiece("h1", "f1", true);

                    }else if (to.equals("c8")) {
                        movePiece("a8", "d8", true);

                    }else if (to.equals("g8")) {
                        movePiece("h8", "f8", true);

                    }
                }
            }

            if (grid.get(to).getPiece().getClass().getName().equals(Pawn.getClassName())) {
                Square movedPawn = grid.get(to);
                if (movedPawn.getPiece().getColor() == PieceColor.WHITE &&
                        movedPawn.fixRow() == 4) {
                    if (movedPawn.getCol() - 1 >= 0)
                    {
                        String pos = Character.toString(Config.letters.charAt(movedPawn.getCol() - 1)) + movedPawn.fixRow();
                        if (!grid.get(pos).isEmpty() &&
                                grid.get(pos).getPiece().getColor() != movedPawn.getPiece().getColor() &&
                                grid.get(pos).getPiece().getClass().getName().equals(Pawn.getClassName()))
                        {
                            ((Pawn) grid.get(pos).getPiece()).setEnPassant(Character.toString(Config.letters.charAt(movedPawn.getCol())) + (movedPawn.fixRow() - 1)+"["+Character.toString(Config.letters.charAt(movedPawn.getCol())) + movedPawn.fixRow()+"]");
                        }

                    }
                    if (movedPawn.getCol() + 1 <= 7) {
                        String pos = Character.toString(Config.letters.charAt(movedPawn.getCol() + 1)) + movedPawn.fixRow();
                        if (!grid.get(pos).isEmpty() &&
                                grid.get(pos).getPiece().getColor() != movedPawn.getPiece().getColor() &&
                                grid.get(pos).getPiece().getClass().getName().equals(Pawn.getClassName())) {
                            ((Pawn) grid.get(pos).getPiece()).setEnPassant(Character.toString(Config.letters.charAt(movedPawn.getCol())) + movedPawn.fixRow());
                        }

                    }
                }
            }

            if ((grid.get(to).getPiece().getClass().getName().equals(Pawn.getClassName()) && grid.get(to).getPiece().getColor() == PieceColor.WHITE && grid.get(to).fixRow() == 8) ||
                    (grid.get(to).getPiece().getClass().getName().equals(Pawn.getClassName()) && grid.get(to).getPiece().getColor() == PieceColor.BLACK && grid.get(to).fixRow() == 1)) {
                new PromotionScreen(to);
            }

            Game.getGameInstance().setSuggestions(null);
            Game.getGameInstance().togglePlayer();
            return true;
        }else {
            JOptionPane.showMessageDialog(null, "This movement is not allowed, try again!", "Alert!", JOptionPane.INFORMATION_MESSAGE, Config.icon);
            return false;
        }
    }

    public void reset() {
        grid = new Hashtable<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid.put(Config.letters.charAt(i)+""+(j+1), new Square(j, i));
            }
        }

        setUpWhite();
        setUpBlack();
    }

    public Board() {
        reset();
    }

    public String draw() {
        String boardDisplay = "";

        boardDisplay += drawLetters();

        for (int i = 7; i >= 0; i--) {
            boardDisplay += drawNumbers(i);

            for (int j = 0; j < 8; j++) {
                boardDisplay += grid.get(Config.letters.charAt(j)+""+(i+1)).drawTile();
            }

            boardDisplay += drawNumbers(i) + Config.lineBreaker;
        }

        boardDisplay += drawLetters();

        return boardDisplay;
    }

    public Map<String, Square> getGrid() {
        return grid;
    }
}
