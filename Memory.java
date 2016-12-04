public class Memory {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Arbitre<String, Integer> arbitre;
		JeuCartesMemory<String,Integer> jeu;
		AfficheurTapis afficheur;
		
		int largeurEcran, nbFamille, tailleFamille;
		boolean mefiant=true;
		
		if (args.length < 4)
			throw new RuntimeException("Not enough arguments.\n Usage: java Memory largeurEcran nombreDeFamilles tailleDUneFamille [-mefiant|-confiant] nom@strategie*.");
		
		try {
			largeurEcran = Integer.parseInt(args[0]);
			nbFamille = Integer.parseInt(args[1]);
			tailleFamille = Integer.parseInt(args[2]);
		}
		catch (NumberFormatException e){
			throw new RuntimeException("Error in Integer arguments.\n Usage: java Memory largeurEcran(px) nombreDeFamilles tailleDUneFamille [-mefiant|-confiant] nom@strategie*.");
		}
		
		jeu = FabriqueJeuCartes.jeuFamilles(nbFamille,tailleFamille);
		afficheur = new AfficheurTapis(jeu, largeurEcran);
		
		
		if(args[3].equals("-confiant")||args[3].equals("-mefiant")) {
			mefiant = args[3].equals("-mefiant");
			arbitre = new Arbitre<String, Integer>(largeurEcran, mefiant);
		}
		else {
			arbitre = new Arbitre<String, Integer>(largeurEcran, mefiant);
				arbitre.inscrire(parseJoueur(args[3]));
		}
		
		for (int i = 4; i<args.length; i++) {
				arbitre.inscrire(parseJoueur(args[i]));
		}
		
		arbitre.arbitrer(jeu, tailleFamille);
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
