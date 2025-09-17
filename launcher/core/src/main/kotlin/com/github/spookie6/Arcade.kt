import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.viewport.FitViewport
import com.github.spookie6.GameLoader
import com.github.spookie6.InputHandler
import com.github.spookie6.ProcessRunner
import com.github.spookie6.scenes.StartScene

class Arcade : Game() {
    lateinit var batch: SpriteBatch
    lateinit var backgroundTexture: Texture
    lateinit var camera: OrthographicCamera
    lateinit var viewport: FitViewport
    lateinit var shader: ShaderProgram

    lateinit var font: BitmapFont

    val VIRTUAL_WIDTH = 320f
    val VIRTUAL_HEIGHT = 180f

    val gameLoader = GameLoader()
    val inputHandler = InputHandler(this)
    val processRunner = ProcessRunner(this)

    override fun create() {
        batch = SpriteBatch()

        backgroundTexture = Texture("images/arcade-background.png")
        backgroundTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)

        font = BitmapFont()

        camera = OrthographicCamera()
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT)
        viewport = FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera)

        gameLoader.loadGames()
        this.screen = StartScene(this)

        val vert = Gdx.files.internal("shaders/default.vert").readString()
        val frag = Gdx.files.internal("shaders/scanline.frag").readString()
        shader = ShaderProgram(vert, frag)

        if (!shader.isCompiled) {
            Gdx.app.error("Shader", shader.log)
        }
    }

    override fun render() {
        inputHandler.handleInputs()

        if (processRunner.process != null) return
        if (screen is StartScene) {
            batch.shader = shader
            shader.setUniformf("u_resolution", VIRTUAL_WIDTH, VIRTUAL_HEIGHT)
        }
        super.render()
        batch.shader = null
    }

    override fun dispose() {
        batch.dispose()
        screen?.dispose()
        backgroundTexture.dispose()
    }
}
