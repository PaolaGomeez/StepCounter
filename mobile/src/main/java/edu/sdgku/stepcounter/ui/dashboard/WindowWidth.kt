package edu.sdgku.stepcounter.ui.dashboard

import androidx.window.core.layout.WindowSizeClass

fun WindowSizeClass.isWide(): Boolean =
    isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
fun WindowSizeClass.label(): String = when {
    isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> "Expanded"
    isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> "Medium"
    else -> "Compact"
}
