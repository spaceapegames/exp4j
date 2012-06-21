package de.congrace.exp4j;

public class Number {
	private double realPart;
	private double imaginaryPart;
	private boolean real;
	
	public Number(double realPart) {
		real=true;
		this.realPart=realPart;
	}
	
	public Number(double realPart, double imaginaryPart) {
		this.real=false;
		this.realPart = realPart;
		this.imaginaryPart = imaginaryPart;
	}

	public double getRealPart() {
		return realPart;
	}
	
	public double getImaginaryPart() {
		return imaginaryPart;
	}
	
	@Override
	public String toString() {
		if (real){
			return String.valueOf(realPart);
		}else{
			return realPart + (imaginaryPart < 0 ? "-" : "+") + Math.abs(imaginaryPart) + "i";
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginaryPart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(realPart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Number other = (Number) obj;
		if (Double.doubleToLongBits(imaginaryPart) != Double.doubleToLongBits(other.imaginaryPart))
			return false;
		if (Double.doubleToLongBits(realPart) != Double.doubleToLongBits(other.realPart))
			return false;
		return true;
	}

	public boolean isReal() {
		return real;
	}
	
	
}
