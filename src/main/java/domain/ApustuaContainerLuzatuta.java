package domain;

import java.util.ArrayList;
import java.util.List;

public class ApustuaContainerLuzatuta {
	ApustuaContainer ap;
	List<KuotaContainer> kuotak;
	Double diruKop;
	
	public ApustuaContainerLuzatuta() {
		ap = null;
		kuotak = new ArrayList<>();
		diruKop = 0.0;
	}
	
	public ApustuaContainerLuzatuta(Apustua a) {
		ap = new ApustuaContainer(a);
		kuotak = new ArrayList<>();
		diruKop = a.getDiruKop();
		for(Kuota k : a.getKuotak()) {
			kuotak.add(new KuotaContainer(k));
		}
	}

	public ApustuaContainer getApustua() {
		return ap;
	}

	public void setAp(ApustuaContainer ap) {
		this.ap = ap;
	}

	public List<KuotaContainer> getKuotak() {
		return kuotak;
	}

	public void setKuotak(List<KuotaContainer> kuotak) {
		this.kuotak = kuotak;
	}

	public Double getDiruKop() {
		return diruKop;
	}
}
