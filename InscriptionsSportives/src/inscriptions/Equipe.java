package inscriptions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant
 * s'inscrire à une compétition.
 * 
 */

public class Equipe extends Candidat {
	private static final long serialVersionUID = 4147819927233466035L;
	private SortedSet<Personne> membres = new TreeSet<>();

	Equipe(Inscriptions inscriptions, String nom) {
		super(inscriptions, nom);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */

	public SortedSet<Personne> getMembres() {
		return Collections.unmodifiableSortedSet(membres);
	}

	/**
	 * Ajoute une personne dans l'équipe.
	 * 
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre) {
		membre.add(this);
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'équipe.
	 * 
	 * @param membre
	 * @return
	 */

	public boolean remove(Personne membre) {
		membre.remove(this);
		return membres.remove(membre);
	}

	/**
	 * Retourne les personnes que l'on peut ajouter dans cette équipe.
	 * 
	 * @return les personnes que l'on peut ajouter dans cette équipe.
	 */

	public Set<Personne> getPersonnesAAjouter() {
		// retourner les personnes que l'on peut ajouter dans cette équipe.
		// des equipes -> des personnes
		// on veut les personnes qui ne sont inscrit a acune equipe
		Set<Personne> persons = new HashSet<>();
		for (Personne person : inscriptions.getPersonnes()) {
			boolean estInscrit = false;
			for (Equipe equipe : inscriptions.getEquipes()) {
				if (equipe.getMembres().contains(person)) {
					estInscrit = true;
					break;
				}
			}
			if (!estInscrit)
				persons.add(person);

		}
		return persons;
	}

	@Override
	public void delete() {
		super.delete();
	}

	@Override
	public String toString() {
		return "Equipe " + super.toString();
	}
}
