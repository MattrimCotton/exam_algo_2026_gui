import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void lancerDe_retourneEntreBornes() {
        int[] des = {4, 6, 8, 10, 12, 20};
        for (int faces : des) {
            for (int i = 0; i < 100; i++) {
                int r = App.lancerDe(faces);
                assertTrue(r >= 1 && r <= faces,
                    "d" + faces + " hors bornes : " + r);
            }
        }
    }

    @Test
    void lancerDe_facesInvalides_retourneMinusUn() {
        assertEquals(-1, App.lancerDe(0));
        assertEquals(-1, App.lancerDe(-1));
        assertEquals(-1, App.lancerDe(-100));
    }
}
