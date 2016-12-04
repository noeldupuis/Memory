import java.util.Scanner;

public class JoueurHumain implements Joueur {
	
	private String nom;
	private int tailleFamille;
	Scanner sc;

	public JoueurHumain(String nom) {
		// TODO Auto-generated constructor stub
		this.nom = nom;
		this.sc = new Scanner(System.in);
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
		int res = -1;
		String temp;
		
		while (res == -1 || res < 0 || res > tapis.taille() || tapis.estVisible(res) || tapis.estPrise(res)) {
			System.out.print("position de la carte #"+numero+" à retourner : ");
			temp = sc.nextLine();
			try {
				res = Integer.parseInt(temp);
				if (res <0 || res >= tapis.taille()) {
					System.out.println("La position est invalide.");
					res = -1;
				}
			}
			catch (NumberFormatException e) {
				if (temp.equals("ABANDON"))
					throw new AbandonException(nom+" abandonne !");
				else if (temp.equals("CHANGE"))
					throw new ChangeException();
				else
					System.out.println("Entrez un entier, ou 'ABANDON'");
			}
		}
		return res;
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
