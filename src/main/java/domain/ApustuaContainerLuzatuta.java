package domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApustuaContainerLuzatuta {
	Apustua apustua;
	List<KuotaContainer> kuotak;
	
	public ApustuaContainerLuzatuta() {
		apustua = null;
		kuotak = new ArrayList<>();
	}
	
	public ApustuaContainerLuzatuta(Apustua a) {
		apustua = a;
		kuotak = new ArrayList<>();
		for(Kuota k : a.getKuotak()) {
			kuotak.add(new KuotaContainer(k));
		}
	}

	public Apustua getApustua() {
		return apustua;
	}

	public void setApustua(Apustua ap) {
		this.apustua = ap;
	}

	public List<KuotaContainer> getKuotak() {
		return kuotak;
	}

	public void setKuotak(List<KuotaContainer> kuotak) {
		this.kuotak = kuotak;
	}

}
