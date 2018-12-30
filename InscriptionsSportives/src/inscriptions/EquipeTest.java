package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class EquipeTest {
	private Inscriptions inscriptions;
	private Equipe lesManouches;

	@Before
	public void setUp() throws Exception {
		inscriptions = Inscriptions.getInscriptions();
		inscriptions.reinitialiser();
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty"),
				boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		// flechettes.add(tony);
		lesManouches = inscriptions.createEquipe("Les Manouches");
		lesManouches.add(boris);
		lesManouches.add(tony);

	}

	@Test
	public void addTest() {
		Personne simon = inscriptions.createPersonne("Simon", "Dent de plomb", "azerty");
		lesManouches.add(simon);
		assertEquals(3, lesManouches.getMembres().size());
		assertTrue(simon.getEquipes().contains(lesManouches));
	}

	@Test
	public void removeTest() {
		Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");
		lesManouches.remove(tony);
		assertEquals(1, lesManouches.getMembres().size());
		assertFalse(tony.getEquipes().contains(lesManouches));
	}

	@Test
	public void getPersonnesAAjouterTest() {
		Set<Personne> personnes = new HashSet<>();
		personnes.add(inscriptions.createPersonne("p1", "p1", "mail"));
		personnes.add(inscriptions.createPersonne("p2", "p2", "mail"));
		personnes.add(inscriptions.createPersonne("p3", "p3", "mail"));

		assertEquals(personnes, lesManouches.getPersonnesAAjouter());

	}

	@Test
	public void delete() {
		Competition flechettes = inscriptions.createCompetition("Mondial de fléchettes", LocalDate.now().plusDays(30),
				true);
		flechettes.add(lesManouches);

		lesManouches.delete();
		assertFalse(flechettes.getCandidats().contains(lesManouches));
		assertFalse(lesManouches.getCompetitions().contains(flechettes));
		assertFalse(inscriptions.getCandidats().contains(lesManouches));

	}

}
