package eternity2;

import java.io.PrintWriter;

public class Tile {
    private Tile topTile;

    public Tile getTopTile() {
        return topTile;
    }

    public Tile getRightTile() {
        return rightTile;
    }

    public Tile getBottomTile() {
        return bottomTile;
    }

    public Tile getLeftTile() {
        return leftTile;
    }

    private Tile rightTile;
    private Tile bottomTile;
    private Tile leftTile;
    private Piece piece;


    public void appendRight(Tile t2) {
        if (this != t2) {
            if (rightTile != null) {
                rightTile.appendRight(t2);
            } else {
                this.rightTile = t2;
                t2.leftTile = this;

            }
        }
    }

    public void appendBottom(Tile t2) {
        if (this != t2) {
            if (bottomTile != null) {

                bottomTile.appendBottom(t2);
            } else {

                this.bottomTile = t2;
                t2.topTile = this;

            }
        }
    }

    public boolean isEdge() {
        return countEdges() >= 1;
    }

    public int countEdges() {
        int edges = 0;
        edges = edges + (topTile == null ? 1 : 0);
        edges = edges + (rightTile == null ? 1 : 0);
        edges = edges + (bottomTile == null ? 1 : 0);
        edges = edges + (leftTile == null ? 1 : 0);
        return edges;
    }

    public boolean isCorner() {
        return countEdges() == 2;
    }

    public TileOrientation getNextTile(int orientation) {
       /* System.out.println(topTile);
        System.out.println(bottomTile);
        System.out.println(rightTile);
        System.out.println(leftTile);*/
        for (int i = 0; i < 4; i++) {
            if (orientation == 0) {
                if (this.leftTile != null) {
                    //System.out.println(this.leftTile.piece);
                    if (this.leftTile.piece == null) {
                        return new TileOrientation(this.leftTile, orientation);
                    }
                }

            } else if (orientation == 1) {
                if (this.topTile != null) {
                    //  System.out.println(this.topTile.piece);

                    if (this.topTile.piece == null) {
                        return new TileOrientation(this.topTile, orientation);
                    }
                }
            } else if (orientation == 2) {
                if (this.rightTile != null) {
                    //  System.out.println(this.rightTile.piece);

                    if (this.rightTile.piece == null) {
                        return new TileOrientation(this.rightTile, orientation);
                    }
                }
            } else if (orientation == 3) {
                if (this.bottomTile != null) {
                    // System.out.println(this.bottomTile.piece);

                    if (this.bottomTile.piece == null) {
                        return new TileOrientation(this.bottomTile, orientation);
                    }
                }
            }
            orientation = (orientation + 1) % 4;
        }
        return null;
    }

    public void print() {
        System.out.print((rightTile == null) ? this.piece : this.piece + ",");
        if (rightTile != null) {
            rightTile.print();
        }
    }

   /* @Override
    public String toString() {
        return "Tile{" +
            "topTile=" + topTile +
            ", rightTile=" + rightTile +
            ", bottomTile=" + bottomTile +
            ", leftTile=" + leftTile +
            ", piece=" + piece +
            '}';
    }*/

    public void write(PrintWriter writer) {
        writer.print((rightTile == null) ? this.piece : this.piece + ",");
        if (rightTile != null) {
            rightTile.write(writer);
        }
    }

    public int setPiece(Piece piece) throws WrongPieceException {
        int reward = 0;
        if (isEdge()) {
            if (isCorner()) {
                if (this.topTile == null) {
                    piece.rotateUntilEdgeAtTop();
                    if (this.leftTile == null) {
                        if (piece.getLeft() != 'X') {
                            piece.rotateLeft();
                        }
                    } else if (this.rightTile == null) {
                        if (piece.getRight() != 'X') {
                            piece.rotateRight();
                        }
                    }
                } else if (this.bottomTile == null) {
                    piece.rotateUntilEdgeAtBottom();
                    if (this.leftTile == null) {
                        if (piece.getLeft() != 'X') {
                            piece.rotateRight();
                        }
                    } else if (this.rightTile == null) {
                        if (piece.getRight() != 'X') {
                            piece.rotateLeft();
                        }
                    }
                }
            }
            if (this.topTile == null) {
                piece.rotateUntilEdgeAtTop();
            } else if (this.rightTile == null) {
                piece.rotateUntilEdgeAtRight();
            } else if (this.bottomTile == null) {
                piece.rotateUntilEdgeAtBottom();
            } else if (this.leftTile == null) {
                piece.rotateUntilEdgeAtLeft();
            }

        }
        if (this.leftTile != null) {
            if (this.leftTile.piece != null) {
                if (this.leftTile.piece.getRight() == piece.getLeft()) {
                    reward++;
                }
            }
        }
        if (this.topTile != null) {
            if (this.topTile.piece != null) {
                if (this.topTile.piece.getBottom() == piece.getTop()) {
                    reward++;
                }
            }
        }
        if (this.rightTile != null) {
            if (this.rightTile.piece != null) {
                if (this.rightTile.piece.getLeft() == piece.getRight()) {
                    reward++;
                }
            }

        }
        if (this.bottomTile != null) {
            if (this.bottomTile.piece != null) {
                if (this.bottomTile.piece.getTop() == piece.getBottom()) {
                    reward++;
                }
            }
        }
        this.piece = piece;

        return reward;
    }


    public void removeAllPieces() {
        this.piece = null;
        if (topTile != null) {
            if (topTile.piece != null) {
                topTile.removeAllPieces();
            }
        }
        if (rightTile != null) {
            if (rightTile.piece != null) {
                rightTile.removeAllPieces();
            }
        }
        if (bottomTile != null) {
            if (bottomTile.piece != null) {
                bottomTile.removeAllPieces();
            }
        }
        if (leftTile != null) {
            if (leftTile.piece != null) {
                leftTile.removeAllPieces();
            }
        }
    }

    public double[] toArray() {
        double[] d = new double[0];
        if (this.rightTile != null) {
            d = this.rightTile.toArray();
        }
        double[] d2 = new double[d.length + 1];
        d2[0] = (piece == null ? 0 : piece.toDouble());
        for (int i = 0; i < d.length; i++) {
            d2[i + 1] = d[i];
        }
        return d2;
    }

    public String returnEdges() {
        return "" + countEdges() + (this.rightTile == null ? "" : rightTile.returnEdges());
    }
    public Piece getPiece(){
        return piece;
    }

}
