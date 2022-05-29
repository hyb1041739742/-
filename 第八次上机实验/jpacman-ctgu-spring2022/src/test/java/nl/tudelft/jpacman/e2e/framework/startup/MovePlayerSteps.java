package nl.tudelft.jpacman.e2e.framework.startup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;

public class MovePlayerSteps {
	private Launcher launcher;
	private Player player;
	private Square nextSquare;
	private Square beforeSquare;
	private Ghost ghost;
	private Pellet pellet;
	private int score;
	private Game getGame() {
		return launcher.getGame();
	}
	
	@Before
	public void setup() {
		launcher = new Launcher();
        launcher.withMapFile("/simplemap.txt");
		launcher.launch();
	}
	
	@Given("the game has started")
	public void the_game_has_started() {
		getGame().start();
		assertThat(getGame().isInProgress()).isTrue();
	}

	@Given("my Pacman is next to a square containing a pellet")
	public void my_Pacman_is_next_to_a_square_containing_a_pellet() {
		List<Player> players = getGame().getPlayers();
		player = players.get(0);
		
		//存储原始分数
		score  = player.getScore();
		
		Square square = player.getSquare();
		
		//取豆子占的方块
		nextSquare = square.getSquareAt(Direction.EAST);
        beforeSquare = square;
		List<Unit> units = nextSquare.getOccupants();
		pellet = (Pellet)units.get(0);
		
		//断言
		assertThat(units.size()).isEqualTo(1);
		assertThat(units.get(0)).isInstanceOf(Pellet.class);
	}
	

	@When("I press an arrow key towards that square")
	public void i_press_an_arrow_key_towards_that_square() {
	    getGame().move(player, Direction.EAST);
	}

	@Then("my Pacman can move to that square")
	public void my_Pacman_can_move_to_that_square() {
		assertThat(player.getSquare()).isEqualTo(nextSquare);
	}

	@Then("I earn the points for the pellet")
	public void i_earn_the_points_for_the_pellet() {
		assertThat(score).isEqualTo(0);
		assertThat(player.getScore()).isEqualTo(score+pellet.getValue());
	}

	@Then("the pellet disappears from that square")
	public void the_pellet_disappears_from_that_square() {
	   assertThat(nextSquare.getOccupants()).containsExactly(player).doesNotContain(pellet);
	}
	
	@After
	public void teardown() {
		launcher.dispose();
	}



    //-----------------------------------------------S2.2_Begin---------------------------------------------------------
    @Given("my Pacman is next to an empty square")
    public void my_Pacman_is_next_to_an_empty_square() {
        List<Player> players = getGame().getPlayers();
        player = players.get(0);

        getGame().move(player, Direction.EAST);
        score  = player.getScore(); //存分數
        Square square = player.getSquare(); //獲取player所在方格
        nextSquare = square.getSquareAt(Direction.EAST); //獲取下一個移動方向的格子
        beforeSquare = square;
        List<Unit> units = nextSquare.getOccupants();
        assertThat(units.size()).isEqualTo(0); //如果是0，説明這個地方是空的
    }

    @Then("my points remain the same")
    public void my_points_remain_the_same(){
        assertThat(player.getScore()).isEqualTo(score); //玩家现在分数应该和之前保留的分数是一样的
    }
    //-----------------------------------------------S2.2_End-----------------------------------------------------------

    //-----------------------------------------------S2.3_Begin---------------------------------------------------------
    @And("my Pacman is next to a cell containing a wall")
    public void my_Pacman_is_next_to_a_cell_containing_a_wall(){
        List<Player> players = getGame().getPlayers();
        player = players.get(0);
        getGame().move(player, Direction.EAST);
        getGame().move(player, Direction.EAST);//移动两次到墙旁边
        Square square = player.getSquare();
        nextSquare = square.getSquareAt(Direction.EAST);//取豆子占的方块

        assertThat(nextSquare.isAccessibleTo(player)).isEqualTo(false); //如果是个墙，那么对于player来说是不可达的
    }


    @When("I press an arrow key towards that cell")
    public void i_press_an_arrow_key_towards_that_cell() {
        getGame().move(player, Direction.EAST);
    }

    @Then("the move is not conducted")
    public void the_move_is_not_conducted(){
	    assertThat(player.getSquare()).isNotEqualTo(nextSquare); //目前位置仍然和之前的目标不同
    }

    //-----------------------------------------------S2.3_End-----------------------------------------------------------
    //-----------------------------------------------S2.4_Begin---------------------------------------------------------
    @Given("my Pacman is next to a cell containing a ghost")
    public void my_Pacman_is_next_to_a_cell_containing_a_ghost(){
        List<Player> players = getGame().getPlayers();
        player = players.get(0);
        getGame().move(player, Direction.WEST);

        score  = player.getScore();//存储原始分数
        Square square = player.getSquare();

        //取豆子占的方块
        nextSquare = square.getSquareAt(Direction.WEST);
        beforeSquare = square;
        List<Unit> units = nextSquare.getOccupants();
        ghost = (Ghost)units.get(0);

        assertThat(units.size()).isEqualTo(1);
        assertThat(units.get(0)).isInstanceOf(Ghost.class); //判断左边这个方格是不是鬼
    }

    @When("I press a left arrow key towards that square")
    public void I_press_a_left_arrow_key_towards_that_square(){
        List<Player> players = getGame().getPlayers();
        player = players.get(0);
        getGame().move(player, Direction.WEST);
        getGame().move(player, Direction.WEST);//移动两次到墙旁边
        Square square = player.getSquare();
        nextSquare = square.getSquareAt(Direction.WEST);//取豆子占的方块

    }


    @Then("my Pacman dies")
    public void my_Pacman_dies(){
        assertThat(player.isAlive()).isFalse();
    }

    @Then("the game is over")
    public void the_game_is_over(){
        assertThat(getGame().isInProgress()).isFalse();
        assertThat(getGame().getLevel().isInProgress()).isFalse();
    }
    //-----------------------------------------------S2.4_End-----------------------------------------------------------
    //-----------------------------------------------S2.5_Begin---------------------------------------------------------
    @When("I have eaten the last pellet")
    public void I_have_eaten_the_last_pellet(){
        List<Player> players = getGame().getPlayers();
        player = players.get(0);
        getGame().move(player, Direction.WEST);//向左移动一次，吃第一个豆子
        getGame().move(player, Direction.EAST);
        getGame().move(player, Direction.EAST);//想右移动两次，吃第二个豆子
    }

    @Then("I win the game")
    public void I_win_the_game(){
	    assertThat(player.isAlive()).isTrue();
	    assertThat(getGame().isInProgress()).isFalse(); //如果游戏结束，同时玩家活着说明游戏胜利
    }
    //-----------------------------------------------S2.5_End-----------------------------------------------------------
}
