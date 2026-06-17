import model.DiceRoller;
import model.RollResult;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void lancer_retourneEntreBornes() {
        for (int faces : DiceRoller.FACES_DISPONIBLES) {
            for (int i = 0; i < 100; i++) {
                RollResult r = DiceRoller.lancer(faces, 1, 0);
                assertTrue(r.total() >= 1 && r.total() <= faces,
                    "d" + faces + " hors bornes : " + r.total());
            }
        }
    }

    @Test
    void lancer_bonusAppliqueAuTotal() {
        for (int i = 0; i < 100; i++) {
            RollResult r = DiceRoller.lancer(6, 1, 10);
            assertTrue(r.total() >= 11 && r.total() <= 16,
                "total avec bonus hors bornes : " + r.total());
        }
    }

    @Test
    void lancer_malusRendTotalNegatif() {
        for (int i = 0; i < 100; i++) {
            RollResult r = DiceRoller.lancer(4, 1, -10);
            assertTrue(r.total() < 0,
                "total devrait être négatif : " + r.total());
        }
    }
}
