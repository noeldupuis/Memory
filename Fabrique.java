/** L'objectif de cette classe est de créer différents objets sans
 * avoir à connaître les choix de réalisation qui ont été faits
 * concernant leurs classes.
 *
 * @author	Xavier Crégut <Prenom.Nom@enseeiht.fr>
 */
public class Fabrique {

	/** Créer un nouveau joueur expert.
	 * @param nom le nom du joueur créé
	 * @return un nouveau joueur expert
	 */
	static public Joueur creerJoueurExpert(String nom) {
		return new JoueurExpert(nom);	// TODO: à corriger
	}
	

	/** Créer un nouveau joueur naif.
	 * @param nom le nom du joueur créé
	 * @return un nouveau joueur naif
	 */
	static public Joueur creerJoueurNaif(String nom) {
		return new JoueurNaif(nom);	// TODO: à corriger
	}
	

	/** Créer un nouveau joueur humain.
	 * @param nom le nom du joueur créé
	 * @return un nouveau joueur humain
	 */
	static public Joueur creerJoueurHumain(String nom) {
		return new JoueurHumain(nom);	// TODO: à corriger
	}
	

	/** Créer un nouveau joueur tricheur.
	 * @param nom le nom du joueur créé
	 * @return un nouveau joueur tricheur
	 */
	static public Joueur creerJoueurTricheur(String nom) {
		return new JoueurTricheur(nom);	// TODO: à corriger
	}


	/** Créer un nouveau tapis.
	 * @param jeu le jeu de carte
	 * @return un nouveau tapis
	 */
	static public <C,F,V> Tapis<C> creerTapis(JeuCartesMemory<F, V> jeu) {
		return new TapisMemory(jeu);	// TODO: à corriger
	}

	/** Créer un nouvel arbitre.
	 * @param tailleEcran la taille de l'écran en nombre de caractères
	 * @param confiant vrai si l'arbitre est confiant, faux s'il est méfiant
	 * @return un nouvel arbitre
	 */
	static public <F,V> Arbitre<F,V>
	creerArbitre(int tailleEcran, boolean mefiant) {
		return new Arbitre<F,V>(tailleEcran, false);	// TODO: à corriger
	}

	public static void main(String[] args) {
		// Utiliser creerTapis
		JeuCartesMemory<String, Integer> jeu
					= FabriqueJeuCartes.jeuFamilles(6, 3);
		Tapis<?> tapis = Fabrique.creerTapis(jeu);
		AfficheurTapis afficheur = new AfficheurTapis(jeu, 80);
		System.out.println(afficheur.toString(tapis));


		// Utiliser creerArbitre
		Arbitre<String, Integer> arbitre = Fabrique.creerArbitre(80, false);

		// inscrire les joueurs
		arbitre.inscrire(Fabrique.creerJoueurNaif("naif"));
		arbitre.inscrire(Fabrique.creerJoueurExpert("expert"));
		arbitre.inscrire(Fabrique.creerJoueurTricheur("tricheur"));
		arbitre.inscrire(Fabrique.creerJoueurHumain("humain"));

		// Jouer une partie
		arbitre.arbitrer(jeu, 3);
	}

}
