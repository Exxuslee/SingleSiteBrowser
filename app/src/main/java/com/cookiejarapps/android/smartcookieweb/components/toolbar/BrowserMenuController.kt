package com.cookiejarapps.android.smartcookieweb.components.toolbar

import android.content.Intent
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.cookiejarapps.android.smartcookieweb.*
import com.cookiejarapps.android.smartcookieweb.browser.HomepageChoice
import com.cookiejarapps.android.smartcookieweb.ext.components
import com.cookiejarapps.android.smartcookieweb.preferences.UserPreferences
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.findCustomTabOrSelectedTab
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.EngineSession.LoadUrlFlags


interface BrowserToolbarMenuController {
    fun handleToolbarItemInteraction(item: ToolbarMenu.Item)
}

class DefaultBrowserToolbarMenuController(
    private val store: BrowserStore,
    private val activity: BrowserActivity,
    private val navController: NavController,
    private val findInPageLauncher: () -> Unit,
    private val browserAnimator: BrowserAnimator,
    private val customTabSessionId: String?
) : BrowserToolbarMenuController {

    private val currentSession
        get() = store.state.findCustomTabOrSelectedTab(customTabSessionId)

    @Suppress("ComplexMethod", "LongMethod")
    override fun handleToolbarItemInteraction(item: ToolbarMenu.Item) {
        val sessionUseCases = activity.components.sessionUseCases

        when (item) {
            is ToolbarMenu.Item.Back -> {
                currentSession?.let {
                    sessionUseCases.goBack.invoke(it.id)
                }
            }
            is ToolbarMenu.Item.Forward -> {
                currentSession?.let {
                    sessionUseCases.goForward.invoke(it.id)
                }
            }
            is ToolbarMenu.Item.Reload -> {
                val flags = if (item.bypassCache) {
                    LoadUrlFlags.select(LoadUrlFlags.BYPASS_CACHE)
                } else {
                    LoadUrlFlags.none()
                }

                currentSession?.let {
                    sessionUseCases.reload.invoke(it.id, flags = flags)
                }
            }
            is ToolbarMenu.Item.Stop -> currentSession?.let {
                sessionUseCases.stopLoading.invoke(
                    it.id
                )
            }
            is ToolbarMenu.Item.Settings -> {}
            is ToolbarMenu.Item.RequestDesktop -> {
                currentSession?.let {
                    sessionUseCases.requestDesktopSite.invoke(
                        item.isChecked,
                        it.id
                    )
                }
            }
            is ToolbarMenu.Item.Print -> {
                activity.printPage()
            }
            is ToolbarMenu.Item.PDF -> {
                activity.components.sessionUseCases.saveToPdf.invoke()
            }
            is ToolbarMenu.Item.AddToHomeScreen -> {
                MainScope().launch {
                    with(activity.components.webAppUseCases) {
                        addToHomescreen()
                    }
                }
            }
            is ToolbarMenu.Item.Share -> {
                MainScope().launch {
                    activity.components.store.state.selectedTab?.content?.let {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        if (it.title != "") {
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, it.title)
                        }
                        shareIntent.putExtra(Intent.EXTRA_TEXT, it.url)
                        ContextCompat.startActivity(
                            activity,
                            Intent.createChooser(
                                shareIntent,
                                activity.resources.getString(R.string.mozac_selection_context_menu_share)
                            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                            null
                        )
                    }
                }
            }

            is ToolbarMenu.Item.FindInPage -> {
                findInPageLauncher()
            }
            is ToolbarMenu.Item.OpenInApp -> {
                val appLinksUseCases = activity.components.appLinksUseCases
                val getRedirect = appLinksUseCases.appLinkRedirect

                currentSession?.let {
                    val redirect = getRedirect.invoke(it.content.url)
                    redirect.appIntent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    appLinksUseCases.openAppLink.invoke(redirect.appIntent)
                }
            }
            is ToolbarMenu.Item.Bookmarks -> browserAnimator.captureEngineViewAndDrawStatically {
                val drawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout)
                val bookmarksDrawer =
                    if (UserPreferences(activity).swapDrawers) activity.findViewById<FrameLayout>(
                        R.id.left_drawer
                    ) else activity.findViewById<FrameLayout>(R.id.right_drawer)

                if (bookmarksDrawer != null) {
                    drawerLayout?.openDrawer(bookmarksDrawer)
                }
            }
            is ToolbarMenu.Item.History -> {}
            is ToolbarMenu.Item.NewTab -> {
                activity.components.tabsUseCases.addTab.invoke(
                    if (UserPreferences(activity).homepageType == HomepageChoice.VIEW.ordinal) "about:homepage" else if (UserPreferences(
                            activity
                        ).homepageType == HomepageChoice.BLANK_PAGE.ordinal
                    ) "about:blank" else UserPreferences(activity).customHomepageUrl,
                    selectTab = true
                )
            }
            is ToolbarMenu.Item.NewPrivateTab -> {
                activity.components.tabsUseCases.addTab.invoke(
                    if (UserPreferences(activity).homepageType == HomepageChoice.VIEW.ordinal) "about:homepage" else if (UserPreferences(
                            activity
                        ).homepageType == HomepageChoice.BLANK_PAGE.ordinal
                    ) "about:blank" else UserPreferences(activity).customHomepageUrl,
                    selectTab = true, private = true
                )
            }
        }
    }
}
