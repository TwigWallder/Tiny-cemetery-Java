package spell;

import main.Main;

public class AnimationSpell {    
	
	// animation
    public boolean animationFD = false;
    public long animationFDTime = 0;
    public static final long ANIMATION_FD = 500; 
    
    public boolean animationM = false;
    public long animationMTime = 0;
    public static final long ANIMATION_M = 500; 
    
    public boolean animationFW = false;
    public long animationFWTime = 0;
    public static final long ANIMATION_FW = 500; 
    
    public boolean animationE = false;
    public long animationETime = 0;
    public static final long ANIMATION_E = 500; 
    
    Main m;

    public AnimationSpell(Main m) {
    	this.m = m;
    }
    public void update() {
    	long ctm =  System.currentTimeMillis();
    	// animation timer
        if (animationFD && ctm - animationFDTime > ANIMATION_FD)  animationFD = false;

        if (animationM && ctm - animationMTime > ANIMATION_M)  animationM = false;

        if (animationFW && ctm - animationFWTime > ANIMATION_FW) animationFW = false;

        if (animationE && ctm - animationETime > ANIMATION_E) animationE = false;
    }

    public boolean getAnimation(int id, int i, int j) {
    	switch(id) {
    	case(0):
    		// patternFD
    		return Math.abs(m.playerX - j) <= 1 && Math.abs(m.playerY - i) <= 1;
    	case(1):
    		// patternM
    		return Math.abs(m.playerX - j) <= 3 && Math.abs(m.playerY - i) <= 3;
    	case(2):
    		// patternFW
    		return Math.abs(m.playerY - i) <= 1;
    	case(3):
    		return Math.abs(m.playerX - j) <= 100;
    	}
		return false;
    }
}
