import java.util.Random;

public class JoueurTricheur extends JoueurExpert {

	public JoueurTricheur(String nom) {
		// TODO Auto-generated constructor stub
		super(nom);
	}
	
	private void setTapis(Tapis<? extends Carte<?,?>> tapis) {
		for (int i=0; i<tapis.taille();i++) {
			if (!tapis.estPrise(i)||!tapis.estVisible(i))
				super.carteRetournee(this.getNom(), 0, i, tapis);
		}
	}
	
	@Override
	public int carteChoisie(int numero, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
		this.setTapis(tapis);
		int ret = -1;
		int i = 0;
		while (i<cartesVues.size()&&ret == -1) {
			if (cartesVues.get(i).size() == this.tailleFamille)
				ret = cartesVues.get(i).get(this.tailleFamille-numero);
			i++;
		}
		if (ret == -1) {
			Random random = new Random();
			while (ret == -1 || tapis.estPrise(ret) || tapis.estVisible(ret)) {
				ret = random.nextInt(tapis.taille());
			}
		}
		return ret;
	}
}
