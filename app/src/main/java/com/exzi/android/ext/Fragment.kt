package com.exzi.android.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.exzi.android.components.Components

/**
 * Get the components of this application.
 */
val Fragment.components: Components
    get() = context!!.components

fun Fragment.nav(@IdRes id: Int?, directions: NavDirections, options: NavOptions? = null) {
    NavHostFragment.findNavController(this).nav(id, directions, options)
}