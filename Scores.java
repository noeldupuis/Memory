import java.util.ArrayList;
import java.util.List;

public class Scores {
	
	List<Integer> scores;
	private List<Joueur> joueurs;
	
	public Scores(List<Joueur> joueurs, int taille) {
		// TODO Auto-generated constructor stub
		this.joueurs = joueurs;
		this.scores = new ArrayList<Integer>();
		for (int i=0;i<taille;i++) {
			scores.add(i, 0);
		}
	}
	
	public int get(String nom) {
		int i = 0;
		while (!joueurs.get(i).getNom().equals(nom)) {
			i++;
		}
		return scores.get(i);
	}
	
	public void incr(String nom) {
		int i = 0;
		while (!joueurs.get(i).getNom().equals(nom)) {
			i++;
		}
		scores.set(i,scores.get(i)+1);
	}
	
	public void printScores() {
		for (int i=0;i<scores.size(); i++) {
			System.out.println("Le joueur "+joueurs.get(i).getNom()+" a marqué "+scores.get(i)+" points");
		}
	}

}
