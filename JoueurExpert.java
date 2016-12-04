import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoueurExpert implements Joueur {

	/* Stratégie:
	 * Le joueur expert va retenir toutes les cartes retournées au cours du jeu dans une liste de cardinal le nombre de famille, et contenant les listes des positions
	 * des cartes retournées. Lors du choix de la carte, il va d'abord vérifier si une famille a été complètement découverte, si il en trouve une (cardinal d'une
	 * des listes de familles égal au nombre de cartes d'une famille) il va la jouer, sinon, il pioche aléatoirement des cartes pour en découvrir. On aurait pu 
	 * imaginer qu'il retourne que des cartes non découvertes, c'est un choix que je n'ai pas fait, faute d'intérêt particulier par rapport aux autres contraintes.
	 */
	private String nom;
	protected int tailleFamille;
	protected List<List<Integer>> cartesVues;
	
	public JoueurExpert(String nom) {
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
		cartesVues= new ArrayList<List<Integer>>();
	}

	private boolean isKnownFamille(int pos, Tapis<? extends Carte<?,?>> tapis) {
		boolean ret = false;
		for (int i = 0; i<cartesVues.size(); i++) {
			tapis.montrer(cartesVues.get(i).get(0));
			ret = ret || (tapis.get(cartesVues.get(i).get(0)).getFamille() == tapis.get(pos).getFamille());
		}
		return ret;	
	}
	
	private int getFamille(int pos, Tapis<? extends Carte<?,?>> tapis) {
		int i=0;
		while (tapis.get(this.cartesVues.get(i).get(0)).getFamille() != tapis.get(pos).getFamille()){
			i++;
		}
		return i;		
	}
	
	@Override
	public int carteChoisie(int numero, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
		int ret = -1;
		int i = 0;
		boolean contains = true;
		while (i<cartesVues.size()&&ret == -1) {
			if (cartesVues.get(i).size() == this.tailleFamille)
				ret = cartesVues.get(i).get(this.tailleFamille-numero);
			i++;
		}
		if (ret == -1) {
			Random random = new Random();
			while (ret == -1 || tapis.estPrise(ret) || tapis.estVisible(ret) || contains) { // A cet endroit, on peut rajouter une condition qui vérifie si la carte random est dans une des listes. C'est une autre version de expert possible.
				ret = random.nextInt(tapis.taille());
				contains = false;
				for (List ind:cartesVues) {
					contains = contains || ind.contains(ret);
				}
			}
		}
		for (int i1 = 0; i1<cartesVues.size(); i1++)
			tapis.masquer(cartesVues.get(i1).get(0));
		return ret;
	}

	@Override
	public void carteRetournee(String nomJoueur, int numero, int position, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
		int i;
		if (this.isKnownFamille(position, tapis)) {
			i = this.getFamille(position, tapis);
			if (!this.cartesVues.get(i).contains(position))
				this.cartesVues.get(i).add(position);
		}
		else {
			i = this.cartesVues.size();
			this.cartesVues.add(new ArrayList<Integer>());
			this.cartesVues.get(i).add(position);
		}
	}

	@Override
	public void cartePrise(String nomJoueur, int position, Tapis<? extends Carte<?, ?>> tapis) {
		// TODO Auto-generated method stub
	    int i = this.getFamille(position,tapis);
	    int remIndex = this.cartesVues.get(i).lastIndexOf(position);
	    this.cartesVues.get(i).remove(remIndex); 
	    if (this.cartesVues.get(i).size() == 0)
	    	this.cartesVues.remove(i);
	}

}
