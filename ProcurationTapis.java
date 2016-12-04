import java.util.List;
import java.util.ArrayList;

/**
 * @author noel
 *
 * Le principe du proxy est le suivant: Pour tricher, le tricheur doit avoir acces aux information des cartes. le proxy va lui interdire tant qu'il ne joue (montre) pas
 * la carte à laquelle il veut accéder. Dès lors qu'il en montre une, il va pouvoir accéder aux informations de cette carte. Les cartes dont il peut tirer les informations 
 * contiennent un true dans leur indice du tableau gettable. Sinon il se fait recaler et on découvre la supercherie (#OperationInterditeException).
 */
public class  ProcurationTapis<F,V> implements Tapis<Carte<F, V>> {

	private boolean mefiant;
	private Tapis<Carte<F, V>> tapis;
	private List<Boolean> gettable;

	
	public ProcurationTapis(Tapis<Carte<F, V>> tapis, boolean mefiant) {
		// TODO Auto-generated constructor stub
		this.tapis = tapis;
		this.mefiant = mefiant;
		this.gettable = new ArrayList<Boolean>();
		for (int i=0;i<tapis.taille();i++) {
			this.gettable.add(false);
		}
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return tapis.taille();
	}

	@Override
	public int nbCartesRestantes() {
		// TODO Auto-generated method stub
		return tapis.nbCartesRestantes();
	}

	@Override
	public void montrer(int position) {
		// TODO Auto-generated method stub
		this.gettable.set(position,true);
		tapis.montrer(position);
	}

	@Override
	public void masquer(int position) {
		this.gettable.set(position,false);
		tapis.masquer(position);
	}

	@Override
	public void prendre(int position) {
		// TODO Auto-generated method stub
		tapis.prendre(position);
	}

	@Override
	public boolean estPrise(int position) {
		// TODO Auto-generated method stub
		return tapis.estPrise(position);
	}

	@Override
	public boolean estVisible(int position) {
		// TODO Auto-generated method stub
		return tapis.estVisible(position);
	}

	@Override
	public Carte<F,V> get(int position) {
		// TODO Auto-generated method stub
		if ((this.gettable.get(position) && this.mefiant) || !this.mefiant) {
			return (Carte<F, V>) tapis.get(position);
		}
		else {
			throw new OperationInterditeException();
		}
	}
}
