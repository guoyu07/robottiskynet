import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Pilot {

	Control control;

	public void setobjects(Control c) {
		this.control = c;
	}

	// "pilotti" switch case, jolla ohjataan ohjelman kulkua
	public void Run(int type) {

		switch (type) {
		case 1:
			Valikko();
			break;

		case 2:
			Calibrate();
			break;

		case 3:
			Drive();
			break;
		}
	}

	// case Main menu, jossa on sensorien kalibrointi, ohjelman sammutus ja
	// robotin ajoon siirtymis vaihtoehdot.
	public void Valikko() {
		while (!control.getStop()) {
			control.Printstring("kalibrointi >", 0, 0);
			control.Printstring("aja <", 0, 1);
			control.Printstring("esc sammuta", 0, 2);
			if (Button.RIGHT.isPressed()) {
				LCD.clear();
				Run(2);
			} else if (Button.LEFT.isPressed()) {
				LCD.clear();
				Run(3);
			}else if (Button.ESCAPE.isPressed()){
				control.shutdown();
			}
		}
	}

	// case v�risensorin kalibrointi. T�m�n casen aikana haetaan k�ytett�v�n
	// viivan maksimi musta arvo, jotta ohjelma tiet�� koska se k��ntyy.
	public void Calibrate() {
		while (!control.getStop()) {
			control.Printstring("black >", 0, 0);
			control.Printstring("white <", 0, 1);
			control.Printstring("enter menu", 0, 2);
			
			int ultrasensoridata = control.sense(); // ultrasensorin arvot
			control.printer.printint(ultrasensoridata, 0, 6);
			if (Button.RIGHT.isPressed()) {
				control.setBlackLight();
				// getit ja setit controlliin
				control.Printint(control.getBlackLight(), 0, 3);
			} else if (Button.LEFT.isPressed()) {
				control.setWhiteLight();
				control.Printint(control.getWhiteLight(), 0, 4);
			} else if (Button.ENTER.isPressed()) {
				LCD.clear();
				Run(1);
			}
		}
	}

	// case Ajo-tila. T�ss� casessa robotti pyrkii seuraamaan mustaa viivaa.
	public void Drive() {
		while (!control.getStop()) {
			// control.colorsensor.checkcolor();
			control.Printint(control.getLight(), 0, 5);
			int ultrasensoridata = control.sense();
			control.printer.printint(ultrasensoridata, 0, 6);

			if (control.getLight() <= control.getBlackLight()) {
				control.turnRight();
			} else {
				control.turnLeft();
			}
			if (Button.ESCAPE.isPressed()) {
				control.shutdown();
		}
		
		}
	}

}
