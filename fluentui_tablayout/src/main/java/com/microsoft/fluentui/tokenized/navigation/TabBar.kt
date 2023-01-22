package com.microsoft.fluentui.tokenized.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.tabItem.TabItem


data class TabData(
    var title: String,
    var icon: ImageVector,
    var selectedIcon: ImageVector = icon,
    var selected: Boolean = false,
    var onClick: () -> Unit,
    var badge: @Composable (() -> Unit)? = null
)

val LocalTabBarInfo = compositionLocalOf { TabBarInfo() }
val LocalTabBarToken = compositionLocalOf { TabBarTokens() }

@Composable
fun getTabBarInfo(): TabBarInfo {
    return LocalTabBarInfo.current
}

@Composable
fun getTabBarToken(): TabBarTokens {
    return LocalTabBarToken.current
}

@Composable
fun TabBar(
    tabDataList: List<TabData>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    tabTextAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    tabItemTokens: TabBarTabItemsTokens? = null,
    tabBarTokens: TabBarTokens? = null
) {
    val token = tabBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabBar] as TabBarTokens

    CompositionLocalProvider(
        LocalTabBarToken provides token
    ) {
        Column {
            Box(
                modifier
                    .fillMaxWidth()
                    .height(getTabBarToken().topBorderWidth(tabBarInfo = getTabBarInfo()))
                    .background(color = getTabBarToken().topBorderColor(tabBarInfo = getTabBarInfo()))
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                tabDataList.forEachIndexed { index, tabData ->
                    tabData.selected = index == selectedIndex
                    TabItem(
                        title = tabData.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        icon = if (tabData.selected) tabData.selectedIcon else tabData.icon,
                        textAlignment = tabTextAlignment,
                        selected = tabData.selected,
                        onClick = tabData.onClick,
                        accessory = tabData.badge,
                        tabItemTokens = tabItemTokens
                            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabBarTabItem] as TabItemTokens
                    )
                }
            }
        }
    }
}