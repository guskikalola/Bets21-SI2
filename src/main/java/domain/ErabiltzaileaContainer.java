package domain;

import java.util.ArrayList;
import java.util.List;

public class ErabiltzaileaContainer {
	Erabiltzailea e;
	List<ApustuaContainerLuzatuta> apustuak;
	
	public ErabiltzaileaContainer() {
		e = null;
		apustuak = new ArrayList<ApustuaContainerLuzatuta>();
	}
	
	public ErabiltzaileaContainer(Erabiltzailea e) {
		this.e = e;
		this.apustuak = new ArrayList<>();
		for(Apustua a : e.getApustuak()) {
			apustuak.add(new ApustuaContainerLuzatuta(a));
		}
	}

	public Erabiltzailea getE() {
		return e;
	}

	public void setE(Erabiltzailea e) {
		this.e = e;
	}

	public List<ApustuaContainerLuzatuta> getApustuak() {
		return apustuak;
	}

	public void setApustuak(List<ApustuaContainerLuzatuta> apustuak) {
		this.apustuak = apustuak;
	}
}
