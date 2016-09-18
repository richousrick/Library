package math;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

import math.equation.Expression;
import math.equation.Term;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Arithmetic {

	public BigDecimal solveSinglePartExpression(Expression e){
		if(e.getTerms().size()==1 && e.getTerms().get(0).getParts().size()==1){
			Term t = e.getTerms().get(0);
			BigDecimal cons = e.getValue().divide(t.getConstant());
			System.out.println("Constant: "+ cons);
			BigDecimal invertedPow = new BigDecimal("1").divide(t.getPart(0).getPower());
			System.out.println("Pow: "+invertedPow);
			return pow(cons, invertedPow);
		}else{
			throw new InvalidParameterException();
		}
	}
	
	
	
	public boolean inDoubleRange(BigDecimal bd){
		return bd.compareTo(new BigDecimal(Double.MAX_VALUE+""))!=1 && bd.compareTo(new BigDecimal(Double.MIN_VALUE+""))!=-1;
	}
	
	public BigDecimal pow(BigDecimal base, BigDecimal exponent){
		if(inDoubleRange(base) && inDoubleRange(exponent)){
			double based = base.doubleValue();
			System.out.println("Base: "+based);
			double exponentd = exponent.doubleValue();
			System.out.println("Exponent: "+exponentd);
			return new BigDecimal(Math.pow(based, exponentd)+"");
		}else{
			throw new InvalidParameterException("values are out of bounds, larger numbers are not implemented yet :(");
		}
	}
	
	public static void main(String[] args) {
		Expression e = new Expression("5x2", "6307040225645441214996424026661.9944204195968183546476490919835751264257590269784");
		System.out.println(new Arithmetic().solveSinglePartExpression(e));
//		BigDecimal d = new BigDecimal("1261408045129088242999284805332.3988840839193636709295298183967150252851518053956");
//		System.out.println(d);
//		System.out.println(d.doubleValue());
	}
	
	
	
}
