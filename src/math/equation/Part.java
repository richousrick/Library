package math.equation;

import java.math.BigDecimal;
import java.security.InvalidParameterException;


/**
 *@TODO Annotate Class
 *
 * @author Richousrick
 */
public class Part implements Comparable<Part>{

	/**
	 * letter associated with the variable
	 */
	private char name;
	/**
	 * power of the variable
	 */
	private BigDecimal power;
	
	/**
	 * 
	 * Initiates the Part class
	 *
	 * @param name of the variable
	 * @param power of the variable
	 */
	public Part(char name, double power) {
		this(name, power+"");
	}
	
	/**
	 * 
	 * Initiates the Part class
	 *
	 * @param name of the variable
	 * @param power of the variable
	 */
	public Part(char name, BigDecimal power) {
		this(name, power.toString());
	}
	
	/**
	 * 
	 * Initiates the Part class
	 *
	 * @param p to copy
	 */
	public Part(Part p){
		this(p.getName(), p.getPower().toString());
	}
	
	/**
	 * 
	 * Initiates the Part class
	 *
	 * @param name of the variable
	 * @param power of the variable
	 */
	public Part(char name, String power) {
		this.name = name;
		this.power = new BigDecimal(power);
	}
	

	/**
	 * 
	 * Initiates the Part class
	 *
	 * @param value of the part in the form letter followed by a real (e.g. x5 is x⁵, y-2 is y⁻², k is k¹ etc.)
	 */
	public Part(String value) {
		if(value.matches("[a-zA-Z]-?[0-9.]*")){
			name = value.charAt(0);
			if(value.length()>1){
				power = new BigDecimal(value.substring(1));
			}else{
				power = new BigDecimal("1");
			}
			
		}else{
			throw new InvalidParameterException("String "+value+" does not match regex \"[a-zA-Z][0-9.]*\"");
		}
	}

	/**
	 * @return the name
	 */
	public char getName() {
		return name;
	}

	/**
	 * @return the power
	 */
	public BigDecimal getPower() {
		return power;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(char name) {
		this.name = name;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(BigDecimal power) {
		this.power = power;
	}
	
	/**
	 * @param increment to change the power by
	 */
	public void  incrementPower(double increment) {
		power = power.add(new BigDecimal(increment+""));
	}
	
	/**
	 * @param increment to change the power by
	 */
	public void  incrementPower(BigDecimal increment) {
		power = power.add(increment);
	}
	
	public String superPower(){
		String powerString = power.toString();
		if(powerString.equals("1")){
			return "";
		}
		powerString = powerString.replaceAll("0", "⁰");
	    powerString = powerString.replaceAll("1", "¹");
	    powerString = powerString.replaceAll("2", "²");
	    powerString = powerString.replaceAll("3", "³");
	    powerString = powerString.replaceAll("4", "⁴");
	    powerString = powerString.replaceAll("5", "⁵");
	    powerString = powerString.replaceAll("6", "⁶");
	    powerString = powerString.replaceAll("7", "⁷");
	    powerString = powerString.replaceAll("8", "⁸");
	    powerString = powerString.replaceAll("9", "⁹");
	    powerString = powerString.replaceAll("-", "⁻");
	    powerString = powerString.replaceAll("\\.", "'");
	    return powerString;
	}
	
	@Override
	public String toString(){
		String powerStr = power.toString();
		if(powerStr.equals("1")){
			powerStr="";
		}
		return name+powerStr;
	}
	
	
	
	
	@Override
	public int compareTo(Part p){
		if(name<p.name){
			return -1;
		}else if (name>p.name){
			return 1;
		}else{
			return power.compareTo(p.power);
		}
	}
	
	
	/*
	 * Makes powers in superscript
	 */
	public String toSuperString(){
		return name+superPower();
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass().equals(Part.class)){
			return ((Part)o).name==this.name && ((Part)o).power.equals(this.power);
		}else{
			return false;
		}
	}

}
