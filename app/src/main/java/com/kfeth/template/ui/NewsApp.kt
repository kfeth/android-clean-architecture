package com.kfeth.template.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kfeth.template.ui.theme.AppTheme

@Composable
fun NewsApp() {
    AppTheme {
        Scaffold {
            NewsNavGraph()
        }
    }
}
