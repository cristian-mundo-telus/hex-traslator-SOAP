package org.jugvale.ola.jaxws;

import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebService
public class CalculadoraWS {

    @WebMethod
	public double fazerOp(@WebParam(name = "num1") double a
						, @WebParam(name = "num2") double b
						, @WebParam(name = "op") String op) {
		switch (op) {
		case "+":
			return a + b;
		case "-":
			return a - b;
		case "*":
			return a * b;
		case "/":
			return a / b;
		default:
			throw new IllegalArgumentException("Operação '" + op
					+ "' não reconhecida. Informa '+', '-', '*' ou '/'.");
		}
	}

	@WebMethod
	public String sayHello(String name){
    	return "Say Hello to " + name;
	}

	@WebMethod
	public String traslateHEX(@WebParam(name = "hex") String hex){
		final String HEX_PATTERN = "^#([a-fA-F0-9]{6})$";
		Pattern p = Pattern.compile(HEX_PATTERN);

		Matcher matcher = p.matcher(hex);

		if(!matcher.matches())
			return "Hexadecimal number invalid";

		double R,G,B;
		Color color = new Color(
				Integer.valueOf(hex.substring(1,3),16),
				Integer.valueOf(hex.substring(3,5),16),
				Integer.valueOf(hex.substring(5,7), 16));

		R = color.getRed();
		G = color.getGreen();
		B = color.getBlue();

		String hsv = rgb_to_hsv(R,G,B);
		String cmyk = rgb_to_cmyk(R,G,B);
		String hsl = rgb_to_hsl(R,G,B);

		return "RGB: " + R + ", " + G + ", " + B + hsv + cmyk + hsl;

	}

	public String rgb_to_hsv(double r, double g, double b){
		r = r / 255.0;
		g = g / 255.0;
		b = b / 255.0;

		double cmax = Math.max(r, Math.max(g,b));
		double cmin = Math.min(r, Math.min(g,b));
		double diff = cmax - cmin;
		double h = -1, s = -1;

		if(cmax == cmin)
			h = 0;
		else if (cmax == r)
			h = (60 * ((g-b)/diff) + 360) % 360;
		else if (cmax == g)
			h = (60 * ((b - r) / diff ) + 120 ) % 360;
		else if (cmax == b)
			h = (60 * ((r - g) / diff) +240) %360;
		if(cmax == 0)
			s = 0;
		else
			s = (diff/cmax)*100;

		double v = cmax *100;

		return " HSV: " + Math.round(h) + " " + Math.round(s) + "% " + Math.round(v) +"% ";
	}

	public String rgb_to_cmyk(double r, double g, double b){
		r = r / 255.0;
		g = g / 255.0;
		b = b / 255.0;

		double max = (Math.max(Math.max(r, g), b));
		double K = 1 - max;
		double C = (1 - r - K) / (1 - K);
		double M = (1 - g - K) / (1 - K);
		double Y = (1 - b - K) / (1 - K);

		return " CMYK: "+ Math.round(C*100) + "% " + Math.round(M*100) + "% " + Math.round(Y*100) + "% " + Math.round(K*100) +"% ";
	}

	public String rgb_to_hsl(double r, double g, double b){
		r = r / 255.0;
		g = g / 255.0;
		b = b / 255.0;

		double max = Math.max(r, Math.max(g,b));
		double min = Math.min(r, Math.min(g,b));
		double delta = max - min;

		double h = 0, s = 0, l = 0;

		if(delta == 0)
			h = 0;
		else if (max == r)
			h = ((g - b) / delta ) % 6;
		else if (max == g)
			h = (b - r) / delta + 2;

		else
			h = (r - g) / delta + 4;

		h = Math.round(h*60);

		if(h<0)
			h+= 360;

		//lightness

		l = (max + min) / 2;

		//saturation
		s = (delta == 0) ? 0 : delta / (1 - Math.abs(2*l-1));

		s = Math.round(s*100);
		l = Math.round(l*100);

		return " HSL: "+h + ", "+s+"% "+l+"% ";

	}

	public void devideRGB(double r, double g, double b){
		r = r/255.0;
		g = g/255.0;
		b = b/255.0;
	}

}
