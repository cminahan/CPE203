/*
Viewport ideally helps control what part of the world we are looking at for drawing only what we see
Includes helpful helper functions to map between the viewport and the real world
 */


final class Viewport
{
   private int row;
   private int col;
   private int numRows;
   private int numCols;

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }
   public int getRow(){
      return row;
   }
   public int getCol(){
      return col;
   }
   public int getNumRows(){
      return numRows;
   }
   public int getNumCols(){
      return numCols;
   }
   //3
   public Point viewportToWorld(int col, int row)
   {
      return new Point(col + this.col, row + this.row);
   }
   //7
   public void shift(int col, int row)
   {
      this.col = col;
      this.row = row;
   }
   //8
   public Point worldToViewport(int col, int row)
   {
      return new Point(col - this.col, row - this.row);
   }
   //20
   public boolean contains(Point p)
   {
      return p.y >= this.row && p.y < this.row + this.numRows &&
              p.x >= this.col && p.x < this.col + this.numCols;
   }
}
