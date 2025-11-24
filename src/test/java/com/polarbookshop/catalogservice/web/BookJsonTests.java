package com.polarbookshop.catalogservice.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.Book;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        // Arrange
        var book = new Book("1234567890", "Title", "Author", 9.90);

        // Act
        var jsonContent = json.write(book);

        // Assert
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        // Arrange
        var content = """
                { "isbn": "1234567890", "title": "Title", "author": "Author", "price": 9.90 }""";
        var book = new Book("1234567890", "Title", "Author", 9.90);

        // Act
        var result = json.parse(content);

        // Assert
        assertThat(result).usingRecursiveComparison().isEqualTo(book);
    }
}
