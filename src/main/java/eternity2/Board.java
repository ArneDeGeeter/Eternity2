package eternity2;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Board {
    private Tile topLeftTile;


    private Tile nextTile;


    private ArrayList<Tile> leftTiles;
    private ArrayList<Tile> topTiles;

    public Board(int size) {
        leftTiles = new ArrayList<>();
        topTiles = new ArrayList<>();
        ArrayList<Tile> lastRow = new ArrayList<>();
        ArrayList<Tile> currentRow = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            Tile t = new Tile();
            leftTiles.add(t);
            currentRow.add(t);
            if (topTiles.size() != size) {
                topTiles.add(t);
            } else {
                topTiles.get(0).appendBottom(t);
            }
            for (int j = 1; j < size; j++) {
                Tile t2 = new Tile();
                leftTiles.get(i).appendRight(t2);
                currentRow.add(t2);
                if (topTiles.size() != size) {
                    topTiles.add(t2);
                } else {
                    topTiles.get(j).appendBottom(t2);
                }
            }
            lastRow = currentRow;
            currentRow.clear();

        }
        nextTile = leftTiles.get(0);
        topLeftTile = nextTile;


    }

    public int placeNextPiece(Piece piece, int orientation) throws WrongPieceException {
        int reward = nextTile.setPiece(piece);
        TileOrientation tileOrientation = nextTile.getNextTile(orientation);
        if (tileOrientation == null) {
            tileOrientation = new TileOrientation(topLeftTile, 0);
        }
        nextTile = tileOrientation.getTile();
        return reward + tileOrientation.getOrientation() * 10;

    }

    public void print() {
        for (Tile t : leftTiles) {
            t.print();
            System.out.println();
        }
    }

    public void write(PrintWriter writer) {

        for (Tile t : leftTiles) {
            t.write(writer);
            writer.println();
        }
    }

    public ArrayList<Tile> getLeftTiles() {
        return leftTiles;
    }

    public Tile getTopLeftTile() {
        return topLeftTile;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void removePieces() {
        nextTile = topLeftTile;
        for (Tile t : leftTiles) {
            t.removeAllPieces();

        }
    }

    public double[] toArray() {
        double[] d = new double[4];
        if (nextTile != null) {
            d[0] = nextTile.getLeftTile()==null?0:nextTile.getLeftTile().getPiece()==null?0:nextTile.getLeftTile().getPiece().getRight();
            d[1] = nextTile.getTopTile()==null?0:nextTile.getTopTile().getPiece()==null?0:nextTile.getTopTile().getPiece().getBottom();
            d[2] = nextTile.getRightTile()==null?0:nextTile.getRightTile().getPiece()==null?0:nextTile.getRightTile().getPiece().getLeft();
            d[3] = nextTile.getBottomTile()==null?0:nextTile.getBottomTile().getPiece()==null?0:nextTile.getBottomTile().getPiece().getTop();
        }
        return d;
    }

    public int placeAllPieces(String[] pieces) {
        int reward=0;
        for(int i=0;i<leftTiles.size();i++){
            nextTile=leftTiles.get(i);
            for (int j = 0; j < leftTiles.size(); j++) {
                try {
                    reward=reward+(placeNextPiece(new Piece(pieces[i*leftTiles.size()+j]),2)%10);
                } catch (WrongPieceException e) {
                    e.printStackTrace();
                } catch (WrongLengthException e) {
                    e.printStackTrace();
                }
            }
        }
        return reward;
    }
}
