package eternity2;

public class Piece {


    private char top;
    private char right;
    private char bottom;
    private char left;

    public Piece(String string) throws WrongLengthException {
        if (string.length() != 4) {
            throw new WrongLengthException("Length of the string isn't 4, it is: " + string.length());
        }
        char[] sides = string.toCharArray();
        this.top = sides[1];
        this.right = sides[2];
        this.bottom = sides[3];
        this.left = sides[0];
    }

    public void rotateRight() {
        char temp = this.top;
        this.top = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = temp;
    }

    public void rotateLeft() {
        char temp = this.top;
        this.top = this.right;
        this.right = this.bottom;
        this.bottom = this.left;
        this.left = temp;
    }
    public void rotateUntilEdgeAtTop() throws WrongPieceException {

        if(this.top!='X'&&this.right!='X'&&this.bottom!='X'&&this.left!='X'){
            throw new WrongPieceException("This piece doesn't have a edge");
        }else{
            while(this.top!='X'){
                rotateRight();
            }
        }
    }
    public void rotateUntilEdgeAtBottom() throws WrongPieceException {

        if(this.top!='X'&&this.right!='X'&&this.bottom!='X'&&this.left!='X'){
            throw new WrongPieceException("This piece doesn't have a edge");
        }else{
            while(this.bottom!='X'){
                rotateRight();
            }
        }
    }
    public void rotateUntilEdgeAtLeft() throws WrongPieceException {

        if(this.top!='X'&&this.right!='X'&&this.bottom!='X'&&this.left!='X'){
            throw new WrongPieceException("This piece doesn't have a edge");
        }else{
            while(this.left!='X'){
                rotateRight();
            }
        }
    }
    public void rotateUntilEdgeAtRight() throws WrongPieceException {
        if(this.top!='X'&&this.right!='X'&&this.bottom!='X'&&this.left!='X'){
            throw new WrongPieceException("This piece doesn't have a edge");
        }else{
            while(this.right!='X'){
                rotateRight();
            }
        }
    }
    public char getTop() {
        return top;
    }


    public char getRight() {
        return right;
    }


    public char getBottom() {
        return bottom;
    }


    public char getLeft() {
        return left;
    }


    @Override
    public String toString() {
        return ""+left+top+right +bottom;
    }

    public double toDouble() {
        return left*100*100*100+top*100*100+right*100+bottom;
    }
}
