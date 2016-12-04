import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/** La classe Arbitre permet de gérer une partie de Memory
  * entre plusieurs jourueurs.
  *
  * @author	Xavier Crégut <Prenom.Nom@enseeiht.fr>
  */
public class Arbitre<F,V> {

	private int tailleFamille, nbFamille, tailleEcran;
	private List<Joueur> joueurs;
	private List<Boolean> canPlay;
	private int nombreJoueurRestant;
	private AfficheurTapis afficheur;
	private boolean mefiant;
	private JeuCartesMemory<F,V> jeu;
	private Joueur courant;
	private Scores scores;
	Scanner sc;
	private boolean anyHuman;

	public Arbitre(int tailleEcran, boolean mefiant) {
		// TODO Auto-generated constructor stub
		this.tailleEcran = tailleEcran;
		this.mefiant = mefiant;
		joueurs = new ArrayList<Joueur>();
		canPlay = new ArrayList<Boolean>();
		sc = new Scanner(System.in);
		anyHuman = false;
	}

	/** Inscrire un joueur.
	 * @param joueur le joueur à inscrire
	 */
	public void inscrire(Joueur joueur) {
		if (isNew(joueur)) {
			joueurs.add(joueur);
			if (joueur instanceof JoueurHumain)
				anyHuman = true;			
		}
		else 
			throw new JoueurExistantException(joueur.getNom());
	}
	
	private boolean isNew(Joueur joueur) {
		boolean ret = true;
		for (Joueur j: joueurs) {
			ret = ret && !j.getNom().equals(joueur.getNom());
		}
		return ret;
	}

	/** Arbitrer une partie de Memory.  La taille de famille utilisée doit être
	 * un diviseur de la taille d'une famille du jeu de carte.
	 * @param jeu le jeu de carte à utliser
	 * @param tailleFamille la taille d'une famille à utilise.
	 * @param <F> le type des familles du jeu de cartes
	 * @param <V> le type des valeurs du jeu de cartes
	 */
	public void arbitrer(JeuCartesMemory<F, V> jeu, int tailleFamille) {
		this.jeu = jeu;
		this.afficheur = new AfficheurTapis(jeu, tailleEcran);
		int nbCartes=0;
		Iterator<Carte<F, V>> it = jeu.iterator();
		while (it.hasNext()) {
			nbCartes++;
			it.next();
		}
		this.nbFamille = nbCartes/tailleFamille;
		this.tailleFamille = jeu.tailleFamille();
		boolean changeJoueur;
		if (this.tailleFamille%tailleFamille != 0)
			throw new RuntimeException("Erreur: la taille de famille utilisée doit etre un diviseur de "+this.tailleFamille);
		System.out.println("Je mélange le jeu...");
		jeu.battre();
		System.out.println("Je distribue les cartes");
		TapisMemory<F, V> tapis_fin = new TapisMemory<F, V>(jeu);
		ProcurationTapis<F,V> tapis = new ProcurationTapis<F,V>(tapis_fin, this.mefiant);
		System.out.println("Le jeu commence avec des familles de taille "+tailleFamille);
		System.out.println();
		
		for (Joueur j : joueurs) {
			j.commencer(tailleFamille);
			canPlay.add(true);
		}
		nombreJoueurRestant = joueurs.size();
		scores = new Scores(joueurs, joueurs.size());
		Iterator<Joueur> it1 = this.joueurs.iterator();
		courant = it1.next();
		
		
		while (tapis.nbCartesRestantes()>0 && nombreJoueurRestant != 0){
			changeJoueur = true;
			if (canCurrentPlayerPlay(courant)) {
				System.out.println("C'est au tour de "+courant.getNom());
				System.out.println(afficheur.toString(tapis));
				changeJoueur = this.jeuCourant(tapis);
			}
			if (changeJoueur) {
				if (it1.hasNext()) {
					courant = it1.next();
				}
				else {
					it1 = this.joueurs.iterator();
					courant = it1.next();
				}
			}
			System.out.println();
			reinitJeu(tapis);
		}
		
		scores.printScores();
		
	}

	private boolean canCurrentPlayerPlay(Joueur courant2) {
		// TODO Auto-generated method stub
		int i = 0;
		while (!joueurs.get(i).getNom().equals(courant2.getNom())) {
			i++;
		}
		return canPlay.get(i);
	}

	private void reinitJeu(Tapis tapis) {
		// TODO Auto-generated method stub
		for (int i = 0; i<tapis.taille();i++) {
			tapis.masquer(i);
		}
	}
	
