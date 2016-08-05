import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class strategy4BigRed implements Strategy{

	@Override
	public Color getButtonColor() {
		// TODO Auto-generated method stub
		return new Color(237,121,121);
	}

	@Override
	public Color getHighLightColor() {
		// TODO Auto-generated method stub
		return new Color(220,80,80);
	}

	@Override
	public Dimension getTextDimension() {
		// TODO Auto-generated method stub
		return new Dimension(800, 300);
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		Font defaultFont = new JLabel().getFont();
		String name = defaultFont.getFontName();
		int style = defaultFont.getStyle();
		int size = defaultFont.getSize();
		//return new JLabel().getFont(); 
		return new Font(name, style, size + 15 );
	}

	@Override
	public String getSunMonLabel() {
		// TODO Auto-generated method stub
		return "   Sun     Mon    Tue    Wed    Thu      Fri      Sat";
	}

}
