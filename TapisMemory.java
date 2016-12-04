import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TapisMemory<F,V> implements Tapis<Carte<F, V>> {
	
	private int taille;
	private int nbCrtRst;
	private JeuCartesMemory<F,V> cartes;
	private List<Boolean> visibilite;
	private List<Boolean> prise;
	

	public TapisMemory(JeuCartesMemory<F,V> jeu) {
		// TODO Auto-generated constructor stub
		this.taille =0;
		for (Carte<F,V> j:jeu) {
			this.taille+=1;
		}
		this.nbCrtRst = this.taille;
		visibilite= new ArrayList<Boolean>(this.taille);
		prise= new ArrayList<Boolean>(this.taille);
		for (int j=0;j<this.taille;j++) {
			this.visibilite.add(false);
			this.prise.add(false);
		}
		cartes = jeu;
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return this.taille;
	}

	@Override
	public int nbCartesRestantes() {
		// TODO Auto-generated method stub
		return this.nbCrtRst;
	}

	@Override
	public void montrer(int position) {
		// TODO Auto-generated method stub
		this.visibilite.set(position, true);
	}

	@Override
	public void masquer(int position) {
		// TODO Auto-generated method stub
		this.visibilite.set(position,  false);
	}

	@Override
	public void prendre(int position) {
		// TODO Auto-generated method stub
		this.prise.set(position,  true);
		this.nbCrtRst--;
	}

	@Override
	public boolean estPrise(int position) {
		// TODO Auto-generated method stub
		return prise.get(position);
	}

	@Override
	public boolean estVisible(int position) {
		// TODO Auto-generated method stub
		return visibilite.get(position);
	}

	@Override
	public Carte<F,V> get(int position) {
		// TODO Auto-generated method stub
		int indice = 0;
		Iterator<Carte<F,V>> it = this.cartes.iterator();
		Carte<F,V> res = it.next();
		while (indice != position) {
			res = it.next();
			indice++;
		}
		return res;
	}

}
