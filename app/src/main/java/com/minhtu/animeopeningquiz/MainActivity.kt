package com.minhtu.animeopeningquiz

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minhtu.animeopeningquiz.ui.theme.AnimeOpeningQuizTheme

class MainActivity : ComponentActivity() {
    companion object{
        var mediaPlayer : MediaPlayer? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeOpeningQuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

fun getRoute(): String{
    return Constant.HomeScreen
}
@Composable
fun MainApp(){
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    Log.e("ScreenSize", screenHeight.toString() + " "+ screenWidth)
    val isSmallPhone = (screenHeight * screenWidth) < 300000
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = getRoute() ){
        composable(getRoute()){
            HomeScreen{
                navController.navigate(PlayActivity.getRoute()) }
        }
        composable(PlayActivity.getRoute()){
            PlayActivity.PlayScreen(context, isSmallPhone)
        }
    }
}

@Composable
fun HomeScreen(onClickPlay : () -> Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.anime),
            contentScale = ContentScale.FillBounds
        ),
        verticalArrangement = Arrangement.Center) {
        Text(text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            color = Color.Red,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp
        )
        Spacer(Modifier.height(50.dp))
        Button(onClick = {
            onClickPlay()
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(100.dp)
                .size(60.dp)
        ) {
            Text(text = stringResource(id = R.string.play))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    AnimeOpeningQuizTheme {
        HomeScreen {

        }
    }
}