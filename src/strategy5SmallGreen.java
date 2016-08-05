import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class strategy5SmallGreen implements Strategy{

	@Override
	public Color getButtonColor() {
		// TODO Auto-generated method stub
		return new Color(138,222,133);
	}

	@Override
	public Color getHighLightColor() {
		// TODO Auto-generated method stub
		return new Color(122, 196, 118);
	}

	@Override
	public Dimension getTextDimension() {
		// TODO Auto-generated method stub
		return new Dimension(600, 155);
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		Font defaultFont = new JLabel().getFont();
		String name = defaultFont.getFontName();
		int style = defaultFont.getStyle();
		int size = defaultFont.getSize();
		//return new JLabel().getFont(); 
		return new Font(name, style, size);
	}

	@Override
	public String getSunMonLabel() {
		// TODO Auto-generated method stub
		return "    Sun         Mon         Tue        Wed        Thu          Fri           Sat";
	}

}
