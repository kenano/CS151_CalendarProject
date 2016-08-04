import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public interface Strategy
{
	public Color getButtonColor();
	public Color getHighLightColor(); 
	public Dimension getTextDimension(); 
	public Font getFont(); 
	public String getSunMonLabel();
}