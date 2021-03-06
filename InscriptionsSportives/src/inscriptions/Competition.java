package inscriptions;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Représente une compétition, c'est-à-dire un ensemble de candidats inscrits
 * à un événement, les inscriptions sont closes à la date dateCloture.
 *
 */

public class Competition implements Comparable<Competition>, Serializable {
	private static final long serialVersionUID = -2882150118573759729L;
	private Inscriptions inscriptions;
	private String nom;
	private Set<Candidat> candidats;
	private LocalDate dateCloture;
	private boolean enEquipe = false;

	Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe) {
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
	}

	/**
	 * Retourne le nom de la compétition.
	 * 
	 * @return
	 */

	public String getNom() {
		return this.nom;
	}

	/**
	 * Modifie le nom de la compétition.
	 */

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, faux si les
	 * inscriptions sont closes.
	 * 
	 * @return
	 */

	public boolean inscriptionsOuvertes() {
		// retourner vrai si et seulement si la date système est antérieure à la date
		// de clôture.
		return LocalDate.now().isBefore(dateCloture);
	}

	/**
	 * Retourne la date de cloture des inscriptions.
	 * 
	 * @return
	 */

	public LocalDate getDateCloture() {
		return dateCloture;
	}

	/**
	 * Est vrai si et seulement si les inscriptions sont réservées aux équipes.
	 * 
	 * @return
	 */

	public boolean estEnEquipe() {
		return enEquipe;
	}

	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la reculer
	 * mais pas de l'avancer.
	 * 
	 * @param dateCloture
	 */

	public void setDateCloture(LocalDate dateCloture) {
		// vérifier que l'on avance pas la date.
		if (dateCloture.isBefore(this.dateCloture))
			return;

		
		this.dateCloture = dateCloture;
	}

	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * 
	 * @return
	 */

	public Set<Candidat> getCandidats() {
		return Collections.unmodifiableSet(candidats);
	}

	/**
	 * Inscrit un candidat de type Personne à la compétition. Provoque une
	 * exception si la compétition est réservée aux équipes ou que les
	 * inscriptions sont closes.
	 * 
	 * @param personne
	 * @return
	 */

	public boolean add(Personne personne) {
		// vérifier que la date de clôture n'est pas passée
		if (LocalDate.now().isAfter(dateCloture))
			return false;

		if (enEquipe)
			throw new RuntimeException();
		personne.add(this);
		return candidats.add(personne);
	}

	/**
	 * Inscrit un candidat de type Equipe à la compétition. Provoque une exception
	 * si la compétition est réservée aux personnes ou que les inscriptions sont
	 * closes.
	 * 
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe) {
		// vérifier que la date de clôture n'est pas passée
		if (LocalDate.now().isAfter(dateCloture))
			return false;

		if (!enEquipe)
			throw new RuntimeException();
		equipe.add(this);

		return candidats.add(equipe);
	}

	/**
	 * Retourne les Candidats que l'on peut inscrire à cette competition.
	 * 
	 * @return les candidats que l'on peut inscrire à cette compétition.
	 */

	public Set<Candidat> getCandidatsAInscrire() {
		// les candidats que l'on peut inscrire à cette compétition.
		Set<Candidat> candidats = new HashSet<>();
		for (Candidat candidat : inscriptions.getCandidats()) {

			if (enEquipe && (candidat instanceof Personne))
				continue;
			if (!enEquipe && (candidat instanceof Equipe))
				continue;
			boolean estInscrit = false;

			for (Competition cmpt : inscriptions.getCompetitions()) {
				if (cmpt.getCandidats().contains(candidat)) {
					estInscrit = true;
					break;
				}
			}
			if (!estInscrit)
				candidats.add(candidat);

		}
		return candidats;
	}

	/**
	 * Désinscrit un candidat.
	 * 
	 * @param candidat
	 * @return
	 */

	public boolean remove(Candidat candidat) {
		candidat.remove(this);
		return candidats.remove(candidat);
	}

	/**
	 * Supprime la compétition de l'application.
	 */

	public void delete() {
		Iterator<Candidat> it = candidats.iterator();
		while(it.hasNext()) {
			Candidat candidat = it.next();
			candidat.remove(this);
			it.remove();
		}
		inscriptions.delete(this);
	}

	@Override
	public int compareTo(Competition o) {
		return getNom().compareTo(o.getNom());
	}

	@Override
	public String toString() {
		return getNom();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidats == null) ? 0 : candidats.hashCode());
		result = prime * result + ((dateCloture == null) ? 0 : dateCloture.hashCode());
		result = prime * result + (enEquipe ? 1231 : 1237);
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Competition other = (Competition) obj;
		if (candidats == null) {
			if (other.candidats != null)
				return false;
		} else if (!candidats.equals(other.candidats))
			return false;
		
		if (dateCloture == null) {
			if (other.dateCloture != null)
				return false;
		} else if (!dateCloture.equals(other.dateCloture))
			return false;
		
		if (enEquipe != other.enEquipe)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

}
