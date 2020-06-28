package app.athome.core.interfaces

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

abstract class BaseActivity<T : BaseNavigator>(
    private val layoutId: Int,
    private val navHostId: Int
) : AppCompatActivity(), NavigatorProvider {

    private lateinit var baseNavigator: BaseNavigator

    private val navController by lazy {
        findNavController(navHostId)
    }

    override fun provideNavigator(): BaseNavigator {
        return baseNavigator
    }

    protected abstract fun getClassNavigator(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        baseNavigator = getClassNavigator()
    }

    override fun onResume() {
        super.onResume()
        baseNavigator.bind(navController)
    }

    override fun onPause() {
        super.onPause()
        baseNavigator.unbind()
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}