package com.experiment.rickandmorty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.experiment.rickandmorty.R
import com.experiment.rickandmorty.data.model.CharactersModel


@Composable
fun IndividualCharacter(item: CharactersModel) {
    val context = LocalContext.current
    val fileName = item.image.replace("https://rickandmortyapi.com/api/character/avatar/", "")
    val filePath = context.filesDir.path + "/" + fileName

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context).data(filePath).build(),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.name, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.status, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = item.species,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun IndividualCharacterPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.rick),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Title", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Title", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Title", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}