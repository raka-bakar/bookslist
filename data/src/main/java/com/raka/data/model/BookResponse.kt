package com.raka.data.model
import com.squareup.moshi.Json

/**
 * Data class to catch Api response of Book
 */
internal data class BookResponse(

	@Json(name="image")
	val image: String? = null,

	@Json(name="release_date")
	val releaseDate: String? = null,

	@Json(name="author")
	val author: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="titlee")
	val titlee: String? = null
)

