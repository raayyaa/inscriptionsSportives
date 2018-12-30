package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CompetitionTest {
	private Inscriptions inscriptions;
	private Competition enEquipe, enPersonne;
	private static final LocalDate DATE_CLOTURE = LocalDate.now().plusDays(30);
	@Before
	public void setUp() throws Exception {

		inscriptions = Inscriptions.getInscriptions();
		inscriptions.reinitialiser();
		Personne	boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		enEquipe = inscriptions.createCompetition("c1", DATE_CLOTURE, true);
		enPersonne = inscriptions.createCompetition("c2", DATE_CLOTURE, false);
        Equipe b = inscriptions.createEquipe("b");
		enPersonne.add(boris);
		b.add(boris);
		enEquipe.add(b);
		// lesManouches = inscriptions.createEquipe("Les Manouches");
		// lesManouches.add(boris);
		// lesManouches.add(tony);
	}

	@Test
	public void testInscriptionsOuvertes() {
		assertTrue(enEquipe.inscriptionsOuvertes());
		enEquipe = inscriptions.createCompetition("c1", LocalDate.now().minusDays(5), true);
		assertFalse(enEquipe.inscriptionsOuvertes());


	}

	@Test
	public void testSetDateCloture() {
		enEquipe.setDateCloture(LocalDate.now().plusDays(15));
		assertEquals(DATE_CLOTURE, enEquipe.getDateCloture());
		enEquipe.setDateCloture(DATE_CLOTURE.plusDays(10));
		assertNotEquals(DATE_CLOTURE,enEquipe.getDateCloture());
		assertEquals(DATE_CLOTURE.plusDays(10), enEquipe.getDateCloture());

	}

	@Test
	public void testAddPersonne() {
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
		assertTrue(enPersonne.add(tony));
		assertFalse(enPersonne.add(tony));
		enPersonne = inscriptions.createCompetition("c1", LocalDate.now().minusDays(5), false);
		assertFalse(enPersonne.add(tony));
		
		try {
			enEquipe.add(tony);
			fail("Runtime exception expected");
		}catch(RuntimeException e){
			
		}
	}

	@Test
	public void testAddEquipe() {
		Equipe equipe = inscriptions.createEquipe("e");
		assertTrue(enEquipe.add(equipe));
		assertFalse(enEquipe.add(equipe));
		enEquipe = inscriptions.createCompetition("c1", LocalDate.now().minusDays(5), true);
		assertFalse(enEquipe.add(equipe));
		
		try {
			enPersonne.add(equipe);
			fail("Runtime exception expected");
		}catch(RuntimeException e){
			
		}
	}

	@Test
	public void testGetCandidatsAInscrire() {
		Set<Candidat> personnes = new HashSet<>();
		personnes.add(inscriptions.createPersonne("p1", "p1", "mail"));
		personnes.add(inscriptions.createPersonne("p3", "p3", "mail"));
		Set<Candidat> equipes = new HashSet<>();
		equipes.add(inscriptions.createEquipe("a"));
		assertEquals(personnes,enPersonne.getCandidatsAInscrire());
		assertEquals(equipes, enEquipe.getCandidatsAInscrire());
		
		
	}

	@Test
	public void testRemove() {
		Personne boris = new Personne(inscriptions,"Boris", "le Hachoir", "ytreza");
		assertTrue(enPersonne.remove(boris));
		assertFalse(enPersonne.remove(boris));
		assertFalse(boris.getCompetitions().contains(enPersonne));
 
	}

	@Test
	public void testDelete() {
		enPersonne.add(inscriptions.createPersonne("p4", "p4", "mail"));
	   
		
		enPersonne.delete();
		for(Personne p:inscriptions.getPersonnes()) {
			assertFalse(p.getCompetitions().contains(enPersonne));
		}
	}

}
