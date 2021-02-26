package com.coq.cocoblio_test_kt


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.coq.cocoblio.divers.CoqActivity
import com.coq.cocoblio.divers.printdebug
import com.coq.cocoblio.divers.printwarning
import com.coq.cocoblio.graphs.Renderer
import com.coq.cocoblio.graphs.Texture
import com.coq.cocoblio.nodes.*
import kotlin.random.Random

class MainActivity
    : CoqActivity(R.style.ThemeOverlay_AppCompat, null, null
) {
    override fun getTheAppRoot(): AppRootBase {
        return AppRoot()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        renderer.initClearColor(0.5f, 0.8f, 1f)
    }
}

class AppRoot : AppRootBase() {
    init {
        changeActiveScreen(FirstScreen(this))
    }

    override fun willDrawFrame() {
    }
}

class FirstScreen(root: AppRoot) : ScreenBase(root) {
    init {
        TiledSurface(this, Texture.getPng(R.drawable.the_cat), 0f, 0f, 0.5f)
        TestButton(this, root) { it.changeActiveScreen(SecondScreen(root)) }
        addFramedString("Allo", R.drawable.frame_mocha, 0f, 0f, 0.3f)
        object : SliderButton(this@FirstScreen, 0f, true, 0f, 0f, 0.3f, 0.6f) {
            override fun action() {
                printdebug("Slider action!")
            }
        }
    }
}

class SecondScreen(root: AppRoot) : ScreenBase(root) {
    init {
        object : SwitchButton(this@SecondScreen, false, 0f, 0f, 0.3f) {
            override fun action() {
                printdebug("Switch action! $isOn")
            }
        }
        TiledSurface(this, Texture.getPng(R.drawable.some_animals), 0f, 0f, 0.5f)
        TestButton(this, root) { it.changeActiveScreen(FirstScreen(root)) }
    }
}

class TestButton : Button {
    private val root: AppRoot
    private val doAction: (AppRoot)->Unit

    constructor(parent: Node, root: AppRoot, doAction: (AppRoot)->Unit
    ) : super(parent, 0f, 0f, 0.5f)
    {
        this.root = root
        this.doAction = doAction
        TiledSurface(this, R.drawable.disks, 0f, 0f, 0.5f, 0f, Random.nextInt(0, 5))
    }
    override fun action() {
        doAction(root)
    }
}


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}