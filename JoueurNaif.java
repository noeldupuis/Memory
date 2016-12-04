import java.util.Random;

public class JoueurNaif implements Joueur {

	private String nom;
	private int tailleFamille;
	
	public JoueurNaif(String nom) {
		// TODO Auto-generated constructor stub
		this.nom = nom;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return this.nom;
	}

	@Override
	public void commencer(int tailleFamille) {
		// TODO Auto-generated method stub
		this.tailleFamille = tailleFamille;
	}

	@Override
	public int carteChoisie(int numero, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
		int ret = -1;
		Random random = new Random();
		while (ret == -1 || tapis.estPrise(ret) || tapis.estVisible(ret)) {
			ret = random.nextInt(tapis.taille());
		}
		return ret;
	}

	@Override
	public void carteRetournee(String nomJoueur, int numero, int position, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cartePrise(String nomJoueur, int position, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub

	}

}
