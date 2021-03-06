package Chess.Basics;

import Chess.Structure.Board;
import Chess.Structure.Config;
import Chess.Structure.Game;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(new String[] {"♕", "♛"}, color);
    }

    @Override
    public boolean isValidMove(Square from, Square to) {
        return from.getPiece().getPossibleMoves(from).contains(Character.toString(Config.letters.charAt(to.getCol())) + to.fixRow());
    }

    @Override
    public ArrayList<String> getPossibleMoves(Square from) {
        ArrayList<String> moves = new ArrayList<String>();
        Board board = Game.getGameInstance().getBoard();

        String pos = "";

        // ROOK MOVES
        for (int up = from.fixRow() + 1; up <= 8; up++) {
            pos = Character.toString(Config.letters.charAt(from.getCol())) + up;

            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }
        for (int down = from.getRow(); down > 0; down--) {
            pos = Character.toString(Config.letters.charAt(from.getCol())) + down;

            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        for (int left = from.getCol() - 1; left >= 0; left--) {
            pos = Character.toString(Config.letters.charAt(left)) + from.fixRow();

            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        for (int right = from.getCol() + 1; right <= 7; right++) {
            pos = Character.toString(Config.letters.charAt(right)) + from.fixRow();

            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        // BISHOP MOVES
        for (int i = 0; i < 8; i++) {
            if ((from.fixRow() + 1) + i > 8 || (from.getCol() + 1) + i > 7) {
                break;
            }

            pos = Character.toString(Config.letters.charAt((from.getCol() + 1) + i)) + ((from.fixRow() + 1) + i);
            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((from.fixRow() + 1) + i > 8 || (from.getCol() - 1) - i < 0) {
                break;
            }

            pos = Character.toString(Config.letters.charAt((from.getCol() - 1) - i)) + ((from.fixRow() + 1) + i);
            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((from.fixRow() - 1) - i < 1 || (from.getCol() + 1) + i > 7) {
                break;
            }

            pos = Character.toString(Config.letters.charAt((from.getCol() + 1) + i)) + ((from.fixRow() - 1) - i);
            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((from.fixRow() - 1) - i < 1 || (from.getCol() - 1) - i < 0) {
                break;
            }
            pos = Character.toString(Config.letters.charAt((from.getCol() - 1) - i)) + ((from.fixRow() - 1) - i);
            if (board.getGrid().get(pos).isEmpty()) {
                moves.add(pos);
            }else if (board.getGrid().get(pos).getPiece().getColor() != getColor()) {
                moves.add(pos);
                break;
            }else if (board.getGrid().get(pos).getPiece().getColor() == getColor()) {
                break;
            }
        }

        return moves;
    }
}