	private void priseJoueurs(List<Joueur> joueurs2, int i, Tapis<? extends Carte<?, ?>> tapis) {
		for (Joueur j:joueurs2)
			j.cartePrise(courant.getNom(), i, tapis);
	}
	
	private void retourneeJoueurs(List<Joueur> joueurs2, int i, Tapis<? extends Carte<?, ?>> tapis, int position) {
		for (Joueur j:joueurs)
			j.carteRetournee(courant.getNom(), i, position, tapis);
	}

	private <F,V> boolean jeuCourant(Tapis<Carte<F, V>> tapis) {
		// TODO Auto-generated method stub
		
			int position;
			ArrayList<Integer> cartesChoisies = new ArrayList<Integer>();;
			try {
				for (int i=0;i<this.tailleFamille;i++){
	
					position = courant.carteChoisie(i+1, tapis);
					tapis.montrer(position);
					cartesChoisies.add(position);
					System.out.println(courant.getNom()+" retourne la carte #"+position+": "+tapis.get(position).getFamille()+" "+tapis.get(position).getValeur()+".");
					System.out.println(afficheur.toString(tapis));
					retourneeJoueurs(joueurs,i,tapis,position);
					if (!(courant instanceof JoueurHumain) && anyHuman) {
						System.out.print("Appuyez sur Entrée...");
						String res = sc.nextLine();
						if (res.equals("CHANGE"))
							throw new ChangeException();
					}
					}
				boolean memeFamille=true;
				for(int i=0; i<cartesChoisies.size()-1;i++) {
					memeFamille=memeFamille && tapis.get(cartesChoisies.get(i)).getFamille().equals(tapis.get(cartesChoisies.get(i+1)).getFamille());
				}
				if (memeFamille) {
					for (int i:cartesChoisies) {
						tapis.prendre(i);
						priseJoueurs(joueurs,i,tapis);
					}
					scores.incr(courant.getNom());
					return false;
				}
				return true;

			}
			catch (OperationInterditeException e) {
				System.out.println("Le joueur "+courant.getNom()+" a triché: ELIMINE");
				rmjoueur(courant.getNom());
			}
			catch (AbandonException e){
				System.out.println("Le joueur "+courant.getNom()+" a ABANDONE");
				rmjoueur(courant.getNom());
			}
			catch (ChangeException e) {
				String temp1="",temp2="";
				while (!existsPlayer(temp1)) {
					System.out.print("Entrez le nom du joueur dont il faut changer la stratégie: ");
					temp1 = sc.nextLine();
				}
				while(!temp2.equals("humain") && !temp2.equals("tricheur") && !temp2.equals("expert") && !temp2.equals("naif")) {
					System.out.print("Entrez la nouvelle strategie: ");
					temp2 = sc.nextLine();
				}
				int i=0;
				while (!joueurs.get(i).getNom().equals(temp1)) {
					i++;
				}
				Joueur newPlayer = parseJoueur(temp1+"@"+temp2);
				joueurs.set(i, newPlayer);
				newPlayer.commencer(this.tailleFamille);
				canPlay.set(i, true);
				this.nombreJoueurRestant++;
				anyHuman = false;
				for (Joueur j: joueurs) {
					if (j instanceof JoueurHumain)
						anyHuman = true;
				}
				return false;
			}

			return true;
	}

	private boolean existsPlayer(String temp1) {
		// TODO Auto-generated method stub
		int i=0;
		while (i< joueurs.size() && !joueurs.get(i).getNom().equals(temp1)) {
			i++;
		}
		if (i!=joueurs.size()) {
			return true;
		}
		else {
			return false;
		}
	}

	private void rmjoueur(String nom) {
		// TODO Auto-generated method stub
		int i = 0;
		while (!joueurs.get(i).getNom().equals(nom)) {
			i++;
		}
		canPlay.set(i,false);
		this.nombreJoueurRestant--;
	}

	private static Joueur parseJoueur(String string) {
		// TODO Auto-generated method stub
		String[] temp =string.split("@");
		Joueur ajout;
		if (temp.length != 2)
			throw new RuntimeException("Usage: nom@strategie");
		if (temp[1].equals("humain"))
			ajout = new JoueurHumain(temp[0]);
		else if (temp[1].equals("tricheur"))
			ajout = new JoueurTricheur(temp[0]);
		else if (temp[1].equals("expert"))
			ajout = new JoueurExpert(temp[0]);
		else if (temp[1].equals("naif"))
			ajout = new JoueurNaif(temp[0]);
		else
			throw new RuntimeException("Erreur: joueur "+temp[0]+"\nLa stratégie doit être humain|tricheur|expert|naif");
		return ajout;
	}
}
