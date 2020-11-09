package com.gayathri.jokesbook

import com.gayathri.jokesbook.data.model.Jokes
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.io.InputStream

class DataModelTest {

    @Test
    fun `Given response is ok, Jokes are parsed correctly`() {
        val file = File(javaClass.getResource("/raw/jokes.json")!!.path)
        val sampleJson: InputStream = file.inputStream()
        val jsonBody = "{ \"jokes\": ${sampleJson.bufferedReader().use { it.readText() }} }"

        // serializing objects
        val jsonData = Json.parse(Jokes.serializer(), jsonBody)
        Assert.assertNotNull("Jokes model should not be null", jsonData)
    }
}