package eternity2;

public class TileOrientation {
    public TileOrientation(Tile tile, int orientation) {
        this.tile=tile;
        this.orientation=orientation;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private Tile tile;
    private int orientation;

}
