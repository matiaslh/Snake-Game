
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AL extends KeyAdapter {
	public int dx=0,dy=1,difficulty=5;
	public boolean close=false;
	boolean once=false,retry=false;
	
	Main m;
	public AL(Main main){
		this.m=main;
	}
	
	public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (!once){
        	if (keyCode == KeyEvent.VK_0 || keyCode == 96){
            	once=true;
            	difficulty=3;
            }
        	if (keyCode == KeyEvent.VK_1 || keyCode == 97){
            	once=true;
            	difficulty=7;
            }
			if (keyCode == KeyEvent.VK_2 || keyCode == 98){
				once=true;
            	difficulty=12;     	
			}
			if (keyCode == KeyEvent.VK_3 || keyCode == 99){
				once=true;
            	difficulty=15;
			}
			if (keyCode == KeyEvent.VK_4 || keyCode == 100){
				once=true;
            	difficulty=19;
			}
			if (keyCode == KeyEvent.VK_5 || keyCode == 101){
				once=true;
            	difficulty=22;
			}
			if (keyCode == KeyEvent.VK_6 || keyCode == 102){
				once=true;
            	difficulty=29;
			}
			if (keyCode == KeyEvent.VK_7 || keyCode == 103){
				once=true;
            	difficulty=37; 	
			}
			if (keyCode == KeyEvent.VK_8 || keyCode == 104){
				once=true;
            	difficulty=42;
			}
			if (keyCode == KeyEvent.VK_9 || keyCode == 105){
				once=true;
            	difficulty=50;
			}

        }
        
        if (once){
        	if (keyCode == KeyEvent.VK_LEFT){
        		if (dx!=1){
	        	dx=-1;
				dy=0;
        		}
	        }
	        if (keyCode == KeyEvent.VK_RIGHT){
	        	if (dx!=-1){
		        	dx=1;
					dy=0;
	        	}
	        }
	        if (keyCode == KeyEvent.VK_UP){
	        	if (dy!=1){
		        	dy=-1;
					dx=0;
	        	}
	        }
	        if (keyCode == KeyEvent.VK_DOWN){
	        	if (dy!=-1){
		        	dy=1;
					dx=0;
	        	}
	        }
        }
        
        if (keyCode == KeyEvent.VK_ESCAPE){
        	close=true;
        }
        if (keyCode == KeyEvent.VK_SPACE && !m.render){
        	retry=true;
        }
    }
}
