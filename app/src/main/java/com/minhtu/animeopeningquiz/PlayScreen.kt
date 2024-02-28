package com.minhtu.animeopeningquiz

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.minhtu.animeopeningquiz.ui.theme.AnimeOpeningQuizTheme
import java.util.Random

class PlayActivity{
    companion object{
        private var sharedPreferences: SharedPreferences? = null
        private var musicList: ArrayList<String>? = null
        private var currentSong : Int? = null
        private var oldPoint: Int = 0
        private var listSongName: ArrayList<String>? = null
        private var currentId: Int? = null
        fun getRoute():String{
            return Constant.PlayScreen
        }

        @Composable
        fun PlayScreen(context: Context, isSmallPhone : Boolean){
            sharedPreferences = context.getSharedPreferences(Constant.Point, Context.MODE_PRIVATE)
            oldPoint = sharedPreferences!!.getInt(Constant.Point, 0)
            val list: Array<String> = context.resources.getStringArray(R.array.ListSong)
            musicList = java.util.ArrayList<String>(mutableListOf(*list))
            var point by remember {
                mutableIntStateOf(oldPoint)
            }
            var image by remember {
                mutableIntStateOf(R.drawable.pause_button)
            }
            var isNewSongCalled by remember {
                mutableStateOf(false)
            }
            var buttonText1 by remember {
                mutableStateOf(Constant.defaultStringValue)
            }
            var buttonText2 by remember {
                mutableStateOf(Constant.defaultStringValue)
            }
            var buttonText3 by remember {
                mutableStateOf(Constant.defaultStringValue)
            }
            var buttonText4 by remember {
                mutableStateOf(Constant.defaultStringValue)
            }
            OnLifecycleEvent { _, event ->
                // do stuff on event
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        onStop()
                        image = R.drawable.play_button
                    }
                    Lifecycle.Event.ON_START -> {
                        onStart(context)
                    }
                    else -> {}
                }
            }
            if(!isNewSongCalled){
                isNewSongCalled = true
                listSongName = newSong(context)
                buttonText1 = listSongName!![0]
                buttonText2 = listSongName!![1]
                buttonText3 = listSongName!![2]
                buttonText4 = listSongName!![3]
            }
            MainActivity.mediaPlayer!!.setOnCompletionListener {
                image = R.drawable.play_button
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                ),
                horizontalAlignment = Alignment.CenterHorizontally) {
                if(isSmallPhone) {
                    Text(text = stringResource(id = R.string.point) + " " + point,
                        modifier = Modifier
                            .padding(10.dp),
                        color = Color.Red,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 30.sp
                    )
                } else {
                    Text(text = stringResource(id = R.string.point) + " " + point,
                        modifier = Modifier
                            .padding(10.dp),
                        color = Color.Red,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 50.sp
                    )
                }
                Image(painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            image = if (!MainActivity.mediaPlayer!!.isPlaying) {
                                MainActivity.mediaPlayer!!.start()
                                R.drawable.pause_button
                            } else {
                                MainActivity.mediaPlayer!!.pause()
                                R.drawable.play_button
                            }
                        },
                    alignment = Alignment.Center)

                if(isSmallPhone){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)) {
                        GameButton(buttonText = buttonText1, padding = 30.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![0], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText2, padding = 30.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![1], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText3, padding = 30.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![2], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText4, padding = 30.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![3], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                    }
                } else {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp)) {
                        GameButton(buttonText = buttonText1, padding = 50.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![0], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText2, padding = 50.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![1], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText3, padding = 50.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![2], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                        GameButton(buttonText = buttonText4, padding = 50.dp) {
                            isNewSongCalled = false
                            image = R.drawable.pause_button
                            if(checkResult(listSongName!![3], context))
                            {
                                point += 10
                            } else{
                                point -= 10
                            }
                            savePoint(point)
                        }
                    }
                }
            }
        }

        @Composable
        fun GameButton(buttonText : String, padding : Dp, onButtonClick : () -> Unit ){
            Button(onClick = {
                onButtonClick()
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = padding)
                    .size(50.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, Color.DarkGray)
            ) {
                Text(text = buttonText, color = Color.White)
            }
        }

        private fun onStart(context: Context) {
            if(MainActivity.mediaPlayer != null){
                if(currentId != null && !MainActivity.mediaPlayer!!.isPlaying)
                {
                    MainActivity.mediaPlayer = MediaPlayer.create(context, currentId!!)
                }
            }
        }

        private fun onStop() {
            if(MainActivity.mediaPlayer != null){
                if(MainActivity.mediaPlayer!!.isPlaying) {
                    MainActivity.mediaPlayer!!.stop()
                }
            }
        }

        private fun checkResult(songName: String, context: Context) : Boolean {
            if (songName == musicList!![currentSong!!]) {
                Toast.makeText(
                    context,
                    Constant.textChooseRight,
                    Toast.LENGTH_SHORT
                ).show()
                MainActivity.mediaPlayer!!.stop()
                return true
            } else {
                Toast.makeText(
                    context,
                    Constant.textChooseWrong,
                    Toast.LENGTH_SHORT
                ).show()
                MainActivity.mediaPlayer!!.stop()
                return false
            }
        }

        @SuppressLint("DiscouragedApi")
        private fun newSong(context: Context) : ArrayList<String> {
            val listSongName = ArrayList<String>()
            val random = Random()
            currentSong = random.nextInt(musicList!!.size)
            var wrongSong1: Int
            var wrongSong2: Int
            var wrongSong3: Int
            do {
                wrongSong1 = random.nextInt(musicList!!.size)
            } while (wrongSong1 == currentSong)
            do {
                wrongSong2 = random.nextInt(musicList!!.size)
            } while (wrongSong2 == currentSong || wrongSong2 == wrongSong1)
            do {
                wrongSong3 = random.nextInt(musicList!!.size)
            } while (wrongSong3 == currentSong || wrongSong3 == wrongSong1 || wrongSong3 == wrongSong2)
            val pos: Int = random.nextInt(4)
            if (pos == 0) {
                listSongName.add(musicList!![currentSong!!])
                listSongName.add(musicList!![wrongSong1])
                listSongName.add(musicList!![wrongSong2])
                listSongName.add(musicList!![wrongSong3])
            }
            if (pos == 1) {
                listSongName.add(musicList!![wrongSong1])
                listSongName.add(musicList!![currentSong!!])
                listSongName.add(musicList!![wrongSong2])
                listSongName.add(musicList!![wrongSong3])
            }
            if (pos == 2) {
                listSongName.add(musicList!![wrongSong1])
                listSongName.add(musicList!![wrongSong2])
                listSongName.add(musicList!![currentSong!!])
                listSongName.add(musicList!![wrongSong3])
            }
            if (pos == 3) {
                listSongName.add(musicList!![wrongSong1])
                listSongName.add(musicList!![wrongSong2])
                listSongName.add(musicList!![wrongSong3])
                listSongName.add(musicList!![currentSong!!])
            }
            currentId = context.resources.getIdentifier(
                musicList!![currentSong!!],
                "raw",
                context.packageName
            )
            MainActivity.mediaPlayer = MediaPlayer.create(context, currentId!!)
            MainActivity.mediaPlayer!!.start()
            return listSongName
        }

        private fun savePoint(point: Int) {
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putInt("Point", point)
            editor.apply()
        }

        @Composable
        fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
            val eventHandler = rememberUpdatedState(onEvent)
            val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

            DisposableEffect(lifecycleOwner.value) {
                val lifecycle = lifecycleOwner.value.lifecycle
                val observer = LifecycleEventObserver { owner, event ->
                    eventHandler.value(owner, event)
                }

                lifecycle.addObserver(observer)
                onDispose {
                    lifecycle.removeObserver(observer)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayPreview() {
    AnimeOpeningQuizTheme {

    }
}