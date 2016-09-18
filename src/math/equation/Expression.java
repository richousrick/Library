package math.equation;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Expression {

	private ArrayList<Term> terms;
	private BigDecimal value;
	
	public Expression(String expression, String value){
		String[] parts = expression.split("\\s");
		terms = new ArrayList<>();
		for (String s:parts){
			terms.add(new Term(s));
		}
		this.value = new BigDecimal(value);
	}
	
	
	public Expression(Expression expression){
		terms = new ArrayList<>(expression.getTerms());
		value = new BigDecimal(expression.value.toString());
	}
	
	
	
	/**
	 * Adds the given term to the current expression
	 *
	 * @param t term to add
	 */
	public void add(Term t){
		boolean found = false;
		loop:for(Term term: terms){
			if(t.compareParts(term)){
				term.incrementConstant(t.getConstant());
				checkRemoveTerm(term);
				found = true;
				break loop;
			}
		}
		if(!found){
			terms.add(t);
		}
	}
	
	/**
	 * Adds the given expression to the current expression
	 *
	 * @param e expression to add
	 */
	public void add(Expression e){
		for(Term t: e.getTerms()){
			add(t);
		}
	}
	
	/**
	 * multiplys the expression by the given term
	 *
	 * @param t term to multiply the expression by
	 */
	public void multiply(Term t){
		for (Term t2: terms){
			t2.multiply(t);
		}
	}
	
	/**
	 * multiplys the current expression with a given expression
	 *
	 * @param e expression to multiply the current expression by
	 */
	public void multiply(Expression e) {
		Expression e2 = new Expression(this);
		this.terms = new ArrayList<>();
		for(Term t1: e.terms){
			for(Term t2: e2.terms){
				Term mult = new Term(t1);
				mult.multiply(t2);
				terms.add(mult);
				System.out.println(t1.toSuperString() +" x "+t2.toSuperString()+" = "+mult.toSuperString());
			}
		}
	}
	
	/**
	 * @return the terms
	 */
	public ArrayList<Term> getTerms() {
		return terms;
	}

	/**
	 * Removes Term t if it has a sontant of 0
	 *
	 * @param t term to check
	 * @return true if t is removed
	 */
	public boolean checkRemoveTerm(Term t){
		if (t.getConstant().compareTo(CommonMethods.zeroBigD)==0){
			terms.remove(t);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Removes all Terms with a constant of 0
	 */
	public void cleanUp(){
		
		for(Term t : terms){
			checkRemoveTerm(t);
		}
	}
	
	@Override
	public String toString(){
		String s = value.toString();
		for(Term t: terms){
			s+=t;
		}
		return s;
	}
	
	/**
	 * @return the value
	 */
	public BigDecimal getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}


	public String toSuperString(){
		String s = "";
		for(Term t: terms){
			s+=" "+t.toSuperString();
		}
		return s;
	}
	
}
