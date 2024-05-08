/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetnews.model

import androidx.annotation.DrawableRes
import com.example.jetnews.R
import com.example.jetnews.data.posts.Article
import java.util.UUID

data class Post(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val url: String,
    val publication: Publication? = null,
    val metadata: Metadata,
    val paragraphs: List<Paragraph> = emptyList(),
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int
)

data class Metadata(
    val author: PostAuthor,
    val date: String,
    val readTimeMinutes: Int
)

data class PostAuthor(
    val name: String,
    val url: String? = null
)

data class Publication(
    val name: String,
    val logoUrl: String
)

data class Paragraph(
    val type: ParagraphType,
    val text: String,
    val markups: List<Markup> = emptyList()
)

data class Markup(
    val type: MarkupType,
    val start: Int,
    val end: Int,
    val href: String? = null
)

enum class MarkupType {
    Link,
    Code,
    Italic,
    Bold,
}

enum class ParagraphType {
    Title,
    Caption,
    Header,
    Subhead,
    Text,
    CodeBlock,
    Quote,
    Bullet,
}

fun mapArticlesToPosts(articles: List<Article>): List<Post> {
    return articles.map { article ->
        Post(
            id = UUID.randomUUID().toString(), // Generate a unique ID
            title = article.title,
            subtitle = article.description,
            url = article.url,
            publication = Publication(
                name = article.source.name,
                logoUrl = article.urlToImage ?: "" // Handle null image URL
            ),
            metadata = Metadata(
                author = PostAuthor(
                    name = article.author ?: "Unknown", // Handle null author
                    url = null
                ),
                date = article.publishedAt,
                readTimeMinutes = calculateReadTime(article.content ?: "") // Estimate read time based on content
            ),
            paragraphs = listOf(
                Paragraph(
                    type = ParagraphType.Text,
                    text = article.content ?: "" // Handle null content
                )
            ),
            imageId = R.drawable.post_5,
            imageThumbId = R.drawable.post_5_thumb
        )
    }
}

fun calculateReadTime(text: String): Int {
    // Simple example: 1 minute for every 100 words
    return (text.split(" ").size / 100) + 1
}
