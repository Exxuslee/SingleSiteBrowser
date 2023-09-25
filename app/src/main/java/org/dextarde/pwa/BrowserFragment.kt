package org.dextarde.pwa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.dextarde.pwa.browser.ToolbarGestureHandler
import org.dextarde.pwa.ext.components
import kotlinx.coroutines.ExperimentalCoroutinesApi
import mozilla.components.browser.state.state.SessionState
import mozilla.components.feature.tabs.WindowFeature
import mozilla.components.support.base.feature.UserInteractionHandler
import mozilla.components.support.base.feature.ViewBoundFeatureWrapper
import org.dextrade.pwa.R

/**
 * Fragment used for browsing the web within the main app.
 */
@ExperimentalCoroutinesApi
@Suppress("TooManyFunctions", "LargeClass")
class BrowserFragment : BaseBrowserFragment(), UserInteractionHandler {

    private val windowFeature = ViewBoundFeatureWrapper<WindowFeature>()

    @Suppress("LongMethod")
    override fun initializeUI(view: View, tab: SessionState) {
        super.initializeUI(view, tab)

        val context = requireContext()
        val components = context.components

        binding.gestureLayout.addGestureListener(
            ToolbarGestureHandler(
                activity = requireActivity(),
                contentLayout = binding.browserLayout,
                store = components.store,
                selectTabUseCase = components.tabsUseCases.selectTab
            )
        )

        windowFeature.set(
            feature = WindowFeature(
                store = components.store,
                tabsUseCases = components.tabsUseCases
            ),
            owner = this,
            view = view
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (requireActivity() as BrowserActivity).openToBrowserAndLoad(
            searchTermOrURL = getString(R.string.url_main)
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}