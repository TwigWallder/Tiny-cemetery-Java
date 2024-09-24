package utils;

import java.awt.Color;

public class ColorPerso {
	/*
	 * Color palette from:
	 * https://lospec.com/palette-list/mushfairy
	 */
	
    public Color DARK_PURPLE_COLOR(int alpha) {
        return new Color(39, 35, 76, alpha);
    }

    public Color PURPLE_COLOR(int alpha) {
        return new Color(115, 60, 124, alpha);
    }
    
    public Color LIGHT_PURPLE_COLOR(int alpha) {
        return new Color(178, 150, 255, alpha);
    }
    
    public Color WHITE_COLOR(int alpha) {
    	return new Color(221, 228, 255, alpha);
    }
    
    public Color YELLOW_COLOR(int alpha) {
    	return new Color(211, 211, 124, alpha);
    }

    public Color PINK_COLOR(int alpha) {
    	return new Color(232, 141, 195, alpha);
    }
    
    public Color RED_COLOR(int alpha) {
    	return new Color(242, 80, 75, alpha);
    }
    
    public Color BLUE_COLOR(int alpha) {
    	return new Color(34, 80, 145, alpha);
    }
    
    public Color TURQUOISE_COLOR(int alpha) {
    	return new Color(0, 117, 130, alpha);
    }
    
    public Color CYAN_COLOR(int alpha) {
    	return new Color(57, 184, 163, alpha);
    }
}