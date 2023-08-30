import chessGame.gameEnum.Colors;
import chessGame.gameEnum.Characters;
import chessGame.exceptions.*;
import chessGame.characters.*;
import chessGame.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TestSchach {

    private Chess getSpieler() {
        return new ChessImpl();
    }

    // ===============================================================================
    // ======= Initialising Tests
    @Test
    //Jeder Spieler bekommt die ausgewählte Farbe
    public void TestStartGame1() throws SetException, StatusException, IOException {
        Chess testObj = this.getSpieler();

        ArrayList<Figur> figurenBlack = testObj.start("player1", Colors.BLACK);
        ArrayList<Figur> figurenWhite = testObj.start("player2",Colors.WHITE);

        Assert.assertEquals(Colors.BLACK, figurenBlack.get(0).getColor());
        Assert.assertEquals(Colors.WHITE, figurenWhite.get(0).getColor());
    }

    @Test
    //Der zweite Spieler bekommt die übrige Farbe
    public void TestStartGame2() throws SetException, StatusException, IOException {
        Chess testObj = this.getSpieler();

        ArrayList<Figur> figurenBlack = testObj.start("player1",Colors.BLACK);
        ArrayList<Figur> figurenWhite = testObj.start("player2",Colors.BLACK);

        Assert.assertEquals(Colors.BLACK, figurenBlack.get(0).getColor());
        Assert.assertEquals(Colors.WHITE, figurenWhite.get(0).getColor());
    }

    // ===============================================================================
    // ======= Figuren Tests: es wird jeweils erlaubt und unerlaubte Bewegung getestet
    @Test
    public void TestBauer() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();

        testObj.start("Player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(0,1);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(2,2);

        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.BAUER, 0, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.BAUER, 3, erlaubteBewegung));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.BAUER, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.BAUER, 1, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }
    }

    @Test
    public void TestTurm() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(0,3);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(2,2);

        // Erstmal müssen die Bauer bewegt werden sonst ist die Bahn nicht leer >> PathException
        testObj.move(Colors.WHITE, Characters.BAUER,0,new CharacterPosition(1,1));
        testObj.move(Colors.BLACK, Characters.BAUER,7,new CharacterPosition(-1,1));
        // Türme
        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.TURM, 1, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.TURM, 2, erlaubteBewegung));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.TURM, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.BLACK, Characters.TURM, 2, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }

    }

    @Test
    public void TestSpringer() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(1,3);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(2,2);

        // Erstmal müssen die Bauer bewegt werden sonst ist die Bahn nicht leer >> PathException
        testObj.move(Colors.WHITE, Characters.BAUER,1,new CharacterPosition(1,1));
        testObj.move(Colors.BLACK, Characters.BAUER,6,new CharacterPosition(-1,1));
        // Springer
        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.SPRINGER, 1, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.SPRINGER, 2, erlaubteBewegung));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.SPRINGER, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.BLACK, Characters.SPRINGER, 2, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }
    }

    @Test
    public void TestLaefer() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(2,2);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(1,2);

        // Erstmal müssen die Bauer bewegt werden sonst ist die Bahn nicht leer >> PathException
        testObj.move(Colors.WHITE, Characters.BAUER,3,new CharacterPosition(-1,1));
        testObj.move(Colors.BLACK, Characters.BAUER,6,new CharacterPosition(-1,1));
        // Laefer
        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.LAEFER, 1, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.LAEFER, 2, erlaubteBewegung));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.LAEFER, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.BLACK, Characters.LAEFER, 2, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }

    }

    @Test
    public void TestDame() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(0,4);
        CharacterPosition erlaubteBewegung2 = new CharacterPosition(-3,3);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(1,2);

        // Erstmal müssen die Bauer bewegt werden sonst ist die Bahn nicht leer >> PathException
        testObj.move(Colors.WHITE, Characters.BAUER,3,new CharacterPosition(1,1));
        testObj.move(Colors.BLACK, Characters.BAUER,2,new CharacterPosition(0,1));
        // Damen
        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.DAME, 1, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.DAME, 1, erlaubteBewegung2));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.DAME, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.BLACK, Characters.DAME, 1, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }
    }

    @Test
    public void TestKoenig() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(0,1);
        CharacterPosition erlaubteBewegung2 = new CharacterPosition(1,1);
        CharacterPosition unerlaubteBewegung = new CharacterPosition(3,2);

        // Erstmal müssen die Bauer bewegt werden sonst ist die Bahn nicht leer >> PathException
        testObj.move(Colors.WHITE, Characters.BAUER,4,new CharacterPosition(0,1));
        testObj.move(Colors.BLACK, Characters.BAUER,5,new CharacterPosition(0,1));
        // Koenigen
        Assert.assertTrue(testObj.move(Colors.WHITE, Characters.KOENIG, 1, erlaubteBewegung));
        Assert.assertTrue(testObj.move(Colors.BLACK, Characters.KOENIG, 1, erlaubteBewegung2));

        try {
            Assert.assertTrue(testObj.move(Colors.WHITE, Characters.KOENIG, 1, unerlaubteBewegung));
            Assert.assertTrue(testObj.move(Colors.BLACK, Characters.KOENIG, 1, unerlaubteBewegung));
        } catch (CharacterException e) {
            System.out.print(e);
        }
    }


    // ===============================================================================
    // ======= Exceptions Tests (hier wurden die Exceptions expected gemacht)
    @Test (expected = chessGame.exceptions.PathException.class)
    public void PathExceptionTest() throws SetException, OutLineException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(2,2);
        Assert.assertFalse(testObj.move(Colors.WHITE, Characters.LAEFER, 1, erlaubteBewegung));
    }

    @Test  (expected = chessGame.exceptions.OutLineException.class)
    public void OutLineExceptionTest() throws SetException, StatusException, OutLineException, PathException, CharacterException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition erlaubteBewegung = new CharacterPosition(5,9);

        Assert.assertFalse(testObj.move(Colors.WHITE, Characters.LAEFER, 1, erlaubteBewegung));
    }

    @Test  (expected = chessGame.exceptions.StatusException.class)
    public void StatusException_3Spieler() throws SetException, OutLineException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);
        testObj.start("player3",Colors.WHITE);
    }

    @Test  (expected = chessGame.exceptions.StatusException.class)
    public void StatusException_FarbeNichtDran() throws SetException, OutLineException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        testObj.move(Colors.BLACK, Characters.BAUER,4,new CharacterPosition(0,1));
        testObj.move(Colors.WHITE, Characters.BAUER,5,new CharacterPosition(0,1));
    }

    // ===============================================================================
    // ======= Test Game Over : ein kleines Spiel wurde komplett getestet
    @Test
    public void TestGameOver() throws OutLineException, SetException, CharacterException, StatusException, PathException, IOException {
        Chess testObj = this.getSpieler();
        testObj.start("player1",Colors.BLACK);
        testObj.start("player2",Colors.WHITE);

        CharacterPosition bewegung1 = new CharacterPosition(1,1);
        CharacterPosition bewegung2 = new CharacterPosition(0,1);
        CharacterPosition bewegung3 = new CharacterPosition(0,3);
        CharacterPosition bewegung4 = new CharacterPosition(4,0);
        CharacterPosition bewegung5 = new CharacterPosition(0,4);

        testObj.move(Colors.WHITE, Characters.BAUER,0,bewegung1);
        testObj.move(Colors.BLACK, Characters.BAUER,2,bewegung2);
        testObj.move(Colors.WHITE, Characters.TURM,1,bewegung3);
        testObj.move(Colors.BLACK, Characters.BAUER,3,bewegung1);
        testObj.move(Colors.WHITE, Characters.TURM,1,bewegung4);
        testObj.move(Colors.BLACK, Characters.BAUER,5,bewegung1);
        testObj.move(Colors.WHITE, Characters.TURM,1,bewegung5);
    }
}

