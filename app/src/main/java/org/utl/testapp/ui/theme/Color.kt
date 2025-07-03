package org.utl.testapp.ui.theme

import androidx.compose.ui.graphics.Color

// Colores de tu diseño
val PrimaryDesignColor = Color(0xFF03556A)     // Azul verdoso oscuro (el color principal de tu UI)
val BackgroundDesignColor = Color(0xFFFFFBF5)  // Blanco roto/crema (el color de fondo principal en claro)
val SecondaryDesignColor = Color(0xFF036C74)   // Azul verdoso medio (para acentos y elementos secundarios)
val SurfaceDesignColor = Color(0xFFF6F8F9)     // Gris azulado muy claro (para superficies de componentes como tarjetas)

// Colores para Tema Claro
val Light_Primary = PrimaryDesignColor
val Light_OnPrimary = Color.White
val Light_PrimaryContainer = SecondaryDesignColor // Contenedores basados en el secundario
val Light_OnPrimaryContainer = Color.White

val Light_Secondary = SecondaryDesignColor
val Light_OnSecondary = Color.White
val Light_SecondaryContainer = PrimaryDesignColor.copy(alpha = 0.8f) // Contenedores secundarios basados en el primario
val Light_OnSecondaryContainer = Color.White

val Light_Tertiary = Color(0xFF90CAF9)
val Light_OnTertiary = Color.Black
val Light_TertiaryContainer = Color(0xFFE3F2FD)
val Light_OnTertiaryContainer = Color.Black

val Light_Background = BackgroundDesignColor
val Light_OnBackground = Color.Black
val Light_Surface = SurfaceDesignColor
val Light_OnSurface = Color.Black
val Light_SurfaceVariant = SurfaceDesignColor.copy(alpha = 0.8f)
val Light_OnSurfaceVariant = Color(0xFF4A4A4A)

val Light_Outline = PrimaryDesignColor.copy(alpha = 0.5f)
val Light_Error = Color(0xFFB00020)
val Light_OnError = Color.White

// Colores para Tema Oscuro
val Dark_Primary = SecondaryDesignColor // Un tono más claro que el primario, ideal para oscuro
val Dark_OnPrimary = Color.White
val Dark_PrimaryContainer = PrimaryDesignColor
val Dark_OnPrimaryContainer = Color.White

val Dark_Secondary = PrimaryDesignColor.copy(alpha = 0.7f)
val Dark_OnSecondary = Color.White
val Dark_SecondaryContainer = SecondaryDesignColor.copy(alpha = 0.5f)
val Dark_OnSecondaryContainer = Color.White

val Dark_Tertiary = Color(0xFFBBDEFB)
val Dark_OnTertiary = Color.Black
val Dark_TertiaryContainer = Color(0xFF90CAF9)
val Dark_OnTertiaryContainer = Color.Black

val Dark_Background = Color(0xFF1C1C1C)
val Dark_OnBackground = Color.White
val Dark_Surface = Color(0xFF2E2E2E)
val Dark_OnSurface = Color.White
val Dark_SurfaceVariant = Color(0xFF424242)
val Dark_OnSurfaceVariant = Color(0xFFD0D0D0)

val Dark_Outline = Color(0xFF616161)
val Dark_Error = Color(0xFFCF6679)
val Dark_OnError = Color.Black

// Colores heredados del template
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)