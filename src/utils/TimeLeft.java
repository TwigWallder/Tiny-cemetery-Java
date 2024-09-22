package utils;

import main.Main;

public class TimeLeft {
	Main m;
	
	public TimeLeft(Main m) {
		this.m = m;
	}
	
	public int getTimer() {
    	m.counterTime++;
    	if(m.counterTime > 100) {
    		m.timeLeft --;
    		m.counterTime = 0;
    	}
    	
    	return m.timeLeft; 
    }
}