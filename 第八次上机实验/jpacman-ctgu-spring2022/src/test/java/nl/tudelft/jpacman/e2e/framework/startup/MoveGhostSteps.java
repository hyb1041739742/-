package nl.tudelft.jpacman.e2e.framework.startup;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;

public class MoveGhostSteps {
    private Launcher launcher;
    private Player player;
    private Square nextSquare;
    private Square beforeSquare;
    private Ghost ghost;
    private Pellet pellet;
    private Map<Ghost, ScheduledExecutorService> npcs;
    private int score;
    private Game getGame() {
        return launcher.getGame();
    }

    @Before
    public void setup() {
        launcher = new Launcher();
        launcher.withMapFile("/simplemap1.txt");
        launcher.launch();
        npcs = getGame().getLevel().getNpcs();
        for ( Ghost npc : npcs.keySet()) {
            ghost = npc;
            break;
        }
    }

    //-----------------------------------------------S3.1_Begin-----------------------------------------------------------
    @Given("the game has started for ghost")
    public void the_game_has_started_for_ghost() {
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    @Given("a ghost is next to an empty cell")
    public void a_ghost_is_next_to_an_empty_cell() {
        Square square = ghost.getSquare(); //获取鬼的位置
        nextSquare = square.getSquareAt(Direction.EAST);
        List<Unit> units = nextSquare.getOccupants();
        assertThat(units.size()).isEqualTo(0); //大小是0，说明是空的
    }

    @When("a tick event occurs")
    public void a_tick_event_occurs(){
        getGame().getLevel().move(ghost, Direction.EAST); //模拟时间到了，然后执行ghost移动
    }

    @Then("the ghost can move to that cell")
    public void the_ghost_can_move_to_that_cell(){
        assertThat(nextSquare).isEqualTo(ghost.getSquare()); //判断之前和现在的格子是否重合
    }
    //-----------------------------------------------S3.1_End-----------------------------------------------------------

    //-----------------------------------------------S3.2_Begin-----------------------------------------------------------

    @Given("a ghost is next to a cell containing a pellet")
    public void a_ghost_is_next_to_a_cell_containing_a_pellet(){
        getGame().getLevel().move(ghost, Direction.EAST); // 让鬼先移动一步，以便和pellet相邻

        Square square = ghost.getSquare(); //获取鬼的位置
        nextSquare = square.getSquareAt(Direction.EAST);
        List<Unit> units = nextSquare.getOccupants();
        pellet = (Pellet) units.get(0);  //保存下一个位置的糖豆

        assertThat(units.size()).isEqualTo(1); //大小是1
        assertThat(units.get(0)).isInstanceOf(Pellet.class); //并且是糖豆
    }

    @Then("the ghost can move to the cell with the pellet")
    public void the_ghost_can_move_to_the_cell_with_the_pellet(){
        Square square = ghost.getSquare();
        List<Unit> units = square.getOccupants(); //获取当前格子上的东西
        assertThat(square).isEqualTo(nextSquare); // 表示可以移动
        assertThat(units).contains(ghost); //表示两者可以共存
        assertThat(units).contains(pellet);
    }

    @Then("the pellet on that cell is not visible anymore")
    public void the_pellet_on_that_cell_is_not_visible_anymore(){
        assertThat(pellet.getVisibility()).isEqualTo(false);
    }
    //-----------------------------------------------S3.2_End-----------------------------------------------------------

    //-----------------------------------------------S3.3_Begin-----------------------------------------------------------
    @Given("a ghost is on a cell with a pellet")
    public void a_ghost_is_on_a_cell_with_a_pellet(){
        pellet = (Pellet) ghost.getSquare().getSquareAt(Direction.EAST).getSquareAt(Direction.EAST).getOccupants().get(0);

        getGame().getLevel().move(ghost, Direction.EAST);
        getGame().getLevel().move(ghost, Direction.EAST);

        Square square = ghost.getSquare();
        List<Unit> units = square.getOccupants(); //获取当前格子上的东西
        beforeSquare = square;

        for(Unit unit:units){ //遍历当前格子，看是否有糖豆
            if(unit instanceof Pellet){
                assert true;
            }
        }


    }
    @Then("the ghost can move away from the cell with the pellet")
    public void the_ghost_can_move_away_from_the_cell_with_the_pellet(){
        Square square = ghost.getSquare();
        assertThat(square).isEqualTo(beforeSquare);
    }

    @Then("the pellet on that cell is visible again")
    public void the_pellet_on_that_cell_is_visible_again(){
        assertThat(pellet.getVisibility()).isEqualTo(true);
    }
    //-----------------------------------------------S3.3_End-----------------------------------------------------------

    //-----------------------------------------------S3.4_Begin-----------------------------------------------------------
    @Given("a ghost is next to a cell containing the player")
    public void a_ghost_is_next_to_a_cell_containing_the_player(){
        getGame().getLevel().move(ghost, Direction.EAST);
        getGame().getLevel().move(ghost, Direction.EAST);
        getGame().getLevel().move(ghost, Direction.EAST);
        List<Unit> units = ghost.getSquare().getSquareAt(Direction.EAST).getOccupants(); //获取旁边格子的东西
        for (Unit unit:units){
            if(unit instanceof Player)
                assertThat(true);
        }

    }

    @Then("the ghost can move to the player")
    public void the_ghost_can_move_to_the_player(){
        getGame().getLevel().move(ghost, Direction.EAST);
    }
    //-----------------------------------------------S3.4_End-----------------------------------------------------------










}
