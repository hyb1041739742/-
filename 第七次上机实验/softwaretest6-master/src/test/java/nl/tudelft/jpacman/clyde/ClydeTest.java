package nl.tudelft.jpacman.clyde;

import nl.tudelft.jpacman.GhostMapParser;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.Clyde;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClydeTest {
    private Level level;
    private GhostMapParser ghostMapParser;
    private PlayerFactory playerFactory;
    private Player player;

    /**
     * 初始化一些测试所需要的对象
     * @throws IOException 读取文件错误时抛出的异常
     */
    @BeforeEach
    public void setUp (){
        PacManSprites sprites = new PacManSprites();
        LevelFactory levelFactory = new LevelFactory(
            sprites,
            new GhostFactory(sprites),
            mock(PointCalculator.class));
        ghostMapParser = new GhostMapParser(levelFactory, new BoardFactory(sprites), new GhostFactory(sprites));

        //获取游戏管理者对象
        //根据文件生成地图
        String[] map = {
            "############",
            "#P        C#",
            "############"
        };
        level = ghostMapParser.parseMap(Arrays.asList(map));
        playerFactory = new PlayerFactory(sprites);
        player = playerFactory.createPacMan();

        //游戏角色初始化朝北面
        player.setDirection(Direction.NORTH);
        level.registerPlayer(player);
    }

    private void reParser(String[] map) {
        level = ghostMapParser.parseMap(Arrays.asList(map));
        //游戏角色初始化朝北面
        player.setDirection(Direction.NORTH);
        level.registerPlayer(player);
    }



    /**
     * 拿到实例化后的 Clyde 对象
     */
    @Test
    public void test() {
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        System.out.println(clyde);
    }

    /**
     * 间隔等于8
     */
    @Test
    public void testEQ8() {
        String[] map = {
            "###########",
            "#P       C#",
            "###########"
        };
        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //预期行为时向东远离
        assert direction.isPresent() && direction.get().equals(Direction.EAST);
    }

    /**
     * 间隔小于8的情况
     */
    @Test
    public void testLT8() {
        String[] map = {
            "##########",
            "#P      C#",
            "##########"
        };
        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //预期行为时向东远离
        assert direction.isPresent() && direction.get().equals(Direction.EAST);
    }

    /**
     * 间隔大于8 的情况
     */
    @Test
    public void testGT8 () {
        String[] map = {
            "##############",
            "#P          C#",
            "##############"
        };

        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //预期行为时向西靠近
        assert direction.isPresent() && direction.get().equals(Direction.WEST);
    }

    /**
     * 不可到达的情况
     */
    @Test
    public void unAccessible() {
        String[] map = {
            "##############",
            "#P##########C#",
            "##############"
        };

        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //不可达，返回空的方向
        assert !direction.isPresent();
    }
    /**
     * 路线曲折大于8的情况
     */
    @Test
    public void routeNotLineGT8() {
        String[] map = {
            "##############",
            "#P  #  #  # C#",
            "##  #    ##  #",
            "##     #     #",
            "##  #    ##  #",
            "##     #     #",
            "##  #    ##  #",
            "##     #     #",
            "##############"
        };

        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //向南方向
        assert direction.isPresent() && direction.get().equals(Direction.SOUTH);
    }

    /**
     *路线曲折小于8
     */
    @Test
    public void routeLineLT8() {
        String[] map = {
            "##############",
            "#P  #  #  #  #",
            "##  #C   ##  #",
            "##     #     #",
            "##############"
        };

        reParser(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Optional<Direction> direction = clyde.nextAiMove();
        //向南方向的反方向即北方向
        assert direction.isPresent() && direction.get().equals(Direction.NORTH);
    }



}
