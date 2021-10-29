class DivideExpression extends BinaryExpression
{

   public DivideExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "/");
   }

   protected double _applyOperator(double lft, double rht){
      return lft/rht;
   }
}

