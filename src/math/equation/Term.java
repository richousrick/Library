package math.equation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Term {

	/**
	 * key is char power 
	 * e.g. x^2's key is x2
	 */
	private ArrayList<Part> parts;
	private BigDecimal constant;

	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param parts of the Term
	 * @param constant of the term
	 */
	public Term(ArrayList<Part> parts, double constant) {
		this(parts, constant+"");
	}
	

	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param parts of the Term
	 * @param constant of the term
	 */
	public Term(ArrayList<Part> parts, BigDecimal constant) {
		this(parts, constant.toString());
	}
	

	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param parts of the Term
	 * @param constant of the term
	 */
	public Term(ArrayList<Part> parts, String constant) {
		this.parts = new ArrayList<Part>(parts);
		this.constant = new BigDecimal(constant);
		Collections.sort(this.parts);
	}
	


	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param p part of the Term
	 * @param constant of the term
	 */
	public Term(Part p, double constant) {
		this(p, constant+"");
	}
	
	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param p part of the Term
	 * @param constant of the term
	 */
	public Term(Part p, BigDecimal constant) {
		this(p, constant.toString());
	}
	
	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param p part of the Term
	 * @param constant of the term
	 */
	public Term(Part p, String constant) {
		this.parts = new ArrayList<>();
		this.parts.add(p);
		this.constant = new BigDecimal(constant);
		Collections.sort(this.parts);
	}
	
	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param constant of the term
	 */
	public Term(double constant) {
		this(constant+"");
	}
	
	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param constant of the term
	 */
	public Term(BigDecimal constant) {
		this(constant.toString());
	}
	
	/**
	 * 
	 * Initiates the Term class
	 *
	 * @param parts of the Term
	 */
	public Term(String parts){
		int pos=0;
		if(parts.charAt(0)=='+'){
			parts = parts.substring(1);
		}
		for(char c:parts.toCharArray()){
			if(Character.isAlphabetic(c)){
				break;
			}else{
				pos ++;
			}
		}
		if(pos !=0){
			this.constant = new BigDecimal(parts.substring(0, pos));
			parts = parts.substring(pos);
		}else{
			this.constant = new BigDecimal("1");
		}
		
		String[] partArr = parts.split("(?=([a-zA-Z]-?[0-9.]*))");
		this.parts = new ArrayList<>();
		for(String s:partArr){
			this.parts.add(new Part(s));
		}
		Collections.sort(this.parts);
	}
	
	/**
	 * Initiates the Term class
	 *
	 *@param t term to copy
	 */
	public Term(Term  t) {
		constant = new BigDecimal(t.getConstant().toString());
		parts = new ArrayList<>(t.getParts());
		Collections.sort(parts);
	}
	
	/**
	 * Multiples the term with a given part
	 *
	 * @param p part to multiply the term by
	 * @param sort wither to order the parts when done
	 */
	public void multiply(Part p, boolean sort){
		boolean found = false;
		loop:for(Part p2:parts){
			if(p2.getName()==p.getName()){
				p2.incrementPower(p.getPower());
				checkRemovePart(p2);
				found = true;
				break loop;
			}
		}
		if(!found){
			parts.add(p);
		}
		if(sort){
			Collections.sort(parts);
		}
	}
	
	/**
	 * Multiples the term with a given part and sorts the parts
	 *
	 * @param p part to multiply the term by
	 */
	public void multiply(Part p){
		this.multiply(p,true);
	}
	
	/**
	 * Multiples this term with a given term
	 *
	 * @param t term to multiply this term by
	 * @param sort wither to order the parts when done
	 */
	public void multiply(Term t, boolean sort){
		for(Part p: t.parts){
			multiply(p, false);
		}
		constant = constant.multiply(t.constant);
		if(sort)
			Collections.sort(parts);
	}
	
	/**
	 * Multiples this term with a given term and sorts the parts
	 *
	 * @param t term to multiply this term by
	 */
	public void multiply(Term t){
		this.multiply(t,true);
	}
	
	
	/**
	 * Removes {@link Part} p if it has a power of 0
	 *
	 * @param p part to remove
	 * @return true if p is removed
	 */
	public boolean checkRemovePart(Part p){
		if (p.getPower().compareTo(CommonMethods.zeroBigD)==0){
			parts.remove(p);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Removes all parts with a power of 0
	 */
	public void cleanUp(){
		for(Part p : parts){
			checkRemovePart(p);
		}
	}
	
	
	/**
	 * @return the parts
	 */
	public ArrayList<Part> getParts() {
		return parts;
	}
	
	/**
	 * @return the parts
	 */
	public Part getPart(int pos) {
		return parts.get(pos);
	}


	/**
	 * @return the constant
	 */
	public BigDecimal getConstant() {
		return constant;
	}
	
	public void incrementConstant(BigDecimal increment){
		constant = constant.add(increment);
	}


	@Override
	public String toString(){
		String returnString;
		if(constant.compareTo(CommonMethods.zeroBigD)!=-1){
			returnString = "+";
		}else{
			returnString = "";
		}
		String constantStr = constant.toString();
		if(constantStr.equals("1")){
			constantStr="";
		}
		returnString += constantStr;
		for(Part p: parts){
			returnString+=p.toString();
		}
		return returnString;
	}
	
	public String paramToString(){
		String returnString = null;
		for(Part p: parts){
			returnString+=p.toString();
		}
		return returnString;
	}
	
	/*
	 * Makes powers in superscript
	 */
	public String toSuperString(){
		String returnString;
		if(constant.compareTo(CommonMethods.zeroBigD)!=-1){
			returnString = "+";
		}else{
			returnString = "";
		}
		String constantStr = constant.toString();
		if(constantStr.equals("1")){
			constantStr="";
		}
		returnString += constantStr;
		for(Part p: parts){
			returnString+=p.toSuperString();
		}
		return returnString;
	}
	
	
	/**
	 *  checks if the given term has the same parts 
	 *  e.g. if this = 4xy2 and t = 6xy2 then return true
	 *
	 * @param t term to compare to
	 * @return ture if the parts are the same
	 */
	public boolean compareParts(Term t){
		return parts.equals(t.parts);
	}
	
	public boolean equals(Term t){
		return constant.compareTo(t.constant) ==0 && compareParts(t);
	}
	
	public static void main(String[] args) {
		Term t = new Term("-3x2y");
		System.out.println(t.toSuperString());
		Term t2 = new Term("-5xy2");
		System.out.println(t2.toSuperString());
		t.multiply(t2);
		System.out.println(t.toSuperString());
	}
}
